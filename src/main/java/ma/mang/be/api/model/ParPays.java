/**
 * 
 */
package ma.mang.be.api.model;

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
@Table(name = "par_pays")
public class ParPays {
	
	private long id;
    private String titleAr;
    private String titleFr;

    public ParPays() {
		super();
	}

    
	public ParPays(long id, String titleAr, String titleFr) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
	}


	public ParPays(long id) {
		this.id = id;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name="title_ar")
	public String getTitleAr() {
		return titleAr;
	}

	public void setTitleAr(String titleAr) {
		this.titleAr = titleAr;
	}

	@Column(name="title_fr")
	public String getTitleFr() {
		return titleFr;
	}

	public void setTitleFr(String titleFr) {
		this.titleFr = titleFr;
	}


	@Override
	public String toString() {
		return "ParPays [id=" + id + ", titleAr=" + titleAr + ", titleFr=" + titleFr + "]";
	}
	
	
	
}