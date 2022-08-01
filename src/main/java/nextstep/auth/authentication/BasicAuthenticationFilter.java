package nextstep.auth.authentication;

import nextstep.auth.context.Authentication;
import nextstep.auth.context.SecurityContextHolder;
import nextstep.auth.userdetails.UserDetails;
import nextstep.auth.userdetails.UserDetailsService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BasicAuthenticationFilter implements HandlerInterceptor {
    private UserDetailsService userDetailsService;

    public BasicAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String authCredentials = AuthorizationExtractor.extract(request, AuthorizationType.BASIC);
            String authHeader = new String(Base64.decodeBase64(authCredentials));

            String[] splits = authHeader.split(":");
            String principal = splits[0];
            String credentials = splits[1];

            AuthenticationToken token = new AuthenticationToken(principal, credentials);

            UserDetails userDetails = userDetailsService.loadUserByUsername(token.getPrincipal());
            if (userDetails == null) {
                throw new AuthenticationException();
            }

            if (!userDetails.isValidCredentials(token.getCredentials())) {
                throw new AuthenticationException();
            }

            Authentication authentication = new Authentication(userDetails.getPrincipal(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return true;
        } catch (Exception e) {
            return true;
        }
    }
}
