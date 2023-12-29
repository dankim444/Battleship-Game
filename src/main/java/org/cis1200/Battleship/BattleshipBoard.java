package org.cis1200.Battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.LinkedList;

/**
 * This class instantiates a Battleship object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 *
 * In a Model-View-Controller framework, BattleshipBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class BattleshipBoard extends JPanel {

    private Battleship model; // model for the game
    private JLabel status; // current status text
    private LinkedList<Battleship> gameState; // Collection for undo

    // Game constants
    public static final int BOARD_WIDTH = 500;
    public static final int BOARD_HEIGHT = 500;

    /**
     * Initializes the game board.
     */
    public BattleshipBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        model = new Battleship(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        gameState = new LinkedList<>(); // initializes the gameState
        gameState.add(new Battleship(model));

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                // only take shot if cell hasn't already been hit yet
                if (!model.getCell(p.x / 50, p.y / 50).equals("X")
                    && !model.getCell(p.x / 50, p.y / 50).equals("x")
                    && !model.getCell(p.x / 50, p.y / 50).equals("O")) {
                    model.takeShot(p.x / 50, p.y / 50); // X for hit, O for miss
                    gameState.add(new Battleship(model));
                    updateStatus(); // updates the status JLabel
                    repaint(); // repaints the game board
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        model.reset();
        updateStatus();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Undoes the game state.
     */
    public void undo() {
        if (gameState.getLast() != null && model.getNumShots() != 0) {
            gameState.removeLast();
            model = gameState.getLast();
            updateStatus();
            repaint();
            requestFocusInWindow();
        }
    }

    /**
     * Saves the game's contents. Used for File/IO.
     *
     */
    public void saveGame() {
        try {
            File file = new File("file.txt");
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    bufferedWriter.write(model.getCopyOfBoard()[i][j] + " ");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.write(model.getSunkenShips() + " ");
            bufferedWriter.write(model.getNumShots() + "");
            bufferedWriter.close();
            updateStatus();
        } catch (IOException e) {
        }
    }

    /**
     * Loads the game's contents. Used for File/IO.
     *
     */
    public void loadGame() {
        try {
            File file = new File("file.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String nextLine = br.readLine();
            String[][] arr = new String[10][10];
            int r = 0;
            while (r < 10) {
                String[] elements = nextLine.split(" ");
                if (r < 10) {
                    for (int c = 0; c < 10; c++) {
                        arr[r][c] = elements[c];
                    }
                }
                r++;
                nextLine = br.readLine();
            }
            String[] elements = nextLine.split(" ");
            model.setNumSunkenShips(Integer.parseInt(elements[0]));
            model.setNumShots(Integer.parseInt(elements[1]));
            br.close();
            model.setBoard(arr);
            updateStatus();
            repaint();
            requestFocusInWindow();
        } catch (IOException e) {

        }
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     *
     */
    private void updateStatus() {
        if (!model.didWin()) {
            status.setText("Shots left: " + model.getNumShots() + "     " + "\n" +
                    "Ships sunken: " + model.getSunkenShips() + " / 10" + "\n" + "     ");
        }
        if (model.didWin()) {
            status.setText("You've won!");
        }
        if (model.getNumShots() == 0 && !model.didWin()) {
            status.setText("No more shots remaining! Game over :(");
        }
    }

    /**
     * Draws the game board.
     *
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw vertical lines
        g.setColor(Color.BLACK);
        g.drawLine(50, 0, 50, 500);
        g.drawLine(100, 0, 100, 500);
        g.drawLine(150, 0, 150, 500);
        g.drawLine(200, 0, 200, 500);
        g.drawLine(250, 0, 250, 500);
        g.drawLine(300, 0, 300, 500);
        g.drawLine(350, 0, 350, 500);
        g.drawLine(400, 0, 400, 500);
        g.drawLine(450, 0, 450, 500);

        // Draw horizontal lines
        g.drawLine(0, 50, 500, 50);
        g.drawLine(0, 100, 500, 100);
        g.drawLine(0, 150, 500, 150);
        g.drawLine(0, 200, 500, 200);
        g.drawLine(0, 250, 500, 250);
        g.drawLine(0, 300, 500, 300);
        g.drawLine(0, 350, 500, 350);
        g.drawLine(0, 400, 500, 400);
        g.drawLine(0, 450, 500, 450);

        // draw shots
        setBackground(new java.awt.Color(173, 216, 230));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                String state = model.getCell(j, i);
                if (state.equals("X")) {
                    g.setColor(Color.BLACK);
                    g.drawLine(11 + 50 * j, 11 + 50 * i, 42 + 50 * j, 42 + 50 * i);
                    g.drawLine(11 + 50 * j, 42 + 50 * i, 42 + 50 * j, 11 + 50 * i);
                } else if (state.equals("x")) {
                    g.setColor(Color.BLACK);
                    g.fillOval(22 + 50 * j, 22 + 50 * i, 10, 10);
                } else if (state.equals("O")) {
                    g.setColor(Color.BLACK);
                    g.fillOval(22 + 50 * j, 22 + 50 * i, 10, 10);
                } else {
                    g.setColor(new java.awt.Color(173, 216, 230));
                    g.fillRect(j * 50 + 2, i * 50 + 2, 47, 47);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
