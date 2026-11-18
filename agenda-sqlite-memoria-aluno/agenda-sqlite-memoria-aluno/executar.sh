#!/usr/bin/env bash
set -e
cd -- "$(dirname -- "$0")"
mkdir -p bin
find src -name '*.java' > fontes.txt
trap 'rm -f fontes.txt' EXIT
javac -encoding UTF-8 -cp 'lib/*' -d bin @fontes.txt
java -cp 'bin:lib/*' br.com.exemplo.agenda.Main
