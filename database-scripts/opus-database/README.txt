Opus-database
22/05/2015

Auteur : Michael Fournier


INFORMATIONS GÉNÉRALES
---------------------------------

Ce projet contient les scripts permettant de générer la base de données d'Opus et au besoin, de la populer de façon minimale afin que les applications Opus puissent être lancées. Le SGBD suggéré est PostgreSQL 9.4.1.

Le login role de base de données connecté lors de la création de tous les éléments présents dans les scripts de ce projet peut etre n'importe qui, en autant qu'il soit super user. De plus, un usager opus doit etre creer avant d'utiliser le script.


DÉMARRAGE
---------------------------------

Créez une base de données. Le choix de son nom est à votre discrétion.

Vous devez être administrateur de cette base de données pour effectuer les étapes suivantes. L'usager postgres créé par défaut est administrateur.

Ouvrez une console de commande sql sur la base de donnees cree pour opus. Copiez-y le script contenu dans "full_database_creation.sql" puis appuyez sur le bouton "execute pgScript".

Pour s'assurer que tout c'est bien deroule, copiez les resultats des Query et collez-les dans un editeur de text, puis faites une recherche sur la string suivante: [W
Si aucun element n'est trouve, le script c'est execute avec succes, sinon, contactez l'administrateur de la base de donnees.

Ensuite, faites de meme avec le script "populate_database.sql".


MANUALLY POPULATE DATABASE
---------------------------------