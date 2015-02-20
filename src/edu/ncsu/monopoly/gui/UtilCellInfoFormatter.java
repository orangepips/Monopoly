package edu.ncsu.monopoly.gui;

import edu.ncsu.monopoly.Cell;
import edu.ncsu.monopoly.OwnedCell;
import edu.ncsu.monopoly.Player;
import edu.ncsu.monopoly.UtilityCell;

public class UtilCellInfoFormatter extends OwnedCellInfoFormatter {

	public String format(Cell cell) {
        UtilityCell c = (UtilityCell)cell;
        StringBuffer buf = new StringBuffer();
        Player owner = ((OwnedCell) cell).getOwner();
        String ownerName = "";
        if(owner != null) {
        	ownerName = owner.getName();
        }
        buf.append("<html><b><font color='olive'>")
                .append(cell.getName())
                .append("</font></b><br>")
                .append("$").append(c.getPrice())
				.append("<br>Owner: ").append(ownerName)
                .append("</html>");
        return buf.toString();
	}
}
