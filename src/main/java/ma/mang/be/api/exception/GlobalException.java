package ma.mang.be.api.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Achraf
 *
 */
public abstract class GlobalException extends RuntimeException {

	private static final long serialVersionUID = -7518793066799655244L;

	public GlobalException(String msg) {
		super(msg);
	}

	abstract HttpStatus getCodeStatus();
}
