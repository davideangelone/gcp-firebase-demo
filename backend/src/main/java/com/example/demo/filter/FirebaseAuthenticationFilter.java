package com.example.demo.filter;

import com.example.demo.security.FirebasePrincipal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

  private final BearerTokenResolver tokenResolver = new DefaultBearerTokenResolver();

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
          throws ServletException, IOException {

    String token = tokenResolver.resolve(request);
    if (null == token) {
      throw new AuthenticationException("Token not found");
    }

    try {
      FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
      Principal principal = new FirebasePrincipal(decodedToken.getEmail());
      UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(principal, token, List.of());

      SecurityContextHolder.getContext().setAuthentication(userAuth);
    } catch (FirebaseAuthException e) {
      throw new AuthenticationException("Token is not valid", e);
    }

    filterChain.doFilter(request, response);
  }
}
