/**
 * 
 */
package ma.mang.be.api.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.reflections.util.Utils;

/**
 * @author achraf
 *
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur {
	
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
	private Role role;
    
    public Utilisateur() {
    	  
    }

    public Utilisateur(String email, String password) {
		super();
		this.email = email;
		this.login = email;
		this.password = password;
		this.creationDate= new Date();
	}
    
    public Utilisateur(String email, String password, String token,String state) {
		super();
		this.email = email;
		this.login = email;
		this.password = password;
		this.creationDate= new Date();
		this.token=token;
		this.state=state;
	}
    
	public Utilisateur(String login, String nom, String prenom, String phone, String entite, String email,
			String password, String token,String state, Role role) {
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
	
	public Utilisateur(String login, String nom, String prenom, String phone, String entite, String email,
			String password, String role) {
		super();
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.entite = entite;
		this.email = email;
		this.password = password;
		this.role = new Role(role);
	}
	public Utilisateur(long id,String login, String nom, String prenom, String phone, String entite, String email,
			String password, String role) {
		super();
		this.id = id;
		this.login = login;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.entite = entite;
		this.email = email;
//		this.password = password;
		this.role = new Role(role);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	public long getId() {
		return id;
	}

	public void setId(long userId) {
		this.id = userId;
	}


	@Column(name="login")
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


	@Column(name="password")
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="token")
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
		this.state = Utils.isEmpty(state)?ACTIVATED_STATE:state;
	}


	@Column(name="nom")
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@Column(name="prenom")
	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Column(name="phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name="entite")
	public String getEntite() {
		return entite;
	}

	public void setEntite(String entite) {
		this.entite = entite;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
	public Role getRole() {
		return role;
	}


	public void setProfile(Role role) {
		this.role = role;
	}
 
}
