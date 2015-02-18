package edu.ncsu.monopoly;

public class GoToJailCell extends Cell {
	
	public GoToJailCell() {
		setName("Go to Jail");
	}

	public void playAction() {
		Player currentPlayer = GameMaster.instance().getCurrentPlayer();
		JailCell jail = (JailCell)(GameMaster.instance().getGameBoard().queryCell("Jail"));
		GameMaster.instance().sendToJail(currentPlayer);
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
