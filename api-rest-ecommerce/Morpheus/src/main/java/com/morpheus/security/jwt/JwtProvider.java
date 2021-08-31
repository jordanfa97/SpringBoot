package com.morpheus.security.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.morpheus.security.model.JwtToken;
import com.morpheus.security.model.PrincipalUser;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {

	private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;
	private final String TOKEN_TYPE = "JWT";

	public String generateToken(Authentication auth) {

		PrincipalUser principalUser = (PrincipalUser) auth.getPrincipal();
		List<String> roles = principalUser.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return Jwts.builder()
				.setSubject(principalUser.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", TOKEN_TYPE)
				.claim("roles", roles)
				.compact();
	}

	public String getUsernameToken(String token) {
		return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token)
				.getBody().getSubject();
	}

	public boolean validateToken(String token) {

		try {
			Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("token mal formado");
		} catch (UnsupportedJwtException e) {
			logger.error("token no soportado");
		} catch (ExpiredJwtException e) {
			logger.error("token expirado");
		} catch (IllegalArgumentException e) {
			logger.error("token vacío");
		} catch (SecurityException e) {
			logger.error("fail en la firma");
		}
		return false;

	}

	public String refreshToken(JwtToken jwtToken) throws ParseException {
		JWT jwt = JWTParser.parse(jwtToken.getToken());
		JWTClaimsSet claims = jwt.getJWTClaimsSet();
		String username = claims.getSubject();
		List<String> roles = (List<String>)claims.getClaim("roles");

		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", TOKEN_TYPE)
				.claim("roles", roles)
				.compact();
	}

}
