package br.edu.catolica.ucb.poo.repository;

import br.edu.catolica.ucb.poo.model.Unidade;

public class UnidadeRepository extends RepositorioBase<Unidade> {

    public UnidadeRepository() {
        super(Unidade.class);
    }

    public Unidade buscarPorBlocoENumero(String bloco, String numero) {
        return consultarUm("SELECT u FROM Unidade u WHERE u.bloco = ?1 AND u.numero = ?2",
                Unidade.class, bloco, numero);
    }
}
