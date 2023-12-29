package org.cis1200.Battleship;
import javax.swing.*;
import java.awt.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a Battleship object to serve as the game's model.
 */
public class RunBattleship implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Battleship");
        frame.setLocation(500, 750);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel();
        status_panel.add(status);

        // Game board
        final BattleshipBoard board = new BattleshipBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Undo button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> board.undo());
        control_panel.add(undo);

        // Save button
        final JButton save = new JButton("Save");
        save.addActionListener(e -> board.saveGame());
        control_panel.add(save);

        // Load button
        final JButton load = new JButton("Load");
        load.addActionListener(e -> board.loadGame());
        control_panel.add(load);

        // Create instructions panel
        final JPanel instruction_panel = new JPanel();
        instruction_panel.setPreferredSize(new Dimension(board.BOARD_WIDTH, board.BOARD_HEIGHT));
        instruction_panel.setLayout(new BorderLayout());
        final JLabel instructions = new JLabel(
                "<html>" +
                        "<B>How to play:<B>" + "\n" +
                        "<br/> There are 10 ships on the board: " +
                        "<br/> One carrier of size 4 (this means that it " +
                        "occupies 4 squares in " +
                        "the 2D array), two battleships of size 3, three cruisers " +
                        "of size 2, and four" +
                        " submarines of size 1. All locations of the ships are " +
                        "fully randomized on the " +
                        " board, and each ship can either be " +
                        "horizontally oriented or vertically oriented." + "\n" +
                        "<br/> Your objective is to sink all 10 ships. Use your mouse" +
                        " to hover over the square" +
                        "you wish to take a shot on, and click the mouse to open fire." +
                        "You have 65 shots. If you sink all 10 ships without running out " +
                        "of shots, you win the game." +
                        "If you run out of shots before all ships are destroyed, you lose." +
                        "Destroyed ships will be enclosed by a border, and no ship can " +
                        "overlap this border." +
                        "Click start below to begin the game." +
                        "Click reset to play a new game with a different randomized board." +
                        "<html>",
                SwingConstants.CENTER
        );
        instructions.setForeground(Color.BLACK);
        instruction_panel.add(instructions, BorderLayout.NORTH);
        instruction_panel.setFocusable(true);
        frame.add(instruction_panel, BorderLayout.CENTER);

        // Starts the game
        final JButton startGame = new JButton("Start");
        startGame.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(board, BorderLayout.CENTER);
            frame.add(control_panel, BorderLayout.NORTH);
            frame.add(status_panel, BorderLayout.SOUTH);
            frame.setVisible(true);
            frame.pack();
            board.reset();
        });
        instruction_panel.add(startGame, BorderLayout.SOUTH);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}
