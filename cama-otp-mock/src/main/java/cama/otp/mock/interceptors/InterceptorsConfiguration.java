package cama.otp.mock.interceptors;//package cama.otp.mock.controller.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorsConfiguration implements WebMvcConfigurer {
    private final XCorrelatorInterceptor xCorrelatorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(xCorrelatorInterceptor);
    }
}

