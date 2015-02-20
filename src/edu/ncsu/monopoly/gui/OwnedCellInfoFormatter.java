package edu.ncsu.monopoly.gui;

import edu.ncsu.monopoly.Cell;
import edu.ncsu.monopoly.OwnedCell;
import edu.ncsu.monopoly.Player;


public abstract class OwnedCellInfoFormatter implements CellInfoFormatter{

	public OwnedCellInfoFormatter() {
		super();
		
	}

	protected String getOwnerName(Cell cell) {
		Player owner = ((OwnedCell) cell).getOwner();
	    String ownerName = "";
	    if(owner != null) {
	    	ownerName = owner.getName();
	    }
		return ownerName;
	}
	
}