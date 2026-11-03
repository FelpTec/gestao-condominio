package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.service.UnidadeService;

import java.util.List;

public class UnidadeView {

    private final Console console;
    private final UnidadeService service = new UnidadeService();

    public UnidadeView(Console console) {
        this.console = console;
    }

    public void exibir() {
        int opcao;
        do {
            console.titulo("UNIDADES (APARTAMENTOS)");
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
        List<Unidade> unidades = service.listar();
        if (unidades.isEmpty()) {
            System.out.println("Nenhuma unidade cadastrada.");
            return;
        }
        for (Unidade u : unidades) {
            System.out.printf("%s | moradores: %d | vagas: %d%n", u,
                    service.contarMoradores(u.getId()), service.contarVagas(u.getId()));
        }
    }

    private void cadastrar() {
        String bloco = console.lerTexto("Bloco: ");
        String numero = console.lerTexto("Numero do apartamento: ");
        Unidade unidade = service.cadastrar(bloco, numero);
        console.sucesso("Unidade cadastrada: " + unidade);
    }

    private void atualizar() {
        listar();
        int id = console.lerInteiro("Id da unidade: ");
        Unidade atual = service.buscarPorId(id);
        String bloco = console.lerTextoOuPadrao("Novo bloco (" + atual.getBloco() + "): ", atual.getBloco());
        String numero = console.lerTextoOuPadrao("Novo numero (" + atual.getNumero() + "): ", atual.getNumero());
        Unidade unidade = service.atualizar(id, bloco, numero);
        console.sucesso("Unidade atualizada: " + unidade);
    }

    private void excluir() {
        listar();
        int id = console.lerInteiro("Id da unidade: ");
        service.excluir(id);
        console.sucesso("Unidade excluida.");
    }
}
