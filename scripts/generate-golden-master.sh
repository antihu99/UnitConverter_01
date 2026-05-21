#!/usr/bin/env bash
# Generate or refresh src/test/resources/golden_master_expected.txt (Golden Master baseline).
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

mvn -q test-compile
mvn -q exec:java \
  -Dexec.classpathScope=test \
  -Dexec.mainClass=GoldenMasterGenerator \
  -Dexec.args="--write"

echo ""
echo "Next: git add src/test/resources/golden_master_expected.txt"
