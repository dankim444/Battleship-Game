package org.cis1200.Battleship;


/**
 * This class is the model for a 1-player Battleship game. This model
 * is similar to Game Pigeon's model for Battleship. It holds 10 ships,
 * including 4 submarines, 3 cruisers, 2 battleships, and 1 carrier.
 * The submarine occupies one space, a cruiser occupies two, a
 * battleship occupies three, and a carrier occupies four. All the
 * positions of the ships are fully randomized during each reset of
 * the board, and each type of ship is given an equal probability of
 * having a vertical orientation or a horizontal orientation. The
 * contents of the game are stored in a 2d array of strings, where "_"
 * represents water, "." represents a border around a ship, "x" represent
 * the border of a destroyed ship, "1" represents a submarine, "H2" and
 * "V2" represent a cruiser, "H3" and "V3" represent a battleship, "H4"
 * and "V4" represent a carrier. No ship can overlap another ship or a
 * border surrounding a ship.
 *
 * The player is given a limited amount of shots. If all ships are
 * destroyed before running out of shots, the player wins the game.
 * If the player runs out of shots before all ships are destroyed,
 * the player loses.
 *
 */
public class Battleship {

    private String[][] board;
    private int size;
    private int numShots; // player only has limited amount of shots
    private int sunkenShips; // keeps track of the number of ships that have been sunk
    private int countSubs; // board should start off with 4 submarines
    private int countCruisers; // board should start off with 3 cruisers
    private int countBattleships; // board should start off with 2 battleships
    private int countCarriers; // board should start off with 1 carrier

