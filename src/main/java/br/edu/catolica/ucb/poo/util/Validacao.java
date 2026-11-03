package br.edu.catolica.ucb.poo.util;

import java.math.BigDecimal;

public final class Validacao {

    private Validacao() {
    }

    public static String texto(String valor, String rotulo, int maximo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("O campo " + rotulo + " e obrigatorio.");
        }
        String limpo = valor.trim();
        if (limpo.length() > maximo) {
            throw new IllegalArgumentException("O campo " + rotulo + " deve ter no maximo " + maximo + " caracteres.");
        }
        return limpo;
    }

    public static String telefoneOpcional(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return texto(valor, "telefone", 20);
    }

    public static String cpf(String valor) {
        String digitos = valor == null ? "" : valor.replaceAll("\\D", "");
        if (digitos.length() != 11) {
            throw new IllegalArgumentException("CPF invalido: informe 11 digitos.");
        }
        return digitos;
    }

    public static BigDecimal valorPositivo(BigDecimal valor, String rotulo) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O campo " + rotulo + " deve ser maior que zero.");
        }
        return valor;
    }
}
