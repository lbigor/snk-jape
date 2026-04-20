#!/usr/bin/env bash
set -euo pipefail

# Instala snk-jape como skill local do Claude Code.

DEST="${HOME}/.claude/skills/snk-jape"
REPO="https://github.com/lbigor/snk-jape.git"

mkdir -p "${HOME}/.claude/skills"

if [ -d "${DEST}/.git" ]; then
  echo "snk-jape já existe em ${DEST} — atualizando..."
  git -C "${DEST}" pull --ff-only
else
  echo "Clonando snk-jape em ${DEST}..."
  git clone --depth=1 "${REPO}" "${DEST}"
fi

echo ""
echo "snk-jape instalado em ${DEST}"
echo "Claude Code consultará a skill antes de gerar código JAPE."
