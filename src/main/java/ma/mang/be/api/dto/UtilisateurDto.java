package ma.mang.be.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;

import ma.mang.be.api.entity.Utilisateur;
import ma.mang.be.api.utils.Utils;

/**
 * DTO Class to manipulate Utilisateur objects in the front side
 * @author achraf
 * @version v0.1
 *
 */
public class UtilisateurDto {

	public static final String ACTIVATED_STATE = "ACTIVATED";
	public static final String DEACTIVATED_STATE = "DEACTIVATED";
	public static final String DELETED_STATE = "DELETED";
	public static final String BLOCKED_STATE = "BLOCKED";
	
	private long id;
	private String login;
	private String email;
	private String password;
	private String token;
	private String nom;
	private String prenom;
	private String phone;
	private Date creationDate;
	private String state;
	private String role;
	
    
    public UtilisateurDto() {
    	  
    }

    public UtilisateurDto(String email, String password) {
		super();
		this.login = email;
		this.email = email;
		this.password = password;
		this.creationDate= new Date();
	}
    
    public UtilisateurDto(String email, String password, String token,String state) {
		super();
		this.login = email;
		this.email = email;
		this.password = password;
		this.creationDate= new Date();
		this.token=token;
		this.state=state;
	}
    
	
    

	public UtilisateurDto(long id, String login, String email, String token, String nom, String prenom, String phone, 
			 String state, String role) {
		super();
		this.id = id;
		this.login = login;
		this.email = email;
		this.token = token;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.state = state;
		this.role = role;
	}
	
	//without token, password
	public UtilisateurDto(long id, String login,  String nom, String prenom, String phone, String email, String state, String role,
			Date creationDate) {
		super();
		this.id = id;
		this.login = login;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.state = state;
		this.role = role;
		this.creationDate = creationDate;
	}

	public UtilisateurDto(long id, String login, String email, String password,
			String token, String nom, String prenom, String phone, String state, String role) {
		super();
		this.id = id;
		this.login = login;
		this.email = email;
		this.password = password;
		this.token = token;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.state = state;
		this.role = role;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long userId) {
		this.id = userId;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate==null ? new Date() : creationDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = Utils.isEmptyString(state) ? ACTIVATED_STATE : state;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public static UtilisateurDto from(Utilisateur user) {
		if(user!=null) {
			return new UtilisateurDto(	
					user.getId(),
					user.getLogin(),
					user.getNom(),
					user.getPrenom(),
					user.getPhone(),
					user.getEmail(),
					user.getState(),
					user.getRole().getCode(),
					user.getCreationDate());
		}
		
		 return new UtilisateurDto();
		
	}
	
	public static Utilisateur to(UtilisateurDto user) {
		if(user!=null) {
			return new Utilisateur(
					user.getId(),
					user.getLogin(),
					user.getEmail(),
					user.getPassword(),
					user.getNom(),
					user.getPrenom(),
					user.getPhone(),
					user.getRole());
		}
		
		 return new Utilisateur();
		
	}
	
	public static List<UtilisateurDto> to(List<Utilisateur> users) {
		List<UtilisateurDto> colDto = new ArrayList<UtilisateurDto>();
		if(users!=null) {
			for(Utilisateur c : users) {
				colDto.add(from(c));
			}
		}
		 return colDto;
		
	}

 
}
