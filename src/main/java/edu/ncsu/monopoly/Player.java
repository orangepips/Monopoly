package edu.ncsu.monopoly;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * <p><Monopoly player representation that keeps track of the following:</p>
 * <ul>
 * <li>Position on the playing board including in Jail status.</li>
 * <li>Money in hand</li>
 * <li>Properties owned</li>
 * </ul>
 */
public class Player {
    /**
     * Maximum number of houses allowed for a monopoly
     */
    public static final int MAX_MONOPOLY_HOUSES = 5;

    /**
     * Set of color values representing {@link Cell} implementations that are not {@link PropertyCell}
     */
    private static final HashSet<String> NON_PROPERTY_COLORS = new HashSet<String>() {{
        add(RailRoadCell.COLOR_GROUP);
        add(UtilityCell.COLOR_GROUP);
    }};

    /**
     * The key of colorGroups is the name of the color group.
     */
    private Hashtable colorGroups = new Hashtable();

    /**
     * True if the player is in jail.
     */
    private boolean inJail;

    /**
     * Amount of money in player's hand.
     */
    private int money;

    /**
     * Player name displayed in the GUI. Collected at the start of the program.
     */
    private String name;

    /**
     * Player position on the game board.
     */
    private Cell position;

    /**
     * All properties that can take houses owned by player.
     */
    private ArrayList properties = new ArrayList();

    /**
     * All railroads owned by player.
     */
    private ArrayList railroads = new ArrayList();

    /**
     * All utilities owned by player.
     */
    private ArrayList utilities = new ArrayList();

    /**
     * Constructor. Places the player on the "Go" (i.e. start) cell of the game board.
     */
    public Player() {
        GameBoard gb = GameMaster.instance().getGameBoard();
        inJail = false;
        if (gb != null) {
            position = gb.queryCell("Go");
        }
    }

    /**
     * Assigns player ownership of specified property and deducts amount from money.
     *
     * @param property property to assign player ownership of
     * @param amount   value to deduct from player money
     */
    public void buyProperty(final IOwnable property, final int amount) {
        property.setOwner(this);
        verifyPurchaseProperty(property);
        setMoney(getMoney() - amount);
    }

    /**
     * True if the player is allowed to buy houses. Does not check relative to the propert(ies) being considered.
     *
     * @return False if the player does not own any monopolies.
     */
    public boolean canBuyHouse() {
        return getMonopolies().length != 0;
    }

    /**
     * Aggregation of all player owned entities.
     *
     * @return array of all properties, railroads and utilities owned by this player.
     */
    public IOwnable[] getAllProperties() {
        ArrayList list = new ArrayList();
        list.addAll(properties);
        list.addAll(utilities);
        list.addAll(railroads);
        return (IOwnable[]) list.toArray(new IOwnable[list.size()]);
    }

    /**
     * How much money the player has.
     *
     * @return money in player's possession.
     */
    public int getMoney() {
        return this.money;
    }

    /**
     * Set how much money player has.
     *
     * @param money money in player's possession.
     */
    public void setMoney(final int money) {
        this.money = money;
    }

    /**
     * All player owned monopolies.
     *
     * @return array of player owned monopolies
     */
    public String[] getMonopolies() {
        ArrayList monopolies = new ArrayList();
        Enumeration colors = colorGroups.keys();
        while (colors.hasMoreElements()) {
            String color = (String) colors.nextElement();

            if (NON_PROPERTY_COLORS.contains(color)) continue;

            Integer num = (Integer) colorGroups.get(color);
            GameBoard gameBoard = GameMaster.instance().getGameBoard();
            if (num.intValue() == gameBoard.getPropertyNumberForColor(color)) {
                monopolies.add(color);
            }
        }
        return (String[]) monopolies.toArray(new String[monopolies.size()]);
    }

    /**
     * Screen name.
     *
     * @return name assigned to this player.
     */
    public String getName() {
        return name;
    }

