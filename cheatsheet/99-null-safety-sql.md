---
id: 99
titulo: Null-safety em SQL — ISNULL (SQL Server) / NVL (Oracle) em campos de valor
severidade: alta
---

## Sintoma

Linha com estoque real não aparece no empenho automático. Query retorna zero registros mas o dado existe. `SUM(x)` retorna `null` em vez de `0`. Filtro `ESTOQUE - RESERVADO > 0` descarta linhas válidas.

## Código errado

```sql
SELECT CODPROD, ESTOQUE - RESERVADO AS DISPONIVEL
FROM TGFEST
WHERE ESTOQUE - RESERVADO > 0;

SELECT SUM(QTDNEG) FROM TGFITE WHERE NUNOTA = ?;
```

## Código correto

**SQL Server** (Sankhya padrão):

```sql
SELECT
  CODPROD,
  ISNULL(ESTOQUE, 0) - ISNULL(RESERVADO, 0) AS DISPONIVEL
FROM TGFEST
WHERE ISNULL(ESTOQUE, 0) - ISNULL(RESERVADO, 0) > 0;

SELECT SUM(ISNULL(QTDNEG, 0)) FROM TGFITE WHERE NUNOTA = ?;
```

**Oracle**:

```sql
SELECT
  CODPROD,
  NVL(ESTOQUE, 0) - NVL(RESERVADO, 0) AS DISPONIVEL
FROM TGFEST
WHERE NVL(ESTOQUE, 0) - NVL(RESERVADO, 0) > 0;

SELECT SUM(NVL(QTDNEG, 0)) FROM TGFITE WHERE NUNOTA = ?;
```

## Por quê

Campos de valor NULL quebram três comportamentos essenciais:

1. **Filtros silenciosamente**: `ESTOQUE - RESERVADO > 0` vira `NULL > 0` quando qualquer parcela é NULL, e o predicado retorna UNKNOWN — linha descartada.
2. **Agregações**: `SUM(x)` sobre coluna toda NULL vira `NULL`, não `0`. O código Java que recebe o retorno pode quebrar no `BigDecimal.valueOf(null)`.
3. **Comparações**: `valor > 0` é falso (UNKNOWN) para NULL.

Incidente recorrente: item com estoque real não aparecia no empenho automático porque `RESERVADO` estava NULL em `TGFEST`.

**Preferir** `SUM(ISNULL(campo, 0))` em vez de `ISNULL(SUM(campo), 0)` quando houver risco de linhas individuais virem NULL — o primeiro protege cada parcela, o segundo só protege o resultado final.

## Campos de valor mais comuns no Sankhya

`QTDNEG`, `QTDPED`, `QTDCAN`, `QTDENT`, `QTDENTREGUE`, `ESTOQUE`, `RESERVADO`, `WMSBLOQUEADO`, `VLRUNIT`, `VLRTOT`, `VLRDESC`, `VLRICMS`, `VLRIPI`, `VLRFRETE`, `SALDO`, `SALDOATUAL`.

## Como Claude aplica

- Ao criar query nova: já nasce com `ISNULL/NVL` nos campos de valor.
- Ao revisar query existente sem proteção: sinalizar e corrigir, mesmo se não for o foco da tarefa.
- Ao migrar query entre DBs: trocar `ISNULL` ↔ `NVL` mantendo o mesmo comportamento.
- Em DAOs com `FinderWrapper` do JAPE: o predicado vai como SQL literal, então `ISNULL` aplica igual.
