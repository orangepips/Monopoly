package edu.ncsu.monopoly;

public abstract class Cell implements IOwnable {
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

	public abstract void playAction();

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
