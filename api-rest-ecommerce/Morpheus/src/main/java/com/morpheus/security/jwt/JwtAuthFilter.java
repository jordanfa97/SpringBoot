package com.morpheus.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.morpheus.security.service.UserDetailsServiceImpl;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final static Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			String token = getToken(request);

			if (token != null && jwtProvider.validateToken(token)) {
				String username = jwtProvider.getUsernameToken(token);
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

				UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(upat);
			}

		} catch (Exception e) {
			logger.error("fail en el método doFilter " + e.getMessage());
		}

		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header != null && header.startsWith("Bearer"))
			return header.replace("Bearer ", "");
		return null;
	}
}
