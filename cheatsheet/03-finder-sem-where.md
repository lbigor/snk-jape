---
id: 03
titulo: Finder / findOne / find — sem WHERE, com array de parâmetros
severidade: alta
---

## Sintoma

`SQLException: incorrect syntax near 'WHERE'` ou filtro ignorado (retorna todos os registros).

## Código errado

```java
DynamicVO parc = dao.findOne("WHERE CODPARC = ?", codparc);
Collection<DynamicVO> itens = dao.find("WHERE NUNOTA = ? AND ATIVO = 'S'", nunota);
```

## Código correto

```java
DynamicVO parc = dao.findOne("CODPARC = ?", new Object[]{codparc});
Collection<DynamicVO> itens = dao.find(
    "NUNOTA = ? AND ATIVO = 'S'",
    new Object[]{nunota}
);
```

## Por quê

Os métodos `findOne(String, Object[])` e `find(String, Object[])` do `JapeWrapper` já concatenam `WHERE` internamente. Ao passar "WHERE" no início o JAPE gera `... WHERE WHERE ...`. Além disso, a assinatura exige `Object[]` — passar um escalar é outro overload e pode não bater com o esperado.

## Como Claude aplica

- Sempre remover "WHERE" do início do predicado.
- Sempre empacotar os parâmetros em `new Object[]{...}`, mesmo que seja um só.
- Vale para `find`, `findOne`, `findList`, `findByDynamicFinderAsVO`.

## Dica extra (null-safety)

Se o predicado tocar campo de valor, aplicar a regra global — ver [99-null-safety-sql.md](99-null-safety-sql.md):

```java
dao.find(
    "ISNULL(ESTOQUE, 0) - ISNULL(RESERVADO, 0) > 0 AND CODPROD = ?",
    new Object[]{codprod}
);
```
