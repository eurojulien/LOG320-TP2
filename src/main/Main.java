package main;

/*
 * La classe BinaryStdIn a ete trouvee sur le site : http://introcs.cs.princeton.edu/java/stdlib/BinaryStdIn.java.html
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Sudoku.getSudokuFromFile(args[0]);
		
		Sudoku.printDebugSudoku();
		
		System.out.println("Sudoku de base est valide : " + Sudoku.EstValide());

        long startTime = System.nanoTime();

        Sudoku.setListeOrdrePreferable(matriceResolution.obtenirListePositionsPreferable(Sudoku.getSudoku()));

		Sudoku.backTracking(0);

        long endTime = System.nanoTime();

		Sudoku.printDebugSudoku();

		System.out.println("Sudoku final est valide : " + Sudoku.EstValide());
		
		System.out.println("Elapsed Time : " + (endTime - startTime)/(1000000) + " milliseconds");
	}

}