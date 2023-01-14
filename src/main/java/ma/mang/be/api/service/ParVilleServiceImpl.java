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
import ma.mang.be.api.entity.ParVille;
import ma.mang.be.api.exception.NotFoundElementException;
import ma.mang.be.api.repository.ParPaysRepository;
import ma.mang.be.api.repository.ParVilleRepository;

/**
 * @author achraf
 * @version v1.0
 *
 */
@Service("ParVilleService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ParVilleServiceImpl implements ParVilleService {

	@Autowired
	ParVilleRepository ParVilleRepo;
	
	@Autowired
	ParPaysRepository ParPaysRepo;

	@Override
	public List<ParVille> getAllParVilles() {
		return ParVilleRepo.findAll();
	}

	@Override
	public ParVille getParVilleById(Long id) throws NotFoundElementException {
		return ParVilleRepo.getById(id);
	}

	@Override
	public ParVille save(ParVille ville) {
		ParPays p = ParPaysRepo.getById(ville.getPays().getId() );
		ville.setPays(p);
		return ParVilleRepo.save(ville);
	}

	@Override
	public void delete(Long villeId) {
		ParVille ville = ParVilleRepo.getById(villeId);
		if(ville!=null) {
			ParVilleRepo.delete(ville);
		}
		
	}
	
	@Override
	public List<ParVille> getParVilleByPaysId(long id) {
		return ParVilleRepo.findByPaysId(id);
	}
	
}
