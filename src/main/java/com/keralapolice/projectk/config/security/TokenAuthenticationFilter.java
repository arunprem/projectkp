package com.keralapolice.projectk.config.security;

import com.keralapolice.projectk.login.service.CustomUserDetailsService;
import com.keralapolice.projectk.login.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String jwt = getTokenFromRequest(request);
            if(StringUtils.hasText(jwt)&&jwtTokenProvider.validateToken(jwt)){
               Long userId = jwtTokenProvider.getUserIdFromJwt(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null, Collections.emptyList()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            logger.error("Could not set user authentication in security context",e);
        }

        filterChain.doFilter(request,response);

    }

    private String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if(StringUtils.hasText(token)&&token.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return token.substring(7,token.length());
        }else{
            return null;
        }
    }
}
