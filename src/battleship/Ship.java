package battleship;

import javafx.scene.Parent;

public class Ship extends Parent {

    public String shipType;
    public int hitScore;
    public int isSunkBonus;
    public int type;
    public boolean vertical;

    private int health;

    /**
     * Method to describe the ship's features
     * @param type of the ship with the specific length
     * @param vertical boolean for the ship placement
     * @param hitScore for keeping the hit points
     * @param isSunkBonus points if the ship sinks
     * @param shipType name of the ship
     */
    public Ship(int type, boolean vertical, int hitScore, int isSunkBonus, String shipType) {
        this.type = type;
        this.vertical = vertical;
        this.hitScore = hitScore;
        this.isSunkBonus = isSunkBonus;
        this.shipType = shipType;
        health = type;
    }

    /**
     * Method for checking if the specific type ship is hit
     * @return true-false if is hit
     */
    public boolean isHit(){
        return !(health == type);
    }

    /**
     * Method to check if ship is sunk or not
     * @return
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Ship health
     */
    public void hit() {
        health--;
    }
}