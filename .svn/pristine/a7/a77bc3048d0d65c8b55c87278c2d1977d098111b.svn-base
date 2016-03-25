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
 * Displays the game board.
 * @author Austin Ingraham
 * @version 9 December 2015
 */
public final class BoardDisplay extends JPanel implements Observer {
    
    /** Serial UID. */
    private static final long serialVersionUID = -6502665427848494597L;

    /** The default width value. */
    private static final int MIN_WIDTH = 200; 
    
    /** The default height value. */
    private static final int MIN_HEIGHT = 600;
    
    /** Scale, will be used for scale of block sizes. */
    private static final int SCALE = 20;
    
    /** Size of display panel. */
    private final Dimension mySize;
    
    /** The space between grid lines. */
    private final int mySpacing;
    
    /** Width of the display panel. */
    private final int myWidth;
    
    /** Height of the display panel. */
    private final int myHeight;
    
    /** Number of rows on this board. */
    private final int myBoardHeight;
    
    /** The game board. */
    private final Board myBoard;
    
    /**
     * Constructs a graphical panel to display the board state.
     * @param theBoard 
     */
    public BoardDisplay(final Board theBoard) {
        super();
        
        myBoard = theBoard;
        myBoardHeight = myBoard.getHeight();
        
        myWidth = myBoard.getWidth() * SCALE;
        myHeight = myBoardHeight * SCALE;
        mySize = new Dimension(myWidth, myHeight);
        mySpacing = myBoard.getHeight();
        setBackground(Color.black);
        setOpaque(true);
    }
    
    
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setStroke(new BasicStroke(1));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        drawGrid(g2d);
        drawCurrentPiece(g2d);
        drawFrozenPieces(g2d);
        
    };
    
    /**
     * Draws the piece currently falling, in its proper color.
     * @param theG2D graphics
     */
    private void drawCurrentPiece(final Graphics2D theG2D) {
        final AbstractPiece abp = (AbstractPiece) myBoard.getCurrentPiece();
        final int[][] piece = abp.getBoardCoordinates();
        theG2D.setColor(chooseColor(abp.getBlock()));
        
        //draw the blocks
        for (final int[] block : piece) {
            theG2D.fillRect(block[0] * SCALE, (myBoardHeight - block[1] - 1) * SCALE, 
                                   SCALE, SCALE);  
            theG2D.drawRect(block[0] * SCALE, (myBoardHeight - block[1] - 1) 
                                      * SCALE, SCALE, SCALE);
        }
        
        //White grid around piece
        theG2D.setColor(Color.WHITE);
        for (final int[] block : piece) { 
            theG2D.drawRect(block[0] * SCALE, (myBoardHeight - block[1] - 1) 
                                      * SCALE, SCALE, SCALE);
        }
    }
    
    /**
     * Draws the frozen pieces onto the display, in their individual colors.
     * @param theG2D graphics
     */
    private void drawFrozenPieces(final Graphics2D theG2D) {
        int row = 1;
        for (final Block[] blocks : myBoard.getFrozenBlocks()) {
            int column = 0;
            for (final Block b : blocks) {
                if (b != Block.EMPTY) {
                    theG2D.setColor(chooseColor(b));
                    theG2D.fillRect(column * SCALE, 
                                      (myBoardHeight - row) * SCALE, SCALE, SCALE);
                    theG2D.setColor(Color.WHITE); //white grid around frozen blocks
                    theG2D.drawRect(column * SCALE, 
                                      (myBoardHeight - row) * SCALE, SCALE, SCALE);
                }
                column++;
            }
            row++;
        }  
    }
    
    /**
     * Draws a gray grid onto the game board. 
     * @param theG2D the Graphics to draw upon.
     */
    private void drawGrid(final Graphics2D theG2D) {
        final Line2D.Double gridLine = new Line2D.Double();
        theG2D.setColor(Color.gray);
        theG2D.setStroke(new BasicStroke(1));
        
        for (int i = 0; i < MIN_HEIGHT; i++) {
            if (i % mySpacing == 0) {
                gridLine.setLine(0, i, myWidth, i);
                theG2D.draw(gridLine);
            }
        }
        
        for (int i = 0; i < MIN_WIDTH; i++) {
            if (i % mySpacing == 0) {
                gridLine.setLine(i, 0, i, myHeight);
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
 
    /** The minimum size of this drawingPanel. 
     * @return MIN_SIZE the dimension.
     */
    @Override
    public Dimension getMinimumSize() {
        return mySize;
    }
 
    /** The preferred size of this drawingPanel. 
     * @return MIN_SIZE the dimension.
     */
    @Override
    public Dimension getPreferredSize() {
        return mySize;
    }
    
    @Override
    public void update(final Observable theOberservable, final Object theObject) {
        repaint();
    }
}
