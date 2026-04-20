---
id: 07
titulo: JapeSession — sempre close() no finally
severidade: alta
---

## Sintoma

Conexões vazando, pool esgotado em produção, erros `Too many connections` ou `connection closed` aleatórios em outras rotinas.

## Código errado

```java
JapeSession.SessionHandle hnd = JapeSession.open();
DynamicVO parc = dao.findByPK(codparc);
// ...processa...
JapeSession.close(hnd); // não executa se o bloco acima lançar exceção
```

## Código correto

```java
JapeSession.SessionHandle hnd = null;
try {
    hnd = JapeSession.open();
    DynamicVO parc = dao.findByPK(codparc);
    // ...processa...
} finally {
    JapeSession.close(hnd);
}
```

## Por quê

Qualquer exceção entre `open()` e `close()` — inclusive `RuntimeException` vinda do próprio JAPE — pula o `close` se ele não estiver em `finally`. A sessão fica pendurada, o `SessionHandle` não libera a conexão, e o pool do Sankhya degrada silenciosamente.

## Como Claude aplica

- Todo `JapeSession.open()` precisa vir dentro de `try` com `finally` fazendo `close`.
- Em entry points (`AcaoRotinaJava`, `EventoProgramavelJava`), geralmente o Sankhya abre/fecha a sessão automaticamente — **não** chamar `open` nesses casos.
- Para daemons/jobs externos que você mesmo controla o ciclo (ex.: main custom), abrir e fechar manualmente com o padrão acima.

## Bônus

Se houver logging via `br.com.lbi.slack`, fazer `flush` antes de sair do `finally` para não perder a última linha em caso de erro.
