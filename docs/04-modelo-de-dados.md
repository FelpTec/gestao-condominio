# 04 — Modelo de dados

Entidades já implementadas: `Unidade`, `Morador`, `Sindico`, `Funcionario`, `Vaga`.
Entidades propostas para módulos futuros: `Visitante`, `AreaComum`, `Reserva`, `Boleto`, `Despesa`, `Ocorrencia`, `Comunicado`.

`Morador` e `Funcionario` herdam de `Pessoa` (`@MappedSuperclass`: id, nome, cpf, telefone).

```mermaid
erDiagram
    UNIDADE ||--o{ MORADOR : "abriga"
    UNIDADE ||--o{ VAGA : "possui (max 2)"
    MORADOR ||--o{ SINDICO : "exerce mandato"
    UNIDADE ||--o{ BOLETO : "recebe"
    UNIDADE ||--o{ VISITANTE : "recebe"
    MORADOR ||--o{ RESERVA : "faz"
    AREA_COMUM ||--o{ RESERVA : "reservada em"
    MORADOR ||--o{ OCORRENCIA : "abre"
    FUNCIONARIO ||--o{ OCORRENCIA : "atende"
    SINDICO ||--o{ COMUNICADO : "publica"

    UNIDADE {
        int id PK
        string bloco
        string numero
    }
    MORADOR {
        int id PK
        string nome
        string cpf UK
        string telefone
        boolean proprietario
        int unidade_id FK
    }
    SINDICO {
        int id PK
        int morador_id FK
        date inicio_mandato
        date fim_mandato
        boolean ativo
    }
    FUNCIONARIO {
        int id PK
        string nome
        string cpf UK
        string telefone
        string cargo
        decimal salario
    }
    VAGA {
        int id PK
        int numero UK
        int unidade_id FK
    }
    VISITANTE {
        int id PK
        string nome
        string documento
        int unidade_id FK
        datetime entrada
        datetime saida
    }
    AREA_COMUM {
        int id PK
        string nome
        int capacidade
    }
    RESERVA {
        int id PK
        int morador_id FK
        int area_id FK
        datetime inicio
        datetime fim
    }
    BOLETO {
        int id PK
        int unidade_id FK
        decimal valor
        date vencimento
        boolean pago
    }
    DESPESA {
        int id PK
        string descricao
        decimal valor
        date data
    }
    OCORRENCIA {
        int id PK
        int morador_id FK
        int funcionario_id FK
        string descricao
        string status
    }
    COMUNICADO {
        int id PK
        int sindico_id FK
        string titulo
        string texto
        date data
    }
```
