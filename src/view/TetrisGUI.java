/*
 * TCSS 305: Assignment 6 - Tetris
 * TetrisGUI Class
 */

package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import model.Board;

/**
 * Creates the Tetris game GUI. 
 * @author Austin Ingraham
 * @version 9 December 2015
 */
public final class TetrisGUI extends JPanel implements Observer {

    /** Generated serial version UID. */
    private static final long serialVersionUID = -3356030126364510884L;
    
    /** default width of Tetris board. Default Height is double this. */
    private static final int DEFAULT_SIZE = 10;
    
    /** The default delay (in milliseconds) for the move timer. */
    private static final int DEFAULT_STEP_TIME = 800;

    /** The initial delay (in milliseconds) for the move timer. */
    private static final int INITIAL_DELAY = 1000;

    /** JFrame window which holds the content. */
    private final JFrame myWindow;
    
    /** Component to represent a menu bar at the top of the window. */
    private final JMenuBar myMenuBar;
    
    /** The menu item used to prematurely end a game of Tetris. */
    private final JMenuItem myEndGame;
    
    /** The timer that manages steps. */
    private final Timer myStepTimer;
    
    /** The play-zone. */
    private final Board myBoard;
    
    /** Area display game board. */
    private final BoardDisplay myDisplay;
    
    /** Panel which tracks, calculates, and displays the current score. */
    private final StatTracker myStatTracker;
    
    /** Keyboard controls. */
    private final KeyControls myKeyControls;
    
    /** Constructor for the Tetris GUI. */
    public TetrisGUI() {
        super();
        
        myWindow = new JFrame("Tetris");
   //     myWindow.addKeyListener(this);
        myMenuBar = new JMenuBar();
        myEndGame = new JMenuItem("End Game", KeyEvent.VK_E);
        myBoard = new Board(DEFAULT_SIZE, DEFAULT_SIZE * 2);
        myDisplay = new BoardDisplay(myBoard);
        myStepTimer = new Timer(DEFAULT_STEP_TIME, new StepListener());
        myStepTimer.setInitialDelay(INITIAL_DELAY);
        myStatTracker = new StatTracker(myBoard, myStepTimer);
        
        myKeyControls = new KeyControls();
    }
    
    /** Start method to create and display the GUI. */
    public void start() {
        setUpComponents();
        
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setContentPane(this);
        myWindow.pack();
        myWindow.setMinimumSize(new Dimension(myWindow.getSize()));
        myWindow.setResizable(false);
        myWindow.setVisible(true);
    }
    
    /** SetUp method to prepare the components of the GUI for display. */
    private void setUpComponents() {
        setLayout(new FlowLayout());
        final ClassLoader cl = this.getClass().getClassLoader();
        myWindow.setIconImage(
                 new ImageIcon(cl.getResource("resources/icon large.png")).getImage());
        
        setUpDisplay();
        setUpInfoArea();
        buildMenuBar();
        
        myBoard.addObserver(myDisplay);
        myBoard.addObserver(this);
    }
    
    /** Helper method which creates and adds the box which holds the game display. */
    private void setUpDisplay() {
        final Box displayBox = Box.createVerticalBox();
        myDisplay.setSize(myDisplay.getPreferredSize());
        displayBox.add(myDisplay);
        add(displayBox);
    }

    /** Helper method which creates the information boxes on the right side of GUI. */
    private void setUpInfoArea() {
        final Box infoBox = Box.createVerticalBox();
        infoBox.add(setUpNextPieceDisplay());
        infoBox.add(Box.createVerticalStrut(DEFAULT_SIZE));
        infoBox.add(setUpStatTracker());
        infoBox.add(Box.createVerticalStrut(DEFAULT_SIZE));
        infoBox.add(buildInfoBoxText());
        add(infoBox);
    }
    
    /**
     * Creates a box which holds the next piece display.
     * @return box which holds the display for the next piece.
     */
    private Box setUpNextPieceDisplay() {
        final Box nextPieceBox = Box.createVerticalBox();
        final NextPieceDisplay npd = new NextPieceDisplay(myBoard);
        npd.setSize(npd.getPreferredSize());
        nextPieceBox.add(npd);
        myBoard.addObserver(npd);
        return nextPieceBox;
    }
    
    /**
     * Creates the stat tracker box.
     * @return statTrackerBox a box containing the game stats
     */
    private Box setUpStatTracker() {
        final Box statTrackerBox = Box.createVerticalBox();
        
        myStatTracker.setSize(myStatTracker.getPreferredSize());
        statTrackerBox.add(myStatTracker);
        myBoard.addObserver(myStatTracker);
        return statTrackerBox;
    }
    
    /**
     * Creates a text area to display the keyboard controls.
     * @return Text area with keyboard controls.
     */
    private Box buildInfoBoxText() {
        final Box controlsBox = Box.createVerticalBox();
        final ControlsPanel controls = new ControlsPanel();
        controls.setSize(controls.getPreferredSize());
        controlsBox.add(controls);
        return controlsBox;
        
    }
    
    /** Top-level helper method to create the menu bar displayed at top of window. */
    private void buildMenuBar() {
        myMenuBar.add(buildFileMenu());
        //myMenuBar.add(buildOptionsMenu());
        myMenuBar.add(buildHelpMenu());
        
        myWindow.setJMenuBar(myMenuBar);
    }
    
