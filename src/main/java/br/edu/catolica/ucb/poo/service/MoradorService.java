package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.repository.MoradorRepository;
import br.edu.catolica.ucb.poo.repository.SindicoRepository;
import br.edu.catolica.ucb.poo.util.Validacao;

import java.util.List;

public class MoradorService {

    private final MoradorRepository repository = new MoradorRepository();
    private final SindicoRepository sindicoRepository = new SindicoRepository();
    private final UnidadeService unidadeService = new UnidadeService();

    public List<Morador> listar() {
        return repository.listar();
    }

    public Morador buscarPorId(Integer id) {
        Morador morador = repository.buscarPorId(id);
        if (morador == null) {
            throw new IllegalArgumentException("Morador com id " + id + " nao encontrado.");
        }
        return morador;
    }

    public Morador cadastrar(String nome, String cpf, String telefone, boolean proprietario, Integer unidadeId) {
        String nomeOk = Validacao.texto(nome, "nome", 100);
        String cpfOk = Validacao.cpf(cpf);
        String telefoneOk = Validacao.telefoneOpcional(telefone);
        if (repository.buscarPorCpf(cpfOk) != null) {
            throw new IllegalStateException("Ja existe um morador com este CPF.");
        }
        Unidade unidade = unidadeService.buscarPorId(unidadeId);
        return repository.salvar(new Morador(nomeOk, cpfOk, telefoneOk, proprietario, unidade));
    }

    public Morador atualizar(Integer id, String nome, String telefone, boolean proprietario, Integer unidadeId) {
        String nomeOk = Validacao.texto(nome, "nome", 100);
        String telefoneOk = Validacao.telefoneOpcional(telefone);
        Morador morador = buscarPorId(id);
        morador.setNome(nomeOk);
        morador.setTelefone(telefoneOk);
        morador.setProprietario(proprietario);
        morador.setUnidade(unidadeService.buscarPorId(unidadeId));
        return repository.atualizar(morador);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        if (sindicoRepository.contarPorMorador(id) > 0) {
            throw new IllegalStateException("Nao e possivel excluir: o morador possui registro de mandato como sindico.");
        }
        repository.excluir(id);
    }

    public long contarProprietarios() {
        return repository.contarProprietarios();
    }
}
