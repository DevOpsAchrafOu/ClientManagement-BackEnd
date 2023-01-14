package ma.mang.be.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not Equals Exception
 * @author achraf
 *
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotEqualsException extends GlobalException {

	private static final long serialVersionUID = -4069288439219054880L;

	public NotEqualsException(String msg) {
		super(msg);
	}

	@Override
	HttpStatus getCodeStatus() {
		return HttpStatus.FORBIDDEN;
	}
}
