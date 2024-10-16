@echo off

:: Définition des chemins de destination et source pour les pages web
set source_pages=C:\e-bossy\S4\Web Dyn\mini-projet\web
set temp_folder=C:\temp\mini-projet_temp
set destination_war=C:\xampp\tomcat\webapps\mini-projet2.war

:: Création du dossier temporaire
mkdir "%temp_folder%"

:: Copie des fichiers de la source vers le dossier temporaire
robocopy "%source_pages%" "%temp_folder%" /E

:: Navigation vers le répertoire du projet
cd C:\e-bossy\S4\Web Dyn\mini-projet

:: Compilation des fichiers Java et copie dans le dossier temporaire
:: Note : Cette étape compile manuellement les fichiers Java.
:: Assurez-vous que le chemin vers le compilateur Java (javac) est correctement configuré dans votre variable d'environnement PATH.

javac -d "%temp_folder%\WEB-INF\classes" -sourcepath src src\java\controller\*.java src\java\model\*.java 

:: Création du fichier .war à partir du dossier temporaire
cd "%temp_folder%"
jar cvf "%destination_war%" *

:: Suppression du dossier temporaire
cd ..
rmdir /s /q "%temp_folder%"

:: Déplacement dans le répertoire bin de Tomcat
cd C:\xampp\tomcat\bin

:: Démarrage de Tomcat
startup.bat

echo Déploiement terminé. Vous pouvez accéder à votre application via http://localhost:8080/mini-projet2/

pause
