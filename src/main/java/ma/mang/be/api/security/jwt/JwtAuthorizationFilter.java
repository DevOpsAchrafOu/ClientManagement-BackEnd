package ma.mang.be.api.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.exception.ErrorDetailsDto;
import ma.mang.be.api.service.AuthService;
import ma.mang.be.api.service.UtilisateurService;
import ma.mang.be.api.utils.Utils;

/**
 * Filter managing authorization for a given user successfully authenticated
 * @author achraf
 * @version v0.1
 */

//exécuter pour chaque requéte 
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private AuthService authService;
	private UtilisateurService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		response.addHeader("Access-Control-Allow-Origin","*");//"*"accepter tout les requet de domaine(4200;4201;.....8080) de aplication de front end (anglar, App mobile, androi ...)
		response.addHeader("Access-Control-Allow-Methods", "GET, PUT, POST, PATCH, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
        
        //lors envoyé front end requéte avec méthode "OPTIONS" ca signofier qu'il veut de connaitre à partir de BackEnd(SC) c'est quoi origin et headers autorisées 
        if(request.getMethod().equals("OPTIONS")) {
        	//si la méthode est de type OPTIONS la réponse est OK
        	response.setStatus(HttpServletResponse.SC_OK);
        	return;
        }
        
		// Read the Authorization header, where the JWT token should be
		String header = request.getHeader(JwtProperties.HEADER_STRING);
		
		// If header does not contain BEARER or is null delegate to Spring impl and exit
		if(header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
			// Continue filter execution()
			chain.doFilter(request, response);
			return;
		}else {//If header does contain BEARER with Token
			try {
				//get token 
				String token = header.substring(JwtProperties.TOKEN_PREFIX.length());
				// parse the token and validate it
				DecodedJWT decodedJWT= JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
						             .build()//JWTVerifier
						             .verify(token);//DecodedJWT
						             
				
				//cette token est diviser 3 partir HEADER.PAYLOAD.VERIFYSIGNATURE
				//1ére partir de token contient Algorithm de cryptage HS512
				//2éme parir contient les informatios username(subject) et role(authority)
				String username = decodedJWT.getSubject();
				//get les roles de ce user à partir token
				String[] roles = decodedJWT.getClaim("role").asString().split(",");
				Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				for(String r:roles) {
						authorities.add(new SimpleGrantedAuthority(r));
				}
				
				//Check token validity
				//TODO : Add start activation date
				//TODO : Use a refresh service to 
				boolean isValidToken = checkTokenValidity(request, response, token, username);
				if (isValidToken) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					
					//Check account is valid
					boolean isProfileToDate = checkProfileUpdate(request, response, username);

					if (!isProfileToDate) {
						return;
					}

				}else {
					return;
				}
							
				// Continue filter execution
				chain.doFilter(request, response);	
			}catch (Exception e) {
				response.setHeader("error-message", e.getMessage());
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			
		}
	}

	/**
	 * Checks If Profile is to-date. If Not a response will be sent to the client
	 * mentioning that the request is accepted but cannot be completed.<br>
	 * The client,by receiving this code, should be able to redirect the user through the
	 * update profile screen.
	 * There are some exceptions in this process : This the case of <b>/logout and /profiles/*</b> request to permit the user the modification of his profile or to make a logout
	 * @param request the request of the client
	 * @param response the response of the server
	 * @param username login of the concerned account
	 * @return True if the profile it to-date, False else.
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	private boolean checkProfileUpdate(HttpServletRequest request, HttpServletResponse response, String username)
			throws IOException, JsonProcessingException {
		
		//Don't block if the user try to logout or manage his profile
		if(request.getRequestURI().contains("logout") || request.getRequestURI().contains("profiles") ) return true;
		
		//Get service Utilisateur (si service Utilisateur is null, then get service Utilisateur by liste de Bean stoker dans webApplicationContext )
		if(userService==null){
		    ServletContext servletContext = request.getServletContext();
		    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    userService = webApplicationContext.getBean(UtilisateurService.class);
		}
		Utilisateur acc = userService.findByLogin(username);
		if(acc!=null && Utilisateur.PENDING_STATE.equals(acc.getState())) {
			response.resetBuffer();
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			response.getOutputStream()
					.print(new ObjectMapper().writeValueAsString(new ErrorDetailsDto(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), "Profile edition is incomplete and/or your phone number is not confirmed. Please consider updating your profile and/or confirming your phone number.", HttpStatus.ACCEPTED.toString())));
			response.setHeader("error-message", "Profile edition is incomplete. Please consider updating your profile.");
			response.flushBuffer(); 
			return false;
		}
		
		return true;
	}

	private boolean checkTokenValidity(HttpServletRequest request, HttpServletResponse response, String token,
			String username) throws IOException, JsonProcessingException {
		
		//Get service auth (si service auth is null, then get service auth by liste de Bean stoker dans webApplicationContext )
		if(authService==null){
		    ServletContext servletContext = request.getServletContext();
		    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    authService = webApplicationContext.getBean(AuthService.class);
		}
		String storedToken = authService.getTokenByUserLogin(username);
		
		//is token valide with storedToken => true sinon false(msg error)
		if (!token.equals(storedToken)) {
			response.resetBuffer();
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			response.getOutputStream()
					.print(new ObjectMapper().writeValueAsString(new ErrorDetailsDto(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), "Forbidden resource. Try to reconnect.", HttpStatus.FORBIDDEN.toString())));
			response.setHeader("error-message", "Forbidden resource. Try to reconnect.");
			response.flushBuffer(); 
			return false;
		}
		return true;
	}

}
