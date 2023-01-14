package ma.mang.be.api.security.jwt;

/**
 * Global Security Properties
 * @author achraf
 * @version 0.1
 */

public class JwtProperties {
	public static final String SECRET = "##$$123456789$$##";
    public static final int EXPIRATION_TIME = 864_000_000; // 7 days
    public static final int EXPIRATION_VALIDATION_TIME = 900_000; // 15 min
    public static final int EXPIRATION_RESET_PWD_TIME = 3_600_000; // 1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
