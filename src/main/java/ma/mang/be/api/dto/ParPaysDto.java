package ma.mang.be.api.dto;


import java.util.ArrayList;
import java.util.List;

import ma.mang.be.api.entity.ParPays;

/**
 * DTO Class to manipulate ParPays objects in the front side
 * @author achraf
 * @version v0.1
 *
 */

public class ParPaysDto {
	
	private long id;
    private String titleAr;
    private String titleFr;

    public ParPaysDto() {
		super();
	}

	public ParPaysDto(long id, String titleAr, String titleFr) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
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
	

	public static ParPaysDto convertToDto(ParPays pays) {
		if(pays!=null) {
			return new ParPaysDto(pays.getId(),
					pays.getTitleAr(),
					pays.getTitleFr()
					);
		}
		 return new ParPaysDto();
		
	}
	
	public static List<ParPaysDto> convertToDto(List<ParPays> payss) {
		List<ParPaysDto> paysDtos = new ArrayList<>();
		if(payss!=null) {
			for(ParPays f : payss) {
				paysDtos.add(convertToDto(f));
			}
		}
		 return paysDtos;
		
	}
	public static ParPays convertToEntity(ParPaysDto pays) {
		if(pays!=null) {
			return new ParPays(pays.getId(),
					pays.getTitleAr(),
					pays.getTitleFr()
					);
		}
		 return new ParPays();
		
	}
	
	
}