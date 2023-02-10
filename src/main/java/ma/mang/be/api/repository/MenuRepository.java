/**
 * 
 */
package ma.mang.be.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mang.be.api.entity.Menu;



/**
 * Specific DAO to manipulate Menu object in the database
 * @author achraf
 * @version v0.2
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	List<Menu> findAllByOrderByOrdreAsc();
	
	List<Menu> findByUrlAndParentIsNull(String url);
	
	Menu findFirstByParentId(Long parentId);
	
	Menu findFirstById(Long parentId);

}
