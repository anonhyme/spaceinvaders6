Opus-database
27/04/2015

Auteur : Pascale Maillé
Adresse courriel : pascale.maille@usherbrooke.ca


INFORMATIONS GÉNÉRALES
---------------------------

Ce projet contient les scripts permettant de générer la base de données d'Opus et au besoin, de la populer de façon minimale afin que les applications Opus puissent être lancées. Le SGBD suggéré est PostgreSQL 9.4.1.

Le login role de base de données connecté lors de la création de tous les éléments présents dans les scripts de ce projet doit être appopus. Autrement, l'instruction de modification de la possession (ownership) présente à la fin du script triggers.sql ne permettra pas de rendre le group role opus propriétaire de tous les éléments précédemment créés.


DÉMARRAGE
---------------------------

Créez une base de données. Le choix de son nom est à votre discrétion.

Vous devez être administrateur de cette base de données pour effectuer les étapes suivantes. L'usager postgres créé par défaut est administrateur.

Dû aux dépendances entre les éléments créés par les scripts suivants, il est recommandé de les lancer dans l'ordre suivant :
1. tables.sql
2. indexes.sql
3. constraints.sql
4. views.sql
5. functions.sql
6. views_with_dependencies_on_functions.sql
7. views_rules.sql
8. triggers.sql

Si nécessaire, un exemple des insertions minimales nécessaires afin que les applications Opus puissent être lancées est fourni. Assurez-vous de modifier les valeurs indiquées avant de lancer ce script :
9. insert_for_applications.sql