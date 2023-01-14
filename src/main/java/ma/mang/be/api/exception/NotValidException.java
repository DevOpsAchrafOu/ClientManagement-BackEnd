package ma.mang.be.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not Valid Password Exception
 * @author jalal
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotValidException extends GlobalException {

	private static final long serialVersionUID = -4069288439219054880L;

	public NotValidException(String msg) {
		super(msg);
	}

	@Override
	HttpStatus getCodeStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
