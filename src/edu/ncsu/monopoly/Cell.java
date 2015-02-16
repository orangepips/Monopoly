package edu.ncsu.monopoly;

public abstract class Cell implements IOwnable {
	private boolean available = true;
	private String name;
	protected Player owner;

	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.monopoly.IOwnable#getOwner()
	 */
	@Override
	public Player getOwner() {
		return owner;
	}
	
	public int getPrice() {
		return 0;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public abstract void playAction();

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see edu.ncsu.monopoly.IOwnable#setOwner(edu.ncsu.monopoly.Player)
	 */
	@Override
	public void setOwner(Player owner) {
		this.owner = owner;
	}
    
    public String toString() {
        return name;
    }
}
