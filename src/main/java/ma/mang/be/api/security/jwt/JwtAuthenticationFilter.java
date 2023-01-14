package ma.mang.be.api.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ma.mang.be.api.exception.ErrorDetailsDto;
import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.security.model.AppUser;
import ma.mang.be.api.service.AuthService;
import ma.mang.be.api.service.UtilisateurService;
import ma.mang.be.api.utils.Utils;

/**
 * Filter performing Authentication login/password based
 * @author achraf
 * @version v0.1
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthService authService;
	private UtilisateurService collabService;

	@Autowired
	private AuthenticationManager authenticationManager;
	

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	// cette methode sera executer une fois l'utilisateur tente de d'authentifier.
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		JwtLogin jwtLogin = null;
		
		try {
			// faire mapper d'object récupérer et lire la valeur des Inputs(Login) qui en
			// forme Json et de type Employeur
			jwtLogin = new ObjectMapper().readValue(request.getInputStream(), JwtLogin.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		//Get service Utilisateur (si service Utilisateur is null, then get service Utilisateur by liste de Bean stoker dans webApplicationContext )
		if(collabService==null){
		    ServletContext servletContext = request.getServletContext();
		    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    collabService = webApplicationContext.getBean(UtilisateurService.class);
		}
		// get Utilisateur by login and verifier : s'il y au mois à une rôle pour Utilisateur
		Utilisateur usr = collabService.findByLogin(jwtLogin.getUsername());
		if (usr != null) {
			GrantedAuthority authority = new SimpleGrantedAuthority(
					usr.getRole() != null ? usr.getRole().getCode() : "ROLE_USER");
			authorities.add(authority);
		}
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(jwtLogin.getUsername(), jwtLogin.getPassword(),authorities);
		
		// returner object (Authenticate user) contient le username et password sinon return message error
		Authentication authentication =null;
		try {
			authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken) ;
			
		}catch(AuthenticationException e) {
			try {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
				
					response.getOutputStream()
							.print(new ObjectMapper().writeValueAsString(new ErrorDetailsDto(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), "Bad credentials", HttpStatus.UNAUTHORIZED.toString())));
					response.setHeader("error-message", "Bad credentials");
					response.flushBuffer();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		 

		return authentication;
	}
	
	
	// si la 1ére fct returne "Authentication" ,on va créer une token dans le 2éme fct et stoker pour collaborateur connecté
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,Authentication authResult) throws IOException, ServletException {
		AppUser appUser = (AppUser) authResult.getPrincipal();
		System.out.println("login : "+appUser.getUser().getLogin());
		// Create JWT Token
		String token = JWT.create()
		   .withSubject(appUser.getUser().getLogin())
		   .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
		   .withClaim("role", appUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
		   .sign(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()));
		
		//Get service Authorities (si service Utilisateur is null, then get service Utilisateur by liste de Bean stoker dans webApplicationContext )
		if(authService==null){ 
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            authService = webApplicationContext.getBean(AuthService.class);
        }
		//Update token dans database pour collaborateur connecté
		authService.storeTokenForUserLogin(appUser.getUser().getLogin(), token);
		response.addHeader(JwtProperties.HEADER_STRING,token);
		
		//Check if the profile is to date By Email
		boolean isProfileToDate = checkProfileUpdate(request, response, appUser.getUser().getEmail());
		if (!isProfileToDate) {
			return;
		}
		response.getOutputStream()
		.print("message : Successful authentication");
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
		if(collabService==null){
		    ServletContext servletContext = request.getServletContext();
		    WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		    collabService = webApplicationContext.getBean(UtilisateurService.class);
		}
		Utilisateur acc = collabService.findByLogin(username);
		if(acc!=null && Utilisateur.PENDING_STATE.equals(acc.getState())) {
			response.resetBuffer();
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			response.getOutputStream()
					.print(new ObjectMapper().writeValueAsString(new ErrorDetailsDto(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), "Pending account. Please contact your administrator to activate it", HttpStatus.ACCEPTED.toString())));
			response.setHeader("error-message", "Profile edition is incomplete. Please consider updating your profile.");
			response.flushBuffer(); 
			return false;
		}
		
		return true;
	}
}
