package br.edu.catolica.ucb.poo.model;

public enum Cargo {
    PORTEIRO("Porteiro"),
    ZELADOR("Zelador"),
    FAXINEIRO("Faxineiro(a)"),
    JARDINEIRO("Jardineiro"),
    SEGURANCA("Seguranca"),
    MANUTENCAO("Manutencao");

    private final String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
