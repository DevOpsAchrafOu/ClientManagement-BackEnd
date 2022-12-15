package ma.mang.be.api.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
 * @author jalal
 *
 */
public class ExceptionModel {

	private String url;
	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestampe;

	public ExceptionModel() {
		this.timestampe = LocalDateTime.now();
	}

	public ExceptionModel(String url, String message) {
		this();
		this.url = url;
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getTimestampe() {
		return timestampe;
	}

	public void setTimestampe(LocalDateTime timestampe) {
		this.timestampe = timestampe;
	}
}
