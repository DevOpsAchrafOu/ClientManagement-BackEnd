/**
 * 
 */
package ma.mang.be.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.mang.be.api.model.ParPays;

/**
 * Specific DAO to manipulate ParPays object in the database
 * @author achraf
 * @version v0.1
 *
 */
public interface ParPaysRepository extends JpaRepository<ParPays, Long> {
	
}
