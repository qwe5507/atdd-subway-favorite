package nextstep.auth.authentication.filter;

import static nextstep.auth.authentication.execption.InvalidTokenException.INVALID_TOKEN;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nextstep.auth.authentication.AuthenticationManager;
import nextstep.auth.authentication.execption.InvalidTokenException;
import nextstep.auth.authentication.handler.AuthenticationSuccessHandler;
import nextstep.auth.context.Authentication;
import nextstep.auth.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public abstract class AbstractAuthenticationFilter implements HandlerInterceptor {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public AbstractAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationManager = authenticationManager;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        try {
            Authentication authentication = attemptAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            return true;
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException(INVALID_TOKEN);
        } catch (Exception e) {
            return true;
        }
    }

    protected abstract Authentication convert(HttpServletRequest request) throws IOException;

    protected Authentication attemptAuthentication(HttpServletRequest request) throws IOException {
        Authentication token = convert(request);

        return authenticationManager.authenticate(token);
    }
}