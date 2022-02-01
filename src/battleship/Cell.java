package battleship;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    public int x, y;
    public Ship ship = null;

    public boolean wasShot = false;
    public int highscore=0;
    public int perc=0;

    private Board board;

    /**
     * Method for creating Cell
     * @param x horizontal coordinates
     * @param y vertical coordinates
     * @param board play board
     */
    public Cell(int x, int y, Board board) {
        super(30, 30);
        this.x = x;
        this.y = y;
        this.board = board;
        setFill(Color.LIGHTBLUE);
        setStroke(Color.BLACK);
    }

    /**
     * Method for filing the Cell with the right color (hit-miss)
     * * @return true or false for a hit or a miss
     */
    public boolean shoot() {
        wasShot = true;
        setFill(Color.YELLOWGREEN);

        if (ship != null) {
            ship.hit();
            highscore =+ ship.hitScore;
            setFill(Color.RED);
            if (!ship.isAlive()) {
                highscore = highscore+ ship.isSunkBonus;
                board.ships--;
            }
            return true;
        }
        return false;
    }
}