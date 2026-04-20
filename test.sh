#!/usr/bin/env bash
set -euo pipefail

# Valida a estrutura da skill localmente (mesmas verificações do CI).

cd "$(dirname "$0")"

echo "==> Verificando cheatsheets obrigatórios..."
for i in 01 02 03 04 05 06 07 99; do
  if ! ls cheatsheet/${i}-*.md >/dev/null 2>&1; then
    echo "FALTA cheatsheet ${i}"
    exit 1
  fi
done
echo "  OK — 8 cheatsheets presentes."

echo "==> Verificando templates obrigatórios..."
for f in buscar-por-pk buscar-por-criterio criar-registro atualizar-registro deletar-registro; do
  if [ ! -f "templates/${f}.java" ]; then
    echo "FALTA template ${f}.java"
    exit 1
  fi
done
echo "  OK — 5 templates presentes."

echo "==> Verificando arquivos-chave..."
for f in README.md SKILL.md INSTALACAO.md BOAS_PRATICAS.md CONTRIBUTING.md LICENSE install.sh; do
  if [ ! -f "${f}" ]; then
    echo "FALTA ${f}"
    exit 1
  fi
done
echo "  OK — arquivos-chave presentes."

echo ""
echo "Tudo certo."
