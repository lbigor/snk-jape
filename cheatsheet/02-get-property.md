---
id: 02
titulo: DynamicVO — usar getProperty ou asString/asBigDecimal, não get
severidade: alta
---

## Sintoma

`ClassCastException` ao converter o retorno para tipo concreto; ou `NullPointerException` ao chamar método em `Object` devolvido.

## Código errado

```java
String nome = (String) vo.get("NOMEPARC");
BigDecimal codigo = (BigDecimal) vo.get("CODPARC");
```

## Código correto

```java
// Genérico (precisa cast apenas se tipo não for o default)
Object valor = vo.getProperty("NOMEPARC");

// Tipados — PREFERÍVEIS
String nome = vo.asString("NOMEPARC");
BigDecimal codigo = vo.asBigDecimal("CODPARC");
Timestamp dt = vo.asTimestamp("DHALTER");
Integer qtd = vo.asInteger("QTDNEG");
```

## Por quê

`DynamicVO` oferece acessores tipados (`asString`, `asBigDecimal`, `asTimestamp`, `asInteger`, `asBoolean`, `asDate`) que fazem conversão segura e tratam `NULL` retornando `null` em vez de explodir. `get(...)` não existe como API pública.

## Como Claude aplica

- Campo texto → `asString`
- Campo numérico → `asBigDecimal` (ou `asInteger` se for PK/contagem)
- Campo data/hora → `asTimestamp`
- Campo lógico (S/N) → `asString` (Sankhya armazena como char)

Nunca fazer cast manual do retorno de `getProperty` sem antes tentar o acessor tipado.
