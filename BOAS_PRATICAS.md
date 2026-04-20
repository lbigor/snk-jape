# Boas Práticas — JAPE (Java Persistence API do Sankhya)

Tabela canônica dos 7 erros mais comuns + regra global de null-safety em SQL.

## Os 7 erros canônicos

| # | Errado | Correto | Detalhe |
|---|---|---|---|
| 1 | `DynamicVO.set("CAMPO", valor)` | `DynamicVO.setProperty("CAMPO", valor)` | [01-set-property.md](cheatsheet/01-set-property.md) |
| 2 | `DynamicVO.get("CAMPO")` | `DynamicVO.getProperty("CAMPO")` ou tipados (`asString`, `asBigDecimal`) | [02-get-property.md](cheatsheet/02-get-property.md) |
| 3 | `findOne("WHERE CAMPO = ?", valor)` | `findOne("CAMPO = ?", new Object[]{valor})` — sem "WHERE" | [03-finder-sem-where.md](cheatsheet/03-finder-sem-where.md) |
| 4 | `prepareToUpdateByPK(BigDecimal)` | `prepareToUpdateByPK(new Object[]{pk})` — array obrigatório | [04-prepare-update-pk.md](cheatsheet/04-prepare-update-pk.md) |
| 5 | `FluidUpdateVO.update()` retorna `DynamicVO` | Retorna `void` — usar `findByPK` depois se precisar | [05-fluid-update-void.md](cheatsheet/05-fluid-update-void.md) |
| 6 | `JapeWrapper.CreateBuilder` / `UpdateBuilder` | `FluidCreateVO` e `FluidUpdateVO` (import: `br.com.sankhya.jape.wrapper.fluid.*`) | [06-fluid-imports.md](cheatsheet/06-fluid-imports.md) |
| 7 | `JapeSession.open()` sem fechar | Sempre fechar no `finally: JapeSession.close(hnd)` | [07-jape-session-close.md](cheatsheet/07-jape-session-close.md) |

## Regra global — Null-safety em SQL

Toda query SQL que toque campo numérico de valor (`QTDNEG`, `QTDPED`, `QTDCAN`, `ESTOQUE`, `RESERVADO`, `WMSBLOQUEADO`, `VLRUNIT`, `VLRTOT`, saldos, agregações etc.) deve envolver o campo em:

- **SQL Server** (Sankhya padrão): `ISNULL(campo, 0)`
- **Oracle**: `NVL(campo, 0)`

Detalhe em [99-null-safety-sql.md](cheatsheet/99-null-safety-sql.md).

## Regras de pacote e HTTP

- Pacote Java padrão: **`br.com.lbi`** — nunca `br.com.devstudios`.
- Cliente HTTP: **`HttpURLConnection`** nativo — nunca OkHttp3 ou libs externas.
- Sempre consultar `sankhya_core.md` antes de usar tabelas/campos nativos.
- Sempre buscar `NOME_DAO` no DDL do cliente antes de passar para `JapeFactory.dao()`.
