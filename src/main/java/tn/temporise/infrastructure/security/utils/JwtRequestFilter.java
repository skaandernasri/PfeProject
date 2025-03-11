package tn.temporise.infrastructure.security.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.temporise.application.exception.UnauthorizedException;
import tn.temporise.application.service.CustomUserDetailsService;
import tn.temporise.domain.model.CustomUserDetails;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private final CustomUserDetailsService userDetailsService;
    @Autowired
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            log.debug("before getJwtFromAuthorizationHeader");
            String jwt = getJwtFromAuthorizationHeader(request);
            String email = null;

            // If JWT is not in Authorization header, check cookies
            if (jwt == null) {
                jwt = getJwtFromCookies(request);
                logger.debug("JWT not found in Authorization header. Checking cookies...");
            }

            // If no JWT is found, return 401 Unauthorized
            if (jwt == null) {
                logger.debug("No JWT token found in request.");
                throw new UnauthorizedException("Unauthorized: No JWT token found");
            }

            // Validate the JWT token
            email = jwtUtil.extractEmail(jwt);
            logger.debug("Extracted email from JWT: {},"+ email);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                CustomUserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    logger.debug("Authenticated user with email: {},"+ email);
                } else {
                    // If the token is invalid, remove the JWT cookie
                    removeJwtCookie(response);
                    throw new UnauthorizedException("Unauthorized: Invalid JWT token");
                }
            }

            // Continue the filter chain
            chain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
        }
    }
    private String getJwtFromAuthorizationHeader(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private String getJwtFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        // Add public endpoints here
        log.debug("Checking if request should be filtered: " + requestURI);

        boolean shouldNotFilter = requestURI.startsWith("/v1/auth/signin") ||
                requestURI.startsWith("/v1/auth/signup") ||
                requestURI.startsWith("/oauth2/") ||
                requestURI.startsWith("/swagger-ui/") ||
                requestURI.startsWith("/v3/api-docs") ||
                requestURI.startsWith("/swagger-resources/") ||
                requestURI.startsWith("/swagger-ui/index.html");
        logger.debug("Should not filter: " + shouldNotFilter);
        return shouldNotFilter;

    }
    public void setJwtCookie(HttpServletResponse response, String jwtToken, int maxAge) {
        Cookie jwtCookie = new Cookie("jwt", jwtToken);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Set to true if using HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(maxAge); // Set cookie expiration time in seconds
        response.addCookie(jwtCookie);
    }
    public void removeJwtCookie(HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Set to true if using HTTPS
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0); // Expire the cookie immediately
        response.addCookie(jwtCookie);
    }

}