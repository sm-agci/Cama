package cama.geofencing.interceptors;//package cama.otp.mock.controller.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
@Component
public class XCorrelatorInterceptor implements HandlerInterceptor {
    private static final String LOGGER_UNIQUE_ID_PLACEHOLDER = "uniqueId";
    private static final String HEADER_UNIQUE_ID = "x-correlator";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String uniqueId = request.getHeader(HEADER_UNIQUE_ID);
        ThreadContext.put(LOGGER_UNIQUE_ID_PLACEHOLDER, uniqueId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}

