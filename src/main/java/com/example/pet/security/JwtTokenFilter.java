package com.example.pet.security;

import com.example.pet.exception.JwtAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtTokenFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);

    try {
      if (Objects.nonNull(token) && jwtTokenProvider.isTokenValid(token)) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        if (Objects.nonNull(authentication)) {
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (JwtAuthenticationException e) {
      throw new JwtAuthenticationException(e.getMessage(), e.getHttpStatus());
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
