---
name: snk-jape
description: Cheatsheet dos 7 erros comuns do JAPE + templates de código correto. Acionar antes de gerar qualquer código Java que use br.com.sankhya.jape.*
type: skill
---

# Instruções pro Claude

Antes de gerar código que use qualquer classe `br.com.sankhya.jape.*`:

1. Consulte `cheatsheet/*.md` para verificar se o uso está correto.
2. Use os templates em `templates/` como base.
3. Regra global: se a query envolve campos de valor (QTDNEG, QTDPED, QTDCAN, ESTOQUE, RESERVADO, WMSBLOQUEADO, VLRUNIT, VLRTOT, saldos, agregações), embrulhe em `ISNULL(campo, 0)` (SQL Server) ou `NVL(campo, 0)` (Oracle).
4. Sempre feche `JapeSession` no `finally`.

## Fluxo obrigatório

| Situação | O que fazer |
|---|---|
| Vai ler/escrever campo no `DynamicVO` | Conferir `cheatsheet/01-set-property.md` e `02-get-property.md` |
| Vai usar `Finder` / `findOne` / `find` | Conferir `cheatsheet/03-finder-sem-where.md` |
| Vai atualizar por PK | Conferir `cheatsheet/04-prepare-update-pk.md` |
| Vai usar `Fluid*VO` | Conferir `cheatsheet/05-fluid-update-void.md` e `06-fluid-imports.md` |
| Abriu `JapeSession` | Conferir `cheatsheet/07-jape-session-close.md` |
| Query toca campo de valor | Conferir `cheatsheet/99-null-safety-sql.md` |

## Prioridade de templates

Quando a tarefa for um CRUD simples, comece pelo template correspondente em `templates/`:

- Buscar por PK → `templates/buscar-por-pk.java`
- Buscar por critério → `templates/buscar-por-criterio.java`
- Criar registro → `templates/criar-registro.java`
- Atualizar registro → `templates/atualizar-registro.java`
- Deletar registro → `templates/deletar-registro.java`

## Regras de pacote

Todo projeto usa `br.com.lbi.<projeto>` — nunca `br.com.devstudios`.

## Regras HTTP

Usar `HttpURLConnection` nativo — nunca OkHttp3 ou libs externas.