    /**
     * Builds the Game menu of the menu bar.
     * @return menu with some menu items.
     */
    private JMenu buildFileMenu() { 
        final JMenu menu = new JMenu("Game");
        final JMenuItem newGame = new JMenuItem("New Game", KeyEvent.VK_N);
        final JMenuItem exitItem = new JMenuItem("Exit", KeyEvent.VK_X);
        
        
        newGame.setEnabled(true);
        newGame.addActionListener(new ActionListener() {
            
            /** Does not create a new board if this is the first time playing. */
            private boolean myFirstGame = true;
            public void actionPerformed(final ActionEvent theEvent) {
                if (myFirstGame) {
                    myFirstGame = false;
                } else {
                    myWindow.removeKeyListener(myKeyControls);
                    myBoard.deleteObserver(myStatTracker);
                    myBoard.newGame(DEFAULT_SIZE, DEFAULT_SIZE * 2, null);
                    myStatTracker.reset();
                    myBoard.addObserver(myStatTracker);
                }
                myWindow.addKeyListener(myKeyControls);
                myStepTimer.start();
                myEndGame.setEnabled(true);
            }
        });
        
        myEndGame.setEnabled(false); 
        myEndGame.addActionListener(new ActionListener() {
            
            public void actionPerformed(final ActionEvent theEvent) {
                endGame();
                newGame.setEnabled(true);
            }
        });
        
        exitItem.addActionListener(new ActionListener() {
          
            public void actionPerformed(final ActionEvent theEvent) {
                myWindow.dispatchEvent(new WindowEvent(myWindow, WindowEvent.WINDOW_CLOSING));
            }
        });
        newGame.setToolTipText("Starts a new game of Tetris.");
        myEndGame.setToolTipText("Ends the current game of Tetris.");
        exitItem.setToolTipText("Exits the game.");

        menu.setMnemonic(KeyEvent.VK_G);
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        myEndGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        
        menu.add(newGame);
        menu.addSeparator();
        menu.add(myEndGame);
        menu.addSeparator();
        menu.add(exitItem);
        return menu;
    }

    /**
     * Builds the Help menu for the menu bar.
     * When clicked, displays a pop-up with info about the PowerPaint program.
     * 
     * @return menu with some menu items.
     */
    private JMenu buildHelpMenu() {
        final JMenu menu = new JMenu("Help");
        final JMenuItem howToPlay = new JMenuItem("How to Play...");
        final JMenuItem aboutPopUp = new JMenuItem("Version Info...");
        
        howToPlay.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) { 
                myStepTimer.stop();
                final String message = "Stack pieces to create lines.\n"
                           + "A complete horizontal line will disappear, yielding points.\n"
                           + "Avoid stacking pieces past the top of the playzone to survive.\n"
                           + "Get extra points by stacking multiple lines at once!";
                javax.swing.JOptionPane.showMessageDialog(null, message, 
                                      "Directions", JOptionPane.INFORMATION_MESSAGE);
                myStepTimer.start();
            }
        });

        aboutPopUp.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(final ActionEvent theEvent) { 
                myStepTimer.stop();
                final String message = "TCSS 305 Tetris, Autumn 2015.\nBy Austin Ingraham"; 
                javax.swing.JOptionPane.showMessageDialog(null, message, 
                                      "About", JOptionPane.INFORMATION_MESSAGE);
                myStepTimer.start();
            }
        });
        
        
        howToPlay.setToolTipText("How to play Tetris.");
        aboutPopUp.setToolTipText("Information about this program.");
        
        menu.setMnemonic(KeyEvent.VK_H);
        howToPlay.setMnemonic(KeyEvent.VK_W);
        aboutPopUp.setMnemonic(KeyEvent.VK_V);
        
        howToPlay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
        aboutPopUp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
        
        menu.add(howToPlay);
        menu.addSeparator();
        menu.add(aboutPopUp);
        
        return menu;
    }
    
    /** Pauses the game, which stops the timer until this message is closed. */
    private void pauseGame() {
        myStepTimer.stop();
        javax.swing.JOptionPane.showMessageDialog(null, "The game is paused.", 
                                      "Pause!", JOptionPane.INFORMATION_MESSAGE);
        myStepTimer.start();

    }
    
    /**
     * Method which ends the game of tetris.
     */
    private void endGame() {
        myStepTimer.stop();
        myWindow.removeKeyListener(myKeyControls);
        javax.swing.JOptionPane.showMessageDialog(null, "Game over!", 
                                      "Game Over!", JOptionPane.WARNING_MESSAGE);
        myEndGame.setEnabled(false);
    }
    
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObservable instanceof Board && myBoard.isGameOver()) {
            endGame();
        }
    }
    
    /**
     * Listens for key presses, controls game input. 
     * @author Austin Ingraham
     * @version 9 December 2015
     */
    private class KeyControls extends KeyAdapter {
        
        @Override
        public void keyPressed(final KeyEvent theEvent) {            
            switch (theEvent.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    myBoard.moveDown();
                    break;
                case KeyEvent.VK_UP:
                    myBoard.rotate();
                    break;
                case KeyEvent.VK_LEFT:
                    myBoard.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    myBoard.moveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    myBoard.hardDrop();
                    break;
                case KeyEvent.VK_P:
                    pauseGame();
                    break;
                default:
                    break;
            } 
        }
    }

    /**
     * Listens for the timer, updates board. 
     * @author Austin Ingraham
     * @version 9 December 2015
     */
    private class StepListener implements ActionListener {
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
    }
}


