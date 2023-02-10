/**
 * 
 */
package ma.mang.be.api.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.mang.be.api.dto.PasswordDto;
import ma.mang.be.api.dto.ResponseDto;
import ma.mang.be.api.dto.UtilisateurDto;
import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.exception.ConflictException;
import ma.mang.be.api.exception.NotEqualsException;
import ma.mang.be.api.exception.NotFoundElementException;
import ma.mang.be.api.security.jwt.JwtProperties;
import ma.mang.be.api.service.UtilisateurService;

/**
 * REST Interfaces to manipulate Utilisateur objects
 * @author achraf
 * @version v0.1
 *
 */
@RestController
@RequestMapping("/api/mng/v1")
@Api(tags = { "02 - Utilisateurs : REST Interfaces to manipulate Utilisateur objects" })
public class UtilisateurRest {
	
	private static final String MSG_USER_CREATED = "User created successfully";
	private static final String MSG_LOGIN_ALREADY_USED = "Login already used by another user!";
	private static final String MSG_INSCRIPTION_FAILED = "Inscription failed because an internal error. Please try later!";
	private static final String MSG_USER_UPDATED = "User successfully ";
	private static final String MSG_PWD_UPDATED = "Password successfully changed ";

	@Autowired
	private UtilisateurService userService;
	
	@RequestMapping(value="/signup",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(notes = "Inscription et création d'un compte", value = "", response = String.class)
	public void createUser(@RequestBody UtilisateurDto user,HttpServletResponse response) throws Exception, ConflictException {
		String result =  userService.createAccount(UtilisateurDto.to(user));
			switch (result) {
			case "KO":
				throw new Exception(MSG_INSCRIPTION_FAILED);
			case "EXIST":
				throw new ConflictException(MSG_LOGIN_ALREADY_USED);
			default:
				Utilisateur acc = userService.findByLogin(user.getLogin());
				response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
				response.setStatus(HttpServletResponse.SC_CREATED);
				response.addHeader(JwtProperties.HEADER_STRING, result);
				response.getOutputStream().print(new ObjectMapper().writeValueAsString(
						new ResponseDto(MSG_USER_CREATED, HttpStatus.CREATED.toString(), "/v1/utilisateurs/"+acc.getId(),acc.getId())));
				response.flushBuffer();
			}
	}


	@GetMapping("/utilisateurs")
	@ApiOperation(notes = "Retrieves all users (Note : Used for debug and test purposes)", value = "", response = UtilisateurDto.class)
	public List<UtilisateurDto> getAllUsers() {
		return UtilisateurDto.to(userService.getAllUsers());
	}

	@GetMapping("/utilisateurs/{id}")
	@ApiOperation(notes = "Retrieves user by ID (Note : Used for debug and test purposes)", value = "", response = UtilisateurDto.class)
	public @ResponseBody ResponseEntity<?> getUserById(@PathVariable(value = "id") Long userId) throws NotFoundElementException  {
		Utilisateur user = userService.getUserById(userId);
		if (user == null) {
			throw new NotFoundElementException("User not found for this id :: " + userId);
		}
		return ResponseEntity.ok(UtilisateurDto.from(user));
	}

	@GetMapping("/utilisateurs/me")
	@ApiOperation(notes = "Retrieves connected user data", value = "", response = UtilisateurDto.class)
	public @ResponseBody ResponseEntity<?> getConnectedUser() throws NotFoundElementException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login =(String) auth.getPrincipal();
		Utilisateur user = userService.findByLogin(login);
		if (user == null) {
			throw new NotFoundElementException("User not found for this login :: " + login);
		}
		return ResponseEntity.ok(UtilisateurDto.from(user));
	}


	@PutMapping("/utilisateurs/{id}")
	@ApiOperation(notes = "Updates an user identified by ID (Note : Used for debug and test purposes)", value = "", response = Utilisateur.class)
	public ResponseEntity<UtilisateurDto> updateUser(@PathVariable(value = "id") Long userId, @RequestBody UtilisateurDto userDetails)
			throws NotFoundElementException {
		Utilisateur user = userService.getUserById(userId);
		if(user==null) {
			throw new NotFoundElementException("User not found for this id :: " + userId);
		}
		userDetails.setId(userId);
		final Utilisateur updatedUser = userService.update(userDetails);
		return ResponseEntity.ok(UtilisateurDto.from(updatedUser));
	}

	@DeleteMapping("/utilisateurs/{id}")
	@ApiOperation(notes = "Deletes an user identified by ID from database (Note : Used for debug and test purposes)", value = "", response = Utilisateur.class)
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws NotFoundElementException {
		Utilisateur user = userService.getUserById(userId);
		if(user==null) {
			throw new NotFoundElementException("User not found for this id :: " + userId);
		}
		userService.delete(userId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@PutMapping("/utilisateurs")
	@ApiOperation(notes = "Changes the status of the connected user", value = "", response = String.class)
	@Deprecated
	public ResponseEntity<?> setAccountState(@RequestParam(value = "status") String status)
			throws Exception, NotFoundElementException {
		Map<String, String> response = new HashMap<>();
		String message = userService.setAccountState(status);
		if("OK".equals(message)) {
			response.put("message", MSG_USER_UPDATED + status);
			return ResponseEntity.ok(response);
		}
		return ResponseEntity.ok(message);
	}
	
	@PutMapping("/utilisateurs/passwords")
	@ApiOperation(notes = "Changes the password of the connected user", value = "", response = String.class)
	public ResponseEntity<?> setPassword(@RequestBody PasswordDto pwd) throws Exception, NotFoundElementException,NotEqualsException {
		Map<String, String> response = new HashMap<>();
		String token = userService.setPassword(pwd.getOldPwd(), pwd.getNewPwd());
		response.put("message",MSG_PWD_UPDATED );
		response.put("token", token );
		return ResponseEntity.ok(response);
	}
}
