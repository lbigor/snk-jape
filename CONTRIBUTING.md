# Contributing

## Como contribuir

1. Abra issue descrevendo o erro/armadilha do JAPE que não está coberto.
2. Fork, branch `feat/<nome-curto>`, PR com base em `main`.
3. CI precisa passar (markdownlint + validação dos 8 cheatsheets + syntax-check dos templates).

## Estrutura

- `cheatsheet/NN-nome.md` — 1 erro por arquivo. Frontmatter + sintoma + errado + certo + por quê + como Claude aplica.
- `templates/<acao>.java` — código copia-e-cola, comentado, pacote `br.com.lbi.<projeto>`.

## Estilo de cheatsheet

```markdown
---
id: NN
titulo: <título curto>
severidade: alta|media|baixa
---

## Sintoma

## Código errado

## Código correto

## Por quê

## Como Claude aplica
```

## Regras do projeto

- Pacote `br.com.lbi` — nunca `br.com.devstudios`.
- HTTP com `HttpURLConnection` — nunca OkHttp3.
- Validar em `sankhya_api.md` antes de citar classe/método Sankhya.

## Contato

lbigor@icloud.com
