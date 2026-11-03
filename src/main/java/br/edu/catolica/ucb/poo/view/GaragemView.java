package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.model.Vaga;
import br.edu.catolica.ucb.poo.service.UnidadeService;
import br.edu.catolica.ucb.poo.service.VagaService;
import br.edu.catolica.ucb.poo.service.VagaService.ResumoGaragem;

import java.util.List;

public class GaragemView {

    private final Console console;
    private final UnidadeView unidadeView;
    private final VagaService service = new VagaService();
    private final UnidadeService unidadeService = new UnidadeService();

    public GaragemView(Console console, UnidadeView unidadeView) {
        this.console = console;
        this.unidadeView = unidadeView;
    }

    public void exibir() {
        int opcao;
        do {
            console.titulo("GARAGEM");
            System.out.println("1 - Contagem de vagas (total / ocupadas / livres)");
            System.out.println("2 - Listar vagas");
            System.out.println("3 - Cadastrar vaga");
            System.out.println("4 - Cadastrar vagas em lote");
            System.out.println("5 - Atribuir vaga a uma unidade");
            System.out.println("6 - Liberar vaga");
            System.out.println("7 - Excluir vaga");
            System.out.println("0 - Voltar");
            opcao = console.lerInteiro("Opcao: ");
            executar(opcao);
        } while (opcao != 0);
    }

    private void executar(int opcao) {
        try {
            switch (opcao) {
                case 1 -> contagem();
                case 2 -> listar();
                case 3 -> cadastrar();
                case 4 -> cadastrarLote();
                case 5 -> atribuir();
                case 6 -> liberar();
                case 7 -> excluir();
                case 0 -> { }
                default -> console.erro("Opcao invalida.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            console.erro(e.getMessage());
        } catch (RuntimeException e) {
            console.erro("Falha ao acessar o banco de dados: " + e.getMessage());
        }
    }

    private void contagem() {
        ResumoGaragem resumo = service.resumo();
        System.out.println("Total de vagas : " + resumo.total());
        System.out.println("Ocupadas       : " + resumo.ocupadas());
        System.out.println("Livres         : " + resumo.livres());
        System.out.printf("Ocupacao       : %.1f%%%n", resumo.percentualOcupacao());
        System.out.println("Vagas por unidade (limite de " + VagaService.MAX_VAGAS_POR_UNIDADE + " por unidade):");
        List<Unidade> unidades = unidadeService.listar();
        if (unidades.isEmpty()) {
            System.out.println("  Nenhuma unidade cadastrada.");
            return;
        }
        for (Unidade u : unidades) {
            System.out.printf("  %s -> %d vaga(s)%n", u.getIdentificacao(), unidadeService.contarVagas(u.getId()));
        }
    }

    private void listar() {
        List<Vaga> vagas = service.listar();
        if (vagas.isEmpty()) {
            System.out.println("Nenhuma vaga cadastrada.");
            return;
        }
        vagas.forEach(System.out::println);
    }

    private void cadastrar() {
        int numero = console.lerInteiro("Numero da vaga: ");
        Vaga vaga = service.cadastrar(numero);
        console.sucesso("Vaga cadastrada: " + vaga);
    }

    private void cadastrarLote() {
        int quantidade = console.lerInteiro("Quantidade de vagas a criar: ");
        List<Vaga> criadas = service.cadastrarLote(quantidade);
        console.sucesso(criadas.size() + " vaga(s) criada(s): numeros "
                + criadas.get(0).getNumero() + " a " + criadas.get(criadas.size() - 1).getNumero() + ".");
    }

    private void atribuir() {
        listar();
        int vagaId = console.lerInteiro("Id da vaga: ");
        System.out.println("Unidades disponiveis:");
        unidadeView.listar();
        int unidadeId = console.lerInteiro("Id da unidade: ");
        Vaga vaga = service.atribuir(vagaId, unidadeId);
        console.sucesso("Vaga atribuida: " + vaga);
    }

    private void liberar() {
        listar();
        int vagaId = console.lerInteiro("Id da vaga: ");
        Vaga vaga = service.liberar(vagaId);
        console.sucesso("Vaga liberada: " + vaga);
    }

    private void excluir() {
        listar();
        int vagaId = console.lerInteiro("Id da vaga: ");
        service.excluir(vagaId);
        console.sucesso("Vaga excluida.");
    }
}
