package br.edu.catolica.ucb.poo.view;

import br.edu.catolica.ucb.poo.model.Cargo;
import br.edu.catolica.ucb.poo.model.Sindico;
import br.edu.catolica.ucb.poo.service.FuncionarioService;
import br.edu.catolica.ucb.poo.service.MoradorService;
import br.edu.catolica.ucb.poo.service.SindicoService;
import br.edu.catolica.ucb.poo.service.UnidadeService;
import br.edu.catolica.ucb.poo.service.VagaService;
import br.edu.catolica.ucb.poo.service.VagaService.ResumoGaragem;

import java.math.BigDecimal;
import java.util.Map;

public class ResumoView {

    private final Console console;
    private final UnidadeService unidadeService = new UnidadeService();
    private final MoradorService moradorService = new MoradorService();
    private final FuncionarioService funcionarioService = new FuncionarioService();
    private final SindicoService sindicoService = new SindicoService();
    private final VagaService vagaService = new VagaService();

    public ResumoView(Console console) {
        this.console = console;
    }

    public void exibir() {
        console.titulo("RESUMO DO CONDOMINIO");

        System.out.println("Unidades     : " + unidadeService.listar().size());

        int totalMoradores = moradorService.listar().size();
        long proprietarios = moradorService.contarProprietarios();
        System.out.printf("Moradores    : %d (proprietarios: %d | inquilinos: %d)%n",
                totalMoradores, proprietarios, totalMoradores - proprietarios);

        Sindico sindico = sindicoService.atual();
        System.out.println("Sindico atual: " + (sindico == null ? "nenhum" : sindico));

        Map<Cargo, Long> porCargo = funcionarioService.contarPorCargo();
        BigDecimal folha = funcionarioService.totalFolha();
        System.out.printf("Funcionarios : %d | folha mensal: R$ %.2f%n",
                porCargo.values().stream().mapToLong(Long::longValue).sum(), folha);
        porCargo.forEach((cargo, qtd) -> System.out.println("   - " + cargo + ": " + qtd));

        ResumoGaragem garagem = vagaService.resumo();
        System.out.printf("Garagem      : %d vagas | %d ocupadas | %d livres (%.1f%% de ocupacao)%n",
                garagem.total(), garagem.ocupadas(), garagem.livres(), garagem.percentualOcupacao());
    }
}
