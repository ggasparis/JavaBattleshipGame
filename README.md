# Battleship Game 

### A naive implementation of the popular battleship game

A project given from the course of [Multimedia Lab](http://www.medialab.ntua.gr/main.html)

### Oblective
The task  is to implement the Battleship Game, where the user plays against the computer. The winner of the game is the one who will sink the opponent's ships first or collect the most points.

### Main assumptions 

* The placement of the ships in the game boards is not done from the
txt files as described in the pronunciation, but by clicking on the board. The import of the files is supported only graphically without the corresponding action being performed
* The measurement of the accuracy of the Hits (accuracy score) has not been implemented.
* In the options menus on the left side-bar the corresponding
options are graphically implemented as described by the utterance, however not all
actions are supported.
* The graphics on the boards are not numbered.

### Starting a game
At the start of the application and the assumptions we have made, the user is asked to place his ships by clicking on his board (bottom table). Left click is for vertical placement, while right-click is for horizontal placement. The opponent's ships are placed at random. After placing all 5 ships, it is random who will start the game, while the user is notified with a pop-up message.

### Game Procedure
Once the ships have been placed and the game has started, the user clicks on the opponent's board (top board) in order to hit a cell with a ship andsink it. Similarly, the computer randomly "hits" the user's panel until it hits a hit. In contrast to the freedom of the user and his possibilities that lies in his perception of the most optimal next hit, for the PC a naive ai is used where in essence each hit is random whether it has been preceded by a successful hit or not. Therefore the game is not particularly difficult for the user. The game ends either with the exhaustion of the remaining efforts or after a player manages to sink all the opponent's ships.

From the right bar, the remaining shots of both the user and the PC are monitored. In the upper bar are recorded the sinking ships of the players and the points they collect from successful hits and sinks. In the left bar there are two menu options Applications and Details. In Applications there are three options as required by the pronunciation, where by clicking the corresponding pop-up message is displayed. However, the corresponding action was not performed in the application. The Details with the corresponding option show the last five shots of the players and their outcome. The first option Enemy Ships has been implemented only graphically, without making further calculations in the application.
The winner of the game is judged either the one who will have sunk all the opponent's ships or the one who will have collected the most points with the exhaustion of the remaining efforts.

### Implementation planning

The classes implemented for the application:
* **Main class**: The main class that contains the main method that "runs" the game.
There is also the main volume of the implementation of the game.
  * Divide the screen into three parts and configure three side-bars (top,
right, left).
 
  * The creation of the left bar menus, where the graphical requirements of the work have been implemented, but the actions that are fully supported are the history of the last 5 shots of the user and the PC.
  * The creation of the top bar where the remaining ships are listed in order on the boards of the user and the PC and the points collected by the player or the PC, including the immersion bonuses.
  * Right bar with the calculation of the remaining efforts.
  * Placement and description of ships on boards
  * Check the necessary conditions for who won.
* **Ship class**: The class in which the characteristics of the ship are defined and the variables that collect the points are initialized. It has methods for:
  * Checking the condition of the ship. That is, if it has been hit.
  * Checking if it has sunk.
  * The measurement of the condition of the ship.
* **Board class**: The class for the implementation of the game boards. It has methods for:
  * The creation and placement of tables on the screen.
  * The placement of the ships in the tables
  * The control of the observance of the limits in the placement of the ships
  * The control for the correct selection of cells
  * The control for the correct identification of the neighboring cells
* **Cell class**: The class for the implementation of the cells. Contains methods for:
○ Creating cells by defining coordinates.
  * The "filling" of the cells with the corresponding color depending on whether there is a ship in the specific cell or not.

### Limitations and Exceptions
*  **AdjacentTilesException class**: To control the placement of ships so that they are not glued.
InvalidCountException  class: To control the number of ships placed.
*  **OverlapTilesException class**: To avoid placing a ship on top of another.
Vers  OversizeException class: To control the application of the correct size of the ships so that they do not exceed the permissible limits.













