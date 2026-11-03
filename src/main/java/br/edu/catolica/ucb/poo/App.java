package br.edu.catolica.ucb.poo;

import br.edu.catolica.ucb.poo.util.JPAUtil;
import br.edu.catolica.ucb.poo.view.MenuPrincipal;

public class App {

    public static void main(String[] args) {
        try {
            System.out.println("Conectando ao banco: " + JPAUtil.bancoSelecionado().toUpperCase());
            JPAUtil.getEntityManagerFactory();
            new MenuPrincipal().exibir();
        } catch (RuntimeException e) {
            System.out.println("ERRO: nao foi possivel iniciar o sistema. " + e.getMessage());
        } finally {
            JPAUtil.fechar();
        }
    }
}
