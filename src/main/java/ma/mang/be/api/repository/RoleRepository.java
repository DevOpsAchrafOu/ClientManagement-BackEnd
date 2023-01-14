/**
 * 
 */
package ma.mang.be.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import ma.mang.be.api.entity.Role;

/**
 * Specific DAO to manipulate Role object in the database
 * @author achraf
 * @version v0.1
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findById(long id);
	
	
	/**
	 * Finds an role identified by his code
	 * @param code identifying the role
	 * @return Role object
	 */
	Role findByCode(String code);
	
}
