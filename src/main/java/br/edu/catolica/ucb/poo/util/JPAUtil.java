package br.edu.catolica.ucb.poo.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class JPAUtil {

    private static final String BANCO_PADRAO = "h2";
    private static final Logger LOGGER_HIBERNATE = Logger.getLogger("org.hibernate");

    private static EntityManagerFactory factory;

    private JPAUtil() {
    }

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (factory == null) {
            LOGGER_HIBERNATE.setLevel(Level.SEVERE);
            factory = Persistence.createEntityManagerFactory(nomeDaUnidade());
        }
        return factory;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    public static String bancoSelecionado() {
        String valor = System.getProperty("banco");
        if (valor == null || valor.isBlank()) {
            valor = System.getenv("BANCO");
        }
        if (valor == null || valor.isBlank()) {
            valor = BANCO_PADRAO;
        }
        return valor.trim().toLowerCase();
    }

    public static synchronized void fechar() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    private static String nomeDaUnidade() {
        String banco = bancoSelecionado();
        if (!banco.equals("h2") && !banco.equals("mysql")) {
            throw new IllegalStateException("Banco invalido: " + banco + ". Use 'h2' ou 'mysql'.");
        }
        return "condominio-" + banco;
    }
}
