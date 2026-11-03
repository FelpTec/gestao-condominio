package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.service.DadosExemplo;

public class MenuPrincipal {

    private final Console console = new Console();
    private final UnidadeView unidadeView = new UnidadeView(console);
    private final MoradorView moradorView = new MoradorView(console, unidadeView);
    private final SindicoView sindicoView = new SindicoView(console, moradorView);
    private final FuncionarioView funcionarioView = new FuncionarioView(console);
    private final GaragemView garagemView = new GaragemView(console, unidadeView);
    private final ResumoView resumoView = new ResumoView(console);

    public void exibir() {
        int opcao;
        do {
            console.titulo("GESTAO DE CONDOMINIO - MENU PRINCIPAL");
            System.out.println("1 - Unidades (apartamentos)");
            System.out.println("2 - Moradores");
            System.out.println("3 - Sindico");
            System.out.println("4 - Funcionarios");
            System.out.println("5 - Garagem");
            System.out.println("6 - Resumo do condominio");
            System.out.println("7 - Carregar dados de exemplo");
            System.out.println("0 - Sair");
            opcao = console.lerInteiro("Opcao: ");
            executar(opcao);
        } while (opcao != 0);
    }

    private void executar(int opcao) {
        try {
            switch (opcao) {
                case 1 -> unidadeView.exibir();
                case 2 -> moradorView.exibir();
                case 3 -> sindicoView.exibir();
                case 4 -> funcionarioView.exibir();
                case 5 -> garagemView.exibir();
                case 6 -> resumoView.exibir();
                case 7 -> carregarExemplo();
                case 0 -> System.out.println("Ate logo!");
                default -> console.erro("Opcao invalida.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            console.erro(e.getMessage());
        } catch (RuntimeException e) {
            console.erro("Falha ao acessar o banco de dados: " + e.getMessage());
        }
    }

    private void carregarExemplo() {
        if (new DadosExemplo().carregar()) {
            console.sucesso("Dados de exemplo carregados. Veja a opcao 6 (Resumo do condominio).");
        } else {
            console.erro("O condominio ja possui unidades cadastradas; dados de exemplo nao foram carregados.");
        }
    }
}
