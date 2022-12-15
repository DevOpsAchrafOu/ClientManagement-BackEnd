package ma.mang.be.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Achraf
 *
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends GlobalException{

	private static final long serialVersionUID = 1L;

	public ConflictException(String msg) {
		super(msg);
	}

	@Override
	HttpStatus getCodeStatus() {
		return HttpStatus.CONFLICT;
	}

}
