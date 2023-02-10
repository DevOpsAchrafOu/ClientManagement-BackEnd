/**
 * 
 */
package ma.mang.be.api.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import  ma.mang.be.api.dto.MenuDto;
import ma.mang.be.api.dto.ResponseDto;
import ma.mang.be.api.entity.Menu;
import  ma.mang.be.api.exception.NotFoundElementException;
import  ma.mang.be.api.exception.ResourceNotFoundException;
import  ma.mang.be.api.service.MenuService;

/**
 * REST Interfaces to manipulate Menu objects
 * @author achraf
 * @version v0.2
 */
@RestController
@RequestMapping("api/mng/v1")
@Api(tags = { "03 - Menus : REST Interfaces to manipulate Menu objects" })
public class MenuRest {
	
	private static final String MSG_FAILED_REQUEST= "Request failed. Please try later!";
	private static final String MSG_MENU_CREATED = "Menu successfully created.";
	private static final String MSG_MENU_UPDATED = "Menu successfully updated.";

	@Autowired
	private MenuService menuService;

	

	@PostMapping("/menus")
	@ApiOperation(notes = "Creates a menu ", value = "", response = MenuDto.class)
	public ResponseEntity<?> addMenu(@RequestBody MenuDto menuDetails)
			throws Exception {
		Menu m =null;
		try {
		 m = menuService.save(MenuDto.from(menuDetails));
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("Menu creation failed.",HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage() + "\n" + e.getCause()));
		}
		return ResponseEntity.ok(MenuDto.to(m));
	}
	
	@GetMapping("/menus")
	@ApiOperation(notes = "Retrieves all menu ", value = "", response = Menu.class)
	public ResponseEntity<List<MenuDto>> getMenus() throws ResourceNotFoundException {
		List<Menu> menus = menuService.getAllMenus();
		return ResponseEntity.ok(MenuDto.counverTo(menus));
	}
	
	
	@GetMapping("/menus/role")
	@ApiOperation(notes = "Retrieves all menu ", value = "", response = Menu.class)
	public ResponseEntity<List<MenuDto>> getMenusForRole() throws ResourceNotFoundException {
		List<Menu> menus = menuService.getAllMenusForRole();
		return ResponseEntity.ok(MenuDto.counverTo(menus));
	}
	
	@GetMapping("/menus/userConnected")
	@ApiOperation(notes = "Retrieves all menu for the connected profile", value = "", response = Menu.class)
	public ResponseEntity<List<MenuDto>> getMenusByUser() throws ResourceNotFoundException {
		List<Menu> menus = menuService.getAllMenusByUser();	
		return ResponseEntity.ok(MenuDto.counverTo(menus));
	}
	
	@GetMapping("/menus/parent")
	@ApiOperation(notes = "Retrieves all menu (Parent) for the connected profile", value = "", response = Menu.class)
	public ResponseEntity<List<MenuDto>> getMenusParent() throws ResourceNotFoundException {
		List<Menu> menus = menuService.getAllMenusWithParentIsNull();
		return ResponseEntity.ok(MenuDto.to(menus));
	}


	@GetMapping("/menus/{id}")
	@ApiOperation(notes = "Retrieves menu by ID", value = "", response = Menu.class)
	public ResponseEntity<MenuDto> getMenuById(@PathVariable(value = "id") Long menuId) throws ResourceNotFoundException {
		Menu menu = menuService.getMenuById(menuId);
		if (menu == null) {
			throw new NotFoundElementException("Menu not found for this id :: " + menuId);
		}
	
		return ResponseEntity.ok(MenuDto.to(menu));
	}


	@PutMapping("/menus/{id}")
	@ApiOperation(notes = "Updates a menu identified by ID", value = "", response = String.class)
	public ResponseEntity<?> updateMenu(@PathVariable(value = "id") Long menuId, @RequestBody MenuDto menuDetails)
			throws Exception {
		Menu menu = menuService.getMenuById(menuId);
		String msg="";
		if(menu==null) {
			new NotFoundElementException("Menu not found for this id :: " + menuId);
		}
		Menu m = menuService.save(MenuDto.from(menuDetails));
		if(m!=null) {
			msg = MSG_MENU_UPDATED;
		}else {
			throw new Exception(MSG_FAILED_REQUEST);
		}
		return ResponseEntity.ok(msg);
	}
	

	@DeleteMapping("/menus/{id}")
	@ApiOperation(notes = "Deletes a menu identified by ID from database", value = "", response = String.class)
	public ResponseEntity<?> deleteMenu(@PathVariable(value = "id") Long menuId) throws ResourceNotFoundException {
		Menu menu = menuService.getMenuById(menuId);
		if(menu==null) {
			new NotFoundElementException("Menu not found for this id :: " + menuId);
		}
		menuService.delete(menuId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
