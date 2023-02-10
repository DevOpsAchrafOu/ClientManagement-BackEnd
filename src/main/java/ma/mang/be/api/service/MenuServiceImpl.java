/**
 * 
 */
package ma.mang.be.api.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ma.mang.be.api.exception.NotFoundElementException;
import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.entity.Menu;
import ma.mang.be.api.repository.UtilisateurRepository;
import ma.mang.be.api.repository.MenuRepository;

/**
 * @author achraf
 * @version v0.2
 *
 */
@Service("MenuService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	UtilisateurRepository userRepo;
	
	@Autowired
	MenuRepository menuRepo;

	@Override
	public List<Menu> getAllMenus() {
		return menuRepo.findAllByOrderByOrdreAsc();
	}
	
	@Override
	public List<Menu> getAllMenusForRole() {
		List<Menu> lstMenu = new ArrayList<Menu>();
		List<Menu> lstMenuTraited = new ArrayList<Menu>();	
		
		lstMenu = menuRepo.findAllByOrderByOrdreAsc();
		for (Menu menu : lstMenu) {
			if(menu.getParent() == null) {
					lstMenuTraited.add(menu);
			}
		}
		return lstMenuTraited;
	}
	
	@Override
	public List<Menu> getAllMenusWithParentIsNull() {
		return menuRepo.findByUrlAndParentIsNull("#");
	}

	@Override
	public Menu getMenuById(Long id) throws NotFoundElementException {
		return menuRepo.getById(id);
	}

	@Override
	public Menu save(Menu ap) {
		return menuRepo.save(ap);
	}

	@Override
	public void delete(Long apId) {
		Menu ap = menuRepo.getById(apId);
		if(ap!=null) {
			menuRepo.delete(ap);
		}
		
	}

	@Override
	public List<Menu> getAllMenusByUser() {
		// Get the concerned user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login = (String) auth.getPrincipal();
		Utilisateur user = userRepo.findByLogin(login);
		
		List<Menu> lstMenu = new ArrayList<Menu>();	
		List<Menu> lstMenuTraited = new ArrayList<Menu>();	
		
		if(user != null) {
			if(user.getRole() != null && user.getRole().getMenu() != null ) {
				lstMenu = user.getRole().getMenu();
				//sort listMenu
				lstMenu = sortList(lstMenu);
				
				for (Menu menu : lstMenu) {
					if(menu.getParent() == null) {
						int index = getIndexFromList(lstMenuTraited,menu);
						if(index == -1)
							lstMenuTraited.add(menu);
					}else {
						Menu menuParent = menuRepo.findFirstById(menu.getParent().getId());
						
						if(menuParent!=null) {
							int index = getIndexFromList(lstMenuTraited,menuParent);
//							add parent with new et old children
							if(index != -1) {
								Menu menuParentTraited = lstMenuTraited.get(index);
								List<Menu> oldChildren = new ArrayList<Menu>();
								if(menuParentTraited.getChildren().size() >0)
									oldChildren = menuParentTraited.getChildren();
								
								int indexChildren = getIndexFromList(oldChildren,menu);
								if(indexChildren == -1)
									oldChildren.add(menu);
								
									menuParentTraited.setChildren(oldChildren);
								lstMenuTraited.set(index, menuParentTraited);
							}
//							add parent with children
							else {
								List<Menu> children = new ArrayList<Menu>();
								children.add(menu);
								menuParent.setChildren(children);
								lstMenuTraited.add(menuParent);
							}
						}
					}
				}
			}
		}
		return lstMenuTraited;
	}

	private List<Menu> sortList(List<Menu> lstMenu) {
		lstMenu = lstMenu.stream()
		        .sorted(Comparator.comparingDouble(Menu::getOrdre))
		        .collect(Collectors.toList());
		return lstMenu;
	}
	
	private int getIndexFromList(List<Menu> lstMenu, Menu menu) {
		int index= IntStream.range(0, lstMenu.size())
		        .filter(i -> Objects.equals(lstMenu.get(i).getId(), menu.getId()))
		        .findFirst()
		        .orElse(-1);
		return index;
	}
	
}
