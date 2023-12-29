package org.cis1200.Battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class BattleshipTest {

    private Battleship model;

    @BeforeEach
    public void setUp() {
        model = new Battleship();
        String[][] board = model.getCopyOfBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    // test default state of sunken ships
    @Test
    public void testDefaultSunkenShips() {
        int actual = model.getSunkenShips();
        int expected = 0;
        assertEquals(expected, actual);
    }

    // test default state of shots remaining
    @Test
    public void testDefaultShotsLeft() {
        int actual = model.getNumShots();
        int expected = 65;
        assertEquals(expected, actual);
    }

    // test that the board starts off with 4 subs
    @Test
    public void testCorrectSubsStartingAmount() {
        int actual = model.getCountSubs();
        int expected = 4;
        assertEquals(expected, actual);
    }

    // test board starts off with 3 cruisers
    @Test
    public void testCorrectCruisersStartingAmount() {
        int actual = model.getCountCruisers();
        int expected = 3;
        assertEquals(expected, actual);
    }

    // test board begins with 2 battleships
    @Test
    public void testCorrectBattleshipsStartingAmount() {
        int actual = model.getCountBattleships();
        int expected = 2;
        assertEquals(expected, actual);
    }

    // test board begins with 1 carrier
    @Test
    public void testCorrectCarriersStartingAmount() {
        int actual = model.getCountCarriers();
        int expected = 1;
        assertEquals(expected, actual);
    }

    // test that there are correct number of ships on the board
    @Test
    public void testCorrectShipsStartingAmount() {
        int actual = model.getNumShips();
        int expected = 10;
        assertEquals(expected, actual);
    }

    // test num ships
    @Test
    public void testGetNumShips() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((model.getCell(i, j).equals("1") || model.getCell(i, j).equals("H2")
                        || model.getCell(i, j).equals("V2") || model.getCell(i, j).equals("H3")
                        || model.getCell(i, j).equals("V3") || model.getCell(i, j).equals("H4")
                        || model.getCell(i, j).equals("V4")) && !model.getCell(i, j).equals("x")) {
                    model.takeShot(i, j);
                }
            }
        }
        int actual = model.getNumShips();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumSubs() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((model.getCell(i, j).equals("1") || model.getCell(i, j).equals("H2")
                        || model.getCell(i, j).equals("V2") || model.getCell(i, j).equals("H3")
                        || model.getCell(i, j).equals("V3") || model.getCell(i, j).equals("H4")
                        || model.getCell(i, j).equals("V4")) && !model.getCell(i, j).equals("x")) {
                    model.takeShot(i, j);
                }
            }
        }
        int actual = model.getCountSubs();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumCruisers() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((model.getCell(i, j).equals("1") || model.getCell(i, j).equals("H2")
                        || model.getCell(i, j).equals("V2") || model.getCell(i, j).equals("H3")
                        || model.getCell(i, j).equals("V3") || model.getCell(i, j).equals("H4")
                        || model.getCell(i, j).equals("V4")) && !model.getCell(i, j).equals("x")) {
                    model.takeShot(i, j);
                }
            }
        }
        int actual = model.getCountCruisers();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumBattleships() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((model.getCell(i, j).equals("1") || model.getCell(i, j).equals("H2")
                        || model.getCell(i, j).equals("V2") || model.getCell(i, j).equals("H3")
                        || model.getCell(i, j).equals("V3") || model.getCell(i, j).equals("H4")
                        || model.getCell(i, j).equals("V4")) && !model.getCell(i, j).equals("x")) {
                    model.takeShot(i, j);
                }
            }
        }
        int actual = model.getCountBattleships();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNumCarriers() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((model.getCell(i, j).equals("1") || model.getCell(i, j).equals("H2")
                        || model.getCell(i, j).equals("V2") || model.getCell(i, j).equals("H3")
                        || model.getCell(i, j).equals("V3") || model.getCell(i, j).equals("H4")
                        || model.getCell(i, j).equals("V4")) && !model.getCell(i, j).equals("x")) {
                    model.takeShot(i, j);
                }
            }
        }
        int actual = model.getCountCarriers();
        int expected = 0;
        assertEquals(expected, actual);
    }

    // test that all ships have been sunken
    @Test
    public void testSunkenShips() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((model.getCell(i, j).equals("1") || model.getCell(i, j).equals("H2")
                        || model.getCell(i, j).equals("V2") || model.getCell(i, j).equals("H3")
                        || model.getCell(i, j).equals("V3") || model.getCell(i, j).equals("H4")
                        || model.getCell(i, j).equals("V4")) && !model.getCell(i, j).equals("x")) {
                    model.takeShot(i, j);
                }
            }
        }
        int actual = model.getSunkenShips();
        int expected = 10;
        assertEquals(expected, actual);
    }

    // test didWin - player wins if all ships have been sunk
    @Test
    public void testDidWin() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if ((model.getCell(i, j).equals("1") || model.getCell(i, j).equals("H2")
                        || model.getCell(i, j).equals("V2") || model.getCell(i, j).equals("H3")
                        || model.getCell(i, j).equals("V3") || model.getCell(i, j).equals("H4")
                        || model.getCell(i, j).equals("V4")) && !model.getCell(i, j).equals("x")) {
                    model.takeShot(i, j);
                }
            }
        }
        boolean expected = true;
        boolean actual = model.didWin();
        assertEquals(expected, actual);
    }

}
