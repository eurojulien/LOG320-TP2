package main;

/*
 * La classe BinaryStdIn a ete trouvee sur le site : http://introcs.cs.princeton.edu/java/stdlib/BinaryStdIn.java.html
 */

public class Main {

	private final static int MAX_FILES = 5;
	
	public static void main(String[] args) {
		
		// Test de plusieurs sudoku si parametre args[0] est vide
		if (args.length == 0){
			testMultiSudokus();
			return;
		}
			
		Sudoku.getSudokuFromFile(args[0]);
		
		Sudoku.printSudoku();
		
		System.out.println("Sudoku de base est valide : " + Sudoku.EstValide());
		
		long startTime = System.nanoTime();
		
		Sudoku.backTracking(0, 0);
		
		long endTime = System.nanoTime();
		
		Sudoku.printSudoku();
		
		System.out.println("Sudoku final est valide : " + Sudoku.EstValide());
	
		System.out.println("Elapsed Time : " + (endTime - startTime)/(1000000) + " milliseconds");
	}
	
	// Méthode pour tester plusieurs sudoku
	public static void testMultiSudokus()
	{
		for(int z = 1; z <= MAX_FILES; z++){
			
			Sudoku.getSudokuFromFile("sudoku" + z + ".txt");
			
			Sudoku.printSudoku();
			
			System.out.println("Sudoku de base [" + z + "] est valide : " + Sudoku.EstValide());
			
			long startTime = System.nanoTime();
			
			Sudoku.backTracking(0, 0);
			
			long endTime = System.nanoTime();
			
			Sudoku.printSudoku();
			
			System.out.println("Sudoku final [" + z + "] est valide : " + Sudoku.EstValide());
		
			System.out.println("Elapsed Time : " + (endTime - startTime)/(1000000) + " milliseconds");

		}
		
	}

}