---
id: 01
titulo: DynamicVO — usar setProperty, não set
severidade: alta
---

## Sintoma

`NoSuchMethodError` ou código compila mas grava em propriedade errada / não persiste.

## Código errado

```java
vo.set("CODPARC", new BigDecimal(1));
vo.set("NOMEPARC", "ACME");
```

O método `set` existe em ancestrais genéricos do `DynamicVO` mas não é o contrato oficial do JAPE — alterações podem não chegar ao banco.

## Código correto

```java
vo.setProperty("CODPARC", new BigDecimal(1));
vo.setProperty("NOMEPARC", "ACME");
```

## Por quê

`DynamicVO` (de `br.com.sankhya.jape.vo`) expõe `setProperty(String, Object)` como única API pública estável para gravação de campo. `set` é herança e seu comportamento muda entre versões do JAPE.

## Como Claude aplica

Sempre que encontrar `DynamicVO.set(...)` em código a gerar ou revisar, trocar por `setProperty(...)`. Idem em subclasses como `RotinaVO`, `NotaVO` etc.
