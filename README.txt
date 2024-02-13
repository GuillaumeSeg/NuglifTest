
#################################################### Instructions 
#################################################################

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
##################################################################
##################################################################

#################################################### Présentation
#################################################################

# General
L'application se compose de 2 écrans (+ 1 splashscreen)
- un 1er écran qui affiche un feed de titres d'articles
- un 2nd écran accessible après le clic sur un titre de la liste et qui détaille un article

L'application possède une feature de tri par date ou 'channel', en utilisant cette feature, le user peut trier les articles dans le feed.
Il y a aussi une feature de filtre de titres d'article selon un 'channel' choisi.
Les options de tri sont sauvegardé en local sur le device du user.

Dans le feed, on affiche le 'channel' de l'article, le gros titre, et la date de publication.
Dans les details, on affiche l'image de l'article, le gros titre, le 'channel', la date de publication, le contenu de l'article, un lien pour rediriger vers le site web, et la date de modification de l'article

# Stack technique
J'ai choisi de faire l'application en full Jetpack Compose.
Mon choix d'architecture est une clean architecture avec MVVM pour la présentation.
# data
La partie data se comporte de 3 implementations de repositories
- FeedRepositoryImpl
- SettingsRepositoryImpl
- FireworksRepositoryImpl

Le FeedRepository gere les données du feed, toute la liste des articles, ainsi que les features de Tri et Filtre.
Il vient récupérer des données 'remote' gràce à un FeedRemoteDataSource, cette classe vient parser un fichier json de la liste de articles, représentant toutes les données à utiliser.
Pour le choix du parser, j'ai utiliser Moshi, j'ai ajouté un adapter pour parser les dates en LocalDateTime.

Le SettingsRepository gére la partie de sauvegarde des données utilisateurs pour les options de tri (ex: Tri par date / par 'channel')
Pour sauver les données en local, il utilise un classe SettingsLocalDataSource, qui va récupérer et sauver les données du user dans les SharedPreferences de l'app.

Le FireworksRepository gére le trigger pour indiquer le moment où la réponse à La grande question sur la vie, l'Univers et le reste est obtenue (42).

# domain
dans la partie domain, je structure mes models de données et je définie les interfaces de mes repositories.

# presentation
dans la couche présentation, j'ai implémenté la navigation avec un NavHost et une navigation avec 2 composables FeedView et DetailsView
pour naviguer j'utilise des NavigationCommand avec une destination en String. le NavigationManager gère l'emition de la NavigationCommand, et aussi le popBack.
Dans la MainActivity, on écoute les changements d'une nouvelle destination et on navigate avec le navController.

_ FeedView affiche la liste des articles, la view a son viewModel FeedViewModel pour gérer toute la logique de la page liée à l'affichage des articles et des fonctionnalités de trie et de filtre.
_ DetailsView affiche le detail d'un article, pareil elle a son viewModel DetailsViewModel. Pour afficher l'image depuis une url, j'utilise Coil avec un composable AsyncImage.

Pour la partie theming,
J'ai créé un theme NugTheme, je set ensuite les LocalReplacementTypography et LocalAppThemeColors dans un CompositionLocalProvider, cela va fournir mes couleurs et textes du themes pour mes pages FeedView et DetailsView.
On peut imaginer de changer les couleurs fournies selon un thème Dark ou un thème Light.


Pour l'injection de dépendances, j'utilise dagger avec hilt.

L'application supporte les API android de 23 à 34

# Tests
Pour la partie Test, 
j'ai choisi de tester FeedRepositoryImpl.
il y a 4 tests a executer, ils vont tester la feature de tri par 'channel', de tri par date et de filtre par un 'channel' ou de filtre par ALL.

# Points d'évolution :
