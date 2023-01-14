/**
 * 
 */
package ma.mang.be.api.service;

import com.auth0.jwt.exceptions.TokenExpiredException;

/**
 * Specific Services to manipulate tokens
 * @author achraf
 * @version v0.1
 *
 */
public interface AuthService {
	
	/**
	 * Finds token for a given user identified by his login(mail)
	 * @param login: the mail (login) for a given user
	 * @return token: the last valid token stored in the database
	 */
	String getTokenByUserLogin(String login);
	
	
	/**
	 * Stores a valid token for a given user
	 * @param login identifying the user
	 * @param token eventually valid token for a given user
	 */
	void storeTokenForUserLogin(String login, String token);
	
	/**
	 * Validates the mail given by the new user when signing up.</br>
	 * The validation process : </br>
	 * 1. Decode the received token to extract the email.</br>
	 * 2. Find the account of the concerned user by the extracted email.</br>
	 * 3. If the entry is found then set the <b>is_email_adress_checked</b> to true and return true.</br>
	 * else DO NOTHING and return false
	 * @param validationToken validation token
	 * @return <b>true</b> if the mail is valid <b>false</b> else.
	 */
	boolean validateEmail(String validationToken) throws TokenExpiredException,Exception ;
	

}
