package ternant.config;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import ternant.errorHandling.ApiResponse;
import ternant.errorHandling.InvalidJwtException;
import ternant.errorHandling.JwtTokenIsNotAvailable;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    public static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        return request.getServletPath().startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.getTokenFromCookie(request);

        logger.debug("Extracted token: {} ", token);

        try{
            if(token != null){

                if(jwtUtil.validateToken(token)){
                    String email = jwtUtil.getUsername(token);

                    UserDetails userDetailsImpl = userDetailsService.loadUserByUsername(email);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetailsImpl,null,userDetailsImpl.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                else {
                    throw new InvalidJwtException("Invalid Jwt token");
                }
            }
            else {
                throw new JwtTokenIsNotAvailable("Jwt Token is null");
            }
        } catch (Exception e) {

            logger.error("Jwt Authentication failed : {}",e.getMessage());

        }
        filterChain.doFilter(request,response);
    }
}
