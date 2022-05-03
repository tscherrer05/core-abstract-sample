# Utilisation

Demander l'état du stock :

`curl -X GET "http://localhost:8080/shoes/stock" -H "version: 3"`

Ajouter 10 chaussures noires de taille 42 :

`curl -X PATCH "http://localhost:8080/shoes/stock" -H "version: 3" --header 'Content-type: application/json' -d '{ "size": 42, "color": "BLACK", "quantity": 10 }'`

Retirer 10 chaussures noires de taille 42 :

`curl -X PATCH "http://localhost:8080/shoes/stock" -H "version: 3" --header 'Content-type: application/json' -d '{ "size": 42, "color": "BLACK", "quantity": -10 }'`