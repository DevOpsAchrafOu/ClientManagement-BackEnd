/**
 * 
 */
package ma.mang.be.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Achraf
 *
 */

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenResourceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ForbiddenResourceException(String message){
        super(message);
    }
}
