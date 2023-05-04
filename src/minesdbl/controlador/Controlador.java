package minesdbl.controlador;

import minesdbl.model.Model;

import java.util.Scanner;

public class Controlador {

    static Scanner scan = new Scanner(System.in);

    /**
     * Menú on es pregunta a l'usuari qué desitja fer (sortir, trepitjar o posar bandera).
     */
    public static void iniciarJoc() {

        Model.initJoc(8, 8, 10);
        System.out.println("¡Que comenci el joc!");
        boolean abandonar = false;
        char opcio;

        while (!Model.jocFinalitzat() && !abandonar) {
            System.out.println("Escull una acció: (T) Trepitjar, (B) posar Bandera, (A) Acabar");
            opcio = scan.next().toUpperCase().charAt(0);
            scan.nextLine();

            if (opcio == 'T') { //Trepitjar
                int fil, col;
                char fila;
                do {
                    System.out.println("Insereix una fila (A-H):");
                    fila = scan.next().toUpperCase().charAt(0);
                    scan.nextLine();
                    fil = fila - 64;  //Conversió de char a int
                    System.out.println("Insereix una columna (1-8):");
                    col = scan.nextInt();
                    scan.nextLine();
                } while (!Model.verificacio(fil, col)); //Verificació de les dades introduïdes per l'usuari
                Model.trepitjar(fil, col);
            } else if (opcio == 'B') { //Posar bandera
                int fil, col;
                char fila;
                do {
                    System.out.println("Insereix una fila (A-H):");
                    fila = scan.next().toUpperCase().charAt(0);
                    scan.nextLine();
                    fil = fila - 64;
                    System.out.println("Insereix una columna (1-8):");
                    col = scan.nextInt();
                    scan.nextLine();
                } while (!Model.verificacio(fil, col));
                Model.posarBandera(fil, col);
            } else if (opcio == 'A') { //Acabar
                System.out.println("Adeu!");
                abandonar = true;
            }
        }
        if (Model.jocFinalitzat()) {
            System.out.println("S'ha acabat el joc!");
        }
    }
}