package it.jflower.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

//@Entity
public class MenuGroup implements Serializable {

	private Long id;
	private String start;
	private String stop;
	private String start_tag;
	private String stop_tag;
	private List<Menu> items;
	private boolean open;

	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getStart_tag() {
		return start_tag;
	}

	public void setStart_tag(String startTag) {
		start_tag = startTag;
	}

	public String getStop_tag() {
		return stop_tag;
	}

	public void setStop_tag(String stopTag) {
		stop_tag = stopTag;
	}

	@OneToMany(mappedBy = "menuGroup", fetch = FetchType.LAZY)
	@OrderBy("ordine")
	public List<Menu> getItems() {
		if (this.items == null)
			this.items = new ArrayList<Menu>();
		return items;
	}

	public void setItems(List<Menu> items) {
		this.items = items;
	}

	public void addItem(Menu menu) {
		getItems().add(menu);
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
}
