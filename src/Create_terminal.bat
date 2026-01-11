@echo off
title Test Pattern Observer - Deux Terminaux
echo.
echo ========================================
echo   TEST DU PATTERN OBSERVER EN TEMPS RÉEL
echo ========================================
echo.
echo TERMINAL 1 : Jean Dupont (Vendeur)
echo TERMINAL 2 : Marie Observatrice (Acheteuse)
echo.
echo Instructions :
echo 1. Terminal 1 : Créer une annonce
echo 2. Terminal 2 : Rechercher et s'abonner
echo 3. Observer les notifications croisées
echo.
pause

start "Jean - Vendeur" cmd /k "cd /d %~dp0 && echo === TERMINAL 1 - JEAN === && java -cp "out;src" fr.votreprojet.app.Main"
timeout /t 3
start "Marie - Acheteuse" cmd /k "cd /d %~dp0 && echo === TERMINAL 2 - MARIE === && java -cp "out;src" fr.votreprojet.app.Main"