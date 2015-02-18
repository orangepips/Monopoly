package edu.ncsu.monopoly;

public abstract class OwnedCell extends Cell {

	private boolean available = true;

	public OwnedCell() {
		super();
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}