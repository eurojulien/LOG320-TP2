package main;

/*
 * La classe BinaryStdIn a ete trouvee sur le site : http://introcs.cs.princeton.edu/java/stdlib/BinaryStdIn.java.html
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Sudoku.getSudokuFromFile(args[0]);
		
		Sudoku.printDebugSudoku();
	}

}