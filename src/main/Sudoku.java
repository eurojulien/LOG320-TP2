package main;

import java.math.*;
import java.util.ArrayList;

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
	public static int[][] sudoku 			= new int[TAILLE_MAX + 1][TAILLE_MAX + 1];
    private static Boolean[][][] tableauPossibilite;
    public static ArrayList<int[]> listeOrdrePreferable;

	private Sudoku(){}

    public static void setTableauPossibilite(Boolean[][][] tabPoss){
        tableauPossibilite = tabPoss;
    }

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

						sudoku[i][j]				= cell;

						// Calcul du nombre de donn�es ou != 0 par range/colonne
						if(cell != 0){
							sudoku[TAILLE_MAX][j]	++;
							sudoku[i][TAILLE_MAX]	++;
						}
					}
				}
			}
		}

		BinaryStdIn.close();
	}

    public static int[][] getSudoku(){
        return sudoku;
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

		for (int i = 0; i < TAILLE_MAX; i ++){

			System.out.println("- - - - - - - - -");

			for (int j = 0; j < TAILLE_MAX; j ++){

				System.out.print(sudoku[i][j] + " ");
			}

			System.out.println("");
		}

        System.out.println(" = = = = = = = = = ");
	}
}