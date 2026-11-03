package br.edu.catolica.ucb.poo.view;

import java.math.BigDecimal;
import java.util.Scanner;

public class Console {

    private final Scanner scanner = new Scanner(System.in);

    public String lerTexto(String rotulo) {
        System.out.print(rotulo);
        if (!scanner.hasNextLine()) {
            return "0";
        }
        return scanner.nextLine().trim();
    }

    /** Se o usuario so apertar ENTER, devolve o valor padrao (util em atualizacoes). */
    public String lerTextoOuPadrao(String rotulo, String padrao) {
        String valor = lerTexto(rotulo);
        return valor.isEmpty() ? padrao : valor;
    }

    public int lerInteiro(String rotulo) {
        while (true) {
            String valor = lerTexto(rotulo);
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero inteiro.");
            }
        }
    }

    public int lerInteiroOuPadrao(String rotulo, int padrao) {
        while (true) {
            String valor = lerTexto(rotulo);
            if (valor.isEmpty()) {
                return padrao;
            }
            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Digite um numero inteiro.");
            }
        }
    }

    public BigDecimal lerDecimal(String rotulo) {
        while (true) {
            String valor = lerTexto(rotulo).replace(",", ".");
            try {
                return new BigDecimal(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Exemplo: 2100.50");
            }
        }
    }

    public BigDecimal lerDecimalOuPadrao(String rotulo, BigDecimal padrao) {
        while (true) {
            String valor = lerTexto(rotulo).replace(",", ".");
            if (valor.isEmpty()) {
                return padrao;
            }
            try {
                return new BigDecimal(valor);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido. Exemplo: 2100.50");
            }
        }
    }

    /** Le S/N. Se padrao for diferente de null, ENTER devolve o padrao. */
    public boolean lerSimNao(String rotulo, Boolean padrao) {
        while (true) {
            String valor = lerTexto(rotulo).toLowerCase();
            if (valor.isEmpty() && padrao != null) {
                return padrao;
            }
            switch (valor) {
                case "s", "sim" -> {
                    return true;
                }
                case "n", "nao", "0" -> {
                    return false;
                }
                default -> System.out.println("Responda com S ou N.");
            }
        }
    }

    public void titulo(String texto) {
        System.out.println();
        System.out.println("=== " + texto + " ===");
    }

    public void sucesso(String mensagem) {
        System.out.println("OK: " + mensagem);
    }

    public void erro(String mensagem) {
        System.out.println("ERRO: " + mensagem);
    }
}
