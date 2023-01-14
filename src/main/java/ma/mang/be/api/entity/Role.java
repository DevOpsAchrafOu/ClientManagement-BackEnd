package ma.mang.be.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author achraf
 *
 */
@Entity
@Table(name = "role")
public class Role {
	
	private long id;
	private String title;
	private String code;

	public Role() {
		super();
	}
	
	public Role(long id, String title, String code) {
		super();
		this.id = id;
		this.title = title;
		this.code = code;
	}
	
	public Role( String title, String code) {
		super();
		this.title = title;
		this.code = code;
	}

	public Role(String code) {
		super();
		this.code = code;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}


	@Column(name="title")
	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
	
}
