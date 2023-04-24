/**
 * 
 */
package ma.mang.be.api.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.exceptions.TokenExpiredException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.mang.be.api.exception.ExceptionDetailsDto;
import ma.mang.be.api.service.AuthService;
import ma.mang.be.api.utils.Utils;

/**
 * REST Interfaces to consume free services
 * @author achraf
 * @version v0.1
 */
@RestController
@RequestMapping("/api/mng/v1/public")
@Api(tags = { "00 - Public : REST Interfaces to consume free services" })
public class PublicRest {
	
	private static final String MSG_LOUGOUT = "Lougout successfully!";
	private static final String MSG_REQUEST_EXPIRED = "Please retry to ask your password reset. Your previously request has been expired";
	private static final String MSG_INTERNAL_ERROR_LOGOUT = "Internal Error. Please try to logout later or contact your admin!";

	@Value("${app.version}")
	private String appVersion;

	@Autowired
	private AuthService authService;

	
	@RequestMapping(value="/about",method = RequestMethod.GET)
	@ApiOperation(notes = "About App message : REST API for OverMap App", value = "", response = String.class)
	public @ResponseBody String test() {
		return "REST API for Client Management App [DEV] - Version : " + appVersion;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)  
	@ApiOperation(notes = "Logout service", value = "", response = String.class)
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String login =(String) auth.getPrincipal();
			authService.storeTokenForUserLogin(login, null);
		}catch(Exception e) {
			throw new Exception(MSG_INTERNAL_ERROR_LOGOUT);
		}
		return ResponseEntity.ok(MSG_LOUGOUT);  
     }  
	
	@RequestMapping(value="/signup/validate/{validationToken}", method=RequestMethod.GET)  
	@ApiOperation(notes = "Validates email provided by the new user ", value = "", response = String.class)
    public ResponseEntity<?>  validateMail(@PathVariable String validationToken)  throws TokenExpiredException ,Exception{
		try {
			return ResponseEntity.ok("validated :"+ authService.validateEmail(validationToken));
		}catch(TokenExpiredException e) {
			return new ResponseEntity<>(new ExceptionDetailsDto(Utils.dateToString(new Date(), "dd/MM/yyyy hh:mm:ss"), MSG_REQUEST_EXPIRED, e.getMessage()),HttpStatus.ACCEPTED);
		}
     }
	

}
