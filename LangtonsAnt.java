/**
 * Extension of CellularAutomaton class to implement Langton's Ant algorithm
 * 
 * @author Santosh Thoduka
 * 
 */

public class LangtonsAnt extends CellularAutomaton {

	private int cx, cy, direction;
	private static final int NORTH = 0;
	private static final int SOUTH = 1;
	private static final int EAST = 2;
	private static final int WEST = 3;

	/**
	 * initialize position to centre and direction to north
	 * 
	 * @param xsize
	 * @param ysize
	 */
	public LangtonsAnt(int xsize, int ysize) {
		super(xsize, ysize);
		cx = xsize / 2;
		cy = ysize / 2;
		direction = NORTH;
	}

	/**
	 * Update cells according to the rules: 1. At a white square, turn 90
	 * degrees right, flip the color of the square, move forward one unit 2. At
	 * a black square, turn 90 degrees left, flip the color of the square, move
	 * forward one unit source: http://en.wikipedia.org/wiki/Langton's_ant
	 */
	public void nextIteration() {
		int nx = cx, ny = cy;
		switch (direction) {
		case NORTH:
			if (values[cx][cy]) { // turn left
				if (cx == 0)
					nx = xsize - 1;
				else
					nx = cx - 1;
				ny = cy;
				direction = WEST;
			} else { // turn right
				if (cx == xsize - 1)
					nx = 0;
				else
					nx = cx + 1;
				ny = cy;
				direction = EAST;
			}
			break;
		case SOUTH:
			if (values[cx][cy]) { // turn left
				if (cx == xsize - 1)
					nx = 0;
				else
					nx = cx + 1;
				ny = cy;
				direction = EAST;
			} else { // turn right
				if (cx == 0)
					nx = xsize - 1;
				else
					nx = cx - 1;
				ny = cy;
				direction = WEST;
			}
			break;
		case WEST:
			if (values[cx][cy]) { // turn left
				if (cy == 0)
					ny = ysize - 1;
				else
					ny = cy - 1;
				nx = cx;
				direction = SOUTH;
			} else { // turn right
				if (cy == ysize - 1)
					ny = 0;
				else
					ny = cy + 1;
				nx = cx;
				direction = NORTH;
			}
			break;
		case EAST:
			if (values[cx][cy]) { // turn left
				if (cy == ysize - 1)
					ny = 0;
				else
					ny = cy + 1;
				nx = cx;
				direction = NORTH;
			} else { // turn right
				if (cy == 0)
					ny = ysize - 1;
				else
					ny = cy - 1;
				nx = cx;
				direction = SOUTH;
			}
			break;
		}
		values[cx][cy] = !values[cx][cy];
		cx = nx;
		cy = ny;
		num_iterations++;
	}

}