    /**
     * Set player screen name.
     *
     * @param name screen name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Deduct bail amount from player's money unless bankrupt in which case money is set to zero.
     * Marks player is no in jail and updates the GUI to reflect that.
     */
    public void getOutOfJail() {
        money -= JailCell.BAIL;
        if (isBankrupt()) {
            money = 0;
            exchangeProperty(null);
        }
        inJail = false;
        GameMaster.instance().updateGUI();
    }

    /**
     * Player's current position on the game board.
     *
     * @return cell object representing current game board positon
     */
    public Cell getPosition() {
        return this.position;
    }

    /**
     * Set player position on the gameboard.
     *
     * @param newPosition player position on the gameboard
     */
    public void setPosition(final Cell newPosition) {
        this.position = newPosition;
    }

    /**
     * Is the player bankrupt?
     *
     * @return True if money is less than or equal to zero.
     */
    public boolean isBankrupt() {
        return money <= 0;
    }

    /**
     * Is player in jail?
     *
     * @return True if player is in jail.
     */
    public boolean isInJail() {
        return inJail;
    }

    /**
     * Set if player is in jail.
     *
     * @param inJail True if player is in jail.
     */
    public void setInJail(final boolean inJail) {
        this.inJail = inJail;
    }

    /**
     * Number of railroads in player's posession.
     *
     * @return number of railroads in player's posession.
     */
    public int numberOfRR() {
        return getPropertyNumberForColor(RailRoadCell.COLOR_GROUP);
    }

    /**
     * Number of utilities in player's posession.
     *
     * @return number of utilities in player's posession.
     */
    public int numberOfUtil() {
        return getPropertyNumberForColor(UtilityCell.COLOR_GROUP);
    }

    /**
     * Have this player pay property rent to another player. If player does not have enough money give the
     * property owner all of the player's money, mark the player as bankrupt, and assign a
     *
     * @param owner     player owning property
     * @param rentValue amount owed in rent.
     */
    public void payRentTo(final Player owner, final int rentValue) {
        if (money < rentValue) {
            owner.money += money;
            money -= rentValue;
        } else {
            money -= rentValue;
            owner.money += rentValue;
        }
        if (isBankrupt()) {
            money = 0;
            exchangeProperty(owner);
        }
    }

    /**
     * Purchase the cell at the player's current position if it can be purchased.
     */
    public void purchase() {
        if (!(getPosition() instanceof OwnedCell) || !((OwnedCell) getPosition()).isAvailable()) {
            return;
        }

        Cell c = getPosition();
        ((OwnedCell) c).setAvailable(false);
        isPropertyCell(c);
        isRailRoadCell(c);
        isUtilityCell(c);
    }

    /**
     * Purchase a specified number of houses for a monopoly of a given color if money available.
     * Allows no more than {@link #MAX_MONOPOLY_HOUSES} house purchases for the monopoly.
     *
     * @param selectedMonopoly color of the properties to buy houses for
     * @param houses           number of houses to buy
     */
    public void purchaseHouse(final String selectedMonopoly, final int houses) {
        PropertyCell[] cells = GameMaster.instance().getGameBoard().getPropertiesInMonopoly(selectedMonopoly);

        boolean doesNotHaveEnoughMoney = !(money >= (cells.length * (cells[0].getHousePrice() * houses)));
        if (doesNotHaveEnoughMoney) return;

        for (int i = 0; i < cells.length; i++) {
            int newNumber = cells[i].getNumHouses() + houses;

            if (newNumber > MAX_MONOPOLY_HOUSES) continue;

            cells[i].setNumHouses(newNumber);
            this.setMoney(money - cells[i].getHousePrice() * houses);
            GameMaster.instance().updateGUI();
        }
    }

    /**
     * Sell property back to the bank for the specified amount. Remove player as the owner.
     *
     * @param property property to sell to the bank
     * @param amount   money to credit player
     */
    public void sellProperty(final IOwnable property, final int amount) {
        property.setOwner(null);
        if (property instanceof PropertyCell) {
            properties.remove(property);
        }
        if (property instanceof RailRoadCell) {
            railroads.remove(property);
        }
        if (property instanceof UtilityCell) {
            utilities.remove(property);
        }
        setMoney(getMoney() + amount);
    }

