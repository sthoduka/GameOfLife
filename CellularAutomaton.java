/**
 * Abstract class for Cellular Automata
 * 
 * @author Santosh Thoduka
 * 
 */

public abstract class CellularAutomaton {
	protected boolean[][] values;
	protected int xsize, ysize;
	protected int num_iterations;

	/**
	 * Initialize size of grid
	 * 
	 * @param x_size
	 * @param y_size
	 */
	public CellularAutomaton(int x_size, int y_size) {
		xsize = x_size;
		ysize = y_size;
		values = new boolean[xsize][ysize];
		num_iterations = 0;
	}

	/**
	 * Return current grid
	 * 
	 * @return grid of values
	 */
	public boolean[][] getValues() {
		return values;
	}

	/**
	 * Return number of iterations completed
	 * 
	 * @return number of iterations
	 */
	public int getIterationNum() {
		return num_iterations;
	}

	/**
	 * Reset all cells to false
	 */
	public void reset() {
		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				values[i][j] = false;
			}
		}
		num_iterations = 0;
	}

	/**
	 * Abstract function to be implemented by classes extending this class
	 */
	public abstract void nextIteration();
}
