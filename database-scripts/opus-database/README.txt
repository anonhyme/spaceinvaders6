Opus-database
22/05/2015

Auteur : Michael Fournier


INFORMATIONS G�N�RALES
---------------------------

Ce projet contient les scripts permettant de g�n�rer la base de donn�es d'Opus et au besoin, de la populer de fa�on minimale afin que les applications Opus puissent �tre lanc�es. Le SGBD sugg�r� est PostgreSQL 9.4.1.

Le login role de base de donn�es connect� lors de la cr�ation de tous les �l�ments pr�sents dans les scripts de ce projet peut etre n'importe qui, en autant qu'il soit super user. De plus, un usager opus doit etre creer avant d'utiliser le script.


D�MARRAGE
---------------------------

Cr�ez une base de donn�es. Le choix de son nom est � votre discr�tion.

Vous devez �tre administrateur de cette base de donn�es pour effectuer les �tapes suivantes. L'usager postgres cr�� par d�faut est administrateur.

Roulez les commandes en utilisant le bouton "execute pgScript" dans la console sql de la base de donnees selectionnee.