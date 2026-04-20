---
id: 05
titulo: FluidUpdateVO.update() retorna void — não encadeie esperando DynamicVO
severidade: media
---

## Sintoma

Compilação falha com `incompatible types: void cannot be converted to DynamicVO`, ou `NullPointerException` em variável que recebeu o retorno de `update()`.

## Código errado

```java
DynamicVO atualizado = dao.prepareToUpdateByPK(new Object[]{codparc})
    .set("NOMEPARC", "ACME")
    .update();
System.out.println(atualizado.asString("NOMEPARC")); // NPE
```

## Código correto

```java
dao.prepareToUpdateByPK(new Object[]{codparc})
   .set("NOMEPARC", "ACME")
   .update();

// Se precisa do VO atualizado, buscar de novo:
DynamicVO atualizado = dao.findByPK(codparc);
```

## Por quê

`FluidUpdateVO.update()` retorna `void` — ele apenas aplica as mudanças no registro. Para inspecionar o estado pós-update, releia via `findByPK` ou `findOne`. Mesmo raciocínio vale para `FluidCreateVO.save()` que retorna o VO recém-criado — mas **não** confundir com `update`.

## Como Claude aplica

- Nunca atribuir o retorno de `FluidUpdateVO.update()` a uma variável.
- Se o código precisa do VO atualizado para continuar, gerar um `findByPK` logo depois.
- Se for `FluidCreateVO.save()`, sim, capturar o retorno (é o `DynamicVO` criado).
