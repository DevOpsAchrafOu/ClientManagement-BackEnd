/**
 * 
 */
package ma.mang.be.api.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * @author achraf
 * @version v0.2
 */
@Entity
@Table(name = "menues")
public class Menu {
	
	private long id;
    private String titleAr;
    private String titleFr;
	private String url;
	private int ordre;
	private String typeNav;
	private String icon;
	private Menu parent;
	private List<Menu> children = new ArrayList<>();
	
	
	public Menu() {
		super();
	}
	
	public Menu(long id, String titleAr,String titleFr, String url, int order, String typeNav,String icon) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.url = url;
		this.ordre = order;
		this.typeNav = typeNav;
		this.icon = icon;
	}

	public Menu(long id, String titleAr,String titleFr, String url, int order, String typeNav,String icon,long parentId) {
		super();
		this.id = id;
		this.titleAr = titleAr;
		this.titleFr = titleFr;
		this.url = url;
		this.ordre = order;
		this.typeNav = typeNav;
		this.icon = icon;
		this.parent=parentId==0?null:new Menu(parentId);
	}


	public Menu(long id) {
		super();
		this.id=id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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


	@Column(name="url")
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}


	@Column(name="`ORDER`")
	public int getOrdre() {
		return ordre;
	}



	public void setOrdre(int order) {
		this.ordre = order;
	}


	@Column(name="typeNav")
	public String getTypeNav() {
		return typeNav;
	}

	public void setTypeNav(String typeNav) {
		this.typeNav = typeNav;
	}

	@Column(name="icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	@OneToOne
	@JoinColumn(name = "parent_id")
	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
}
