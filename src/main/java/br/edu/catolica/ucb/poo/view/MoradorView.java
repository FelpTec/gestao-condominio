package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.service.MoradorService;

import java.util.List;

public class MoradorView {

    private final Console console;
    private final UnidadeView unidadeView;
    private final MoradorService service = new MoradorService();

    public MoradorView(Console console, UnidadeView unidadeView) {
        this.console = console;
        this.unidadeView = unidadeView;
    }

    public void exibir() {
        int opcao;
        do {
            console.titulo("MORADORES");
            System.out.println("1 - Listar");
            System.out.println("2 - Cadastrar");
            System.out.println("3 - Atualizar");
            System.out.println("4 - Excluir");
            System.out.println("0 - Voltar");
            opcao = console.lerInteiro("Opcao: ");
            executar(opcao);
        } while (opcao != 0);
    }

    private void executar(int opcao) {
        try {
            switch (opcao) {
                case 1 -> listar();
                case 2 -> cadastrar();
                case 3 -> atualizar();
                case 4 -> excluir();
                case 0 -> { }
                default -> console.erro("Opcao invalida.");
            }
        } catch (IllegalArgumentException | IllegalStateException e) {
            console.erro(e.getMessage());
        } catch (RuntimeException e) {
            console.erro("Falha ao acessar o banco de dados: " + e.getMessage());
        }
    }

    public void listar() {
        List<Morador> moradores = service.listar();
        if (moradores.isEmpty()) {
            System.out.println("Nenhum morador cadastrado.");
            return;
        }
        moradores.forEach(System.out::println);
    }

    private void cadastrar() {
        String nome = console.lerTexto("Nome: ");
        String cpf = console.lerTexto("CPF (11 digitos): ");
        String telefone = console.lerTexto("Telefone (opcional): ");
        boolean proprietario = console.lerSimNao("Proprietario? (S = proprietario, N = inquilino): ", null);
        System.out.println("Unidades disponiveis:");
        unidadeView.listar();
        int unidadeId = console.lerInteiro("Id da unidade: ");
        Morador morador = service.cadastrar(nome, cpf, telefone, proprietario, unidadeId);
        console.sucesso("Morador cadastrado: " + morador);
    }

    private void atualizar() {
        listar();
        int id = console.lerInteiro("Id do morador: ");
        Morador atual = service.buscarPorId(id);
        String nome = console.lerTextoOuPadrao("Novo nome (" + atual.getNome() + "): ", atual.getNome());
        String telefoneAtual = atual.getTelefone() == null ? "" : atual.getTelefone();
        String telefone = console.lerTextoOuPadrao("Novo telefone (" + telefoneAtual + "): ", telefoneAtual);
        boolean proprietario = console.lerSimNao("Proprietario? S/N (" + (atual.isProprietario() ? "S" : "N") + "): ",
                atual.isProprietario());
        System.out.println("Unidades disponiveis:");
        unidadeView.listar();
        int unidadeId = console.lerInteiroOuPadrao("Id da unidade (" + atual.getUnidade().getId() + "): ",
                atual.getUnidade().getId());
        Morador morador = service.atualizar(id, nome, telefone, proprietario, unidadeId);
        console.sucesso("Morador atualizado: " + morador);
    }

    private void excluir() {
        listar();
        int id = console.lerInteiro("Id do morador: ");
        service.excluir(id);
        console.sucesso("Morador excluido.");
    }
}
