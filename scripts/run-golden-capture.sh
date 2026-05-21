#!/usr/bin/env bash
# Capture current Golden Master output to stdout (compare with golden_master_expected.txt).
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

mvn -q test-compile
mvn -q exec:java \
  -Dexec.classpathScope=test \
  -Dexec.mainClass=GoldenMasterGenerator \
  > actual.txt

echo "Wrote actual.txt — diff against src/test/resources/golden_master_expected.txt"
