
public abstract class CellularAutomaton {
	protected boolean[][] values;
	protected int xsize, ysize;
	protected int num_iterations;
	
	public CellularAutomaton(int x_size, int y_size) {
		xsize = x_size;
		ysize = y_size;
		values = new boolean[xsize][ysize];
		num_iterations = 0;
	}
	
	/**
	 * Return current grid
	 * 
	 * @return
	 */
	public boolean[][] getValues() {
		return values;
	}
	
	public int getIterationNum() {
		return num_iterations;
	}
	
	public void reset() {
		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				values[i][j] = false;
			}
		}
		num_iterations = 0;
	}
	
	public abstract void nextIteration();	
}
