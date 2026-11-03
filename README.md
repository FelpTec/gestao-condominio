# Gestao de Condominio (Java + JPA/Hibernate)

Projeto em Java 17 baseado na estrutura do projeto da loja (model / repository / service / view / util),
com menu CLI para demonstrar o funcionamento.

## Funcionalidades
- **Unidades**: bloco + apartamento (nao permite duplicar; nao exclui se tiver moradores ou vagas).
- **Moradores**: CPF unico, proprietario ou inquilino, vinculados a uma unidade.
- **Sindico**: um sindico ativo por vez, mandato em meses, encerramento automatico de mandato vencido, historico.
- **Funcionarios**: cargo (porteiro, zelador, faxineiro...), salario, total da folha e contagem por cargo.
- **Garagem**: cadastro de vagas (uma ou em lote), atribuir/liberar vaga por unidade (limite de 2 por unidade)
  e contagem de vagas totais, ocupadas e livres.
- **Resumo do condominio** e **dados de exemplo** (menu 6 e 7) para apresentar o sistema rapidamente.

## Como executar
Requisitos: JDK 17+ e Maven.

    mvn -q compile exec:java

Banco padrao: H2 em arquivo (`./dados/condominio`), sem instalar nada.
Para MySQL (localhost:3306, usuario/senha `root`/`root`, banco criado automaticamente):

    mvn -q compile exec:java -Dbanco=mysql

(ou defina a variavel de ambiente `BANCO=mysql`).

## Roteiro de demonstracao
1. Menu 7 - carregar dados de exemplo
2. Menu 6 - resumo do condominio
3. Menu 5 > 1 - contagem de garagem; 5 > 5 - atribuir vaga; 5 > 6 - liberar vaga
4. Menu 3 > 2 - eleger outro sindico (o anterior e encerrado automaticamente)
5. Menu 1 > 4 - tentar excluir unidade com moradores (regra de negocio bloqueia)
