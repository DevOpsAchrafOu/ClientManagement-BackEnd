/**
 * 
 */
package ma.mang.be.api.service;

import java.util.List;

import ma.mang.be.api.model.ParVille;

/**
 * Specific Services to manipulate ParVilles 
 * @author achraf
 * @version v0.1
 */
public interface ParVilleService {
	
	
	/**Generic services **/
	List<ParVille> getAllParVilles();
	
	ParVille getParVilleById(Long id) throws Exception ;
	
	ParVille save(ParVille ap);
	
	void delete(Long apId);
	
	List<ParVille> getParVilleByPaysId(long id);
}