    /**
     * Equivalent to the player's name.
     *
     * @return player name.
     */
    public String toString() {
        return name;
    }

    /**
     * Ensures named property belongs to this Player.
     *
     * @param property name of property to check ownership of
     * @return True if player owns this property
     */
    boolean checkProperty(final String property) {
        for (int i = 0; i < properties.size(); i++) {
            Cell cell = (Cell) properties.get(i);
            if (cell.getName().equals(property)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Change ownership of all this player's properties to parameter player .
     * If parameter player is null, set all properties as available and houses from them.
     *
     * @param player player assuming ownership of this player's properties.
     */
    void exchangeProperty(final Player player) {
        for (int i = 0; i < getPropertyNumber(); i++) {
            PropertyCell cell = getProperty(i);
            cell.setOwner(player);
            if (player == null) {
                cell.setAvailable(true);
                cell.setNumHouses(0);
            } else {
                player.properties.add(cell);
                colorGroups.put(
                        cell.getColorGroup(),
                        new Integer(getPropertyNumberForColor(cell.getColorGroup()) + 1));
            }
        }
        properties.clear();
    }

    /**
     * Returns the property object for an associated ordinal.
     *
     * @param index the ordinal of an associated property
     * @return property associated with the passed ordinal
     */
    PropertyCell getProperty(final int index) {
        return (PropertyCell) properties.get(index);
    }

    /**
     * Number of properties in player's possession.
     *
     * @return count of player owned properties properties
     */
    int getPropertyNumber() {
        return properties.size();
    }

    /**
     * Clear tracking of all player properties, railroads and utilities.
     */
    void resetProperty() {
        properties = new ArrayList();
        railroads = new ArrayList();
        utilities = new ArrayList();
    }

    /**
     * Number of properties player owns for a passed name.
     *
     * @param name either a color, utility or railroad to count ownership of
     * @return count of properties owned for that name. Zero if none.
     */
    private int getPropertyNumberForColor(final String name) {
        Integer number = (Integer) colorGroups.get(name);
        if (number != null) {
            return number.intValue();
        }
        return 0;
    }

    /**
     * If passed cell is a property purchase.
     *
     * @param c cell to check as a property and purchase if so
     */
    private void isPropertyCell(final Cell c) {
        if (!(c instanceof PropertyCell)) return;

        PropertyCell cell = (PropertyCell) c;
        buyProperty(cell, cell.getPrice());
    }

    /**
     * If passed cell is a railroad purchase.
     *
     * @param c cell to check as a railroad and purchase if so
     */
    private void isRailRoadCell(final Cell c) {
        if (!(c instanceof RailRoadCell)) return;

        OwnedCell cell = (OwnedCell) c;
        buyProperty(cell, cell.getPrice());
    }

    /**
     * If passed cell is a utility purchase.
     *
     * @param c cell to check as a utility and purchase if so
     */
    private void isUtilityCell(final Cell c) {
        if (!(c instanceof UtilityCell)) return;

        UtilityCell cell = (UtilityCell) c;
        buyProperty(cell, cell.getPrice());
    }

    /**
     * Evaluates class type of passed property and tracks player as the owner.
     *
     * @param property property to assign to this player
     */
    private void verifyPurchaseProperty(final IOwnable property) {
        if (property instanceof UtilityCell) {
            utilities.add(property);
            colorGroups.put(
                    UtilityCell.COLOR_GROUP,
                    new Integer(getPropertyNumberForColor(UtilityCell.COLOR_GROUP) + 1));
        }
        if (property instanceof RailRoadCell) {
            railroads.add(property);
            colorGroups.put(
                    RailRoadCell.COLOR_GROUP,
                    new Integer(getPropertyNumberForColor(RailRoadCell.COLOR_GROUP) + 1));
        }
        if (property instanceof PropertyCell) {
            PropertyCell cell = (PropertyCell) property;
            properties.add(cell);
            colorGroups.put(
                    cell.getColorGroup(),
                    new Integer(getPropertyNumberForColor(cell.getColorGroup()) + 1));
        }
    }

}
