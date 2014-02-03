package main;

import javax.lang.model.element.Name;
import java.util.ArrayList;

/**
 * Created by alexandre on 14-01-29.
 */
public class matriceResolution {
    int[][] puzzleEnCours;
    matriceResolution parent;
    int[][] tabSolving = new int[9][9];
    final static private int TAILLE_MAX = 9;
    private int highestScore = 0;
    String name = "";
    ArrayList<int[]> tabCoordoneeGagnante = new ArrayList<int[]>();


    public static int[][] obtenirListePositionsPreferable(int[][] sudoku){

        int[][] listePreferable = new int[81][2];
        int[][] tabSolving = new int[9][9];

        for(int i = 0;i<TAILLE_MAX;i++){
            for(int j = 0;j<TAILLE_MAX;j++){
                if(sudoku[i][j] != 0){
                    tabSolving = addValue(i,j,tabSolving);
                }
            }
        }

        for(int x =0; x<listePreferable.length;x++){
            int meilleurI = -1;
            int meilleurJ = -1;
            int meilleurScore =0;
            for(int i =0;i<tabSolving.length;i++){
                for(int j = 0;j<tabSolving[i].length;j++){
                    if(tabSolving[i][j] >= meilleurScore){
                        meilleurScore = tabSolving[i][j];
                        meilleurI = i;
                        meilleurJ = j;
                    }
                }
            }
            listePreferable[x][0] = meilleurI;
            listePreferable[x][1] = meilleurJ;
        }
        return listePreferable;
    }

