import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Programa unico demonstrando ArrayList, HashSet e HashMap
 * aplicados ao dominio de gestao de condominio.
 */
public class Colecoes {

    static class Morador {
        private final String nome;
        private final String cpf;
        private final String unidade;

        Morador(String nome, String cpf, String unidade) {
            this.nome = nome;
            this.cpf = cpf;
            this.unidade = unidade;
        }

        String getNome() { return nome; }
        String getCpf() { return cpf; }
        String getUnidade() { return unidade; }

        // CPF identifica o morador: dois moradores com mesmo CPF sao o mesmo.
        // Necessario para o HashSet rejeitar duplicados.
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Morador)) return false;
            return cpf.equals(((Morador) o).cpf);
        }

        @Override
        public int hashCode() {
            return cpf.hashCode();
        }

        @Override
        public String toString() {
            return nome + " (CPF " + cpf + ", " + unidade + ")";
        }
    }

    public static void main(String[] args) {
        // ---------- ArrayList: lista ordenada, aceita duplicados, acesso por indice ----------
        System.out.println("=== ArrayList: moradores em ordem de cadastro ===");
        List<Morador> moradores = new ArrayList<>();
        moradores.add(new Morador("Ana Souza", "11111111111", "Bloco A - 101"));
        moradores.add(new Morador("Bruno Lima", "22222222222", "Bloco A - 102"));
        moradores.add(new Morador("Carla Dias", "33333333333", "Bloco B - 201"));
        moradores.add(new Morador("Diego Rocha", "44444444444", "Bloco B - 201"));

        for (int i = 0; i < moradores.size(); i++) {
            System.out.println(i + " -> " + moradores.get(i));
        }
        moradores.remove(1); // remove Bruno pelo indice
        System.out.println("Apos remover indice 1, total: " + moradores.size());

        // ---------- HashSet: sem duplicados, sem ordem garantida ----------
        System.out.println("\n=== HashSet: CPFs unicos (sem duplicados) ===");
        Set<Morador> unicos = new HashSet<>();
        unicos.add(new Morador("Ana Souza", "11111111111", "Bloco A - 101"));
        boolean inseriu = unicos.add(new Morador("Ana S. (repetida)", "11111111111", "Bloco A - 101"));
        System.out.println("Segunda Ana foi inserida? " + inseriu + " (equals/hashCode por CPF)");
        System.out.println("Tamanho do set: " + unicos.size());

        Set<String> blocos = new HashSet<>();
        for (Morador m : moradores) {
            blocos.add(m.getUnidade().split(" - ")[0]);
        }
        System.out.println("Blocos distintos com moradores: " + blocos);
        System.out.println("Existe Bloco C? " + blocos.contains("Bloco C"));

        // ---------- HashMap: chave -> valor, busca rapida pela chave ----------
        System.out.println("\n=== HashMap: busca por CPF ===");
        Map<String, Morador> porCpf = new HashMap<>();
        for (Morador m : moradores) {
            porCpf.put(m.getCpf(), m);
        }
        System.out.println("Busca 33333333333: " + porCpf.get("33333333333"));
        System.out.println("Busca 99999999999: " + porCpf.get("99999999999"));
        System.out.println("Contem 11111111111? " + porCpf.containsKey("11111111111"));

        System.out.println("\n=== HashMap: moradores por unidade ===");
        Map<String, List<Morador>> porUnidade = new HashMap<>();
        for (Morador m : moradores) {
            porUnidade.computeIfAbsent(m.getUnidade(), k -> new ArrayList<>()).add(m);
        }
        for (Map.Entry<String, List<Morador>> e : porUnidade.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue().size() + " morador(es)");
            for (Morador m : e.getValue()) {
                System.out.println("    " + m.getNome());
            }
        }

        System.out.println("\n=== HashMap: contagem de vagas de garagem por unidade ===");
        String[] vagasAtribuidas = {"Bloco A - 101", "Bloco B - 201", "Bloco A - 101", "Bloco B - 201", "Bloco B - 201"};
        Map<String, Integer> vagasPorUnidade = new HashMap<>();
        for (String u : vagasAtribuidas) {
            vagasPorUnidade.merge(u, 1, Integer::sum);
        }
        vagasPorUnidade.forEach((u, qtd) ->
                System.out.println(u + ": " + qtd + " vaga(s)" + (qtd > 2 ? " (excede o limite de 2!)" : "")));
    }
}
