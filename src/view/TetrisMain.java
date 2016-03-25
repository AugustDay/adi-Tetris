/*
 * TCSS 305: Assignment 6 - Tetris
 * TetrisMain class
 */

package view;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Starts the program.
 * @author Austin Ingraham
 * @version 8 December 2015
 */
public final class TetrisMain {

    /** Private constructor to prevent construction of instances. */
    private TetrisMain() {
        //do nothing
    }
    
    /**
     * Constructs the GUI window frame.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        // Turn off metal's use of bold fonts
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {     
                new TetrisGUI().start();
            }
        });
    }
}
