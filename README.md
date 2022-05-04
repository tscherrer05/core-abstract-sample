# Utilisation

Pour démarrer l'API :

`mvn clean package && java -jar controller/target/controller-1.0.jar`

Demander l'état du stock :

`curl -X GET "http://localhost:8080/shoes/stock" -H "version: 3"`

Ajouter 10 chaussures noires de taille 42 :

`curl -X PATCH "http://localhost:8080/shoes/stock" -H "version: 3" --header 'Content-type: application/json' -d '{ "size": 42, "color": "BLACK", "quantity": 10 }'`

Retirer 10 chaussures noires de taille 42 :

`curl -X PATCH "http://localhost:8080/shoes/stock" -H "version: 3" --header 'Content-type: application/json' -d '{ "size": 42, "color": "BLACK", "quantity": -10 }'`

# Architecture

## Persistance de données
Le nouveau Core est situé dans le projet core-shoe-shop. Il embarque une base de données HSQL. Pour les besoins de l'exercice, elle est initialisée et seedée à chaque lancement de l'application.

L'interface DatabaseAdapter permet de découpler le code d'accès aux données de la logique métier, en plus de pouvoir être mockée dans les tests automatiques.

## Pattern facade
Les core-legacy et core-new exposaient l'interface ShoeCore. Le controller récupérait une implémentation de cette interface de cette manière : `shoeFacade.get(version)`

Le nouveau core-shoe-shop expose une nouvelle interface ShoeShopCore. J'ai donc générifié la facade pour qu'elle puisse prendre en compte les deux types interfaces (et dans le futur, d'autres interfaces potentielles) : `commonShoeFacade.<ShoeCore>get(version)`

La nouvelle facade s'apparente d'ailleurs à un service locator.

## Tests automatiques

J'ai opté pour des tests "subcutanés", c'est à dire qui consomment la facade et non les contrôleurs.

# Améliorations possibles

- Utiliser un ORM pour générer les requêtes SQL
- Automatiser les tests du EmbeddedDatabaseAdapter
- Factoriser le code des façades
- Meilleur retours de l’API en cas d’erreur (limite atteinte, stock épuisé)
- Validation input des paramètres de l’API