package util;
import exception.InvalidValueException;

import java.util.Scanner;

public class Tratamento {

    public static String validacaoDasStringsPorPadrao(Scanner sc, int tamanhoIdeal, String padrao, String padrao2){
        boolean valido = false;
        String stringAlvo = null;

        while(!valido){
            try{
                stringAlvo = sc.nextLine();

                if (stringAlvo.length() != tamanhoIdeal || (!stringAlvo.equalsIgnoreCase(padrao) && !stringAlvo.equalsIgnoreCase(padrao2))){
                    throw new InvalidValueException();
                }else{
                    valido = true;
                }
            }catch (InvalidValueException e){
                System.out.println("ERRO, VALOR DIGITADO É INVÁLIDO, DIGITE NOVAMENTE COM BASE NAS OPÇÕES!");
            }
        }
        return stringAlvo;
    }

//    public static String validacaoDasDatasEmString(Scanner sc){
//        boolean valido = false;
//        String data;
//
//        while(!valido){
//            data = sc.nextLine();
//            sc.nextLine();
//
//            //metodo incompleto falta terminar ainda!
//        }
//    }

    public static long validacaoDosInteiros(Scanner sc, int tamanhoIdeal){
        boolean valido = false;
        long valorInt = 0;

        while(!valido){
            try {
                valorInt = sc.nextLong();
                sc.nextLine();

                String valorString = String.valueOf(valorInt);

                if (valorString.length() != tamanhoIdeal){
                    throw new InvalidValueException();
                }else{
                    valido = true;
                }
            }catch (InvalidValueException e) {
                System.out.println("ERRO, QUANTIDADE DE CARACTERES INVÁLIDA, DIGITE \"" + tamanhoIdeal + "\" NÚMEROS! ");
            }
        }
        return valorInt;
    }
}
