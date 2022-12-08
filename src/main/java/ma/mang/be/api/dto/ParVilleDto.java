/**
 * 
 */
package ma.mang.be.api.dto;

import java.util.ArrayList;
import java.util.List;

import ma.mang.be.api.model.ParVille;

/**
 * @author achraf
 *
 */
public class ParVilleDto {
	
	private long id;
    private String labelAr;
    private String labelFr;
    private long idPays;
    
    public ParVilleDto() {
		super();
	}

    public ParVilleDto(long id, String labelAr, String labelFr) {
		super();
		this.id = id;
		this.labelAr = labelAr;
		this.labelFr = labelFr;
	}
    public ParVilleDto(long id, String labelAr, String labelFr,long idPays) {
		super();
		this.id = id;
		this.labelAr = labelAr;
		this.labelFr = labelFr;
		this.idPays = idPays; 
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLabelAr() {
		return labelAr;
	}

	public void setLabelAr(String labelAr) {
		this.labelAr = labelAr;
	}

	public String getLabelFr() {
		return labelFr;
	}

	public void setLabelFr(String labelFr) {
		this.labelFr = labelFr;
	}

	public long getIdPays() {
		return idPays;
	}

	public void setIdPays(long idPays) {
		this.idPays = idPays;
	}

	public static ParVilleDto from(ParVille ville) {
		if(ville!=null) {
			return new ParVilleDto(ville.getId(),
					ville.getLabelAr(),
					ville.getLabelFr(),
					ville.getPays().getId()
					);
		}
		 return new ParVilleDto();
		
	}
	
	public static List<ParVilleDto> from(List<ParVille> villes) {
		List<ParVilleDto> villeDtos = new ArrayList<>();
		if(villes!=null) {
			for(ParVille f : villes) {
				villeDtos.add(from(f));
			}
		}
		 return villeDtos;
		
	}
	public static ParVille to(ParVilleDto ville) {
		if(ville!=null) {
			return new ParVille(ville.getId(),
					ville.getLabelAr(),
					ville.getLabelFr(),
					ville.getIdPays()
					);
		}
		 return new ParVille();
		
	}
	
	
}