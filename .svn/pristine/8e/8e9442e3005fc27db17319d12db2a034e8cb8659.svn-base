package com.titan.irgs.security.configuration;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.titan.irgs.user.model.UserPrinciple;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/*
 * @author hari.k
 */

@Component
public class JwtProvider {

	    @Value("${jwt.secret}")
	    private String jwtSecret;
	 
	    @Value("${jwt.expiration}")
	    private Long jwtExpiration;
	    
	    
		private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	 
	    public String generateJwtToken(Authentication authentication) {
	    	
	    
	        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
	 
	        return Jwts.builder()
	                    .setSubject((userPrincipal.getUsername()))
	                    .setIssuedAt(new Date())
	                    .setExpiration(new Date(System.currentTimeMillis() + 36000 * 1000))
	                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
	                    .compact();
	    }
	 
	    public String getUserNameFromJwtToken(String token) {
	        return Jwts.parser()
	                      .setSigningKey(jwtSecret)
	                      .parseClaimsJws(token)
	                      .getBody().getSubject();
	    }
	 
	    public boolean validateJwtToken(String authToken)  {
	    	System.out.println("token validating");
	        try {
	            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
	            return true;
	        } catch (SignatureException e) {
	            logger.error("Invalid JWT signature -> Message: {} ", e);
	            throw e;

	        } catch (MalformedJwtException e) {
	            logger.error("Invalid JWT token -> Message: {}", e);
	        } catch (ExpiredJwtException e) {
	            logger.error("Expired JWT token -> Message: {}", e);
	            throw e;

	        } catch (UnsupportedJwtException e) {
	            logger.error("Unsupported JWT token -> Message: {}", e);
	        } catch (IllegalArgumentException e) {
	            logger.error("JWT claims string is empty -> Message: {}", e);
	        }
	        
	        return false;
	    }
}
