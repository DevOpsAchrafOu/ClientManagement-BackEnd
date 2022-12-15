package ma.mang.be.api.dto;

import java.util.ArrayList;
import java.util.List;

import ma.mang.be.api.entity.Role;


/**
 * DTO Class to manipulate Role objects in the front side
 * @author achraf
 * @version v0.1
 */
public class RoleDto {

	
	private long id;
	private String title;
	private String code;

	public RoleDto() {
		super();
	}
	
	public RoleDto(long id, String title, String code) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static Role from(RoleDto dto) {
		return new Role(dto.getId(), dto.getTitle(), dto.getCode());
		
	}
	
	public static RoleDto to(Role role) {
		
		return new RoleDto(
				role.getId(), 
				role.getTitle(), 
				role.getCode());
		
	}
	
	public static List<RoleDto> to(List<Role> roles) {
		List<RoleDto> rolesDtos = new ArrayList<>();
		for(Role ct : roles) {
			rolesDtos.add(to (ct));
		}
		return rolesDtos;
		
	}

}
