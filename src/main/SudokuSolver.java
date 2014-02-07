package main;

import java.util.ArrayList;

/**
 * Created by alexandre on 14-02-06.
 */
public class SudokuSolver {

    int compteur = 0;
    int[][] sudoku;
    private final int TAILLE_MAX		= 9;
    private Boolean[][][] tableauPossibilite;
    public ArrayList<int[]> listeOrdrePreferable;

    public SudokuSolver(int[][] sudoku,ArrayList<int[]> listeOrdrePreferable){
        tableauPossibilite = new Boolean[9][9][9];
        this.sudoku = manualClone(sudoku);
        this.listeOrdrePreferable = listeOrdrePreferable;
    }

    public SudokuSolver(int[][] sudoku){
        tableauPossibilite = new Boolean[9][9][9];
        this.sudoku = manualClone(sudoku);
        this.listeOrdrePreferable = obtenirListePositionsPreferable();
    }

    public SudokuSolver(int[][] sudoku,ArrayList<int[]> listeOrdrePreferable,int compteur){
        tableauPossibilite = new Boolean[9][9][9];
        this.sudoku = manualClone(sudoku);
        this.listeOrdrePreferable = listeOrdrePreferable;
        this.compteur = compteur;
    }

    public void setTableauPossibilite(Boolean[][][] tabPoss){
        tableauPossibilite = tabPoss;
    }

    public int[][] getSudoku(){
        return sudoku;
    }

    public void setListeOrdrePreferable(ArrayList<int[]> listeOrdrePreferableRecu){
        listeOrdrePreferable = listeOrdrePreferableRecu;
    }

    public boolean EstValide(int i, int j, int k){

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

    public boolean EstValide(){

        for(int i = 0; i < TAILLE_MAX; i ++){
            for(int j = 0; j < TAILLE_MAX; j ++){
                if (!EstValide(i,j,sudoku[i][j])) return false;
            }
        }

        return true;
    }

    public boolean backTracking(){
        if(!genererTablePossibilites(true)){
            // on remplis la liste des possibilitée et on regarde pour les certitudes.
            // si l'algo détecte un problème, on retourne false
            return false;
        }
        if(compteur == listeOrdrePreferable.size()){
            // pas sur que sa veut dire victoire, mais bon todo chek it out
            return true;
        }

        // on obtien la position courrante de la liste, puis on incrémente
        int i = listeOrdrePreferable.get(compteur)[0];
        int j = listeOrdrePreferable.get(compteur)[1];
        while(sudoku[i][j] != 0){
            // si jamais la position trouvée est déjà remplie par une certitude
            compteur ++;
            if(compteur == listeOrdrePreferable.size()){
                return true;
            }else{
                i = listeOrdrePreferable.get(compteur)[0];
                j = listeOrdrePreferable.get(compteur)[1];
            }
        }
        compteur ++;



        // on trouve la valeur manuellement
        Boolean[] tabValeurTrouvee = obtenirResultatPossible(i,j);
        for(int x=0;x<tabValeurTrouvee.length;x++){
            if(tabValeurTrouvee[x]==null){
                if(EstValide(i,j,x+1)){
                    sudoku[i][j] = x+1;
                    SudokuSolver ss = new SudokuSolver(sudoku, listeOrdrePreferable,compteur);
                    //System.out.println(compteur + "("+i+"," + j + "):" + (x+1));
                    if(ss.backTracking()){
                        this.sudoku = ss.getSudoku();
                        return true;
                    }else{
                        //System.out.println("we have returned");
                    }
                }else{
                    // resultat non valide
                    //printDebugSudoku();
                    //System.out.println(compteur + "("+i+"," + j + "):" + (x+1));
                    //System.out.println("coucou !");
                }
            }
        }


        return false;
    }

    public ArrayList<int[]> obtenirListePositionsPreferable(){
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
        }

        tableauPossibilite[i][j] = tabResult;
        return valid;
    }

    public Boolean[] obtenirResultatPossible(int i, int j){
        Boolean[] tabResult = new Boolean[9];
        for(int x=0;x<TAILLE_MAX;x++){
            // on vérifie toute la ligne et colone de la case trouvée
            if(x != i){
                if(sudoku[x][j] != 0){
                    if(sudoku[x][j] != 0){
                        tabResult[sudoku[x][j] -1] = true;
                    }
                }
            }

            if(x != j){
                if(sudoku[i][x] != 0){
                    if(sudoku[i][x] != 0){
                        tabResult[sudoku[i][x] -1] = true;
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
                if(sudoku[n][m] != 0){
                    tabResult[sudoku[n][m] -1] = true;
                }

            }
        }
        return tabResult;
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

    public int[][] addValue(int i,int j,int[][] tabSolving,int[][] sudoku){

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

    public Boolean genererTablePossibilites(Boolean toFill){
        Boolean valid = true;
        for(int i=0;i<tableauPossibilite.length;i++){
            for(int j = 0;j<tableauPossibilite[i].length;j++){
                if(toFill){
                    if(!obtenirResultatPossibleWithFilling(i,j)){
                        return false;
                    }
                }else{
                    tableauPossibilite[i][j] = obtenirResultatPossible(i,j);
                }
            }
        }
        return valid;
    }
}