    /**
     * Constructor sets up game state.
     */
    public Battleship() {
        reset();
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        size = 10;
        board = new String[size][size];
        numShots = 65;
        sunkenShips = 0;

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                board[r][c] = "_"; // initialize all cells to water, denoted by "_"
            }
        }
        placeShipsRandomly(); // places ships in random positions on the board
    }

    /**
     * Returns a copy of a Battleship object
     */
    public Battleship(Battleship b) {
        this.numShots = b.getNumShots();
        this.sunkenShips = b.getSunkenShips();
        this.board = b.getCopyOfBoard();
    }

    /**
     * Returns a copy of the Battleship board.
     *
     * @return a 2D array of Strings.
     */
    public String[][] getCopyOfBoard() {
        String[][] board = new String[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                board[r][c] = this.board[r][c];
            }
        }
        return board;
    }

    /**
     * Randomizes the positions of the ships on the board. The placeShipsRandomly() method places
     * each type of ship separately, making sure that none of ships overlap with other ships or
     * the borders of other ships. This ensures that there are minimal biases in the patterns of the
     * ships' position placements. It also makes the game more interesting and less predictable by
     * creating unique placements whenever the board is reset.
     *
     * @return n/a
     */
    public void placeShipsRandomly() {
        // place submarines
        countSubs = 0; // board should have 4 submarines
        while (countSubs < 4) {
            boolean subNotInBorder = true;
            int row = (int) (Math.random() * 10); // generates a number from 0 to 9
            int col = (int) (Math.random() * 10);
            // checks that the randomly-generated row and column are in bounds
            if ((row < board.length) && (row >= 0) && (col < board.length) &&
                    (col >= 0)) {
                // verify that the sub and its border does not overlap any ship
                // or any borders of other ships
                for (int i = Math.max(row - 1, 0); i <
                        Math.min(row + 1, board.length - 1); i++) {
                    for (int j = Math.max(col - 1, 0); j <
                            Math.min(col + 1, board.length - 1); j++) {
                        if (board[i][j].equals(".") || (board[i][j].equals("1")) ||
                                (board[i][j].equals("H2")) || (board[i][j].equals("V2")) ||
                                (board[i][j].equals("H3")) || (board[i][j].equals("V3")) ||
                                (board[i][j].equals("H4")) || (board[i][j].equals("V4"))) {
                            subNotInBorder = false;
                        }
                    }
                }
                if (subNotInBorder) {
                    // creates a border around each ship
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 1, board.length - 1); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 1, board.length - 1); j++) {
                            board[i][j] = ".";
                        }
                    }
                    board[row][col] = "1"; // place submarine in the center of the border
                    countSubs++;
                }
            }
        }

        // place cruisers
        countCruisers = 0; // board should have 3 cruisers
        while (countCruisers < 3) {
            boolean cruiserNotInBorder = true;
            int row = (int) (Math.random() * 10);
            int col = (int) (Math.random() * 10);

            // simulates a coin flip - a number less than 50 is analogous to
            // heads, which indicates that the ship is oriented horizontally;
            // otherwise, it is oriented vertically.
            if (((int) (Math.random() * 100)) < 50) {
                if ((row < board.length) && (row >= 0) && (col + 1 < board.length) &&
                        (col >= 0)) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 1, board.length - 1); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length - 1); j++) {
                            if (board[i][j].equals(".") || (board[i][j].equals("1")) ||
                                    (board[i][j].equals("H2")) || (board[i][j].equals("V2")) ||
                                    (board[i][j].equals("H3")) || (board[i][j].equals("V3")) ||
                                    (board[i][j].equals("H4")) || (board[i][j].equals("V4"))) {
                                cruiserNotInBorder = false;
                            }
                        }
                    }
                    if (cruiserNotInBorder) {
                        for (int i = Math.max(row - 1, 0); i <
                                Math.min(row + 1, board.length - 1); i++) {
                            for (int j = Math.max(col - 1, 0); j <
                                    Math.min(col + 2, board.length - 1); j++) {
                                board[i][j] = ".";
                            }
                        }
                        board[row][col] = "H2";
                        board[row][col + 1] = "H2";
                        countCruisers++;
                    }
                }
            } else { // vertical orientation
                if ((row < board.length) && (row - 1 >= 0) && (col < board.length)
                        && (col >= 0)) {
                    for (int i = Math.max(row - 2, 0); i <
                            Math.min(row + 1, board.length - 1); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 1, board.length - 1); j++) {
                            if (board[i][j].equals(".") || (board[i][j].equals("1")) ||
                                    (board[i][j].equals("H2")) || (board[i][j].equals("V2")) ||
                                    (board[i][j].equals("H3")) || (board[i][j].equals("V3")) ||
                                    (board[i][j].equals("H4")) || (board[i][j].equals("V4"))) {
                                cruiserNotInBorder = false;
                            }
                        }
                    }
                    if (cruiserNotInBorder) {
                        for (int i = Math.max(row - 2, 0); i <
                                Math.min(row + 1, board.length - 1); i++) {
                            for (int j = Math.max(col - 1, 0); j <
                                    Math.min(col + 1, board.length - 1); j++) {
                                board[i][j] = ".";
                            }
                        }
                        board[row][col] = "V2";
                        board[row - 1][col] = "V2";
                        countCruisers++;
                    }
                }
            }
        }

        // place battleships
        countBattleships = 0; // board should have 2 battleships
        while (countBattleships < 2) {
            boolean battleshipNotInBorder = true;
            int row = (int) (Math.random() * 10);
            int col = (int) (Math.random() * 10);

            // horizontal orientation
            if (((int) (Math.random() * 100)) < 50) {
                if ((row < board.length) && (row >= 0) && (col + 1 < board.length)
                        && (col - 1 >= 0)) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 1, board.length - 1); i++) {
                        for (int j = Math.max(col - 2, 0); j <
                                Math.min(col + 2, board.length - 1); j++) {
                            if (board[i][j].equals(".") || (board[i][j].equals("1")) ||
                                    (board[i][j].equals("H2")) || (board[i][j].equals("V2")) ||
                                    (board[i][j].equals("H3")) || (board[i][j].equals("V3")) ||
                                    (board[i][j].equals("H4")) || (board[i][j].equals("V4"))) {
                                battleshipNotInBorder = false;
                            }
                        }
                    }
                    if (battleshipNotInBorder) {
                        for (int i = Math.max(row - 1, 0); i <
                                Math.min(row + 1, board.length - 1); i++) {
                            for (int j = Math.max(col - 2, 0); j <
                                    Math.min(col + 2, board.length - 1); j++) {
                                board[i][j] = ".";
                            }
                        }
                        board[row][col] = "H3";
                        board[row][col + 1] = "H3";
                        board[row][col - 1] = "H3";
                        countBattleships++;
                    }
                }
            } else { // vertical orientation
                if ((row + 1 < board.length) && (row - 1 >= 0) && (col < board.length)
                        && (col >= 0)) {
                    for (int i = Math.max(row - 2, 0); i <
                            Math.min(row + 2, board.length - 1); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 3, board.length - 1); j++) {
                            if (board[i][j].equals(".") || (board[i][j].equals("1")) ||
                                    (board[i][j].equals("H2")) || (board[i][j].equals("V2")) ||
                                    (board[i][j].equals("H3")) || (board[i][j].equals("V3")) ||
                                    (board[i][j].equals("H4")) || (board[i][j].equals("V4"))) {
                                battleshipNotInBorder = false;
                            }
                        }
                    }
                    if (battleshipNotInBorder) {
                        for (int i = Math.max(row - 2, 0); i <
                                Math.min(row + 2, board.length - 1); i++) {
                            for (int j = Math.max(col - 1, 0); j <
                                    Math.min(col + 3, board.length - 1); j++) {
                                board[i][j] = ".";
                            }
                        }
                        board[row][col] = "V3";
                        board[row - 1][col] = "V3";
                        board[row + 1][col] = "V3";
                        countBattleships++;
                    }
                }
            }
        }

        // place carriers
        countCarriers = 0; // board should have 1 carrier
        while (countCarriers < 1) {
            boolean carrierNotInBorder = true;
            int row = (int) (Math.random() * 10);
            int col = (int) (Math.random() * 10);

            // horizontal orientation
            if (((int) (Math.random() * 100)) < 50) {
                if ((row < board.length) && (row >= 0) && (col + 2 < board.length)
                        && (col - 1 >= 0)) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 1, board.length - 1); i++) {
                        for (int j = Math.max(col - 2, 0); j <
                                Math.min(col + 3, board.length - 1); j++) {
                            if (board[i][j].equals(".") || (board[i][j].equals("1")) ||
                                    (board[i][j].equals("H2")) || (board[i][j].equals("V2")) ||
                                    (board[i][j].equals("H3")) || (board[i][j].equals("V3")) ||
                                    (board[i][j].equals("H4")) || (board[i][j].equals("V4"))) {
                                carrierNotInBorder = false;
                            }
                        }
                    }
                    if (carrierNotInBorder) {
                        for (int i = Math.max(row - 1, 0); i <
                                Math.min(row + 1, board.length - 1); i++) {
                            for (int j = Math.max(col - 2, 0); j <
                                    Math.min(col + 3, board.length - 1); j++) {
                                board[i][j] = ".";
                            }
                        }
                        board[row][col - 1] = "H4";
                        board[row][col] = "H4";
                        board[row][col + 1] = "H4";
                        board[row][col + 2] = "H4";
                        countCarriers++;
                    }
                }
            } else { // vertical orientation
                if ((row + 1 < board.length) && (row - 2 >= 0) && (col < board.length)
                        && (col >= 0)) {
                    for (int i = Math.max(row - 3, 0); i <
                            Math.min(row + 2, board.length - 1); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 1, board.length - 1); j++) {
                            if (board[i][j].equals(".") || (board[i][j].equals("1")) ||
                                    (board[i][j].equals("H2")) || (board[i][j].equals("V2")) ||
                                    (board[i][j].equals("H3")) || (board[i][j].equals("V3")) ||
                                    (board[i][j].equals("H4")) || (board[i][j].equals("V4"))) {
                                carrierNotInBorder = false;
                            }
                        }
                    }
                    if (carrierNotInBorder) {
                        for (int i = Math.max(row - 3, 0); i <
                                Math.min(row + 2, board.length - 1); i++) {
                            for (int j = Math.max(col - 1, 0); j <
                                    Math.min(col + 1, board.length - 1); j++) {
                                board[i][j] = ".";
                            }
                        }
                        board[row - 2][col] = "V4";
                        board[row - 1][col] = "V4";
                        board[row][col] = "V4";
                        board[row + 1][col] = "V4";
                        countCarriers++;
                    }
                }
            }
        }
        if (countSubs + countCruisers + countBattleships + countCarriers != 10) {
            placeShipsRandomly();
        }
    }

    /**
     * Simulate a player taking a shot. Player is able to take a shot as long as the number of
     * shots remaining is greater than 0. When a shot is taken, the method goes through various
     * cases to determine if a ship was hit successfully or not. If a ship is successfully
     * hit, then the corresponding cell's value is updated to "X". If a ship is sunken, then
     * the sunken ship is enclosed by a border and the value of sunkenShips is incremented by 1.
     * If a ship is not hit, then the corresponding cell is updated to an "O". For each shot,
     * numShots is decremented by one.
     *
     * @param row is the row that the specified cell is located
     * @param col is the column that the specified cell is located
     * @return n/a
     */
    public void takeShot(int col, int row) {
        if (numShots > 0 && !didWin()) {
            // checks if a submarine was successfully hit
            if (board[row][col].equals("1")) {
                // this encloses the ship with a border
                for (int i = Math.max(row - 1, 0); i <
                        Math.min(row + 2, board.length); i++) {
                    for (int j = Math.max(col - 1, 0); j <
                            Math.min(col + 2, board.length); j++) {
                        if (!board[i][j].equals("X")) {
                            board[i][j] = "x";
                        }
                    }
                }
                board[row][col] = "X";
                numShots--;
                countSubs--;
                sunkenShips++;
            } else if (board[row][col].equals("H2")) { // checks if a horizontal cruiser is hit
                if (board[row][Math.min(col + 1, board.length - 1)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 3, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCruisers--;
                    numShots--;
                } else if (board[row][Math.max(col - 1, 0)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 2, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCruisers--;
                    numShots--;
                } else {
                    board[row][col] = "X";
                    numShots--;
                }
            } else if (board[row][col].equals("V2")) { // checks if a vertical cruiser is hit
                if (board[Math.max(row - 1, 0)][col].equals("X")) {
                    for (int i = Math.max(row - 2, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCruisers--;
                    numShots--;
                } else if (board[Math.min(row + 1, board.length - 1)][col].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 3, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCruisers--;
                    numShots--;
                } else {
                    board[row][col] = "X";
                    numShots--;
                }
            } else if (board[row][col].equals("H3")) { // checks if a horizontal battleship is hit
                if (board[row][Math.min(col + 1, board.length - 1)].equals("X") &&
                        board[row][Math.min(col + 2, board.length - 1)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 4, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countBattleships--;
                    numShots--;
                } else if (board[row][Math.max(col - 1, 0)].equals("X") &&
                        board[row][Math.min(col + 1, board.length - 1)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 2, 0); j <
                                Math.min(col + 3, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countBattleships--;
                    numShots--;
                } else if (board[row][Math.max(col - 2, 0)].equals("X") &&
                        board[row][Math.max(col - 1, 0)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 3, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countBattleships--;
                    numShots--;
                } else {
                    board[row][col] = "X";
                    numShots--;
                }
            } else if (board[row][col].equals("V3")) { // checks if a vertical battleship is hit
                if (board[Math.min(row + 1, board.length - 1)][col].equals("X") &&
                        board[Math.min(row + 2, board.length - 1)][col].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 4, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countBattleships--;
                    numShots--;
                } else if (board[Math.max(row - 1, 0)][col].equals("X") &&
                        board[Math.min(row + 1, board.length - 1)][col].equals("X")) {
                    for (int i = Math.max(row - 2, 0); i <
                            Math.min(row + 3, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countBattleships--;
                    numShots--;
                } else if (board[Math.max(row - 2, 0)][col].equals("X") &&
                        board[Math.max(row - 1, 0)][col].equals("X")) {
                    for (int i = Math.max(row - 3, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countBattleships--;
                    numShots--;
                } else {
                    board[row][col] = "X";
                    numShots--;
                }
            } else if (board[row][col].equals("H4")) { // checks if a horizontal carrier is hit
                if (board[row][Math.min(col + 1, board.length - 1)].equals("X") &&
                        board[row][Math.min(col + 2, board.length - 1)].equals("X") &&
                        board[row][Math.min(col + 3, board.length - 1)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 5, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else if (board[row][Math.max(col - 1, 0)].equals("X") &&
                        board[row][Math.min(col + 1, board.length - 1)].equals("X") &&
                        board[row][Math.min(col + 2, board.length - 1)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 2, 0); j <
                                Math.min(col + 4, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else if (board[row][Math.max(col - 2, 0)].equals("X") &&
                        board[row][Math.max(col - 1, 0)].equals("X") &&
                        board[row][Math.min(col + 1, board.length - 1)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 3, 0); j <
                                Math.min(col + 3, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else if (board[row][Math.max(col - 3, 0)].equals("X") &&
                        board[row][Math.max(col - 2, 0)].equals("X") &&
                        board[row][Math.max(col - 1, 0)].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 4, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else {
                    board[row][col] = "X";
                    numShots--;
                }
            } else if (board[row][col].equals("V4")) { // checks if a vertical carrier is hit
                if (board[Math.min(row + 1, board.length - 1)][col].equals("X") &&
                        board[Math.min(row + 2, board.length - 1)][col].equals("X") &&
                        board[Math.min(row + 3, board.length - 1)][col].equals("X")) {
                    for (int i = Math.max(row - 1, 0); i <
                            Math.min(row + 5, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else if (board[Math.max(row - 1, 0)][col].equals("X") &&
                        board[Math.min(row + 1, board.length - 1)][col].equals("X") &&
                        board[Math.min(row + 2, board.length - 1)][col].equals("X")) {
                    for (int i = Math.max(row - 2, 0); i <
                            Math.min(row + 4, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else if (board[Math.max(row - 2, 0)][col].equals("X") &&
                        board[Math.max(row - 1, 0)][col].equals("X") &&
                        board[Math.min(row + 1, board.length - 1)][col].equals("X")) {
                    for (int i = Math.max(row - 3, 0); i <
                            Math.min(row + 3, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else if (board[Math.max(row - 3, 0)][col].equals("X") &&
                        board[Math.max(row - 2, 0)][col].equals("X") &&
                        board[Math.max(row - 1, 0)][col].equals("X")) {
                    for (int i = Math.max(row - 4, 0); i <
                            Math.min(row + 2, board.length); i++) {
                        for (int j = Math.max(col - 1, 0); j <
                                Math.min(col + 2, board.length); j++) {
                            if (!board[i][j].equals("X")) {
                                board[i][j] = "x";
                            }
                        }
                    }
                    board[row][col] = "X";
                    sunkenShips++;
                    countCarriers--;
                    numShots--;
                } else {
                    board[row][col] = "X";
                    numShots--;
                }
            } else if (board[row][col].equals(".") || board[row][col].equals("_")) {
                board[row][col] = "O";
                numShots--;
            }
        }
    }

    /**
     * Checks if the player has won yet
     *
     * @return a boolean that represents true if player has won; false otherwise
     */
    public boolean didWin() {
        return (getSunkenShips() == 10);
    }

    /**
     * Returns the current number of shots remaining
     *
     * @return an int representing numShots
     */
    public int getNumShots() {
        if (numShots > 0) {
            return numShots;
        }
        return 0;
    }

    /**
     * Returns the current number of sunken ships on the board
     *
     * @return an int representing sunkenShips
     */
    public int getSunkenShips() {
        if (sunkenShips >= 0 && sunkenShips <= 10) {
            return sunkenShips;
        }
        return 10;
    }

    /**
     * Returns the current number of ships on the board.
     *
     * @return an int representing the number of ships.
     */
    public int getNumShips() {
        return (getCountSubs() + getCountCruisers() + getCountBattleships() + getCountCarriers());
    }

    /**
     * Returns the current number of subs on the board.
     *
     * @return an int representing the number of sub.
     */
    public int getCountSubs() {
        if (countSubs > 0) {
            return countSubs;
        }
        return 0;
    }

    /**
     * Returns the current number of cruisers on the board.
     *
     * @return an int representing the number of cruisers.
     */
    public int getCountCruisers() {
        if (countCruisers > 0) {
            return countCruisers;
        }
        return 0;
    }

    /**
     * Returns the current number of battleships on the board.
     *
     * @return an int representing the number of battleships.
     */
    public int getCountBattleships() {
        if (countBattleships > 0) {
            return countBattleships;
        }
        return 0;
    }

    /**
     * Returns the current number of carriers on the board.
     *
     * @return an int representing the number of carriers.
     */
    public int getCountCarriers() {
        if (countCarriers > 0) {
            return countCarriers;
        }
        return 0;
    }

    /**
     * Setter for board.
     *
     */
    public void setBoard(String[][] b) {
        this.board = b;
    }

    public void setNumShots(int shots) {
        this.numShots = shots;
    }

    public void setNumSunkenShips(int ships) {
        this.sunkenShips = ships;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public String getCell(int c, int r) {
        return board[r][c];
    }
}
