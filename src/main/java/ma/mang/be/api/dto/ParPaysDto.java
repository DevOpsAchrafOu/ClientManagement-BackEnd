/**
 * 
 */
package ma.mang.be.api.dto;


import java.util.ArrayList;
import java.util.List;

import ma.mang.be.api.model.ParPays;

/**
 * @author achraf
 *
 */
public class ParPaysDto {
	
	private long id;
    private String labelAr;
    private String labelFr;

    public ParPaysDto() {
		super();
	}

	public ParPaysDto(long id, String labelAr, String labelFr) {
		super();
		this.id = id;
		this.labelAr = labelAr;
		this.labelFr = labelFr;
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
	

	public static ParPaysDto from(ParPays pays) {
		if(pays!=null) {
			return new ParPaysDto(pays.getId(),
					pays.getLabelAr(),
					pays.getLabelFr()
					);
		}
		 return new ParPaysDto();
		
	}
	
	public static List<ParPaysDto> from(List<ParPays> payss) {
		List<ParPaysDto> paysDtos = new ArrayList<>();
		if(payss!=null) {
			for(ParPays f : payss) {
				paysDtos.add(from(f));
			}
		}
		 return paysDtos;
		
	}
	public static ParPays to(ParPaysDto pays) {
		if(pays!=null) {
			return new ParPays(pays.getId(),
					pays.getLabelAr(),
					pays.getLabelFr()
					);
		}
		 return new ParPays();
		
	}
	
	
}