    public static String obtenirResultatPossible(int[][] puzzleEncours, int i, int j){
        String retour = "";
        for(int x=0;x<TAILLE_MAX;x++){
            // on vérifie toute la ligne et colone de la case trouvée
            if(x != i){
                if(puzzleEncours[x][j] != 0){
                    if(!retour.contains("" + puzzleEncours[x][j])){
                        if(puzzleEncours[x][j] != 0){
                            retour += puzzleEncours[x][j];
                        }
                    }
                }
            }

            if(x != j){
                if(puzzleEncours[i][x] != 0){
                    if(!retour.contains("" + puzzleEncours[i][x])){
                        if(puzzleEncours[i][x] != 0){
                            retour += puzzleEncours[i][x];
                        }
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
                if(!retour.contains("" + puzzleEncours[n][m])){
                    if(puzzleEncours[n][m] != 0){
                        retour += puzzleEncours[n][m];
                    }
                }
            }
        }


        return retour;
    }

    public matriceResolution(int[][] puzzleEnCours,matriceResolution parent,String name){
        this.puzzleEnCours = puzzleEnCours;
        this.parent = parent;
        this.name = name;
        createNewMatrice();
        calculeValeurs();
    }

    private void calculeValeurs(){
        // on itère dans la table du sudoku pour remplir la table de solving
        for(int i = 0;i<TAILLE_MAX;i++){
            for(int j = 0;j<TAILLE_MAX;j++){
                if(puzzleEnCours[i][j] != 0){
                    //addValue(i,j);
                }
            }
        }

        // on trouve les coordonées les plus probables
        for(int i = 0;i<TAILLE_MAX;i++){
            for(int j = 0;j<TAILLE_MAX;j++){
                    if(tabSolving[i][j] == highestScore){
                        tabCoordoneeGagnante.add(new int[] {i,j});
                    }
            }
        }

        // pour chaque case gagnante, on crée des enfants a cette occurence
        int plusGrandScore = 0;
        Boolean hasWinner = false;
        ArrayList<String> strTabValues = new ArrayList<String>();
        for(int s = 0; s< tabCoordoneeGagnante.size();s++){
            String accumulateur = "";
            int i = tabCoordoneeGagnante.get(s)[0];
            int j = tabCoordoneeGagnante.get(s)[1];
            for(int x=0;x<TAILLE_MAX;x++){
                // on vérifie toute la ligne et colone de la case trouvée
                if(x != i){
                    if(puzzleEnCours[x][j] != 0){
                        if(!accumulateur.contains("" + puzzleEnCours[x][j])){
                            if(puzzleEnCours[x][j] != 0){
                                accumulateur += puzzleEnCours[x][j];
                            }
                        }
                    }
                }

                if(x != j){
                    if(puzzleEnCours[i][x] != 0){
                        if(!accumulateur.contains("" + puzzleEnCours[i][x])){
                            if(puzzleEnCours[i][x] != 0){
                                accumulateur += puzzleEnCours[i][x];
                            }
                        }
                    }
                }
            }

            int startIndexI = getStartIndexForSquare(i);
            int startIndexJ = getStartIndexForSquare(j);
            int maxN = startIndexI + 3;
            int maxM = startIndexJ + 3;
            for(int n = startIndexI; n<maxN;n++){
                for(int m = startIndexJ; m<maxM;m++){
                        if(!accumulateur.contains("" + puzzleEnCours[n][m])){
                            if(puzzleEnCours[n][m] != 0){
                                accumulateur += puzzleEnCours[n][m];
                            }
                        }
                }
            }
            strTabValues.add(accumulateur);
            if(accumulateur.length() == 7){
                hasWinner = true;
            }else if(accumulateur.length() == 8){
                System.out.println(name + " une erreur s'est produite ici !");

            }
        }
        if(strTabValues.size() == 0){
            System.out.println("COUCOU");
        }

        System.out.println(name + ": HIGHSCORE IS :" + highestScore + " Has winner :" + hasWinner);
        for(int x = 0; x<strTabValues.size();x++){
            String strEnCours = analyseChaine(strTabValues.get(x));
            int[] positionEncours = tabCoordoneeGagnante.get(x);

            int posX = positionEncours[0] + 1;
            int posY = positionEncours[1] + 1;

            if(strEnCours.length() == 1){
                System.out.println(name + ":La position " + posX  + ":" + posY);
                System.out.println(name + ":Pleine certitude, on insert : " + strEnCours);
                // coder la descente directe
                puzzleEnCours[positionEncours[0]][positionEncours[1]] = Integer.parseInt(strEnCours);
                matriceResolution MR = new matriceResolution(puzzleEnCours,this,name + ":" + strEnCours);
            }else if(!hasWinner){
                // on garde le/les plus probable et on recommence a partir de là
                System.out.println(name + ":La position " + posX  + ":" + posY);
                System.out.println(name + ":On essaie les valeurs : " + strEnCours);
                if(strEnCours.length() == 0){
                    System.out.println(name + "COUCOU !");
                }
                for(int s=0;s<strEnCours.length();s++){
                    String caseTestee = strEnCours.substring(s,s+1);
                    puzzleEnCours[positionEncours[0]][positionEncours[1]] = Integer.parseInt(caseTestee);
                    matriceResolution MR = new matriceResolution(puzzleEnCours,this,name + ":" + caseTestee);
                }
            }else{
                System.out.println(name + " Lenght:" + strEnCours + " n'a pas passer");
            }
        }

        System.out.println(name + ": out ---");

    }
   private String analyseChaine(String contenu){
       String retour= "";
       for(int i = 1; i<10;i++){
           if(!contenu.contains(""+i)){
               retour += i;
           }
       }
       return retour;
   }


    private void createNewMatrice(){
        // on initialise la table
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
    }

    public void printDebugSudoku(){
        System.out.println("===Debug matrice===");
        for (int i = 0; i < TAILLE_MAX; i ++){

            System.out.println("- - - - - - - - -");

            for (int j = 0; j < TAILLE_MAX; j ++){

                System.out.print(puzzleEnCours[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println("===Debug matrice===");
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

    public static int[][] addValue(int i,int j,int[][] tabSolving){

        for(int x=0;x<TAILLE_MAX;x++){
            if(x != i){
                if(tabSolving[x][j] != -1){
                    tabSolving[x][j]++;
                }
            }

            if(x != j){
                if(tabSolving[i][x] != -1){
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
                if(tabSolving[n][m] != -1){
                    tabSolving[n][m] ++;
                }
            }
        }
        return tabSolving;
    }
}
