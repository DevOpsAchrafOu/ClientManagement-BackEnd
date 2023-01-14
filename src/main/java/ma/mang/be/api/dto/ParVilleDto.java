/**
 * 
 */
package ma.mang.be.api.dto;

import java.util.ArrayList;
import java.util.List;

import ma.mang.be.api.entity.ParVille;


/**
 * DTO Class to manipulate ParVille objects in the front side
 * @author achraf
 * @version v1.0
 *
 */
public class ParVilleDto {
	
	private long id;
    private String titleAr;
    private String titleFr;
    private long idPays;
    
    public ParVilleDto() {
		super();
	}

    public ParVilleDto(long id, String titleAr, String titleFr) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
	}
    public ParVilleDto(long id, String titleAr, String titleFr,long idPays) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.idPays = idPays; 
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitleAr() {
		return titleAr;
	}

	public void setTitleAr(String titleAr) {
		this.titleAr = titleAr;
	}

	public String getTitleFr() {
		return titleFr;
	}

	public void setTitleFr(String titleFr) {
		this.titleFr = titleFr;
	}

	public long getIdPays() {
		return idPays;
	}

	public void setIdPays(long idPays) {
		this.idPays = idPays;
	}

	public static ParVilleDto convertToDto(ParVille ville) {
		if(ville!=null) {
			return new ParVilleDto(ville.getId(),
					ville.getTitleAr(),
					ville.getTitleFr(),
					ville.getPays().getId()
					);
		}
		 return new ParVilleDto();
		
	}
	
	public static List<ParVilleDto> convertToDto(List<ParVille> villes) {
		List<ParVilleDto> villeDtos = new ArrayList<>();
		if(villes!=null) {
			for(ParVille f : villes) {
				villeDtos.add(convertToDto(f));
			}
		}
		 return villeDtos;
		
	}
	public static ParVille convertToEntity(ParVilleDto ville) {
		if(ville!=null) {
			return new ParVille(ville.getId(),
					ville.getTitleAr(),
					ville.getTitleFr(),
					ville.getIdPays()
					);
		}
		 return new ParVille();
		
	}
	
	
}