package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.model.Vaga;
import br.edu.catolica.ucb.poo.repository.VagaRepository;

import java.util.ArrayList;
import java.util.List;

public class VagaService {

    public static final int MAX_VAGAS_POR_UNIDADE = 2;
    private static final int MAX_LOTE = 200;

    public record ResumoGaragem(long total, long ocupadas) {
        public long livres() {
            return total - ocupadas;
        }

        public double percentualOcupacao() {
            return total == 0 ? 0.0 : ocupadas * 100.0 / total;
        }
    }

    private final VagaRepository repository = new VagaRepository();
    private final UnidadeService unidadeService = new UnidadeService();

    public List<Vaga> listar() {
        return repository.listar();
    }

    public Vaga buscarPorId(Integer id) {
        Vaga vaga = repository.buscarPorId(id);
        if (vaga == null) {
            throw new IllegalArgumentException("Vaga com id " + id + " nao encontrada.");
        }
        return vaga;
    }

    public Vaga cadastrar(int numero) {
        if (numero <= 0) {
            throw new IllegalArgumentException("O numero da vaga deve ser maior que zero.");
        }
        if (repository.buscarPorNumero(numero) != null) {
            throw new IllegalStateException("Ja existe a vaga numero " + numero + ".");
        }
        return repository.salvar(new Vaga(numero));
    }

    public List<Vaga> cadastrarLote(int quantidade) {
        if (quantidade <= 0 || quantidade > MAX_LOTE) {
            throw new IllegalArgumentException("A quantidade deve ser de 1 a " + MAX_LOTE + ".");
        }
        int proximo = repository.maiorNumero() + 1;
        List<Vaga> criadas = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            criadas.add(repository.salvar(new Vaga(proximo + i)));
        }
        return criadas;
    }

    public Vaga atribuir(Integer vagaId, Integer unidadeId) {
        Vaga vaga = buscarPorId(vagaId);
        if (vaga.estaOcupada()) {
            throw new IllegalStateException("A vaga " + vaga.getNumero() + " ja esta ocupada por "
                    + vaga.getUnidade().getIdentificacao() + ".");
        }
        Unidade unidade = unidadeService.buscarPorId(unidadeId);
        if (repository.contarPorUnidade(unidadeId) >= MAX_VAGAS_POR_UNIDADE) {
            throw new IllegalStateException("A unidade ja possui o limite de " + MAX_VAGAS_POR_UNIDADE + " vagas.");
        }
        vaga.setUnidade(unidade);
        return repository.atualizar(vaga);
    }

    public Vaga liberar(Integer vagaId) {
        Vaga vaga = buscarPorId(vagaId);
        if (!vaga.estaOcupada()) {
            throw new IllegalStateException("A vaga " + vaga.getNumero() + " ja esta livre.");
        }
        vaga.setUnidade(null);
        return repository.atualizar(vaga);
    }

    public void excluir(Integer vagaId) {
        Vaga vaga = buscarPorId(vagaId);
        if (vaga.estaOcupada()) {
            throw new IllegalStateException("Nao e possivel excluir: a vaga esta ocupada. Libere-a antes.");
        }
        repository.excluir(vagaId);
    }

    public ResumoGaragem resumo() {
        return new ResumoGaragem(repository.contarTotal(), repository.contarOcupadas());
    }
}
