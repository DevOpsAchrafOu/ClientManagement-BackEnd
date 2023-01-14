/**
 * 
 */
package ma.mang.be.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mang.be.api.entity.Utilisateur;



/**
 * Specific DAO to manipulate Utilisateur object in the database
 * @author achraf
 * @version v0.1
 *
 */

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
	
	/**
	 * Finds an account identified by his email
	 * @param email identifying the account
	 * @return Utilisateur object
	 */
	Utilisateur findByEmail(String email);
	
	
	/**
	 * Finds an account identified by his login
	 * @param email identifying the account
	 * @return Utilisateur object
	 */
	Utilisateur findByLogin(String login);
	
	Utilisateur findFirstById(long id);
	
	long countByRoleId(Long roleId);


}
