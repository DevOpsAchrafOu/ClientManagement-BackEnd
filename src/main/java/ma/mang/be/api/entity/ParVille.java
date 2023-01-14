/**
 * 
 */
package ma.mang.be.api.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author achraf
 * @version v1.0
 */
@Entity
@Table(name = "par_ville")
public class ParVille {
	
	private long id;
    private String titleAr;
    private String titleFr;
	private ParPays pays;

    public ParVille() {
		super();
	}

    
	public ParVille(long id, String titleAr, String titleFr) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
	}
	
	public ParVille(long id, String titleAr, String titleFr,long idPays) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.pays = new ParPays(idPays);
	}


	public ParVille(long id) {
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
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "pays_id", referencedColumnName = "id")
	public ParPays getPays() {
		return pays;
	}


	public void setPays(ParPays pays) {
		this.pays = pays;
	}


	@Override
	public String toString() {
		return "ParVille [id=" + id + ", titleAr=" + titleAr + ", titleFr=" + titleFr + ", pays=" + pays + "]";
	}
	
	
	
}