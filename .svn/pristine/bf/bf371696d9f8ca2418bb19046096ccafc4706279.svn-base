/*
 * TCSS 305: Assignment 6 - Tetris
 * ControlsPanel Class
 */


package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Displays the game controls.
 * @author Austin Ingraham
 * @version 11 December 2015
 */
public final class ControlsPanel extends JPanel {
    
    /** Serial ID. */
    private static final long serialVersionUID = 6573702012706890671L;

    /** The minimum size this component should be. */
    private static final Dimension DEFAULT_SIZE = new Dimension(100, 150);
    
    /** Format style. */
    private static final String FORMAT = "%7s%2s";
    
    /** A frequently used integer value. */
    private static final int TEN = 10;
    
    /** A frequently used integer value. */
    private static final int FORTY = 40;
    
    /** A frequently used integer value. */
    private static final int SIXTY = 60;
    
    /** A frequently used integer value. */
    private static final int EIGHTY = 80;
    
    /** A frequently used integer value. */
    private static final int HUNDRED = 100;
    
    /** A frequently used integer value. */
    private static final int HUNDRED_TWENTY = 120;
    
    /** A frequently used integer value. */
    private static final int HUNDRED_FORTY = 140;
    
    /** Font size. */
    private static final int FONT_SIZE = 16;
    
    
    /**
     * Gets the preferred size of this panel.
     * @return DEFAULT_SIZE
     */
    @Override
    public Dimension getPreferredSize() {
        return DEFAULT_SIZE;
    }
    
    /** Paints the text on the screen. */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, TEN + TEN));
        g2d.drawString("CONTROLS:", TEN, TEN + TEN);
        
        g2d.setFont(new Font(Font.MONOSPACED, Font.PLAIN, FONT_SIZE));
        
        g2d.drawString(String.format("%7s%7s", "Pause: ", "'P'"), TEN, FORTY);
        g2d.drawString(String.format("%7s%5s", "Rotate: ", "\u21E7"), TEN, SIXTY);
        g2d.drawString(String.format(FORMAT, "Move Left: ", "\u21E6"), TEN, EIGHTY);
        g2d.drawString(String.format("%7s%1s", "Move Right: ", "\u21E8"), TEN, HUNDRED);
        g2d.drawString(String.format(FORMAT, "Soft Drop: ", "\u21E9"), TEN, HUNDRED_TWENTY);
        g2d.drawString("Hard Drop:SPACE", TEN, HUNDRED_FORTY);
        
    }
}

