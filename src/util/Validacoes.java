package util;

import exception.InvalidValueException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Validacoes {

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

    public static String validacaoDasDatas(Scanner sc){

        //esse formatador abaixo exige que a data seja válida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        String dataStr = "";
        boolean valido = false;

        while(!valido){
           try {
            dataStr = sc.nextLine().trim();

            if (dataStr.isEmpty()){continue;}

            LocalDate dataLD = LocalDate.parse(dataStr, formatter);
            Period idadeEmData = Period.between(dataLD, LocalDate.now());
            int idade = idadeEmData.getYears();
            if (idade < 18){throw new InvalidValueException("ERRO, a data \""+dataStr+"\" é inválida, pois o usuário tem apenas "+idade+" anos, idade mínima é 18");}

            valido = true;
           }catch (DateTimeParseException e){
               System.out.println("ERRO, a data \"" + dataStr + "\" é inválida. Digite novamente no formato dd/MM/uuuu (ex: 12/02/2000):");
           }catch (InvalidValueException e){
               System.out.println(e.getMessage());
           }
        }
        return dataStr;
    }

    public static String validacaoDosInteiros(Scanner sc, int tamanhoIdeal){
        boolean valido = false;
        String valor = "";

        while(!valido){
            try {
                valor = sc.nextLine();

                if (valor.length() != tamanhoIdeal || !valor.matches("[0-9]+")){
                    throw new InvalidValueException();
                }else{
                    valido = true;
                }
            }catch (InvalidValueException e) {
                System.out.println("ERRO, QUANTIDADE DE CARACTERES INVÁLIDA, DIGITE \"" + tamanhoIdeal + "\" NÚMEROS! ");
            }
        }
        return valor;
    }

}
