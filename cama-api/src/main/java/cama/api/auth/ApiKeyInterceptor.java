package cama.api.auth;

import cama.api.exceptions.BadCredentialsException;
import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class ApiKeyInterceptor implements HandlerInterceptor {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private final AuthConfig authConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader(API_KEY_HEADER);

        if (StringUtil.isNullOrEmpty(apiKey) || !authConfig.isEnabled()) {
            return true;
        }
        if (!apiKey.trim().equals(authConfig.getApiToken())) {
            throw new BadCredentialsException("Invalid API Key");
        }

        return true;
    }
}