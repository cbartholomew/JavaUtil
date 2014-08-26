/*
 * Puzzle.java
 *
 * Implementation of a class that represents a Sudoku puzzle and solves
 * it using recursive backtracking.
 *
 * Computer Science Harvard University
 *
 * Modified by:    Christopher Bartholomew, cbartholomew@fas.harvard.edu
 * Date modified:  09/26/2012
 */

import java.io.*;
import java.util.Scanner;

public class Puzzle implements IBacktracking {
    // the dimension of the puzzle grid
    public static final int DIM = 9;
    
    // the demension of dim minus 1;
    public static final int DIM_MINUS_ONE = DIM - 1;
    
    // the dimension of the smaller squares within the grid
    public static final int SQUARE_DIM = 3; 
    
    // an empty location digit
    public static final int EMPTY = 0;
    
    // a maximum digit + 1 allowed in sudoku
    public static final int END_SUDOKU_INPUT = 10;
    
    // the current contents of the cells of the puzzle values[r][c]
    // gives the value in the cell at row r, column c
    private int[][] values;
    
    // this is a copy of the board, so that we don't incorrectly overwrite the original 
    private int[][] solution;
    
    // the adjustment needed for the corners
    private int _adjustX, _adjustY = 0;
    
    // the current instance of the corners
    private int _cornerX, _cornerY = 0;
    
    
    
    /** 
     * Constructs a new Puzzle object, which initially
     * has all empty cells.
     */
    public Puzzle() {
    	// initialize the board(s) variables
        values = new int[DIM][DIM];
        solution = new int[DIM][DIM];
    }
    
