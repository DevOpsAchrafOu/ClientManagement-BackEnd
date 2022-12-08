/**
 * 
 */
package ma.mang.be.api.model;

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
 *
 */
@Entity
@Table(name = "par_ville")
public class ParVille {
	
	private long id;
    private String labelAr;
    private String labelFr;
	private ParPays pays;

    public ParVille() {
		super();
	}

    
	public ParVille(long id, String labelAr, String labelFr) {
		super();
		this.id = id;
		this.labelAr = labelAr;
		this.labelFr = labelFr;
	}
	
	public ParVille(long id, String labelAr, String labelFr,long idPays) {
		super();
		this.id = id;
		this.labelAr = labelAr;
		this.labelFr = labelFr;
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

	@Column(name="label_ar")
	public String getLabelAr() {
		return labelAr;
	}

	public void setLabelAr(String labelAr) {
		this.labelAr = labelAr;
	}

	@Column(name="label_fr")
	public String getLabelFr() {
		return labelFr;
	}

	public void setLabelFr(String labelFr) {
		this.labelFr = labelFr;
	}
	@OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "pays_id", referencedColumnName = "id")
	public ParPays getPays() {
		return pays;
	}


	public void setPays(ParPays pays) {
		this.pays = pays;
	}
	
}