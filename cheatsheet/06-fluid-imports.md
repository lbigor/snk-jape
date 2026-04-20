---
id: 06
titulo: Use FluidCreateVO / FluidUpdateVO — não JapeWrapper.CreateBuilder / UpdateBuilder
severidade: alta
---

## Sintoma

`cannot find symbol: class CreateBuilder` ou imports que não resolvem em IDE.

## Código errado

```java
import br.com.sankhya.jape.wrapper.JapeWrapper;

JapeWrapper.CreateBuilder cb = dao.prepareToCreate();
JapeWrapper.UpdateBuilder ub = dao.prepareToUpdateByPK(new Object[]{codparc});
```

## Código correto

```java
import br.com.sankhya.jape.wrapper.fluid.FluidCreateVO;
import br.com.sankhya.jape.wrapper.fluid.FluidUpdateVO;

FluidCreateVO novo = dao.prepareToCreate();
FluidUpdateVO upd  = dao.prepareToUpdateByPK(new Object[]{codparc});
```

## Por quê

As classes fluidas oficiais moraram em `br.com.sankhya.jape.wrapper.fluid`. `JapeWrapper.CreateBuilder` e `UpdateBuilder` são nomes antigos/inexistentes que aparecem em autocompletes errados e exemplos desatualizados.

## Como Claude aplica

- Sempre que gerar código que use builders fluidos, importar de `br.com.sankhya.jape.wrapper.fluid.*`.
- Validar também em `sankhya_api.md` se surgir dúvida sobre a classe existir.

## Referência rápida

| Ação | Classe | Método do DAO |
|---|---|---|
| Criar | `FluidCreateVO` | `dao.prepareToCreate()` |
| Atualizar por PK | `FluidUpdateVO` | `dao.prepareToUpdateByPK(Object[])` |
| Atualizar por filtro | `FluidUpdateVO` | `dao.prepareToUpdate(String, Object[])` |
