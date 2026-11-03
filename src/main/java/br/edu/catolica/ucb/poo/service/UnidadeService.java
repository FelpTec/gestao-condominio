package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.repository.MoradorRepository;
import br.edu.catolica.ucb.poo.repository.UnidadeRepository;
import br.edu.catolica.ucb.poo.repository.VagaRepository;
import br.edu.catolica.ucb.poo.util.Validacao;

import java.util.List;

public class UnidadeService {

    private final UnidadeRepository repository = new UnidadeRepository();
    private final MoradorRepository moradorRepository = new MoradorRepository();
    private final VagaRepository vagaRepository = new VagaRepository();

    public List<Unidade> listar() {
        return repository.listar();
    }

    public Unidade buscarPorId(Integer id) {
        Unidade unidade = repository.buscarPorId(id);
        if (unidade == null) {
            throw new IllegalArgumentException("Unidade com id " + id + " nao encontrada.");
        }
        return unidade;
    }

    public Unidade cadastrar(String bloco, String numero) {
        String blocoOk = Validacao.texto(bloco, "bloco", 10).toUpperCase();
        String numeroOk = Validacao.texto(numero, "numero", 10);
        verificarDuplicidade(null, blocoOk, numeroOk);
        return repository.salvar(new Unidade(blocoOk, numeroOk));
    }

    public Unidade atualizar(Integer id, String bloco, String numero) {
        String blocoOk = Validacao.texto(bloco, "bloco", 10).toUpperCase();
        String numeroOk = Validacao.texto(numero, "numero", 10);
        Unidade unidade = buscarPorId(id);
        verificarDuplicidade(id, blocoOk, numeroOk);
        unidade.setBloco(blocoOk);
        unidade.setNumero(numeroOk);
        return repository.atualizar(unidade);
    }

    public void excluir(Integer id) {
        buscarPorId(id);
        if (moradorRepository.contarPorUnidade(id) > 0) {
            throw new IllegalStateException("Nao e possivel excluir: existem moradores nesta unidade.");
        }
        if (vagaRepository.contarPorUnidade(id) > 0) {
            throw new IllegalStateException("Nao e possivel excluir: existem vagas vinculadas a esta unidade.");
        }
        repository.excluir(id);
    }

    public long contarMoradores(Integer id) {
        return moradorRepository.contarPorUnidade(id);
    }

    public long contarVagas(Integer id) {
        return vagaRepository.contarPorUnidade(id);
    }

    private void verificarDuplicidade(Integer idAtual, String bloco, String numero) {
        Unidade existente = repository.buscarPorBlocoENumero(bloco, numero);
        if (existente != null && !existente.getId().equals(idAtual)) {
            throw new IllegalStateException("Ja existe a unidade Bloco " + bloco + " - Apto " + numero + ".");
        }
    }
}
