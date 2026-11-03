package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Cargo;
import br.edu.catolica.ucb.poo.model.Funcionario;
import br.edu.catolica.ucb.poo.service.FuncionarioService;

import java.math.BigDecimal;
import java.util.List;

public class FuncionarioView {

    private final Console console;
    private final FuncionarioService service = new FuncionarioService();

    public FuncionarioView(Console console) {
        this.console = console;
    }

    public void exibir() {
        int opcao;
        do {
            console.titulo("FUNCIONARIOS (TRABALHADORES)");
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

    private void listar() {
        List<Funcionario> funcionarios = service.listar();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionario cadastrado.");
            return;
        }
        funcionarios.forEach(System.out::println);
    }

    private void cadastrar() {
        String nome = console.lerTexto("Nome: ");
        String cpf = console.lerTexto("CPF (11 digitos): ");
        String telefone = console.lerTexto("Telefone (opcional): ");
        listarCargos();
        Cargo cargo = cargoPorNumero(console.lerInteiro("Cargo: "));
        BigDecimal salario = console.lerDecimal("Salario: ");
        Funcionario funcionario = service.cadastrar(nome, cpf, telefone, cargo, salario);
        console.sucesso("Funcionario cadastrado: " + funcionario);
    }

    private void atualizar() {
        listar();
        int id = console.lerInteiro("Id do funcionario: ");
        Funcionario atual = service.buscarPorId(id);
        String nome = console.lerTextoOuPadrao("Novo nome (" + atual.getNome() + "): ", atual.getNome());
        String telefoneAtual = atual.getTelefone() == null ? "" : atual.getTelefone();
        String telefone = console.lerTextoOuPadrao("Novo telefone (" + telefoneAtual + "): ", telefoneAtual);
        listarCargos();
        int numeroAtual = atual.getCargo().ordinal() + 1;
        Cargo cargo = cargoPorNumero(console.lerInteiroOuPadrao("Cargo (" + numeroAtual + "): ", numeroAtual));
        BigDecimal salario = console.lerDecimalOuPadrao("Novo salario (" + atual.getSalario() + "): ", atual.getSalario());
        Funcionario funcionario = service.atualizar(id, nome, telefone, cargo, salario);
        console.sucesso("Funcionario atualizado: " + funcionario);
    }

    private void excluir() {
        listar();
        int id = console.lerInteiro("Id do funcionario: ");
        service.excluir(id);
        console.sucesso("Funcionario excluido.");
    }

    private void listarCargos() {
        System.out.println("Cargos:");
        for (Cargo c : Cargo.values()) {
            System.out.println("  " + (c.ordinal() + 1) + " - " + c);
        }
    }

    private Cargo cargoPorNumero(int numero) {
        Cargo[] cargos = Cargo.values();
        if (numero < 1 || numero > cargos.length) {
            throw new IllegalArgumentException("Cargo invalido.");
        }
        return cargos[numero - 1];
    }
}
