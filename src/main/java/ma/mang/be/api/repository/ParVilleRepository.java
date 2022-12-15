/**
 * 
 */
package ma.mang.be.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mang.be.api.entity.ParVille;

/**
 * Specific DAO to manipulate ParVille object in the database
 * @author achraf
 * @version v0.1
 *
 */
public interface ParVilleRepository extends JpaRepository<ParVille, Long> {
	
	List<ParVille> findByPaysId(long id);
	
}
