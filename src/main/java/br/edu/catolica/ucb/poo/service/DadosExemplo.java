package br.edu.catolica.ucb.poo.service;

import br.edu.catolica.ucb.poo.model.Cargo;
import br.edu.catolica.ucb.poo.model.Morador;
import br.edu.catolica.ucb.poo.model.Unidade;
import br.edu.catolica.ucb.poo.model.Vaga;

import java.math.BigDecimal;
import java.util.List;

/** Popula o banco com dados ficticios para demonstrar o sistema. */
public class DadosExemplo {

    private final UnidadeService unidades = new UnidadeService();
    private final MoradorService moradores = new MoradorService();
    private final FuncionarioService funcionarios = new FuncionarioService();
    private final VagaService vagas = new VagaService();
    private final SindicoService sindicos = new SindicoService();

    /** Retorna false (sem alterar nada) se o condominio ja possui unidades cadastradas. */
    public boolean carregar() {
        if (!unidades.listar().isEmpty()) {
            return false;
        }

        Unidade a101 = unidades.cadastrar("A", "101");
        Unidade a102 = unidades.cadastrar("A", "102");
        Unidade b201 = unidades.cadastrar("B", "201");
        Unidade b202 = unidades.cadastrar("B", "202");

        Morador maria = moradores.cadastrar("Maria Souza", "111.222.333-44", "(61) 99999-0001", true, a101.getId());
        moradores.cadastrar("Joao Souza", "222.333.444-55", "(61) 99999-0002", true, a101.getId());
        moradores.cadastrar("Ana Pereira", "333.444.555-66", "(61) 99999-0003", false, a102.getId());
        moradores.cadastrar("Carlos Mendes", "444.555.666-77", "(61) 99999-0004", true, b201.getId());
        moradores.cadastrar("Beatriz Lima", "555.666.777-88", "(61) 99999-0005", false, b202.getId());

        funcionarios.cadastrar("Roberto Alves", "666.777.888-99", "(61) 98888-0001", Cargo.PORTEIRO, new BigDecimal("2100.00"));
        funcionarios.cadastrar("Luciana Costa", "777.888.999-00", "(61) 98888-0002", Cargo.ZELADOR, new BigDecimal("2400.00"));
        funcionarios.cadastrar("Marcos Dias", "888.999.000-11", "(61) 98888-0003", Cargo.FAXINEIRO, new BigDecimal("1800.00"));
        funcionarios.cadastrar("Paulo Nunes", "999.000.111-22", "(61) 98888-0004", Cargo.SEGURANCA, new BigDecimal("2300.00"));

        List<Vaga> criadas = vagas.cadastrarLote(6);
        vagas.atribuir(criadas.get(0).getId(), a101.getId());
        vagas.atribuir(criadas.get(1).getId(), a101.getId());
        vagas.atribuir(criadas.get(2).getId(), a102.getId());
        vagas.atribuir(criadas.get(3).getId(), b201.getId());

        sindicos.eleger(maria.getId(), 24);
        return true;
    }
}
