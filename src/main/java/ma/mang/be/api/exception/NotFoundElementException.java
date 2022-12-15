package ma.mang.be.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not Found Element Exception
 * @author Achraf
 *
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NotFoundElementException extends GlobalException {

	private static final long serialVersionUID = 5052553158885402027L;

	public NotFoundElementException(String msg) {
		super(msg);
	}

	@Override
	HttpStatus getCodeStatus() {
		return HttpStatus.NO_CONTENT;
	}

}
