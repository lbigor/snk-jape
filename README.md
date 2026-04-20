# snk-jape

> Cheatsheet viva dos 7 erros mais comuns do JAPE. Pare de quebrar a API pela décima vez.

**Problema:** 7 armadilhas recorrentes do JAPE (Java Persistence API do Sankhya) gastam 2h por bug.
**Solução:** Claude consulta a cheatsheet antes de gerar código — evita de primeira.
**Você faz:** `"Claude, busca esse registro e atualiza"` — sai certo.

## Instalação

```bash
curl -fsSL https://raw.githubusercontent.com/lbigor/snk-jape/main/install.sh | bash
```

## O que tem aqui

- `SKILL.md` — orquestrador pro Claude consultar antes de gerar código JAPE
- `cheatsheet/` — um arquivo por erro, com ERRADO, CERTO e o porquê
- `templates/` — código pronto pra copiar: buscar, criar, atualizar, deletar
- `BOAS_PRATICAS.md` — tabela consolidada dos 7 erros + null-safety SQL

## Os 7 erros canônicos

Ver [BOAS_PRATICAS.md](BOAS_PRATICAS.md).

## Licença

MIT · 2026 · Igor Lima · Contato: lbigor@icloud.com
