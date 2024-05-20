package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {
        StdIn.setFile(file);
        int row = StdIn.readInt();
        int column = StdIn.readInt();

        grid = new boolean[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
            grid[i][j] = StdIn.readBoolean();
            }
        }
        
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {
        boolean state = grid[row][col];

        return state; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {
        boolean[][] grid = getGrid();
        int rows = grid.length;
        int columns = grid[0].length;
        boolean lively = false;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == true) {
                    lively = true;
                    return lively;
                }
            }
        }
        
        return lively; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        boolean[][] grid = getGrid();
        int startingrow = row-1;
        int startingcol = col-1;
        int aliveneighbors = 0;

        for (int i = startingrow; i < startingrow+3; i++) {
            for (int j = startingcol; j < startingcol+3; j++) {
                if (!((i < 0)||(j < 0)||(i > grid.length-1)||(j > grid[0].length-1))) {
                    if (!(i==row)||!(j==col)) {
                        if (grid[i][j] == true) {
                            aliveneighbors++;
                        }
                    }
                } else if (((i < 0)||(j < 0)||(i > grid.length-1)||(j > grid[0].length-1))) {
                    int iedit = 0;
                    int jedit = 0;
                    if (i < 0) {
                        iedit = grid.length;
                    } if (i > grid.length-1) {
                        iedit = -grid.length;
                    } if (j < 0) {
                        jedit = grid[0].length;
                    } if (j > grid[0].length-1) {
                        jedit = -grid[0].length;
                    }

                    // System.out.println("iedit: " + iedit + ") + (i: " + i);
                    // System.out.println("jedit: " + jedit + ") + (j: " + j);
                    // System.out.println();

                    if (!(i+iedit==row)||!(j+jedit==col)) {
                        if (grid[i+iedit][j+jedit] == true) {
                            aliveneighbors++;
                        }
                    }
                }
            }
        }
    
        
        return aliveneighbors; // update this line, provided so that code compiles
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        boolean[][] grid = getGrid();
        int rows = grid.length;
        int columns = grid[0].length;
        boolean[][] newgrid = new boolean[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newgrid[i][j] = grid[i][j];
            }
        } 

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int neighbors = numOfAliveNeighbors(i, j);
                

                if (grid[i][j] == false) {
                    
                    // System.out.println("Status: " + grid[i][j]);
                    // System.out.println("x,y: " + j + "," + i);
                    // System.out.println("Neighbors: " + neighbors);
                    // System.out.println();
                    if (neighbors == 3) {
                        newgrid[i][j] = true;
                    }
                } else {
                    
                    // System.out.println("Status: " + grid[i][j]);
                    // System.out.println("x,y: " + j + "," + i);
                    // System.out.println("Neighbors: " + neighbors);
                    // System.out.println();
                    if ((neighbors <= 1)||(neighbors >= 4)) {
                        newgrid[i][j] = false;
                    } else if ((neighbors == 2)||(neighbors == 3)) {
                        newgrid[i][j] = true;
                    }
                }
            }
        }
        
        return newgrid;// update this line, provided so that code compiles
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {
        boolean[][] newgrid = computeNewGrid();
        grid = newgrid;
        
    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        for (int i = 0; i < n; i++) {
            nextGeneration();
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {


        boolean[][] grid = getGrid();
        int row = grid.length;
        int col = grid[0].length;
        
        WeightedQuickUnionUF a = new WeightedQuickUnionUF(row, col);
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == true) {
                    a.union(i,j,i,j);
                    System.out.println("i: " + i);
                    System.out.println("j: " + j);
                    System.out.println("find: " + a.find(i, j));
                    System.out.println();
                    
                }
            }
        }

        for (int y = 0; y < row; y++) {
            for (int x = 0; x < col; x++) {
                int startingrow = y-1;
                int startingcol = x-1;
                
                for (int i = startingrow; i < startingrow+3; i++) {
                    for (int j = startingcol; j < startingcol+3; j++) {
                        if (!((i < 0)||(j < 0)||(i > grid.length-1)||(j > grid[0].length-1))) {
                            if (!(i==y)||!(j==x)) {
                                if (grid[i][j] == true) {
                                    // connect the neighbor to the original
                                    a.union(i,j,y,x);
                                }
                            }
                        } else if (((i < 0)||(j < 0)||(i > grid.length-1)||(j > grid[0].length-1))) {
                            int iedit = 0;
                            int jedit = 0;
                            if (i < 0) {
                                iedit = grid.length;
                            } if (i > grid.length-1) {
                                iedit = -grid.length;
                            } if (j < 0) {
                                jedit = grid[0].length;
                            } if (j > grid[0].length-1) {
                                jedit = -grid[0].length;
                            }
        
                            // System.out.println("iedit: " + iedit + ") + (i: " + i);
                            // System.out.println("jedit: " + jedit + ") + (j: " + j);
                            // System.out.println();
        
                            if (!(i+iedit==y)||!(j+jedit==x)) {
                                if (grid[i+iedit][j+jedit] == true) {
                                    // connect the neighbor to the original
                                    a.union(i+iedit,j+jedit,y,x);
                                }
                            }
                        }
                    }
                }
            }
        }

        int temp = 0;
        int b = 0;
        int groups = 0;

        for (int i = 0; i < row; i++) {
            // System.out.println("i: " + i);
            // System.out.println("row: " + row);
            for (int j = 0; j < col; j++) {
                // System.out.println("j: " + j);
                // System.out.println("col: " + col);
                if (grid[i][j] == true) {
                    b = a.find(i, j);
                    // System.out.println("b: " + b);

                    if (b != temp) {
                        groups++;
                    }

                    temp = b;
                } 
            }
        }
            
        
        
        
        return groups; // update this line, provided so that code compiles
    }
}
