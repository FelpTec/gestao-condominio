package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Sindico;
import br.edu.catolica.ucb.poo.repository.SindicoRepository;

import java.time.LocalDate;
import java.util.List;

public class SindicoService {

    private static final int MAXIMO_MESES = 60;

    private final SindicoRepository repository = new SindicoRepository();
    private final MoradorService moradorService = new MoradorService();

    /** Retorna o sindico ativo ou null. Mandatos vencidos sao encerrados automaticamente. */
    public Sindico atual() {
        Sindico ativo = repository.buscarAtivo();
        if (ativo != null && ativo.getFimMandato().isBefore(LocalDate.now())) {
            ativo.encerrar(LocalDate.now());
            repository.atualizar(ativo);
            return null;
        }
        return ativo;
    }

    public List<Sindico> historico() {
        return repository.listar();
    }

    public Sindico eleger(Integer moradorId, int meses) {
        if (meses <= 0 || meses > MAXIMO_MESES) {
            throw new IllegalArgumentException("A duracao do mandato deve ser de 1 a " + MAXIMO_MESES + " meses.");
        }
        Morador morador = moradorService.buscarPorId(moradorId);
        Sindico atual = atual();
        if (atual != null) {
            if (atual.getMorador().getId().equals(moradorId)) {
                throw new IllegalStateException("Este morador ja e o sindico atual.");
            }
            atual.encerrar(LocalDate.now());
            repository.atualizar(atual);
        }
        LocalDate inicio = LocalDate.now();
        return repository.salvar(new Sindico(morador, inicio, inicio.plusMonths(meses)));
    }

    public Sindico encerrarMandato() {
        Sindico atual = atual();
        if (atual == null) {
            throw new IllegalStateException("Nao ha sindico ativo no momento.");
        }
        atual.encerrar(LocalDate.now());
        return repository.atualizar(atual);
    }
}
