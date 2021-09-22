package com.nt.filter;

import java.io.IOException;
import java.util.Arrays;

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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nt.configuration.MyUserDetialsService;
import com.nt.controller.HomeController;
import com.nt.utility.JwtUtil;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private MyUserDetialsService userServiceDetails;
	@Autowired
	private JwtUtil jwtUtil;
	Logger logger=LoggerFactory.getLogger(JwtRequestFilter.class);
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.trace("JwtRequestFilter.doFilterInternal()=");
		
		final String authorizationHeader=request.getHeader("Authorization");
		System.out.println(authorizationHeader);
		String username=null;
		String jwt=null;
		if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")) {
			logger.trace("Authorization header is present ");
			jwt=authorizationHeader.substring(7);
			username=jwtUtil.extractUserName(jwt);
		}
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = userServiceDetails.loadUserByUsername(username);
			logger.trace("User is present");
			if(jwtUtil.validateToken(jwt, userDetails)) {
				logger.trace("Token is valid");
				UsernamePasswordAuthenticationToken authenticationToken=new 
					UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			else {
				logger.warn("Invalid token");
			}
		}
		filterChain.doFilter(request, response);
		
	}
}
