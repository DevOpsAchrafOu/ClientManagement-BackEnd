package ma.mang.be.api.exception;

/**
 * @author Achraf
 *
 */
public class ExceptionModel {
	
    private String date;
    private String message;
    private String details;

    public ExceptionModel(String date, String message, String details) {
        super();
        this.date = date;
        this.message = message;
        this.details = details;
    }

    public String getTimestamp() {
        return date;
    }

    public String getMessage() {
         return message;
    }

    public String getDetails() {
         return details;
    }
}