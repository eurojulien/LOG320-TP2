package main;

import java.util.ArrayList;

/**
 * Created by alexandre on 14-02-07.
 */
public class AutoComplete {
    public static final int TAILLE_MAX = 9;
    private int[][] sudoku;
    ArrayList<int[]> tabDidntFind = new ArrayList<int[]>();


    public int[][] getSudoku(){
        return sudoku;
    }

    public AutoComplete(int[][] paramSudoku){
        this.sudoku = manualClone(paramSudoku);
    }

    private int[][] manualClone(int[][] sudo){
        int[][] nouvoSudo =new int[9][9];
        for(int i=0;i<TAILLE_MAX;i++){
            for(int j=0;j<TAILLE_MAX;j++){
                nouvoSudo[i][j]=sudo[i][j];
            }
        }
        return nouvoSudo;
    }

    public Boolean tryAutoComplete(){
        Boolean retour = true;
        for(int i = 0;i<sudoku.length;i++){
            for(int j = 0;j<sudoku[i].length;j++){
                if(sudoku[i][j] == 0){
                    if(!obtenirResultatPossibleWithFilling(i,j)) return false;
                }
            }
        }

        for(int i = 0;i<tabDidntFind.size();i++){
            if(!obtenirResultatPossibleWithFilling(tabDidntFind.get(i)[0],tabDidntFind.get(i)[1]))return false;
        }

        return retour;
    }

    public Boolean obtenirResultatPossibleWithFilling(int i, int j){
        Boolean valid = true;
        Boolean[] tabResult = new Boolean[9];
        int compteur = 0;
        for(int x=0;x<TAILLE_MAX;x++){
            // on vérifie toute la ligne et colone de la case trouvée
            if(x != i){
                if(sudoku[x][j] != 0){
                    if(tabResult[sudoku[x][j] -1] == null)
                        compteur++;
                    tabResult[sudoku[x][j] -1] = true;
                }
            }

            if(x != j){
                if(sudoku[i][x] != 0){
                    if(tabResult[sudoku[i][x] -1] == null)
                        compteur++;
                    tabResult[sudoku[i][x] -1] = true;
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
                    if(sudoku[n][m] != 0){
                        if(tabResult[sudoku[n][m] -1] == null)
                            compteur++;
                        tabResult[sudoku[n][m] -1] = true;
                    }
                }
            }
        }

        if(compteur == 8){
            for(int x=0;x<tabResult.length;x++){
                if(tabResult[x] == null){
                    if(Sudoku.EstValide(i,j,x+1)){
                        sudoku[i][j] = x+1;
                    }
                }
            }

        }else if(compteur == 9){
            sudoku[i][j] = 0;
            valid = false;
        }else{
            tabDidntFind.add(new int[]{i,j});
        }

        return valid;
    }

    private int getStartIndexForSquare(int position){
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

    public void printDebugSudoku(){

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
