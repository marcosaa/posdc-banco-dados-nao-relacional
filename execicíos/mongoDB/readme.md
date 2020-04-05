# Exercício 1- Aquecendo com os pets

Insira os seguintes registros no MongoDB e em seguida responda as questões
abaixo:

``` shell
use petshop
db.pets.insert({name: "Mike", species: "Hamster"})
db.pets.insert({name: "Dolly", species: "Peixe"})
db.pets.insert({name: "Kilha", species: "Gato"})
db.pets.insert({name: "Mike", species: "Cachorro"})
db.pets.insert({name: "Sally", species: "Cachorro"})
db.pets.insert({name: "Chuck", species: "Gato"})
```

1 - Adicione outro Peixe e um Hamster com nome Frodo

``` shell
> db.pets.insert({name: "Nemo", species: "Peixe"})
WriteResult({ "nInserted" : 1 })
> db.pets.insert({name: "Frodo", species: "Hamster"})
WriteResult({ "nInserted" : 1 })
``` 

2 - Faça uma contagem dos pets na coleção

``` shell
> db.pets.find({}).count()
8
```

3 - Retorne apenas um elemento o método prático possível

``` shell
> db.pets.findOne();
{
        "_id" : ObjectId("5e8a40a483bd5b7430df3d4e"),
        "name" : "Mike",
        "species" : "Hamster"
}
```

4 - Identifique o ID para o Gato Kilha.
id: 5e8a43e1e033d13ae18b248d

``` shell
> db.pets.find({"name": "Kilha", "species": "Gato"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248d"), "name" : "Kilha", "species" : "Gato" }
```

5 - Faça uma busca pelo ID e traga o Hamster Mike

``` shell
> db.pets.find({"name": "Mike", "species": "Hamster"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
> db.pets.find({"_id": ObjectId("5e8a43e1e033d13ae18b248b")});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
```

6 - Use o find para trazer todos os Hamsters

``` shell
> db.pets.find({"species": "Hamster"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
{ "_id" : ObjectId("5e8a43efe033d13ae18b2492"), "name" : "Frodo", "species" : "Hamster" }
```

7 - Use o find para listar todos os pets com nome Mike

``` shell
> db.pets.find({"name": "Mike"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248e"), "name" : "Mike", "species" : "Cachorro" }
```

8 - Liste apenas o documento que é um Cachorro chamado Mike

``` shell
> db.pets.findOne({"name": "Mike", "species": "Cachorro"});
{
        "_id" : ObjectId("5e8a43e1e033d13ae18b248e"),
        "name" : "Mike",
        "species" : "Cachorro"
}
>  
```
