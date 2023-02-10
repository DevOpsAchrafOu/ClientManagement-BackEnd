/**
 * 
 */
package ma.mang.be.api.service;

import java.util.List;

import  ma.mang.be.api.entity.Menu;

/**
 * Specific services to manipulate contacts
 * @author achraf
 * @version v0.2
 */
public interface MenuService {
	
	/** Generic service **/
	List<Menu> getAllMenus();
	
	List<Menu> getAllMenusForRole();
	
	List<Menu> getAllMenusWithParentIsNull();
	
	List<Menu> getAllMenusByUser();
	
	
	Menu getMenuById(Long id);
	
	Menu save(Menu contact);
	
	void delete(Long contactId);
}
