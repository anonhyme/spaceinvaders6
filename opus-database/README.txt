Opus-database
27/04/2015

Auteur : Pascale Maill�
Adresse courriel : pascale.maille@usherbrooke.ca


INFORMATIONS G�N�RALES
---------------------------

Ce projet contient les scripts permettant de g�n�rer la base de donn�es d'Opus et au besoin, de la populer de fa�on minimale afin que les applications Opus puissent �tre lanc�es. Le SGBD sugg�r� est PostgreSQL 9.4.1.

Le login role de base de donn�es connect� lors de la cr�ation de tous les �l�ments pr�sents dans les scripts de ce projet doit �tre appopus. Autrement, l'instruction de modification de la possession (ownership) pr�sente � la fin du script triggers.sql ne permettra pas de rendre le group role opus propri�taire de tous les �l�ments pr�c�demment cr��s.


D�MARRAGE
---------------------------

Cr�ez une base de donn�es. Le choix de son nom est � votre discr�tion.

Vous devez �tre administrateur de cette base de donn�es pour effectuer les �tapes suivantes. L'usager postgres cr�� par d�faut est administrateur.

D� aux d�pendances entre les �l�ments cr��s par les scripts suivants, il est recommand� de les lancer dans l'ordre suivant :
1. tables.sql
2. indexes.sql
3. constraints.sql
4. views.sql
5. functions.sql
6. views_with_dependencies_on_functions.sql
7. views_rules.sql
8. triggers.sql

Si n�cessaire, un exemple des insertions minimales n�cessaires afin que les applications Opus puissent �tre lanc�es est fourni. Assurez-vous de modifier les valeurs indiqu�es avant de lancer ce script :
9. insert_for_applications.sql