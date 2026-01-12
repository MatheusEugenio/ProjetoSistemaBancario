package util;

import exception.InvalidValueException;
import model.Cliente;
import model.Conta;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class Validacoes {
    public static void validacaoDasDatas(String dataTexto) throws InvalidValueException {

        //esse formatador abaixo exige que a data seja válida
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

        if (dataTexto.trim().isEmpty() || dataTexto.trim() == null) {
            throw new InvalidValueException("Erro na data: A data não pode estar vazia.");
        }

        try {
            LocalDate dataLD = LocalDate.parse(dataTexto, formatter);

            Period idadeEmData = Period.between(dataLD, LocalDate.now());

            int idade = idadeEmData.getYears();

            if (idade < 18) {
                throw new InvalidValueException("ERRO, a data \"" + dataTexto + "\" é inválida, pois o usuário tem apenas " + idade + " anos, idade mínima é 18");
            }

        } catch (DateTimeParseException e) {
            throw new InvalidValueException("ERRO, a data \"" + dataTexto + "\" é inválida. Digite novamente no formato dd/MM/uuuu (ex: 12/02/2000):");
        } catch (InvalidValueException e) {
            throw new InvalidValueException(e.getMessage());
        }
    }


    public static boolean validacaoDasStrings(String stringREF, int tamanhoIdeal, String nomeDoCampo) throws InvalidValueException {

        if (stringREF == null || stringREF.length() != tamanhoIdeal || !stringREF.matches("[0-9]+")) {
            
            throw new InvalidValueException("Erro no " + nomeDoCampo + ": Deve ter exatamente " + tamanhoIdeal + " números!");
        }
        return true;
    }

    public static void validacaoDasStrings(String stringREF) throws InvalidValueException {
        try {
            if (stringREF.trim().isEmpty() || stringREF == null) {
                throw new InvalidValueException();
            }
        } catch (InvalidValueException e) {
            throw new InvalidValueException("Erro: algum campo ficou vazio!");
        }
    }

    public static boolean validaTipoDaContaExistente(Cliente clienteReference, String tipoDaConta) {
        for (Conta contaExistente : clienteReference.consultarContasVinculadas()) {
            if (contaExistente.getTipo().equalsIgnoreCase(tipoDaConta)) {
                //se verdadeiro já tem uma conta do tipo
                return true;
            }
        }
        // não tem conta do tipo
        return false;
    }
}
