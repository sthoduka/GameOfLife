import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * Extension of JPanel to display and animate grid of cells
 * @author Santosh Thoduka
 *
 */
public class GridPanel extends JPanel implements Runnable {
	int xsize, ysize,tile_size;
	public boolean[][] values;
	private Dimension size;
	int current;
	Thread t;
	String f;
	UI ui;
	GameOfLife gol;
	int sleep_time;
	/**
	 * Initialize GridPanel
	 * @param ui Instance of UI class which uses this GridPanel
	 * @param gol Instance of the GameOfLife class to which the grid is mapped
	 * @param x_size width (in cells) of grid
	 * @param y_size height (in cells) of grid
	 * @param sleep_time time in ms to sleep between updates of grid
	 * @param tile_size size in pixels of a cell
	 */
	GridPanel(UI ui, GameOfLife gol, int x_size, int y_size, int sleep_time, int tile_size) {
		this.ui = ui;
		this.gol = gol;
		xsize = x_size;
		ysize = y_size;
		this.tile_size = tile_size;
		this.sleep_time = sleep_time;
		values = new boolean[xsize][ysize];
		t = new Thread(this, "gridpanel");
		t.start();
	}
	
	/**
	 * Main loop - refresh grid every sleep_time ms unless paused
	 */
	public void run() {
		while (true) {
			try {
				Thread.sleep(sleep_time);
				synchronized (this) {
					while (ui.pauseFlag) {
						wait(50);
						repaint();
					}
				}
			} catch (InterruptedException ie) {
			}
			gol.nextIteration();
			repaint();
		}
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		size = this.getSize();
		drawBoxes(g);
	}
	
	/**
	 * Draw grid
	 * @param g Graphics object
	 */
	private void drawBoxes(Graphics g) {
		values = gol.getValues();
		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				Color tileColor = Color.white;
				if (values[i][j]) // black
				{
					tileColor = Color.darkGray;
				} else // white
				{
					tileColor = Color.lightGray;
				}
				g.setColor(tileColor);
				g.fill3DRect(i * tile_size, j * tile_size, tile_size, tile_size, false);
			}
		}
	}

}
