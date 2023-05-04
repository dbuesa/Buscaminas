package minesdbl.model;

import minesdbl.vista.Vista;

public class Model {

    static int numFiles, numColumnes, numBombes;
    static boolean jocFinalitzat;
    static char[][] minesOcult, minesVisible;

    /**
     * inicia el joc i crida a les demés funcions.
     *
     * @param nf número de files
     * @param nc número de columnes
     * @param nb número de bombes
     */
    public static void initJoc(int nf, int nc, int nb) {
        numFiles = nf;
        numColumnes = nc;
        numBombes = nb;

        char[][] mo = new char[numFiles][numColumnes];
        char[][] mv = new char[numFiles][numColumnes];

        minesOcult = mo;
        minesVisible = mv;

        iniciarCampMines(minesOcult, ' ');
        iniciarCampMines(minesVisible, '·');
        introBombes(minesOcult, numBombes);
        comptarBombes(minesOcult);
        Vista.mostrarCamp(minesVisible);
    }

    /**
     * funció que verifica que els valors proporcionats es trobin dins de l'array bidimensional que representa el
     * pescamines
     *
     * @param nf número enter que determine el número de files
     * @param nc número enter que determine el número de columnes
     * @return un bolean que indica si se surt fora dels valors vàlids o no.
     */
    public static boolean verificacio(int nf, int nc) {
        if (nf < 0 || nf > 7 || nc < 0 || nc > 7) return false;
        else return true;
    }


    /**
     * Funció que declara si el joc ha sigut finalitzat o no
     *
     * @return un bolean true si ha finalitzat, false si no.
     */
    public static boolean jocFinalitzat() {

        return jocFinalitzat;

    }

    /**
     * Funció que genera el camp de mines ocult i el visible en funció de les files i les columnes
     *
     * @param cm vector bidimensional a ser modificat
     * @param c  caràcter que s'introdueix
     */
    static void iniciarCampMines(char[][] cm, char c) {
        for (int i = 0; i < numFiles; ++i)
            for (int j = 0; j < numColumnes; j++) {
                cm[i][j] = c;
            }
    }

    /**
     * Funció que introdueix en un vector el caràcter 'B' (bombes) aleatòriament
     *
     * @param cmo     (camp de mines ocult) vector bidimensional on s'introdueixen les bombes
     * @param nBombes enter que determina el número de bombes
     */
    static void introBombes(char[][] cmo, int nBombes) {


        for (int i = 0; i < nBombes; i++) {
            numFiles = (int) (Math.random() * cmo.length);  //Genera una fila i columna random i afegeix una bomba
            numColumnes = (int) (Math.random() * cmo.length);
            if (cmo[numFiles][numColumnes] == 'B') --i;
            else cmo[numFiles][numColumnes] = 'B';
        }
    }


    /**
     * Funció que compta les bombes adjacents a cada casella d'un vector, tenint en compte que no se surti d'aquest mateix.
     * Després guarda a cada casella el número de bombes que ha comptat al voltant (' ' si no en te cap).
     *
     * @param cm vector bidimensional el qual es revisa
     * @return
     */
    static void comptarBombes(char[][] cm) {
        int nFiles = cm.length;
        int nColumnes = cm[0].length;
        for (int i = 0; i < nFiles; i++) {
            for (int j = 0; j < nColumnes; j++) {
                int numBombes = 0;
                if (cm[i][j] != ('B')) {
                    if (j != 0 && i != 0 && j != nColumnes - 1 && i != nFiles - 1) {
                        if (cm[i][j - 1] == 'B') {
                            numBombes++;
                        }
                        if (cm[i - 1][j - 1] == 'B') {
                            numBombes++;
                        }
                        if (cm[i + 1][j - 1] == 'B') {
                            numBombes++;
                        }
                        if (cm[i][j + 1] == 'B') {
                            numBombes++;
                        }
                        if (cm[i + 1][j + 1] == 'B') {
                            numBombes++;
                        }
                        if (cm[i - 1][j + 1] == 'B') {
                            numBombes++;
                        }
                        if (cm[i + 1][j] == 'B') {
                            numBombes++;
                        }
                        if (cm[i - 1][j] == 'B') {
                            {
                                numBombes++;
                            }
                        }
                        if (numBombes != 0) {
                            cm[i][j] = (char) ('0' + numBombes);
                        }
                    }
                }
            }
        }
    }

