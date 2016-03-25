package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.AbstractPiece;
import model.Block;
import model.Board;

/**
 * Displays the next piece.
 * @author Austin Ingraham
 * @version 10 December 2015
 */
public final class NextPieceDisplay extends JPanel implements Observer {

    /** Serial ID. */
    private static final long serialVersionUID = -4758898750815780902L;

    /** The default width value. */
    private static final int PANEL_WIDTH = 160; 
    
    /** The default height value. */
    private static final int PANEL_HEIGHT = 80;
    
    /** The minimum size this component should be. */
    private static final Dimension MIN_SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    
    /** Constant size of blocks. */
    private static final int SCALE = 20;
    
    /** The game board. */
    private final Board myBoard;
    
    /** The game's next piece, to be displayed on this panel. */
    private AbstractPiece myNextPiece;
    
    /**
     * Constructs a panel which displays the upcoming piece in a game of Tetris.
     * @param theBoard Board which represents the game geometry, and contains the piece.
     */
    public NextPieceDisplay(final Board theBoard) {
        super();
        
        myBoard = theBoard;
        myNextPiece = (AbstractPiece) myBoard.getCurrentPiece();

        setBackground(Color.black);
        setOpaque(true);
    }
    
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(g2d);
        
        if (myNextPiece != null) {
            g2d.setColor(chooseColor(myNextPiece.getBlock()));
            final int[][] piece = myNextPiece.getRotation();
        
            for (final int[] block : piece) {
                g2d.fillRect(block[0] * SCALE + (SCALE * 2), 
                             -1 * block[1] * SCALE + (PANEL_HEIGHT - SCALE),
                             SCALE, SCALE);                  
            }
            
            g2d.setColor(Color.WHITE);
            for (final int[] block : piece) {                
                g2d.drawRect(block[0] * SCALE + (SCALE * 2), 
                             -1 * block[1] * SCALE + (PANEL_HEIGHT - SCALE), 
                             SCALE, SCALE);
            }
        }
    };
    
    /**
     * Draws a gray grid onto the game board. 
     * @param theG2D the Graphics to draw upon.
     */
    private void drawGrid(final Graphics2D theG2D) {
        final Line2D.Double gridLine = new Line2D.Double();
        theG2D.setColor(Color.gray);
        theG2D.setStroke(new BasicStroke(1));
        
        for (int i = 0; i < PANEL_HEIGHT; i++) {
            if (i % SCALE == 0) {
                gridLine.setLine(0, i, PANEL_WIDTH, i);
                theG2D.draw(gridLine);
            }
        }
        
        for (int i = 0; i < PANEL_WIDTH; i++) {
            if (i % SCALE == 0) {
                gridLine.setLine(i, 0, i, PANEL_HEIGHT);
                theG2D.draw(gridLine);
            }
        } 
    }
    
    /**  
     * Chooses what color this block should be.
     * @param theBlock the Block enum which is used to choose the color
     * @return result the color
     */
    private Color chooseColor(final Block theBlock) {
        Color result = Color.BLACK;
        switch (theBlock) {
            case I:
                result = Color.CYAN;
                break;
            case J:
                result = Color.BLUE;
                break;
            case L:
                result = Color.ORANGE;
                break;
            case O:
                result = Color.YELLOW;
                break;
            case S:
                result = Color.GREEN;
                break;
            case T:
                result = Color.MAGENTA;
                break;
            case Z:
                result = Color.RED;
                break;
            default:
                break;
        }
        return result;
    }
    
    /**
     * Gets the preferred dimension of this panel.
     * @return MIN_SIZE the preferred dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return MIN_SIZE;
    }
    
    /** Listens for changes in board state. */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObservable instanceof Board) {
            myNextPiece = (AbstractPiece) myBoard.getNextPiece();
            repaint();
        }
    }

}
