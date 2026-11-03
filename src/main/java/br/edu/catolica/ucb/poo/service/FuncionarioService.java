package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Cargo;
import br.edu.catolica.ucb.poo.model.Funcionario;
import br.edu.catolica.ucb.poo.repository.FuncionarioRepository;
import br.edu.catolica.ucb.poo.util.Validacao;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FuncionarioService {

    private final FuncionarioRepository repository = new FuncionarioRepository();

    public List<Funcionario> listar() {
        return repository.listar();
    }

    public Funcionario buscarPorId(Integer id) {
        Funcionario funcionario = repository.buscarPorId(id);
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionario com id " + id + " nao encontrado.");
        }
        return funcionario;
    }

    public Funcionario cadastrar(String nome, String cpf, String telefone, Cargo cargo, BigDecimal salario) {
        String nomeOk = Validacao.texto(nome, "nome", 100);
        String cpfOk = Validacao.cpf(cpf);
        String telefoneOk = Validacao.telefoneOpcional(telefone);
        if (cargo == null) {
            throw new IllegalArgumentException("O cargo e obrigatorio.");
        }
        Validacao.valorPositivo(salario, "salario");
        if (repository.buscarPorCpf(cpfOk) != null) {
            throw new IllegalStateException("Ja existe um funcionario com este CPF.");
        }
        return repository.salvar(new Funcionario(nomeOk, cpfOk, telefoneOk, cargo, salario));
    }

    public Funcionario atualizar(Integer id, String nome, String telefone, Cargo cargo, BigDecimal salario) {
        String nomeOk = Validacao.texto(nome, "nome", 100);
        String telefoneOk = Validacao.telefoneOpcional(telefone);
        if (cargo == null) {
            throw new IllegalArgumentException("O cargo e obrigatorio.");
        }
        Validacao.valorPositivo(salario, "salario");
        Funcionario funcionario = buscarPorId(id);
        funcionario.setNome(nomeOk);
        funcionario.setTelefone(telefoneOk);
        funcionario.setCargo(cargo);
        funcionario.setSalario(salario);
        return repository.atualizar(funcionario);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        repository.excluir(id);
    }

    public BigDecimal totalFolha() {
        return listar().stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<Cargo, Long> contarPorCargo() {
        Map<Cargo, Long> contagem = new EnumMap<>(Cargo.class);
        for (Funcionario f : listar()) {
            contagem.merge(f.getCargo(), 1L, Long::sum);
        }
        return contagem;
    }
}
