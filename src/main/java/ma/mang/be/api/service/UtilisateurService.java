/**
 * 
 */
package ma.mang.be.api.service;

import java.util.List;

import ma.mang.be.api.dto.UtilisateurDto;
import ma.mang.be.api.exception.NotEqualsException;
import ma.mang.be.api.exception.NotFoundElementException;
import ma.mang.be.api.entity.Utilisateur;

/**
 * Specific Services to manipulate accounts 
 * @author achraf
 * @version v0.1
 *
 */
public interface UtilisateurService {
	
	/**Generic services **/
	List<Utilisateur> getAllUsers();
	
	Utilisateur getUserById(Long id) throws NotFoundElementException ;
	
	Utilisateur save(Utilisateur usr);
	
	void delete(Long userId);
	
	/**
	 * Creates an account by using the login and the password received from the front side.</br>
	 * If the entry is well stored then the system sends a validation email <b>with an encrypted token</b> to the email used when signing up.</br>
	 * Another token is generated and returned, this token can be used to get access to other services
	 * @param usr object encapsulating the mail (login) and the password
	 * @return Message : If the mail(login) is already used by another account then the return <b>message=EXIST</b> </br>
	 * else a <b>valid token</b> will be returned</br>
	 * else a short message will be delivered <b>message = KO</b>
	 */
	String createAccount(Utilisateur usr);
	
	
	/**
	 * Finds an account identified by the login (mail) from the database
	 * @param login login (mail) identifying the account
	 * @return An account object
	 */
	Utilisateur findByLogin(String login);
	
	/**
	 * Sets Utilisateur's status 
	 * @param status : account's status with the following values :</br>PENDING_STATE = "PENDING" ,</br> ACTIVATED_STATE = "ACTIVATED" ,</br> DEACTIVATED_STATE = "DEACTIVATED" ,</br> DELETED_STATE = "DELETED" </br>or BLOCKED_STATE = "BLOCKED"
	 * @return Message : if the account is well updated then <b>message = OK</b></br>
	 * else an error message will be delivered <b>message = KO + error message</b> 
	 */
	String setAccountState(String status) throws Exception, NotFoundElementException ;
	
	/**
	 * Sets Utilisateur's status 
	 * @param oldPwd : old password
	 * @param newPwd : new password
	 * @return token : if the account's pwd is well updated then a new token is generated and returned</br>
	 * else an error message will be delivered <b>message = KO + error message</b> with no token 
	 */
	String setPassword(String oldPwd, String newPwd) throws Exception, NotFoundElementException,NotEqualsException ;
	
	Utilisateur update(UtilisateurDto usr);
}
