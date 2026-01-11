# Notice d'installation de l'application sur son ordinateur

Pour pouvoir utiliser l'application sur son ordinateur il est nécessaire d'installer plusieurs autres outils permettant 
de le faire fonctionner au mieux. Nous devrons installer et configurer plusieurs outils pour se faire.
Voici un résumé des actions à effectuer:

## 1/ Installer Java 17
## 2/ Mettre en place les variables d'environnement pour Java 17
## 3/ Installer Maven
## 4/ Mettre en place les variables d'environnement pour Maven
## 5/ Installer WAMP (Windows Apache MySql PHP)
## 6/ Installer IntelliJ Idea
## 7/ Installer le projet backend
## 8/ Préparer le projet backend et la base de donnée
## 9/ Lancer l'application

## 1/ Installer Java 17

- Aller sur le site https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
- Télécharger le .exe qui se situe sur la ligne "Windows x64 Installer"
- Utiliser l'installer téléchargé précédemment pour installer Java 17 sur sa machine
- Ouvrir un terminal de commande et effectuer l'instruction suivante : java -version permettra de vérifier que l'installation s'est parfaitement déroulé

## 2/ Mettre en place les variables d'environnement pour Java 17

- Sur votre ordinateur, ouvrir le menu des variables d'environnement
- Dans variable utilisateur, créer une nouvelle variable nommé JAVA_HOME avec le chemin pointant sur le dossier ou se situe Java 17
- Ajouter une nouvelle valeur à la variable Path (dans les variables systèmes) : %JAVA_HOME%\bin
- Fermer le précédent terminal de commande
- En ouvrir un nouveau pour le raffraîchir puis faire echo %JAVA_HOME%, si le chemin renvoyé est le bon alors vos variables d'environnement sont bien configurés

## 3/ Installer Maven

- Aller sur le site https://maven.apache.org/download.cgi
- Télécharger le binary zip archive, option Link
- Décompresser le .zip obtenu dans un dossier

## 4/ Mettre en place les variables d'environnement pour Maven

- Ouvrir de nouveau le menu des variables d'environnement
- Ajouter aux variables utilisateurs MAVEN_HOME cablé sur le chemin d'installation de Maven
- Ajouter au Path (dans les variables systèmes): %MAVEN_HOME%\bin
- Fermer le précédent terminal de commande
- En ouvrir un nouveau pour le raffraîchir puis faire echo %MAVEN_HOME%, si le chemin renvoyé est le bon alors vos variables d'environnement sont bien configurés

## 5/ Installer WAMP (Windows Apache MySql PHP)

- Si diriger vers le site https://wampserver.aviatechno.net
- Télécharger la version complète wampserver 3.0.4 situé dans la zone Installer wampserver
- En initialisant l'installation l'assistant d'installation vous demandera de télécharger des dépendances nécessaire
- Les télécharger
- Terminer l'installation de Wamp

## 6/ Installer IntelliJ Idea

- Rendez-vous sur le site https://www.jetbrains.com/fr-fr/idea/download/?section=windows pour y télécharger la version Windows d'IntelliJ Idea
- Après téléchargement du launcher, l'exécuter pour installer IntelliJ Idea

## 7/ Installer le projet backend

- Direction le site https://github.com/NeoFlake/alterhub-backend
- Appuyer sur le bouton "< code >" vert pour y trouver l'url permettant de cloner le projet backend
- Ouvrir une console de commande dans le dossier où se situe également /frontend
- Effectuer la commande git clone l'urldemonprojetbackend
- Renommer le dossier ainsi importé de alterhub-backend à backend (absolument crucial pour la suite)

## 8/ Préparer le projet backend et la base de donnée

- Dans le projet /backend créer un fichier application.properties
- En se basant sur les informations contenus dans application-example.properties entrer les informations nécessaire pour pouvoir relier son application backend avec la base de donnée
- Ouvrir PhpMyAdmin avec votre nom d'utilisateur et votre mot de passe pour y créer la base de donnée alterhub
- Revenir dans IntelliJ Idea
- Dans le menu File > Project Structure choisir la sdk: Oracle OpenJDK 17.0.2
- Dans le même menu mais pour Language Level, sélectionner version 17
- Cliquer sur Apply, puis sur OK pour fermer ce menu
- Dans le menu de maven (totem de droite) effectuer l'action clean (équivalent à mvn clean) puis l'action install (équivalent à mvn install) pour initialiser le projet

## 9/ Lancer l'application

- Lorsque les deux notices d'utilisation sont terminés et que tout fonctionne correctement, se diriger sur Visual Studio Code
- Ouvrir un terminal de commande interne à Visual Studio COde
- Effectuer l'instruction npm run start
- Cela aura pour effet de lancer de manière simultanée les deux projets et de rendre accessible l'application sur la route http://localhost:4200