    /**
     * Funció que, a partir d'una posició d'un vector, comprova si hi ha una bomba, una bandera o una casella en blanc.
     * Ademés, comrprova si el jugador ha guanyat.
     *
     * @param nf   enter que determina el número de fila
     * @param nc   enter que determina el número de columna
     */
    public static void trepitjar(int nf, int nc) {
        if (Model.minesVisible[nf - 1][nc - 1] == 'F') {
            System.out.println("Aquesta casella conté una bandera!");
            return;
        }
        if (minesVisible[nf - 1][nc - 1] != '·') {
            System.out.println("Aquesta casella ja està destapada");
            return;
        }
        if (minesOcult[nf - 1][nc - 1] == 'B') {
            System.out.println("------GAME OVER------");
            mostrarSolucio();
            jocFinalitzat();
        } else  {
            recursivitat(nf-1,nc-1); //crida a la funció recursiva
            Vista.mostrarCamp(Model.minesVisible);
        }
        if (jugadorGuanya()) System.out.println("Has guanyat!");


    }


    /**
     * Funció que, a partir d'una posició concreta d'un vector bidimensional, comprova si té bandera  o no (i la posa o
     * la treu), o si ja existeix una bandera. Ademés, comrprova si el jugador ha guanyat.
     *
     * @param nf número enter que determina el número de fila
     * @param nc número enter que determina el número de columna
     */
    public static void posarBandera(int nf, int nc) {
        if (minesVisible[nf - 1][nc - 1] != '·' && minesVisible[nf - 1][nc - 1] != 'F') {
            System.out.println("Ja has trepitjat aquesta casella!");
            return;
        }
        if (minesVisible[nf - 1][nc - 1] == 'F') minesVisible[nf - 1][nc - 1] = ' '; //si hi ha una bandera, la treu
        if (minesVisible[nf - 1][nc - 1] == '·') minesVisible[nf - 1][nc - 1] = 'F'; //si no hi ha bandera, la posa
        Vista.mostrarCamp(minesVisible);
        if (jugadorGuanya()) System.out.println("Has guanyat!");
    }

    /**
     * Funció que comprova que la fila i columna estiguin dins dels paràmetres del vector i, si es així, li dona el valor
     * del camp ocult i després comprova les seves caselles del voltant i les mostra si son espais buits
     * @param fila número enter que denomina la fila
     * @param columna número enter que denomina la columna
     */
    static void recursivitat(int fila, int columna) {
        if (verificacio(fila, columna)) {
            if (minesVisible[fila][columna] == '·')
                minesVisible[fila][columna] = minesOcult[fila][columna];

        if (minesVisible[fila][columna] == ' ') {
            recursivitat(fila - 1, columna - 1);
            recursivitat(fila - 1, columna);
            recursivitat(fila - 1, columna + 1);
            recursivitat(fila, columna - 1);
            recursivitat(fila, columna + 1);
            recursivitat(fila + 1, columna - 1);
            recursivitat(fila + 1, columna);
            recursivitat(fila + 1, columna + 1);

        }
        }
    }


    /**
     * Funció que compara el camp de mines ocult i el visible veure si el jugador ha guanyat o no
     *
     * @return{ boolean que diu si el jugador ha guanyat o no
     */
    static boolean jugadorGuanya() {
        for (int i = 0; i < minesOcult.length; ++i)
            for (int j = 0; j < minesOcult[0].length; ++j) {
                if (minesOcult[i][j] != minesVisible[i][j])
                    if (!(minesOcult[i][j] == 'B' && minesVisible[i][j] == 'F')) return false;
            }
        return true;
    }


    /**
     * Funció que mostra la sol·lució del pescamines, indicant també els erros que ha comès l'usuari
     */
    static void mostrarSolucio() {
        for (int i = 0; i < minesOcult.length; ++i)
            for (int j = 0; j < minesOcult[0].length; ++j) {
                if (minesOcult[i][j] == 'B' && minesVisible[i][j] != 'F') minesVisible[i][j] = 'B';
                if (minesOcult[i][j] != 'B' && minesVisible[i][j] == 'F')
                    minesVisible[i][j] = '*'; // posa un * en una casella on hi hagi una bandera incorrecte
            }

        Vista.mostrarCamp(minesVisible);
    }
}







