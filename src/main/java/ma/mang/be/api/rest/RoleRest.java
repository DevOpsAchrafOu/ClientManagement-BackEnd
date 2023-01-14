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
import ma.mang.be.api.dto.ResponseDto;
import ma.mang.be.api.dto.RoleDto;
import ma.mang.be.api.entity.Role;
import ma.mang.be.api.exception.NotFoundElementException;
import ma.mang.be.api.exception.ResourceNotFoundException;
import ma.mang.be.api.service.RoleService;

/**
 * REST Interfaces to manipulate Role objects
 * @author achraf
 * @version v0.3
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = { "01 - Roles : REST Interfaces to manipulate Role objects" })
public class RoleRest {
	
	private static final String MSG_FAILED_REQUEST= "Request failed. Please try later!";
	private static final String MSG_CONTACT_CREATED = "Role successfully created.";
	private static final String MSG_CONTACT_UPDATED = "Role successfully updated.";

	@Autowired
	private RoleService roleService;

	

	@PostMapping("/roles")
	@ApiOperation(notes = "Creates a role ", value = "", response = RoleDto.class)
	public ResponseEntity<?> addRole(@RequestBody RoleDto roleDetails)
			throws Exception {
		Role m =null;
		try {
		 m = roleService.add(roleDetails);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("Role creation failed.",HttpStatus.INTERNAL_SERVER_ERROR.toString(),e.getMessage() + "\n" + e.getCause()));
		}
		return ResponseEntity.ok(RoleDto.to(m));
	}
	
	@GetMapping("/roles")
	@ApiOperation(notes = "Retrieves all roles", value = "", response = Role.class)
	public ResponseEntity<List<RoleDto>> getRoles() throws ResourceNotFoundException {
		List<Role> roles = roleService.getAllRoles();
		return ResponseEntity.ok(RoleDto.to(roles));
	}


	@GetMapping("/roles/collaborateur/count/{id}")
	@ApiOperation(notes = "Retrieves role by ID count collaborateur", value = "", response = Long.class)
	public ResponseEntity<?> getRoleById(@PathVariable(value = "id") Long roleId) throws ResourceNotFoundException {
		long count = roleService.countCollborateurByRole(roleId);


		return ResponseEntity.ok(count);
	}


	@PutMapping("/roles/{id}")
	@ApiOperation(notes = "Updates a role identified by ID", value = "", response = String.class)
	public ResponseEntity<?> updateRole(@PathVariable(value = "id") Long roleId, @RequestBody RoleDto roleDetails)
			throws Exception {
		Role role = roleService.getRoleById(roleId);
		String rst="";
		String msg="";
		if(role==null) {
			new NotFoundElementException("Role not found for this id :: " + roleId);
		}
		Role m = roleService.update(roleDetails);
		if(m!=null) {
			msg = MSG_CONTACT_UPDATED;
		}else {
			throw new Exception(MSG_FAILED_REQUEST);
		}
		return ResponseEntity.ok(msg);
	}
	

	@DeleteMapping("/roles/{id}")
	@ApiOperation(notes = "Deletes a role identified by ID from database", value = "", response = String.class)
	public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long roleId) throws ResourceNotFoundException {
		Role role = roleService.getRoleById(roleId);
		if(role==null) {
			new NotFoundElementException("Role not found for this id :: " + roleId);
		}
		roleService.delete(roleId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
