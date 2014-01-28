package main;

import java.math.*;
public class Sudoku {

	
	/*
	 * Description Sudoku
	 * 
	 * 0 = Valeur de la case
	 * # = Nombre de donn�es > 0 de la colonne/ligne
	 * # sera utilis� plus tard pour un algorithme plus avance
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
					
						// Calcul du nombre de donn�es ou != 0 par range/colonne
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
	
	// i : Position en X
	// j : Position en Y
	// k : Valeur numerique a verifier
	public static boolean EstValide(int i, int j, int k){
		
		if(i > TAILLE_MAX || j > TAILLE_MAX) return false;
		if(k == 0) return true;
		
		int offsetI = (int)Math.floor(i/3) * 3;
		int offsetJ = (int)Math.floor(j/3) * 3;
		
		// Verification de la rangee
		for(int z = 0; z < TAILLE_MAX; z ++){
			
			// Verification de la rangee
			if(z != i){
				if (sudoku[z][j] == k) return false;
			}
			
			// Verification de la colonne
			if(z != j){
				if (sudoku[i][z] == k) return false;
			}
			
			// Verification dans le carre 3x3
			if((offsetI + z%3) != i && (offsetJ + (int)Math.floor(z/3)) != j){
				
				//System.out.println("i : " + i + " Offset I : " + offsetI + " Cell I " + z%3 + " j : " + j + " Offset J : " + offsetJ + " Cell J " + (int)Math.floor(z/3));
				if(sudoku[offsetI + z%3][offsetJ + (int)Math.floor(z/3)] == k) return false;
			}
		}
		
		return true;
	}
	
	// Verifie tout le sudoku
	public static boolean EstValide(){
		
		for(int i = 0; i < TAILLE_MAX; i ++){
			for(int j = 0; j < TAILLE_MAX; j ++){
				if (!EstValide(i,j,sudoku[i][j])) return false;
			}
		}
		
		return true;
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
	
	// Solver le sudoku
	public static boolean backTracking(int i, int j){
		
		int nextI = i, nextJ = j;
		
		// Recherche de la prochaine case a verifier (Valeur initiale doit etre = zero)
		do{
			if(nextJ < (TAILLE_MAX - 1)){
				nextJ 	++;
			}
			else if(nextI < (TAILLE_MAX - 1)){
				nextI 	++;
				nextJ = 0;
			}
			else{
				// Derniere case du tableau 2D Sudoku atteinte
				// i = 9, j = 9
				// doit faire le retour final !
				
				System.out.println("LAST I : " + nextI + " LAST J : " + nextJ);
				
				return true;
			}
			
		// Boucle pour sauter par dessus les cases ou il y a deja un chiffre
		}while(sudoku[nextI][nextJ] != 0);
		
		//System.out.println("Next I : " + nextI + " Next J : " + nextJ);
		
		// Verification des differentes possibilites
		for(int cell = 1; cell <= TAILLE_MAX; cell ++){
				
				System.out.println(i + ":" + j + " TRY : " + cell);
				
				if(EstValide(i, j, cell)){
					sudoku[i][j] = cell;
					backTracking(nextI, nextJ);
				}
		}
	
		System.out.println(" FALSE ;( ");
		
		// Re Initialisation de la case ...
		sudoku[i][j] = 0;
		return false;
	}
}