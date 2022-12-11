package ma.mang.be.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import org.reflections.util.Utils;

import ma.mang.be.api.model.Utilisateur;

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
	private String nom;
	private String prenom;
	private String phone;
	private String entite;
	private String email;
	private String password;
	private String token;
	private Date creationDate;
	private String state;
	private String role;
	
    
    public UtilisateurDto() {
    	  
    }

    public UtilisateurDto(String email, String password) {
		super();
		this.email = email;
		this.login = email;
		this.password = password;
		this.creationDate= new Date();
	}
    
    public UtilisateurDto(String email, String password, String token,String state) {
		super();
		this.email = email;
		this.login = email;
		this.password = password;
		this.creationDate= new Date();
		this.token=token;
		this.state=state;
	}
    
	public UtilisateurDto(String login, String nom, String prenom, String phone, String entite, String email,
			String password, String token,String state, String role) {
		super();
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.entite = entite;
		this.email = email;
		this.password = password;
		this.token = token;
		this.state = state;
		this.role = role;
	}
	
	public UtilisateurDto(String login, String nom, String prenom, String phone, String entite, String email,
			 String state, boolean isTemporaryPwd, String role) {
		super();
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.entite = entite;
		this.email = email;
		this.state = state;
		this.role = role;
	}
	
	public UtilisateurDto(long id,String login, String nom, String prenom, String phone, String entite, String email,
			 String state, String role) {
		super();
		this.id = id;
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.entite = entite;
		this.email = email;
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
		this.creationDate = creationDate==null?new Date():creationDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = Utils.isEmpty(state) ? ACTIVATED_STATE : state;
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

	public String getEntite() {
		return entite;
	}

	public void setEntite(String entite) {
		this.entite = entite;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public static UtilisateurDto from(Utilisateur user) {
		if(user!=null) {
			return new UtilisateurDto(	user.getId(),user.getLogin(),
					user.getNom(),user.getPrenom(),
					user.getPhone(),
					user.getEntite(),
					user.getEmail(),
					user.getState(),
					user.getRole().getCode());
		}
		
		 return new UtilisateurDto();
		
	}
	
	public static Utilisateur to(UtilisateurDto user) {
		if(user!=null) {
			return new Utilisateur(user.getLogin(),
					user.getNom(),
					user.getPrenom(),
					user.getPhone(),
					user.getEntite(),
					user.getEmail(),
					user.getPassword(),
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
