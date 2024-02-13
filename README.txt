
#################################################### Instructions #################################################################
## Test technique - candidat Développeur Android

Ce test technique vise à évaluer vos compétences en développement Android, votre capacité à prendre des décisions éclairées et
à résoudre des problèmes de manière efficace dans un contexte de projet réel.

De plus, ce test vous offre la liberté de repenser l'architecture et d'adapter la pile technologique selon vos préférences et votre expertise.

L'application doit consister en 2 écrans:
- Un écran principal présentant un feed provenant d'un fichier JSON (déjà parsé dans le projet)
- Un écran de détail d'article

### Suivre les instructions suivantes :
* 1 : Utilisez le repo git;
* 2 : Implémenter le bouton du "Tri" pour que l'usager puisse trier par date ou par « channelName »;
* 3 : Ajouter la fonctionalité pour filtrer par « ChannelName » dans un autre écran;
* 4 : Afficher la liste des channelName d'article dans un écran;
* 5 : Afficher le contenu d'un article dans un deuxième écran lors du click(sur la droite pour les tablettes);
* 6 : Persister le choix de l'utilisateur de fontionnalités aux choix

Sur une base volontaire:

- L'application devrait supporter l'api 23 à 34;
- Implémenter des tests et une analyse statique du code;
- Utiliser la stack technologique de ton choix;
- Utiliser l'architecture de ton choix;
- Expliquer les hypothèses/choix pris si certains points ne sont pas faits. Un projet incomplet et propre sera toujours privilégié par rapport à un test complet mais avec des points non-fonctionnels.
###################################################################################################################################

#################################################### Présentation #################################################################

# 
L'application se compose de 2 écrans (+ 1 splashscreen)
- un 1er écran qui affiche un feed de titres d'articles
- un 2nd écran accessible après le clic sur un titre de la liste et qui détaille un article

L'application possède une feature de tri par date ou 'channel', en utilisant cette feature, le user peut trier les articles dans le feed.
Il y a aussi une feature de filtre de titres d'article selon un 'channel' choisi.
Les options de tri sont sauvegardé en local sur le device du user.

Dans le feed, on affiche le 'channel' de l'article, le gros titre, et la date de publication.
Dans les details, on affiche l'image de l'article, le gros titre, le 'channel', la date de publication, le contenu de l'article, un lien pour rediriger vers le site web, et la date de modification de l'article

# Stack technique


# Points d'évolution :
