package edu.ncsu.monopoly;

public class FreeParkingCell extends Cell {

	public FreeParkingCell() {
		setName("Free Parking");
	}

	public void playAction() {
		return;
	}

	@Override
	public Player getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(Player owner) {
		// TODO Auto-generated method stub
		
	}
}
