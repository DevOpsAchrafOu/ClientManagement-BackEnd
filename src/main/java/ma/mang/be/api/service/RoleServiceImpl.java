/**
 * 
 */
package ma.mang.be.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import ma.mang.be.api.dto.RoleDto;
import ma.mang.be.api.entity.Menu;
import ma.mang.be.api.entity.Role;
import ma.mang.be.api.repository.MenuRepository;
import ma.mang.be.api.repository.RoleRepository;
import ma.mang.be.api.repository.UtilisateurRepository;

/**
 * @author achraf
 * @version v0.1
 */
@Service("RoleService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	MenuRepository menuRepo;
	
	@Autowired
	UtilisateurRepository collRepo;
	
	
	@Override
	public List<Role> getAllRoles() {
		return roleRepo.findAll();
	}

	@Override
	public Role getRoleById(Long id) {
		Role contact = null;
		try {
			contact =(roleRepo.findById(id) != null && roleRepo.findById(id).isPresent())? roleRepo.findById(id).get() : null;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return contact;
	}

	@Override
	public Role save(Role contact) {
		return roleRepo.save(contact);
	}
	
	@Override
	public Role add(RoleDto roleDto) {

		List<Menu> listM = new ArrayList<Menu>();	

		//Prepare role
		Role roleSave = new Role(roleDto.getTitle(),roleDto.getCode());
		
		for (int i = 0; i < roleDto.getMenus().size(); i++) {
			
			long idMenu = roleDto.getMenus().get(i);
			Menu menu = menuRepo.findById(idMenu).get();
			if(menu != null)
				listM.add(menu);
		}
		roleSave.setMenu(listM);
		
		return roleRepo.save(roleSave);
	}

	@Override
	public Role update(RoleDto roleDto) {

		List<Menu> listM = new ArrayList<Menu>();	

		//get role
		Role roleFind = this.getRoleById(roleDto.getId());
		
		if(roleFind != null) {
			
			roleFind.setCode(roleDto.getCode());
			roleFind.setTitle(roleDto.getTitle());
			
			for (int i = 0; i < roleDto.getMenus().size(); i++) {
				
				long idMenu = roleDto.getMenus().get(i);
				Menu menu = menuRepo.findById(idMenu).get();
				if(menu != null)
					listM.add(menu);
			}
			
			//delete list menu old => implicitement
			
			//set list menu
			roleFind.setMenu(listM);
						
			return roleRepo.save(roleFind);
		}
		return null;
	}


	@Override
	public void delete(Long contactId) {
		
		roleRepo.deleteById(contactId);
	}

	

	@Override
	public List<Role> getAllRolesByUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countCollborateurByRole(long roleId) {
		// TODO Auto-generated method stub
		return collRepo.countByRoleId(roleId);
	}
	
	
}
