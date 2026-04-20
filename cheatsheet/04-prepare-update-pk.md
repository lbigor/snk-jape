---
id: 04
titulo: prepareToUpdateByPK — PK vai como Object[], não BigDecimal solto
severidade: alta
---

## Sintoma

`NoSuchMethodError` em runtime, ou compila mas grava no registro errado por casting implícito.

## Código errado

```java
FluidUpdateVO upd = dao.prepareToUpdateByPK(codparc);
FluidUpdateVO upd = dao.prepareToUpdateByPK(new BigDecimal(1));
```

## Código correto

```java
FluidUpdateVO upd = dao.prepareToUpdateByPK(new Object[]{codparc});

// PK composta (ex.: TGFITE — NUNOTA + SEQUENCIA)
FluidUpdateVO upd = dao.prepareToUpdateByPK(new Object[]{nunota, sequencia});
```

## Por quê

A assinatura canônica é `prepareToUpdateByPK(Object[] pk)`. Sankhya suporta PK composta — sempre `Object[]` para não quebrar quando a tabela tiver mais de um campo-chave. Overloads com escalar existem mas variam entre versões e não cobrem PK composta.

## Como Claude aplica

- Sempre envolver a PK em `new Object[]{...}`.
- Antes de gerar o update, verificar no `sankhya_core.md` (ou DDL do cliente) se a tabela tem PK simples ou composta e passar todos os campos na ordem do DDL.
