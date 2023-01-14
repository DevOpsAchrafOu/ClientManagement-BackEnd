/**
 * 
 */
package ma.mang.be.api.service;
import java.util.List;

import ma.mang.be.api.dto.RoleDto;
import ma.mang.be.api.entity.Role;

/**
 * Specific services to manipulate roles
 * @author jalal
 * @version v0.1
 */
public interface RoleService {
	
	/** Generic service **/
	List<Role> getAllRoles();
	
	List<Role> getAllRolesByUser();
	
	Role getRoleById(Long id);
	
	Role save(Role contact);
	
	Role add(RoleDto roleDto);
	
	Role update(RoleDto roleDto);
	
	void delete(Long contactId);
	
	Long countCollborateurByRole(long roleId);
}
