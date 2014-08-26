/****************************************************************************
 * IBacktracking.java
 *
 * Computer Science E119
 * Christopher Bartholomew
 *
 * Interface for Backtracking Data Structure
 *
 * Allows user to implement interface.
 ***************************************************************************/
public interface IBacktracking {
	boolean backtrack(int x, int y);
	boolean shouldRejectThisCandidate(int x, int y, int input);
	boolean acceptCandidate(int x, int y, int input);
	boolean checkOriginal(int x, int y);
	boolean checkColumn(int x, int y, int input);
	boolean checkRow(int x, int y, int input);
	boolean checkZone(int x, int y,int input);
	void buildZone(int x, int y);
	void	setAdjustX(int x);
	void 	setAdjustY(int y);
	int		getAdjustedX();
	int		getAdjustedY();
	void	setCornerX(int x);
	void	setCornerY(int y);
	int		getCornerX();
	int		getCornerY();

	
}
