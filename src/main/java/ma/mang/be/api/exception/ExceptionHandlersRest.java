/**
 * 
 */
package ma.mang.be.api.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import com.auth0.jwt.exceptions.TokenExpiredException;

import ma.mang.be.api.utils.Utils;

/**
 * Global Exception handler to customize thrown exceptions that should be returned to the client.<br>
 * The list of handled exceptions :<br>
 * <ul>
 * <b><li>NotFoundElementException</b> returns 204 code</li>
 * <b><li>MethodArgumentNotValidException</b> returns 400 code</li>
 * <b><li>AuthenticationException</b> returns 401 code</li>
 * <b><li>ForbiddenResourceException</b> returns 403 code</li>
 * <b><li>ResourceNotFoundException</b> returns 404 code</li>
 * <b><li>ConflictException</b> returns 409 code</li>
 * <b><li>Exception</b> returns 500 code</li>
 * </ul>
 * @author Achraf
 *
 */
@ControllerAdvice
public class ExceptionHandlersRest extends ResponseEntityExceptionHandler{
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
         ExceptionModel errorDetails = new ExceptionModel(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), ex.getMessage(), request.getDescription(false));
         return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ForbiddenResourceException.class)
    public ResponseEntity<?> forbiddenExcpetionHandler(ForbiddenResourceException ex, WebRequest request) {
        ExceptionModel errorDetails = new ExceptionModel(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
        
    }
    
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> conflictExceptionHandler(ConflictException ex, WebRequest request) {
        ExceptionModel errorDetails = new ExceptionModel(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
        
    }
    
    @ExceptionHandler(NotFoundElementException.class)
    public @ResponseBody ResponseEntity<?> notFoundElementExceptionHandler(NotFoundElementException ex, WebRequest request) {
        ExceptionModel errorDetails = new ExceptionModel(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), ex.getMessage(), request.getDescription(true));
        return new ResponseEntity<>(errorDetails, HttpStatus.NO_CONTENT);
    }
    
    @Override
    protected @ResponseBody ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ExceptionModel error = new ExceptionModel(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    
    
    @ExceptionHandler(AuthenticationException.class)
   	public ResponseEntity<?> notEqualsPasswordExceptionHandler(AuthenticationException ex, WebRequest request) {
    	ExceptionModel errorDetails = new ExceptionModel(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), ex.getMessage(), request.getDescription(false));
   		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
   	}
    
    
    @ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ExceptionModel errorDetails = new ExceptionModel(Utils.dateToString(new Date(),Utils.DD_MM_YYYY_HH_MM_SS_PATTERN_2), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}

   
}