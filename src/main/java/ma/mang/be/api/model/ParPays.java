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
    private String labelAr;
    private String labelFr;

    public ParPays() {
		super();
	}

    
	public ParPays(long id, String labelAr, String labelFr) {
		super();
		this.id = id;
		this.labelAr = labelAr;
		this.labelFr = labelFr;
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
	
}