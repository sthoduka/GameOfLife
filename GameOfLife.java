import java.util.Arrays;

/**
 * Implementation of Game of Life
 * @author Santosh Thoduka
 *
 */
public class GameOfLife {
	private boolean[][] values;
	private int xsize, ysize;
	
	/**
	 * Initialize grid with size xsize x ysize
	 * @param xsize width of grid
	 * @param ysize height of grid
	 */
	public GameOfLife(int xsize, int ysize) {
		this.xsize = xsize;
		this.ysize = ysize;
		values = new boolean[xsize][ysize];
	}
	
	/**
	 * Return current grid
	 * @return
	 */
	public boolean [][] getValues() {
		return values;
	}

	/**
	 * Calculate next generation of live cells and update grid values
	 */
	public void nextIteration() {
		boolean[][] valscopy = new boolean[xsize][ysize];
		// Game of Life
		int[] born = { 3 };
		int[] survive = { 2, 3 };
		// Diamoeoba
		// int [] survive = {3,4,6,7,8};
		// int [] born = {0,1,2,3,4,7,8};
		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				int live = live_neighbours(i, j);
				if (values[i][j]) {
					if (Arrays.binarySearch(survive, live) >= 0) {
						valscopy[i][j] = true;
					} else {
						valscopy[i][j] = false;
					}
					/*
					 * if(live < 2 || live > 3) { valscopy[i][j] = false; } else
					 * { valscopy[i][j] = true; }
					 */
				} else {
					if (Arrays.binarySearch(born, live) >= 0) {
						valscopy[i][j] = true;
					} else {
						valscopy[i][j] = false;
					}
				}
			}
		}
		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				values[i][j] = valscopy[i][j];
			}
		}
	}
	
	/**
	 * Return number of live neighbours around cell (i,j)
	 * @param i x coordinate of cell to check
	 * @param j y coordinate of cell to check
	 * @return number of live neighbours of (i,j)
	 */
	private int live_neighbours(int i, int j) {
		return N(i, j) + S(i, j) + E(i, j) + W(i, j) + NE(i, j) + NW(i, j)
				+ SE(i, j) + SW(i, j);
	}
	
	/**
	 * Return 1 if neighbour to north of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int N(int i, int j) {
		if (j == 0)
			j = ysize - 1;
		else
			j--;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
	
	/**
	 * Return 1 if neighbour to south of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int S(int i, int j) {
		if (j == ysize - 1)
			j = 0;
		else
			j++;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
	
	/**
	 * Return 1 if neighbour to east of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int E(int i, int j) {
		if (i == xsize - 1)
			i = 0;
		else
			i++;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
	
	/**
	 * Return 1 if neighbour to west of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int W(int i, int j) {
		if (i == 0)
			i = xsize - 1;
		else
			i--;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
	
	/**
	 * Return 1 if neighbour to north-east of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int NE(int i, int j) {
		if (j == 0)
			j = ysize - 1;
		else
			j--;
		if (i == xsize - 1)
			i = 0;
		else
			i++;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
	
	/**
	 * Return 1 if neighbour to north-west of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int NW(int i, int j) {
		if (j == 0)
			j = ysize - 1;
		else
			j--;
		if (i == 0)
			i = xsize - 1;
		else
			i--;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
	
	/**
	 * Return 1 if neighbour to south-east of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int SE(int i, int j) {
		if (j == ysize - 1)
			j = 0;
		else
			j++;
		if (i == xsize - 1)
			i = 0;
		else
			i++;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
	
	/**
	 * Return 1 if neighbour to south-west of (i,j) is alive, 0 otherwise
	 * @param i
	 * @param j
	 * @return
	 */
	private int SW(int i, int j) {
		if (j == ysize - 1)
			j = 0;
		else
			j++;
		if (i == 0)
			i = xsize - 1;
		else
			i--;
		if (values[i][j])
			return 1;
		else
			return 0;
	}
}
