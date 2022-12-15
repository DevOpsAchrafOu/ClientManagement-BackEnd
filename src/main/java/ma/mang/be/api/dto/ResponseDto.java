/**
 * 
 */
package ma.mang.be.api.dto;

/**
 * @author Achraf
 *
 */
public class ResponseDto {
	
	private String message;
	private String code;
	private String details;
	private String identifiant;
	private long id;
	
	public ResponseDto(String message, String code, String details) {
		super();
		this.message = message;
		this.code = code;
		this.details = details;
	}
	
	public ResponseDto(String message, String code, String details,String identifiant) {
		super();
		this.message = message;
		this.code = code;
		this.details = details;
		this.identifiant = identifiant;
	}
	
	public ResponseDto(String message, String code, String details,long id) {
		super();
		this.message = message;
		this.code = code;
		this.details = details;
		this.id = id;
	}

	public ResponseDto() {
		super();
	}

	public ResponseDto(String message) {
	    this.message = message;
	  }

	  public String getMessage() {
	    return message;
	  }

	  public void setMessage(String message) {
	    this.message = message;
	  }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
