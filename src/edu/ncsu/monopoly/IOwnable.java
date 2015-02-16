package edu.ncsu.monopoly;

public interface IOwnable {

	public abstract Player getOwner();

	public abstract void setOwner(Player owner);

}