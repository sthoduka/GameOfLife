import java.util.Arrays;

/**
 * Implementation of Game of Life
 * 
 * @author Santosh Thoduka
 * 
 */
public class LifeLikeAutomaton extends CellularAutomaton {

	/**
	 * Initialize grid with size xsize x ysize
	 * 
	 * @param xsize
	 *            width of grid
	 * @param ysize
	 *            height of grid
	 */
	public LifeLikeAutomaton(int xsize, int ysize) {
		super(xsize,ysize);		
	}

	/**
	 * Calculate next generation of live cells and update grid values
	 */
	public void nextIteration() {
		boolean[][] valscopy = new boolean[xsize][ysize];
		// Game of Life
		int[] born = { 3 }; // if dead cell has 3 neighbours, it comes alive
		int[] survive = { 2, 3 }; // if live cell has 2 or 3 neighbours it stays alive
								// all other cells die/stay dead
		// Diamoeoba
		// int [] survive = {3,4,6,7,8};
		// int [] born = {0,1,2,3,4,7,8};
		boolean seeds = true;
		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				int live = live_neighbours(i, j);
				if (seeds) {
					if (!values[i][j] && live == 2) {
						valscopy[i][j] = true;
					} else {
						valscopy[i][j] = false;
					}
				} else {
					if (values[i][j]) {
						if (Arrays.binarySearch(survive, live) >= 0) {
							valscopy[i][j] = true;
						} else {
							valscopy[i][j] = false;
						}
					} else {
						if (Arrays.binarySearch(born, live) >= 0) {
							valscopy[i][j] = true;
						} else {
							valscopy[i][j] = false;
						}
					}
				}
			}
		}
		for (int i = 0; i < xsize; i++) {
			for (int j = 0; j < ysize; j++) {
				values[i][j] = valscopy[i][j];
			}
		}
		num_iterations++;
	}

	/**
	 * Return number of live neighbours around cell (i,j)
	 * 
	 * @param i
	 *            x coordinate of cell to check
	 * @param j
	 *            y coordinate of cell to check
	 * @return number of live neighbours of (i,j)
	 */
	private int live_neighbours(int i, int j) {
		return N(i, j) + S(i, j) + E(i, j) + W(i, j) + NE(i, j) + NW(i, j)
				+ SE(i, j) + SW(i, j);
	}

	/**
	 * Return 1 if neighbour to north of (i,j) is alive, 0 otherwise
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
