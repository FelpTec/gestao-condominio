package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Sindico;
import br.edu.catolica.ucb.poo.service.SindicoService;

import java.util.List;

public class SindicoView {

    private final Console console;
    private final MoradorView moradorView;
    private final SindicoService service = new SindicoService();

    public SindicoView(Console console, MoradorView moradorView) {
        this.console = console;
        this.moradorView = moradorView;
    }

    public void exibir() {
        int opcao;
        do {
            console.titulo("SINDICO");
            System.out.println("1 - Ver sindico atual");
            System.out.println("2 - Eleger novo sindico");
            System.out.println("3 - Encerrar mandato atual");
            System.out.println("4 - Historico de mandatos");
            System.out.println("0 - Voltar");
            opcao = console.lerInteiro("Opcao: ");
            executar(opcao);
        } while (opcao != 0);
    }

    private void executar(int opcao) {
        try {
            switch (opcao) {
                case 1 -> verAtual();
                case 2 -> eleger();
                case 3 -> encerrar();
                case 4 -> historico();
                case 0 -> { }
                default -> console.erro("Opcao invalida.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            console.erro(e.getMessage());
        } catch (RuntimeException e) {
            console.erro("Falha ao acessar o banco de dados: " + e.getMessage());
        }
    }

    private void verAtual() {
        Sindico atual = service.atual();
        if (atual == null) {
            System.out.println("Nao ha sindico ativo no momento.");
            return;
        }
        System.out.println("Sindico atual: " + atual);
    }

    private void eleger() {
        System.out.println("Moradores disponiveis:");
        moradorView.listar();
        int moradorId = console.lerInteiro("Id do morador eleito: ");
        int meses = console.lerInteiro("Duracao do mandato em meses (ex: 24): ");
        Sindico sindico = service.eleger(moradorId, meses);
        console.sucesso("Novo sindico eleito: " + sindico);
    }

    private void encerrar() {
        Sindico encerrado = service.encerrarMandato();
        console.sucesso("Mandato encerrado: " + encerrado);
    }

    private void historico() {
        List<Sindico> historico = service.historico();
        if (historico.isEmpty()) {
            System.out.println("Nenhum mandato registrado.");
            return;
        }
        historico.forEach(System.out::println);
    }
}
