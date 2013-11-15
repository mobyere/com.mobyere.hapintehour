package com.mobyere.hapintehour.dao;

public class FavorisMetier {

	private long id;
	private long barId;
	
	public FavorisMetier() {
	}
	
	public FavorisMetier(long id, long barId) {
		super();
		this.id = id;
		this.barId = barId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBarId() {
		return barId;
	}
	public void setBarId(long barId) {
		this.barId = barId;
	}
}
