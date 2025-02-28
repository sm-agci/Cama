package tapis.cama.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tapis.cama.api.exceptions.BadCredentialsException;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private static final String API_KEY_HEADER = "X-API-KEY";
    @Value("${api.token}")
    private String authToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader(API_KEY_HEADER);

        if (apiKey == null || !apiKey.equals(authToken)) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
//            return false;
            throw new BadCredentialsException("Invalid API Key");
        }

        return true;
    }
}