package main;

public class Sudoku {

	
	/*
	 * Description Sudoku
	 * 
	 * 0 = Valeur de la case
	 * # = Nombre de données > 0 de la colonne/ligne
	 * # sera utilisé plus tard pour un algorithme plus avance
	 * _ _ _ _ _ _ _ _ _ _ _ _ _
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * - - - - - - - - - - - - -
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * - - - - - - - - - - - - - 
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * | 0 0 0 | 0 0 0 | 0 0 0 | #
	 * - - - - - - - - - - - - -
	 *   # # #   # # #   # # # 
	 */
	private static final int TAILLE_MAX		= 9;
	private static int[][] sudoku 			= new int[TAILLE_MAX + 1][TAILLE_MAX + 1];
	
	
	private Sudoku(){}
	
	// Creation du sudoku a partir d'un fichier texte 
	public static void getSudokuFromFile(String fileName){
	
		BinaryStdIn.setInputFile(fileName);
		int[] columnCounter 	= new int[TAILLE_MAX];
		int cell				= 0;
		
		for (int i = 0; i <= TAILLE_MAX; i ++){
			
			if (i < TAILLE_MAX){
				
				for(int j = 0; j <= TAILLE_MAX; j++){
					
					
					// Lecture des cellules du sudoku
					if(j < TAILLE_MAX){
						
						// Important, car '/n' et '/r' retournent '-1'
						do{
							cell 						= Character.getNumericValue((int) BinaryStdIn.readChar());
						}while(cell < 0);
						
						sudoku[j][i]				= cell;
					
						// Calcul du nombre de données != 0 par range/colonne
						if(cell != 0){
							sudoku[TAILLE_MAX][i]	++;
							sudoku[j][TAILLE_MAX]	++;
						}
					}				
				}
			}
		}
		
		BinaryStdIn.close();
	}
	
	// Fonction Debug
	public static void printDebugSudoku(){
		
		for (int i = 0; i < TAILLE_MAX + 1; i ++){
			
			System.out.println("- - - - - - - - -");
			
			for (int j = 0; j < TAILLE_MAX + 1; j ++){
				
				System.out.print(sudoku[j][i] + " ");
			}
			
			System.out.println("");
		}
	}
	
	
}
