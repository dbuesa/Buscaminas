package minesdbl.vista;

public class Vista {

    public static void mostrarCamp(char[][] camp){

        System.out.print("  ");
        for (int i = 0; i < camp[0].length; i++) {
            System.out.print(i+1 + " ");
        }
        System.out.println();
        for (int i = 0; i < camp.length; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < camp[i].length; j++) {
                if (camp[i][j] == -1) {
                    System.out.print("* ");
                } else {
                    System.out.print(camp[i][j] + " ");
                }
            }
            System.out.println();
        }
    }




    public static void mostrarMissatge(String missatge) {

        System.out.println(missatge);
    }


}



