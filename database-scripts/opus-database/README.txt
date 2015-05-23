Opus-database
22/05/2015

Auteur : Michael Fournier


INFORMATIONS GÉNÉRALES
---------------------------

Ce projet contient les scripts permettant de générer la base de données d'Opus et au besoin, de la populer de façon minimale afin que les applications Opus puissent être lancées. Le SGBD suggéré est PostgreSQL 9.4.1.

Le login role de base de données connecté lors de la création de tous les éléments présents dans les scripts de ce projet peut etre n'importe qui, en autant qu'il soit super user. De plus, un usager opus doit etre creer avant d'utiliser le script.


DÉMARRAGE
---------------------------

Créez une base de données. Le choix de son nom est à votre discrétion.

Vous devez être administrateur de cette base de données pour effectuer les étapes suivantes. L'usager postgres créé par défaut est administrateur.

Roulez les commandes en utilisant le bouton "execute pgScript" dans la console sql de la base de donnees selectionnee.