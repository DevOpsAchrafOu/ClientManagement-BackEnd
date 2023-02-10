package ma.mang.be.api.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 
 * @author achraf
 *
 */
public class ErrorDetailsDto {

	private String url;
	private String message;
	private String timestampe;
	
    private String details;

	
	public ErrorDetailsDto(String url, String message) {
		super();
		this.url = url;
		this.message = message;
	}

	public ErrorDetailsDto(String url, String message, String details) {
		super();
		this.url = url;
		this.message = message;
		this.details = details;
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

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
