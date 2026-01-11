@echo off
echo ===================================
echo  PLATEFORME COLLABORATIVE ESISAR
echo ===================================
echo.

echo 1. Nettoyage du dossier out...
if exist out rmdir /s /q out
mkdir out 2>nul
echo.

echo 2. Compilation des fichiers Java...
javac -d out -cp "src" ^
  src/fr/votreprojet/app/Main.java ^
  src/fr/votreprojet/app/InterfaceConsole.java ^
  src/fr/votreprojet/modele/*.java ^
  src/fr/votreprojet/service/*.java ^
  src/fr/votreprojet/util/*.java

if %ERRORLEVEL% NEQ 0 (
  echo.
  echo ERREUR: Compilation echouee!
  pause
  exit /b 1
)

echo.
echo 3. Lancement de l'application...
echo ===================================
echo.
java -cp "out;src" fr.votreprojet.app.Main

pause