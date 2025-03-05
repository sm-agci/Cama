package cama.api.webclient;

import cama.api.exceptions.ExternalSystemException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.net.SocketException;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebClientRetryFactory {
    public @NotNull RetryBackoffSpec retrySpec() {
        return Retry.backoff(3, Duration.ofMillis(500))
                .filter(throwable -> isTimeoutError(throwable) || isResponseError(throwable) || isSocketException(throwable))
                .doBeforeRetry(this::logRetryAttempt)
                .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> throwExternalSystemException(retrySignal));
    }

    private boolean isTimeoutError(Throwable e) {
        return e instanceof WebClientRequestException;
    }

    private boolean isResponseError(Throwable e) {
        return e instanceof WebClientResponseException webException && webException.getStatusCode().is5xxServerError();
    }

    private boolean isSocketException(Throwable throwable) {
        return throwable instanceof SocketException;
    }

    private void logRetryAttempt(Retry.RetrySignal retrySignal) {
        log.info("Retry {} after {}", retrySignal.totalRetries() + 1,
                Optional.ofNullable(retrySignal.failure().getMessage())
                        .orElse(retrySignal.failure().getClass().getName()));
    }

    private @NotNull ExternalSystemException throwExternalSystemException(Retry.RetrySignal retrySignal) {
        return new ExternalSystemException("Retry exhausted after 3 attempts.", retrySignal.failure());
    }

}
