Opus-database
22/05/2015

Auteur : Michael Fournier


INFORMATIONS G�N�RALES
---------------------------------

Ce projet contient les scripts permettant de g�n�rer la base de donn�es d'Opus et au besoin, de la populer de fa�on minimale afin que les applications Opus puissent �tre lanc�es. Le SGBD sugg�r� est PostgreSQL 9.4.1.

Le login role de base de donn�es connect� lors de la cr�ation de tous les �l�ments pr�sents dans les scripts de ce projet peut etre n'importe qui, en autant qu'il soit super user. De plus, un usager opus doit etre creer avant d'utiliser le script.


D�MARRAGE
---------------------------------

Cr�ez une base de donn�es. Le choix de son nom est � votre discr�tion.

Vous devez �tre administrateur de cette base de donn�es pour effectuer les �tapes suivantes. L'usager postgres cr�� par d�faut est administrateur.

Ouvrez une console de commande sql sur la base de donnees cree pour opus. Copiez-y le script contenu dans "full_database_creation.sql" puis appuyez sur le bouton "execute pgScript".

Pour s'assurer que tout c'est bien deroule, copiez les resultats des Query et collez-les dans un editeur de text, puis faites une recherche sur la string suivante: [W
Si aucun element n'est trouve, le script c'est execute avec succes, sinon, contactez l'administrateur de la base de donnees.

Ensuite, faites de meme avec le script "populate_database.sql".


MANUALLY POPULATE DATABASE
---------------------------------