    /**
     * Solves the puzzle by calling another method to perform
     * recursive backtracking.  Returns true if a solution is found,
     * and false otherwise.
     */
    public boolean solve() {
        
    	// oh so pretty - i just want to hold you ternary operators
    	// does the back track work? If so, copy array else - just return false
        return (backtrack(EMPTY,EMPTY)) ? solutionCopy() : false;
    }
   
    
    /**
     * Reads in a puzzle specification from the specified Scanner,
     * and uses it to initialize the state of the puzzle.  The
     * specification should consist of one line for each row, with the
     * values in the row specified as digits separated by spaces.  A
     * value of 0 should be used to indicate an empty cell.
     */ 
    public void readFrom(Scanner input) {
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {
                int val = input.nextInt();
                
                // ensure we populate both versions of this array
                this.values[r][c] = val;
                
                // here is the array for the solution, so i dont clobber the array values
                this.solution[r][c] = val;
             
            }
            input.nextLine();
        }
    }
    
    /**
     * Displays the current state of the puzzle.
     * You should not change this method.
     */
    public void display() {
    	// if we got this far, we have something in solution. let's copy it to values.
        for (int r = 0; r < DIM; r++) {
            printRowSeparator();
            for (int c = 0; c < DIM; c++) {
                System.out.print("|");
                if (values[r][c] == 0)
                    System.out.print("   ");
                else
                    System.out.print(" " + values[r][c] + " ");
            }
            System.out.println("|");
        }
        printRowSeparator();
    }
    

	/**
	 * solutionCopy()
	 * purpose: this is used to copy the values of my solution to the 
	 * values array once i'm positive that we have done this
	 */
    public boolean solutionCopy(){
    	
        for (int r = 0; r < DIM; r++) {
            for (int c = 0; c < DIM; c++) {           
                // ensure we populate both versions of this array
                this.values[r][c] = this.solution[r][c];          
            }
        }
        
        return true;
    }

	/**
	 * displaySolution()
	 * purpose: this will display my solution array for testing
	 */
    public void displaySolution(){
    	// if we got this far, we have something in solution. let's copy it to values.
        for (int r = 0; r < DIM; r++) {
            printRowSeparator();
            for (int c = 0; c < DIM; c++) {
                System.out.print("|");
                if (this.solution[r][c] == 0)
                    System.out.print("   ");
                else
                    System.out.print(" " + this.solution[r][c] + " ");
            }
            System.out.println("|");
        }
        printRowSeparator();
    }
    
    // A private helper method used by display() 
    // to print a line separating two rows of the puzzle.
    private static void printRowSeparator() {
        for (int i = 0; i < DIM; i++)
            System.out.print("----");
        System.out.println("-");
    }

	/**
	 * backtrack(int x, int y)
	 * @param1: x location of the array
	 * @param2: y location of the array
	 * purpose: purposes of this recursive method is to use a backtracking
	 * algorithm to populate a sudoku board. once the solution the first solution is found, 
	 * the board will return
	 */
	public boolean backtrack(int x, int y) {
		
		int candidate = EMPTY;

		// is the current location empty
		if (this.solution[x][y] == EMPTY) {
			
			// the numbers I, the program, should enter 1-9
			for(candidate=1;candidate<END_SUDOKU_INPUT;candidate++) {
				
				// lets start the constraint 
				if(!shouldRejectThisCandidate(x,y,candidate)){
					
					// constraint check returned false, looks good
					this.acceptCandidate(x,y,candidate);
					
					// looks like I, the program, have gone too far - time to head back
					if(x == DIM_MINUS_ONE && y == DIM_MINUS_ONE) {					
						
							return true;
							
					} else if(x == DIM_MINUS_ONE) {
						// we will increase the Y axis only, start new route
						if(this.backtrack(0,y + 1)){
							return true;
						}
						
					} else {
						// increase x, and start on a new route
						if(this.backtrack(x + 1, y)) {
							return true;						
						}
					}					
					
				}

			}
			
			// no number was found - must've been a invalid entry - set back to empty
			if(candidate == END_SUDOKU_INPUT) {
				
				// if the location digits do not match - then just return empty slot
				if(this.solution[x][y] != this.values[x][y]){					
					
					// blank solution to zero
					this.solution[x][y] = EMPTY;
				
					return false;
				}
					
			}

		} else {
			
				// the current route is not a zero, let us continue.
				if(y == DIM_MINUS_ONE && x == DIM_MINUS_ONE) {
					// end of the line
					return true;
					
				} else if( x == DIM_MINUS_ONE ) {
					
					//increase Y if x is at the max
					if(this.backtrack(0,y + 1)){
						return true;
					}
					
				} else {
					
					// increase x because you know, y isn't maxed yet
					if(this.backtrack(x + 1, y)) {
						return true;
					}
				}
				
		}
		
		// wait what? no solution? What a waste of CPU resources this was, sigh...
		return false;
	}

	/**
	 * shouldRejectThisCandidate(int x, int y, int input)
	 * @param1: x location of the array
	 * @param2: y location of the array
	 * @parmm3: input is the input of the value that we are testing
	 * purpose: purposes if it should reject the candidate based on these tests
	 */
	public boolean shouldRejectThisCandidate(int x, int y, int input) {
		
		// I, this program, wishes to check if this number is good enough!
		//Begin Constraint Checking
		
		//1. Does this board have a value already?
		if(this.checkOriginal(x,y))
			//2. Fine - let's see if it's in the row
			if(this.checkRow(x,y,input))
				//3. Think you are pretty clever, eh? let's try column!
				if(this.checkColumn(x,y,input))
					//4. Final Test: are you in the same zone?
					if(this.checkZone(x,y,input))
						//5. Interesting, proceed
						return false;
		
		
		// Yes! You have been rejected!
		return true;
	}

	/**
	 * acceptCandidate(int x, int y, int input)
	 * @param1: x location of the array
	 * @param2: y location of the array
	 * @parmm3: input is the input of the value that we are testing
	 * purpose: places the input value into the position of the array
	 */
	public boolean acceptCandidate(int x, int y, int input) {
		
		// based on our number, can we even allow this?
		if (input < END_SUDOKU_INPUT) {
			
			// update our solution board
			this.solution[x][y] = input;
			
			// we set the update on the solution board
			return true;
			
		} 
		
		// who put a number in here larger than 9? Oh come on - too good for that trickery! :P
		return false;
	}

	/**
	 * checkOriginal(int x, int y)
	 * @param1: x location of the array
	 * @param2: y location of the array
	 * purpose: checks is original location has empty
	 */
	public boolean checkOriginal(int x, int y) {
		
		// it's so pretty, I love you too ternary operators: hold me
		return (this.values[x][y] == EMPTY) ? true : false;
		
	}

	/**
	 * checkColumn(int _x, int _y, int input)
	 * @param1: x location of the array
	 * @param2: y location of the array
	 * @parmm3: input is the input of the value that we are testing
	 * purpose: checks the column for a valid move
	 */
	public boolean checkColumn(int _x, int _y, int input) {
		
		for(int y=0;y<DIM;y++){
			
			// reset the number location to empty
			int location = EMPTY;
			
			location = this.solution[_x][y];
			
			// if the number is found, return false ASAP
			if(location == input)
				return false;
						
		}
		// valid check
		return true;
	}

	/**
	 * checkRow(int _x, int _y, int input)
	 * @param1: x location of the array
	 * @param2: y location of the array
	 * @parmm3: input is the input of the value that we are testing
	 * purpose: checks the row for a valid move
	 */
	public boolean checkRow(int _x, int _y, int input) {
				
		for(int x=0;x<DIM;x++){
						
			// reset the number location to empty
			int location = EMPTY;
			
			// get the digit at current location
			location = this.solution[x][_y];
			
			// if the number is found, return false ASAP
			if(location == input)
				return false;
						
		}
		// valid check
		return true;
	}

	/**
	 * checkZone(int _x, int _y, int input)
	 * @param1: x location of the array
	 * @param2: y location of the array
	 * @parmm3: input is the input of the value that we are testing
	 * purpose: checks the 3 x 3 zone of the sudoku game for valid move
	 */
	public boolean checkZone(int _x, int _y, int input) {
		// create the index counter for x/y so I don't get confused -_-
		int counterX, counterY, lengthX, lengthY = 0;
		
		// build the surrounding zone conditions
		this.buildZone(_x,_y);
		
		// this will set the correct corner for checking the zone by setting x/x
		this.setCornerX(_x + this.getAdjustedX());
		this.setCornerY(_y + this.getAdjustedY());
		
		
		// our corners are multiples of three, thus, 0,0 - will be 3x3 - this is where we end.
		for(counterY=this.getCornerY(),lengthY=this.getCornerY() + SQUARE_DIM;counterY<lengthY;counterY++) {
			
			for(counterX=this.getCornerX(),lengthX=this.getCornerX() + SQUARE_DIM;counterX<lengthX;counterX++){
				
				// get the current location
				
				int location = this.solution[counterX][counterY];
				
				// check the constraint - does this number live in the current zone?
				if(location == input)
					return false;

			}		
		}
		// valid check
		return true;
	}

	/**
	 * buildZone(int x, int y)
	 * @param 1: x location of the array
	 * @param 2: y location of the array
	 * purpose: builds the 3 x 3 zone of the sudoku game
	 */
	public void buildZone(int x, int y) {
		
		// based on the current location of our i'th indexes for x/y we adjust
		for (int i=0;i<DIM;i++){
			
			// set the proper corner adjust for Y
			if(y == i){
				
				// there is a formula / pattern for correcting the adjustments
				if(i == 1 || i == 4 || i == 7)
					this.setAdjustY(-1);
				else if( i== 2 || i == 5 || i==8)
					this.setAdjustY(-2);
				else
					this.setAdjustY(0);
									
			}
			
			// set the proper corner adjust for x
			if(x == i){
				
				// there is a formula / pattern for correcting the adjustments
				if(i == 1 || i == 4 || i == 7)
					this.setAdjustX(-1);
				else if( i== 2 || i == 5 || i==8)
					this.setAdjustX(-2);
				else
					this.setAdjustX(0);
									
			}
		}
	}

	/**
	 * setAdjustedx()
	 * purpose: sets the adjusted amount for x
	 */
	public void setAdjustX(int x) {
		this._adjustX = x;
	}

	/**
	 * setAdjustedy()
	 * purpose: sets the adjusted amount for y
	 */
	public void setAdjustY(int y) {
		this._adjustY = y;
	}

	/**
	 * getAdjustedX()
	 * purpose: gets the adjusted amount for x
	 */
	public int getAdjustedX() {
		return this._adjustX;
	}

	/**
	 * getAdjustedY()
	 * purpose: gets the adjusted amount for y
	 */
	public int getAdjustedY() {
		return this._adjustY;
	}

	/**
	 * setCornerX()
	 * purpose: sets the corner of X axis
	 */
	public void setCornerX(int x) {
		this._cornerX = x;
	}

	/**
	 * setCornerY()
	 * purpose: sets the corner of y axis
	 */
	public void setCornerY(int y) {
		this._cornerY = y;
	}

	/**
	 * getCornerX()
	 * purpose: gets the corner of xaxis
	 */
	public int getCornerX() {
		return this._cornerX;
	}

	/**
	 * getCornerY()
	 * purpose: gets the corner of yaxis
	 */
	public int getCornerY() {
		return this._cornerY;
	}

	
}
