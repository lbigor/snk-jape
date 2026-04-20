# Instalação

## Um comando

```bash
curl -fsSL https://raw.githubusercontent.com/lbigor/snk-jape/main/install.sh | bash
```

Isso clona o repo em `~/.claude/skills/snk-jape/` e o Claude passa a consultar a skill antes de gerar código JAPE.

## Verificar

```bash
ls ~/.claude/skills/snk-jape/SKILL.md
```

Se existir, está ativo.

## Atualizar

```bash
cd ~/.claude/skills/snk-jape && git pull
```

## Desinstalar

```bash
rm -rf ~/.claude/skills/snk-jape
```
