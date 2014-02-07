package main;

/*
 * La classe BinaryStdIn a ete trouvee sur le site : http://introcs.cs.princeton.edu/java/stdlib/BinaryStdIn.java.html
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        Sudoku.getSudokuFromFile(args[0]);
		
		Sudoku.printDebugSudoku();

        if(Sudoku.EstValide()){
            System.out.println("Sudoku de base est valide");

            long startTime = System.nanoTime();
            long endTime = 0;

            SudokuSolver ss = new SudokuSolver(Sudoku.getSudoku());

            if(ss.backTracking()){
                System.out.println("Sudoku résolut!");
                ss.printDebugSudoku();
                endTime = System.nanoTime();
            }else{
                System.out.println("Sudoku non résolut!");
            }
            System.out.println("Elapsed Time : " + (endTime - startTime)/(1000000) + " milliseconds");

        }else{
            System.out.println("Sudoku de base n'est pas valide");
        }

	}

}