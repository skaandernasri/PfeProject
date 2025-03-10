package tn.temporise.application.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import tn.temporise.application.exception.BadRequestException;
import tn.temporise.application.exception.LogoutException;
import tn.temporise.application.exception.UnauthorizedException;
@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
                SecurityContextHolder.clearContext();
                log.info("---------------------Context cleared");
                // Get the JWT token from the cookie
                Cookie[] cookies = request.getCookies();
                String token = null;

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("jwt".equals(cookie.getName())) { // Replace "jwt" with the name of your cookie
                            token = cookie.getValue();
                            break;
                        }
                    }
                }

                if (token != null && !token.isEmpty()) {
                    log.info("JWT Token found in cookie: " + token);

                    Cookie jwtCookie = new Cookie("jwt", null);
                    jwtCookie.setHttpOnly(true);
                    jwtCookie.setSecure(true);
                    jwtCookie.setPath("/");
                    jwtCookie.setMaxAge(0);
                    response.addCookie(jwtCookie);
                    log.info("still cookie? "+jwtCookie.getName() );
                } else {
                    log.error("JWT Token not found in cookies");
                    throw new BadRequestException("JWT token not found in cookies", "4000");
                }

                log.info("User logged out successfully: ");
            } else {
                throw new UnauthorizedException("User is not authenticated", "401");
            }
        } catch (Exception e) {
            log.error("Error during logout: ", e);
            throw new LogoutException(e.getMessage(), "5000");
        }
    }

}