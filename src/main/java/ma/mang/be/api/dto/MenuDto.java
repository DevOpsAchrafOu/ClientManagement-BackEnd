package ma.mang.be.api.dto;

import java.util.ArrayList;
import java.util.List;
import ma.mang.be.api.entity.Menu;


/**
 * DTO Class to manipulate Menu objects in the front side
 * @author achraf
 * @version v0.2
 */
public class MenuDto {
	
	private long id;
    private String titleAr;
    private String titleFr;
	private String url;
	private int order;
	private String typeNav;
	private String icon;
	private long parentId;
	private MenuDto parent;
	private List<MenuDto> children = new ArrayList<>();
	private boolean hasChildren;
	
	public MenuDto() {
		super();
	}
	
	public MenuDto(long id, String titleAr, String titleFr, String url, int order, String typeNav,String icon,
			List<MenuDto> children , boolean hasChildren) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.url = url;
		this.order = order;
		this.typeNav = typeNav;
		this.icon = icon;
		this.children = children;
		this.hasChildren = hasChildren;
	}
	

	public MenuDto(long id, String titleAr, String titleFr, String url, int order, String typeNav,String icon) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.url = url;
		this.order = order;
		this.typeNav = typeNav;
		this.icon = icon;
	}
	
	public MenuDto(long id, String titleAr, String titleFr, String url, int order, String typeNav,String icon,long parentId) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.url = url;
		this.order = order;
		this.typeNav = typeNav;
		this.icon = icon;
		this.parentId = parentId;
		
	}
	
	public MenuDto(long id, String titleAr, String titleFr, String url, int order, String typeNav,String icon,long parentId,
			List<MenuDto> children , boolean hasChildren) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.url = url;
		this.order = order;
		this.typeNav = typeNav;
		this.icon = icon;
		this.parentId = parentId;
		this.children = children;
		this.hasChildren = hasChildren;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getTypeNav() {
		return typeNav;
	}

	public void setTypeNav(String typeNav) {
		this.typeNav = typeNav;
	}
	

	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public MenuDto getParent() {
		return parent;
	}

	public void setParent(MenuDto parent) {
		this.parent = parent;
	}

	public List<MenuDto> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDto> children) {
		this.children = children;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	public static Menu from(MenuDto dto) {
		return new Menu(
				dto.getId(),
				dto.getTitleAr(),
				dto.getTitleFr(),
				dto.getUrl(),
				dto.getOrder(),
				dto.getTypeNav(),
				dto.getIcon(),
				dto.getParentId());
		
	}
	
	public static MenuDto to(Menu menu) {
		return new MenuDto(menu.getId(),
				menu.getTitleAr(),
				menu.getTitleFr(),
				menu.getUrl(),
				menu.getOrdre(),
				menu.getTypeNav(),
				menu.getIcon(),
				menu.getParent()!=null ? menu.getParent().getId():0
				);
	}
	
	public static MenuDto counverTo(Menu menu) {
		return new MenuDto(menu.getId(),
				menu.getTitleAr(),
				menu.getTitleFr(),
				menu.getUrl(),
				menu.getOrdre(),
				menu.getTypeNav(),
				menu.getIcon(),
				menu.getParent()!=null ? menu.getParent().getId():0,
				MenuDto.to(menu.getChildren()),
				menu.getChildren().size() > 0 ? true : false
				);
	}
	
	public static List<MenuDto> to(List<Menu> menus) {
		List<MenuDto> menusDtos = new ArrayList<>();
		for(Menu ct : menus) {
			menusDtos.add(to (ct));
		}
		return menusDtos;
		
	}
	
	public static List<MenuDto> counverTo(List<Menu> menus) {
		List<MenuDto> menusDtos = new ArrayList<>();
		for(Menu ct : menus) {
			menusDtos.add(counverTo (ct));
		}
		return menusDtos;
		
	}

}
