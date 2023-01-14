/**
 * 
 */
package ma.mang.be.api.service;

import java.util.List;

import ma.mang.be.api.entity.ParVille;
import ma.mang.be.api.exception.NotFoundElementException;

/**
 * Specific Services to manipulate ParVilles 
 * @author achraf
 * @version v1.0
 */
public interface ParVilleService {
	
	
	/**Generic services **/
	List<ParVille> getAllParVilles();
	
	ParVille getParVilleById(Long id) throws NotFoundElementException ;
	
	ParVille save(ParVille ap);
	
	void delete(Long apId);
	
	List<ParVille> getParVilleByPaysId(long id);
}
