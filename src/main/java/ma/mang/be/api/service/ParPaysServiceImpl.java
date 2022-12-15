/**
 * 
 */
package ma.mang.be.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import ma.mang.be.api.entity.ParPays;
import ma.mang.be.api.exception.NotFoundElementException;
import ma.mang.be.api.repository.ParPaysRepository;

/**
 * @author achraf
 * @version v0.1
 *
 */
@Service("ParPaysService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ParPaysServiceImpl implements ParPaysService {

	@Autowired
	ParPaysRepository ParPaysRepo;

	@Override
	public List<ParPays> getAllParPayss() {
		return ParPaysRepo.findAll();
	}

	@Override
	public ParPays getParPaysById(Long id) throws NotFoundElementException {
		return ParPaysRepo.getById(id);
	}

	@Override
	public ParPays save(ParPays pays) {
		return ParPaysRepo.save(pays);
	}

	@Override
	public void delete(Long paysId) {
		ParPays pays = ParPaysRepo.getById(paysId);
		if(pays!=null) {
			ParPaysRepo.delete(pays);
		}
		
	}
	
}
