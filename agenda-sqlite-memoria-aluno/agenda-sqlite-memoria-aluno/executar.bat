@echo off
setlocal
cd /d "%~dp0"
if not exist bin mkdir bin
if exist fontes.txt del fontes.txt
for /r src %%f in (*.java) do echo "%%f">>fontes.txt
javac -encoding UTF-8 -cp "lib/*" -d bin @fontes.txt
if errorlevel 1 (
    del fontes.txt
    echo Verifique se o JDK esta instalado e javac esta no PATH.
    exit /b 1
)
del fontes.txt
java -cp "bin;lib/*" br.com.exemplo.agenda.Main
endlocal
