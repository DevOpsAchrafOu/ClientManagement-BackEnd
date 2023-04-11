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

import ma.mang.be.api.utils.Utils;


/**
 * @author achraf
 * @version v0.1
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur {
	
	public static final String PENDING_STATE = "PENDING";// he has to complete the profile info
	public static final String ACTIVATED_STATE = "ACTIVATED";//  he has complete the profile info and activation by Administrator
	public static final String DEACTIVATED_STATE = "DEACTIVATED";// deactivation by Administrator
	public static final String DELETED_STATE = "DELETED";// delete by Administrator
	public static final String BLOCKED_STATE = "BLOCKED";// blocked by Administrator
	
	
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
	private Role role;
    
    public Utilisateur() {
    	  
    }

    public Utilisateur(String email, String password) {
		super();
		this.login = email;
		this.email = email;
		this.password = password;
		this.creationDate= new Date();
	}
    
    public Utilisateur(String email, String password, String token,String state) {
		super();
		this.login = email;
		this.email = email;
		this.password = password;
		this.creationDate= new Date();
		this.token=token;
		this.state=state;
	}
    
   //without token, state
	public Utilisateur(long id, String login, String email, String password, String nom, String prenom,
			String phone, String role) {
		super();
		this.id = id;
		this.login = login;
		this.email = email;
		this.password = password;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.role = new Role(role);
	}
    
    

	public Utilisateur(long id, String login, String email, String password, String token, String nom, String prenom,
			String phone, Date creationDate, String state, Role role) {
		super();
		this.id = id;
		this.login = login;
		this.email = email;
		this.password = password;
		this.token = token;
		this.nom = nom;
		this.prenom = prenom;
		this.phone = phone;
		this.creationDate = creationDate;
		this.state = state;
		this.role = role;
	}
	
	  public Utilisateur(int id, Date creationDate, String email, String login, String nom, String password, String phone, String prenom, String state, String token, int roleId) {
	        this.id = id;
	        this.creationDate = creationDate;
	        this.email = email;
	        this.login = login;
	        this.nom = nom;
	        this.password = password;
	        this.phone = phone;
	        this.prenom = prenom;
	        this.state = state;
	        this.token = token;
	        this.role = new Role(roleId);
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
		this.state = Utils.isEmptyString(state)?ACTIVATED_STATE:state;
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
