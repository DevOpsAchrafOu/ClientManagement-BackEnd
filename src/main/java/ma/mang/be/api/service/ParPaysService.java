/**
 * 
 */
package ma.mang.be.api.service;

import java.util.List;

import ma.mang.be.api.entity.ParPays;
import ma.mang.be.api.exception.NotFoundElementException;

/**
 * Specific Services to manipulate ParPayss 
 * @author achraf
 * @version v0.1
 */
public interface ParPaysService {
	
	
	/**Generic services **/
	List<ParPays> getAllParPayss();
	
	ParPays getParPaysById(Long id) throws NotFoundElementException ;
	
	ParPays save(ParPays ap);
	
	void delete(Long apId);

}
