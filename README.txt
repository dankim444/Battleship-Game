===================
=: Core Concepts :=
===================

  1. 2d array of Strings. This represents the game board and stores all information about the different types of
  ships. Using strings was appropriate because I needed a way to represent the variety of different ship types and
  their orientations. "_" represents water, "1" represents a submarine, "H2" represents a horizontal cruiser, "V2"
  represents a vertical cruiser, "H3" represents a horizontal battleship, "V3" represents a vertical battleships,
  "H4" represents a horizontal carrier, and "V4" represents a vertical cruiser. Note, the number that corresponds to
  each type of ship represents the amount of squares it occupies on the board. Additionally, when a hit is successful,
  the corresponding cell is changed to "X" to reveal the portion of the ship that was hit; otherwise, it is changed to
  an "O" to indicate a miss. When a ship is fully destroyed, the destroyed ship is indicated by a sequence of "X"
  values on the board, and the border surrounding the destroyed ship is indicated by a bunch of "x" values.
  Additionally, the 2D array makes it easier to randomize the positions of the ships, by allowing me to choose a
  random cell with random row/column indices and verifying that the ship to be placed doesn't overlap any ships or
  their respective borders.

  2. Collections. The specific collection I used was a LinkedList called gameState to store instances of each Battleship
   game. The BattleshipBoard class holds the list, and it is initialized in the BattleshipBoard constructor, and
   it adds the initialized Battleship called "model" to the list. Whenever the model is updated (ie. by taking a shot),
   the newly updated model is added to the end of the list. So whenever undo is called, I could just remove the
   most recent instance of Battleship by calling .removeLast() and storing the second to last instance in the model.

  3. JUnit Testing. The methods I have stores the position (int, row) and information about the specific ship type
  in each cell of the board. I exhaustively test each method, ensuring that my implementation is correct, by testing
  correct ship placement, making sure that ships are placed correctly on the board and that none are overlapping each
  other. I also test for correct hit/miss detection to ensure that the game properly updates the board when the
  player has made a shot.  I will also test for game-over state, making sure that the game properly ends after all
  ships have been sunk.

  4. File I/O. I implement File I/O to save the game state as a text file. Specifically, there will be a save button that when
  clicked, the game will import all the information stored in the 2d array to a text file. Each row will represent a
  string of strings where each string value is separated by a space and each sequential row is represented by a new
  line in the file. The File/IO feature will then parse the data to properly display the board's state when the player
  wants to save the game.

=========================
=: Implementation :=
=========================

  Battleship.java - Represents the model and holds most of the game logic. The Battleship class instantiates the 2D
  array that represents the board game in the constructor, initially setting all cells to water, and then calling
  placeShipsRandomly() to fully randomize the placement of the 10 ships. It also has a takeShots() method, which is
  analogous to Tictactoe's playTurn method, and takes in the row and column indices as parameters. takeShot allows
  a player to open fire on the corresponding cell specified by the parameters. The Battleship class also has basic
  getters and setters for the instance variables.

  BattleshipBoard.java - This class stores the model as a field and acts as both the controller (with a MouseListener)
  and the view (with its paintComponent method and the status JLabel). It also has a linked list instance variable
  called gameState that stores each instance of the Battleship game each time a shot is taken. This is used to easily
  undo a move. This class allows for listening of mouse events to update the game based off of the updated model. The
  class calls updateStatus() and repaint() whenever the new model is updated. In addition to undo(), this class also
  holds the saveGame() and loadGame() methods, which are called by RunBattleship.java.

  RunBattleship.java - Sets up the top level view and widgets for GUI. Helps the game to initialize the view,
  implements controller functionality through the undo, save, and load buttons, and also instantiates the game board.
  The Battleship object is the model, and it will handle the rest of the game's view and functionality.

