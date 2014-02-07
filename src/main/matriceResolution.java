package main;

import javax.lang.model.element.Name;
import java.util.ArrayList;

/**
 * Created by alexandre on 14-01-29.
 */
public class matriceResolution {
    final static private int TAILLE_MAX = 9;

    public static Boolean[][][] genererTablePossibilites(int[][] sudoku, Boolean toFill) throws Exception{
        Boolean[][][] tableRetour = new Boolean[9][9][9];
        for(int i=0;i<tableRetour.length;i++){
            for(int j = 0;j<tableRetour[i].length;j++){
                if(toFill){
                    tableRetour[i][j] = obtenirResultatPossibleWithFilling(sudoku,i,j);
                }else{
                    tableRetour[i][j] = obtenirResultatPossible(sudoku,i,j);
                }
            }
        }
        return tableRetour;
    }

    public static ArrayList<int[]> obtenirListePositionsPreferable(int[][] sudoku){

        //int[][] listePreferable = new int[81][2];
        ArrayList<int[]> lstPositionPref = new ArrayList<int[]>();
        int[][] tabSolving = new int[9][9];

        for(int i = 0;i<TAILLE_MAX;i++){
            for(int j = 0;j<TAILLE_MAX;j++){
                if(sudoku[i][j] != 0){
                    tabSolving = addValue(i,j,tabSolving,sudoku);
                }
            }
        }
        int meilleurScore =1;
        while(meilleurScore>0){
            int meilleurI = -1;
            int meilleurJ = -1;
            meilleurScore =0;
            for(int i =0;i<tabSolving.length;i++){
                for(int j = 0;j<tabSolving[i].length;j++){
                    if(tabSolving[i][j] >= meilleurScore){
                        meilleurScore = tabSolving[i][j];
                        meilleurI = i;
                        meilleurJ = j;
                    }
                }
            }

            lstPositionPref.add(new int[]{meilleurI,meilleurJ});
            if(meilleurI > -1 && meilleurJ > -1)
                tabSolving[meilleurI][meilleurJ] = -1;
        }
        return lstPositionPref;
    }

    public static Boolean[] obtenirResultatPossibleWithFilling(int[][] puzzleEncours, int i, int j) throws Exception{
        Boolean[] tabResult = new Boolean[9];
        int compteur = 0;
        for(int x=0;x<TAILLE_MAX;x++){
            // on vérifie toute la ligne et colone de la case trouvée
            if(x != i){
                if(puzzleEncours[x][j] != 0){
                    if(tabResult[puzzleEncours[x][j] -1] == null)
                        compteur++;
                    tabResult[puzzleEncours[x][j] -1] = true;
                }
            }

            if(x != j){
                if(puzzleEncours[i][x] != 0){
                    if(tabResult[puzzleEncours[i][x] -1] == null)
                        compteur++;
                    tabResult[puzzleEncours[i][x] -1] = true;
                }
            }
        }


        int startIndexI = getStartIndexForSquare(i);
        int startIndexJ = getStartIndexForSquare(j);
        int maxN = startIndexI + 3;
        int maxM = startIndexJ + 3;

        //todo a optimiser ici !
        for(int n = startIndexI; n<maxN;n++){
            for(int m = startIndexJ; m<maxM;m++){
                if(n != i && m != i){
                    if(puzzleEncours[n][m] != 0){
                        if(tabResult[puzzleEncours[n][m] -1] == null)
                            compteur++;
                        tabResult[puzzleEncours[n][m] -1] = true;
                    }
                }

            }
        }

        if(compteur == 8){
            for(int x=0;x<tabResult.length;x++){
                if(tabResult[x] == null){
                    if(Sudoku.EstValide(i,j,x+1)){
                        puzzleEncours[i][j] = x+1;
                    }
                }
            }
        }else if(compteur == 9){
            puzzleEncours[i][j] = 0;
            throw new Exception();
        }

        return tabResult;
    }



    public static Boolean[] obtenirResultatPossible(int[][] puzzleEncours, int i, int j){
        Boolean[] tabResult = new Boolean[9];
        for(int x=0;x<TAILLE_MAX;x++){
            // on vérifie toute la ligne et colone de la case trouvée
            if(x != i){
                if(puzzleEncours[x][j] != 0){
                    if(puzzleEncours[x][j] != 0){
                        tabResult[puzzleEncours[x][j] -1] = true;
                    }
                }
            }

            if(x != j){
                if(puzzleEncours[i][x] != 0){
                    if(puzzleEncours[i][x] != 0){
                        tabResult[puzzleEncours[i][x] -1] = true;
                    }
                }
            }
        }


        int startIndexI = getStartIndexForSquare(i);
        int startIndexJ = getStartIndexForSquare(j);
        int maxN = startIndexI + 3;
        int maxM = startIndexJ + 3;

        //todo a optimiser ici !
        for(int n = startIndexI; n<maxN;n++){
            for(int m = startIndexJ; m<maxM;m++){
                if(puzzleEncours[n][m] != 0){
                    tabResult[puzzleEncours[n][m] -1] = true;
                }

            }
        }
        return tabResult;
    }

    private static int[][] createNewMatrice(int[][] puzzleEnCours){
        // on initialise la table
        int[][] tabSolving = new int[9][9];
        for(int i = 0;i<tabSolving.length;i++){
            tabSolving[i] = new int[9];
            for(int j = 0;j<tabSolving[i].length;j++){
                if(puzzleEnCours[i][j] == 0){
                    tabSolving[i][j] = 0;
                }else{
                    tabSolving[i][j] = -1;
                }
            }
        }
        return tabSolving;
    }



    private static int getStartIndexForSquare(int position){
        int resultmodulo = (position+1) % 3;
        int startIndex = 0;
        if(resultmodulo == 1){
            startIndex = position;
        }else if(resultmodulo == 2){
            startIndex = position-1;
        }else if(resultmodulo == 0){
            startIndex = position -2;
        }
        return startIndex;
    }

    public static int[][] addValue(int i,int j,int[][] tabSolving,int[][] sudoku){

        for(int x=0;x<TAILLE_MAX;x++){
            if(x != i){
                if(sudoku[x][j] == 0){
                    tabSolving[x][j]++;
                }
            }

            if(x != j){
                if(sudoku[i][x] == 0){
                    tabSolving[i][x]++;
                }
            }
        }

        int startIndexI = getStartIndexForSquare(i);
        int startIndexJ = getStartIndexForSquare(j);
        // TODO FOO : A OPTIMISER ! (FCT EST VALIDE)
        int maxN = startIndexI + 3;
        int maxM = startIndexJ + 3;
        for(int n = startIndexI; n<maxN;n++){
            for(int m = startIndexJ; m<maxM;m++){
                if(sudoku[n][m] == 0){
                    tabSolving[n][m] ++;
                }
            }
        }
        return tabSolving;
    }
}
