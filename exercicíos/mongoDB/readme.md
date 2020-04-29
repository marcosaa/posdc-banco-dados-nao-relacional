## Exercícios sobre MongoDB


## Exercício 1- Aquecendo com os pets

Insira os seguintes registros no MongoDB e em seguida responda as questões
abaixo:

```shell script
use petshop
db.pets.insert({name: "Mike", species: "Hamster"})
db.pets.insert({name: "Dolly", species: "Peixe"})
db.pets.insert({name: "Kilha", species: "Gato"})
db.pets.insert({name: "Mike", species: "Cachorro"})
db.pets.insert({name: "Sally", species: "Cachorro"})
db.pets.insert({name: "Chuck", species: "Gato"})
```

1 - Adicione outro Peixe e um Hamster com nome Frodo

```shell script
> db.pets.insert({name: "Nemo", species: "Peixe"})
WriteResult({ "nInserted" : 1 })
> db.pets.insert({name: "Frodo", species: "Hamster"})
WriteResult({ "nInserted" : 1 })
``` 

2 - Faça uma contagem dos pets na coleção

```shell script
> db.pets.find({}).count()
8
```

3 - Retorne apenas um elemento o método prático possível

```shell script
> db.pets.findOne();
{
        "_id" : ObjectId("5e8a40a483bd5b7430df3d4e"),
        "name" : "Mike",
        "species" : "Hamster"
}
```

4 - Identifique o ID para o Gato Kilha.
id: 5e8a43e1e033d13ae18b248d

```shell script
> db.pets.find({"name": "Kilha", "species": "Gato"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248d"), "name" : "Kilha", "species" : "Gato" }
```

5 - Faça uma busca pelo ID e traga o Hamster Mike

```shell script
> db.pets.find({"name": "Mike", "species": "Hamster"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
> db.pets.find({"_id": ObjectId("5e8a43e1e033d13ae18b248b")});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
```

6 - Use o find para trazer todos os Hamsters

```shell script
> db.pets.find({"species": "Hamster"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
{ "_id" : ObjectId("5e8a43efe033d13ae18b2492"), "name" : "Frodo", "species" : "Hamster" }
```

7 - Use o find para listar todos os pets com nome Mike

```shell script
> db.pets.find({"name": "Mike"});
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248b"), "name" : "Mike", "species" : "Hamster" }
{ "_id" : ObjectId("5e8a43e1e033d13ae18b248e"), "name" : "Mike", "species" : "Cachorro" }
```

8 - Liste apenas o documento que é um Cachorro chamado Mike

```shell script
> db.pets.findOne({"name": "Mike", "species": "Cachorro"});
{
        "_id" : ObjectId("5e8a43e1e033d13ae18b248e"),
        "name" : "Mike",
        "species" : "Cachorro"
}
>  
```


## Exercício 2 – Mama mia!
Importe o arquivo dos italian-people.js do seguinte endereço: Downloads NoSQL
FURB. Em seguida, importe o mesmo com o seguinte comando:
mongo italian-people.js

Analise um pouco a estrutura dos dados e em seguida responda:

1. Liste/Conte todas as pessoas que tem exatamente 99 anos. Você pode
usar um count para indicar a quantidade.

```shell script
> db.italians.find({"age":99})
> db.italians.find({"age":99}).count()
0
```

2. Identifique quantas pessoas são elegíveis atendimento prioritário
(pessoas com mais de 65 anos)

```shell script
> db.italians.find({"age" : {"$gte" : 65}}).count()
5214
```


3. Identifique todos os jovens (pessoas entre 12 a 18 anos).

```shell script
> db.italians.find({"age" : {"$gte" : 12, "$lte" : 18}}).count()
2502
```


4. Identifique quantas pessoas tem gatos, quantas tem cachorro e quantas
não tem nenhum dos dois

```shell script
> db.italians.find({"cat" : { "$exists" : true} }).count()
16994
> db.italians.find({"dog" : { "$exists" : true} }).count()
11411
> db.italians.find({"dog" : { "$exists" : false}, "cat" : { "$exists" : false} }).count()
6774
```


5. Liste/Conte todas as pessoas acima de 60 anos que tenham gato

```shell script
> db.italians.find({"age" : { "$gt" : 60}, "cat" : { "$exists" : true} }).count()
3938
```


6. Liste/Conte todos os jovens com cachorro

```shell script
> db.italians.find({"age" : { "$gte" : 14, "$lte": 29 }, "dog" : { "$exists" : true} }).count()
2259
```


7. Utilizando o $where, liste todas as pessoas que tem gato e cachorro

```shell script
> db.italians.find({ "$where": "this.dog != null && this.cat != null"}).count()
6906
```


8. Liste todas as pessoas mais novas que seus respectivos gatos.

```shell script
> db.italians.find({ "$and" : [ {"cat": {$exists: true}}, {"$where" : "this.age < this.cat.age" } ]}).count()
1887
```


9. Liste as pessoas que tem o mesmo nome que seu bichano (gatou ou cachorro)

```shell script
> db.italians.findOne( { "$or" :[ { "$and" : [{"cat": { "$exists": true}}, {"$where" : "this.name == this.cat.name" }]},  { "$and" : [{"dog": { "$exists": true}}, {"$where" : "this.name == this.dog.name" }]} ]})
null
```


10. Projete apenas o nome e sobrenome das pessoas com tipo de sangue de fator RH negativo

```shell script
> db.italians.find({"bloodType" : /-/i}, {"firstname": 1,"surname": 1, "_id": 0})
{ "firstname" : "Sara", "surname" : "Mancini" }
{ "firstname" : "Maurizio", "surname" : "Messina" }
{ "firstname" : "Emanuele", "surname" : "Rinaldi" }
{ "firstname" : "Patrizia", "surname" : "D’Amico" }
{ "firstname" : "Mario", "surname" : "Lombardo" }
{ "firstname" : "Daniele", "surname" : "Marini" }
{ "firstname" : "Tiziana", "surname" : "Ferri" }
{ "firstname" : "Angela", "surname" : "Ferrari" }
{ "firstname" : "Davide", "surname" : "Caputo" }
{ "firstname" : "Antonio", "surname" : "Marchetti" }
{ "firstname" : "Tiziana", "surname" : "Ferretti" }
{ "firstname" : "Dario", "surname" : "Coppola" }
{ "firstname" : "Filipo", "surname" : "Ferraro" }
{ "firstname" : "Sabrina", "surname" : "Greco" }
{ "firstname" : "Daniela", "surname" : "Serra" }
{ "firstname" : "Claudio", "surname" : "Colombo" }
{ "firstname" : "Giorgio", "surname" : "Rinaldi" }
{ "firstname" : "Teresa", "surname" : "Leone" }
{ "firstname" : "Claudio", "surname" : "Marino" }
{ "firstname" : "Gianni", "surname" : "Riva" }
Type "it" for more
```


11. Projete apenas os animais dos italianos. Devem ser listados os animais com nome e idade. 
Não mostre o identificado do mongo (ObjectId)

```shell script
> db.italians.find({"$or" :[{"cat": { "$exists": true}},{"dog": { "$exists": true}}]}, {"cat.name":1, "dog.name":1, "_id":0 })
{ "cat" : { "name" : "Daniela" }, "dog" : { "name" : "Alberto" } }
{ "dog" : { "name" : "Cristina" } }
{ "cat" : { "name" : "Paola" } }
{ "cat" : { "name" : "Vincenzo" } }
{ "cat" : { "name" : "Fabio" }, "dog" : { "name" : "Antonio" } }
{ "cat" : { "name" : "Rita" } }
{ "cat" : { "name" : "Antonella" } }
{ "cat" : { "name" : "Marta" }, "dog" : { "name" : "Gianni" } }
{ "cat" : { "name" : "Mauro" }, "dog" : { "name" : "Dario" } }
{ "cat" : { "name" : "Barbara" } }
{ "dog" : { "name" : "Gianni" } }
{ "dog" : { "name" : "Laura" } }
{ "cat" : { "name" : "Giusy" } }
{ "cat" : { "name" : "Vincenzo" } }
{ "dog" : { "name" : "Fabio" } }
{ "cat" : { "name" : "Elena" } }
{ "cat" : { "name" : "Stefano" } }
{ "cat" : { "name" : "Antonella" }, "dog" : { "name" : "Simone" } }
{ "cat" : { "name" : "Matteo" } }
{ "dog" : { "name" : "Lorenzo" } }
Type "it" for more
```


12. Quais são as 5 pessoas mais velhas com sobrenome Rossi?

```shell script
> db.italians.find({ "surname": "Rossi"}).sort({ "age": -1}).limit(5)
{ "_id" : ObjectId("5e8a4ca9c9d7b9bfa2536eef"), "firstname" : "Stefano", "surname" : "Rossi", "username" : "user3580", "age" : 79, "email" : "Stefano.Rossi@uol.com.br", "bloodType" : "B+", "id_num" : "801060025566", "registerDate" : ISODate("2018-10-21T06:38:06.120Z"), "ticketNumber" : 5721, "jobs" : [ "Ciências Sociais e Humanas", "Gestão de Recursos Humanos" ], "favFruits" : [ "Tangerina", "Mamão", "Uva" ], "movies" : [ { "title" : "O Resgate do Soldado Ryan (1998)", "rating" : 3.93 }, { "title" : "Seven: Os Sete Crimes Capitais (1995)", "rating" : 2.35 }, { "title" : "O Resgate do Soldado Ryan (1998)", "rating" : 2.22 } ], "cat" : { "name" : "Daniele", "age" : 17 }, "dog" : { "name" : "Sonia", "age" : 0 } }
{ "_id" : ObjectId("5e8a4cb3c9d7b9bfa2537ef3"), "firstname" : "Valeira", "surname" : "Rossi", "username" : "user7680", "age" : 79, "email" : "Valeira.Rossi@uol.com.br", "bloodType" : "AB-", "id_num" : "084563063048", "registerDate" : ISODate("2018-08-10T18:55:36.610Z"), "ticketNumber" : 5760, "jobs" : [ "Psicologia" ], "favFruits" : [ "Banana", "Goiaba", "Pêssego" ], "movies" : [ { "title" : "Matrix (1999)", "rating" : 3.6 }, { "title" : "Star Wars, Episódio V: O Império Contra-Ataca (1980)", "rating" : 1.58 } ] }
{ "_id" : ObjectId("5e8a4cd3bb206c9231199037"), "firstname" : "Elisabetta", "surname" : "Rossi", "username" : "user4728", "age" : 79, "email" : "Elisabetta.Rossi@hotmail.com", "bloodType" : "A+", "id_num" : "070152032267", "registerDate" : ISODate("2014-08-21T08:49:41.378Z"), "ticketNumber" : 3626, "jobs" : [ "Construção Civil", "Farmácia" ], "favFruits" : [ "Tangerina", "Tangerina" ], "movies" : [ { "title" : "A Viagem de Chihiro (2001)", "rating" : 0.4 }, { "title" : "Gladiador (2000)", "rating" : 1.74 }, { "title" : "O Senhor dos Anéis: As Duas Torres (2002)", "rating" : 0.79 }, { "title" : "O Poderoso Chefão II (1974)", "rating" : 4.35 } ], "dog" : { "name" : "Alessandra", "age" : 7 } }
{ "_id" : ObjectId("5e8a4a00e9112505eb8cc705"), "firstname" : "Monica", "surname" : "Rossi", "username" : "user1782", "age" : 78, "email" : "Monica.Rossi@outlook.com", "bloodType" : "O-", "id_num" : "863318766101", "registerDate" : ISODate("2008-09-21T00:40:00.724Z"), "ticketNumber" : 7254, "jobs" : [ "Eventos" ], "favFruits" : [ "Uva", "Melancia" ], "movies" : [ { "title" : "Star Wars, Episódio V: O Império Contra-Ataca (1980)", "rating" : 4.69 } ] }
{ "_id" : ObjectId("5e8a4ca2c9d7b9bfa2536409"), "firstname" : "Maurizio", "surname" : "Rossi", "username" : "user790", "age" : 78, "email" : "Maurizio.Rossi@outlook.com", "bloodType" : "B-", "id_num" : "486404162370", "registerDate" : ISODate("2008-09-05T10:14:16.745Z"), "ticketNumber" : 9185, "jobs" : [ "Ciências Naturais e Exatas" ], "favFruits" : [ "Maçã" ], "movies" : [ { "title" : "Clube da Luta (1999)", "rating" : 3.05 }, { "title" : "A Felicidade Não se Compra (1946)", "rating" : 1.96 } ], "cat" : { "name" : "Domenico", "age" : 11 }, "dog" : { "name" : "Roberta", "age" : 8 } }
>
```


13. Crie um italiano que tenha um leão como animal de estimação. Associe um nome e idade ao bichano

```shell script
> db.italians.insert({ "firstname" : "Josepe", "surname" : "Matiello", "username" : "Josepe0007", "age" : 50, "email" : "josepe.matiello@hotmail.com", "bloodType" : "A-", "id_num" : "123475327074", "registerDate" : ISODate("2011-02-04T05:04:49.828Z"), "ticketNumber" : 6118, "jobs" : [ "Estudos de Gênero e Diversidade" ], "favFruits" : [ "Laranja", "Uva" ], "movies" : [ { "title" : "Interestelar (2014)", "rating" : 2.02 } ], "leao" : { "name" : "Lion", "age": 7} })
WriteResult({ "nInserted" : 1 })
```


14. Infelizmente o Leão comeu o italiano. Remova essa pessoa usando o Id.

```shell script
> db.italians.insert({ "firstname" : "Josepe", "surname" : "Matiello", "username" : "Josepe0007", "age" : 50, "email" : "josepe.matiello@hotmail.com", "bloodType" : "A-", "id_num" : "123475327074", "registerDate" : ISODate("2011-02-04T05:04:49.828Z"), "ticketNumber" : 6118, "jobs" : [ "Estudos de Gênero e Diversidade" ], "favFruits" : [ "Laranja", "Uva" ], "movies" : [ { "title" : "Interestelar (2014)", "rating" : 2.02 } ], "leao" : { "name" : "Lion", "age": 7} })
WriteResult({ "nInserted" : 1 })
> db.italians.find({"leao": {"$exists" : true}})
{ "_id" : ObjectId("5e8a6713169550b659113b06"), "firstname" : "Josepe", "surname" : "Matiello", "username" : "Josepe0007", "age" : 50, "email" : "josepe.matiello@hotmail.com", "bloodType" : "A-", "id_num" : "123475327074", "registerDate" : ISODate("2011-02-04T05:04:49.828Z"), "ticketNumber" : 6118, "jobs" : [ "Estudos de Gênero e Diversidade" ], "favFruits" : [ "Laranja", "Uva" ], "movies" : [ { "title" : "Interestelar (2014)", "rating" : 2.02 } ], "leao" : { "name" : "Lion", "age" : 7 } }
> db.italians.remove({_id : ObjectId("5e8a6713169550b659113b06")})
WriteResult({ "nRemoved" : 1 })
> db.italians.find({"leao": {"$exists" : true}}).count()
0
```


15. Passou um ano. Atualize a idade de todos os italianos e dos bichanos em 1.


```shell script
> db.italians.update({}, { "$inc" : { "age": 1 }}, {multi: true})
WriteResult({ "nMatched" : 28078, "nUpserted" : 0, "nModified" : 28078 })
> db.italians.update({"cat" : {"$exists" : true}}, { "$inc" : { "cat.age": 1 }}, {multi: true})
WriteResult({ "nMatched" : 16799, "nUpserted" : 0, "nModified" : 16799 })
> db.italians.update({"dog" : {"$exists" : true}}, { "$inc" : { "dog.age": 1 }}, {multi: true})
WriteResult({ "nMatched" : 11329, "nUpserted" : 0, "nModified" : 11329 })
```


16. O Corona Vírus chegou na Itália e misteriosamente atingiu pessoas somente com gatos e de 66 anos. Remova esses italianos.

```shell script
> db.italians.remove({ "$and" :[{"cat" : {"$exists" : true}}, { age :  66}]})
WriteResult({ "nRemoved" : 195 })
```


17. Utilizando o framework agregate, liste apenas as pessoas com nomes iguais a sua respectiva mãe e que tenha gato ou cachorro.


```shell script

> db.italians.aggregate(
... [ { $match : { "$or" :[{"cat" : {"$exists" : true}},{"cat" : {"$exists" : true}}]}},
... {'$project': {"firstname": 1,"mother": 1,"isEqual": { "$cmp": ["$firstname","$mother.firstname"]}}},
... {'$match': {"isEqual": 0}}
... ])
{ "_id" : ObjectId("5e8a4a01e9112505eb8cc92f"), "firstname" : "Angelo", "mother" : { "firstname" : "Angelo", "surname" : "Negri", "age" : 29 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4a01e9112505eb8cc9b0"), "firstname" : "Barbara", "mother" : { "firstname" : "Barbara", "surname" : "Negri", "age" : 75 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4a01e9112505eb8cc9b4"), "firstname" : "Alberto", "mother" : { "firstname" : "Alberto", "surname" : "Monti", "age" : 76 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4a02e9112505eb8cca70"), "firstname" : "Michela", "mother" : { "firstname" : "Michela", "surname" : "Ruggiero", "age" : 60 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4a02e9112505eb8ccad1"), "firstname" : "Gabiele", "mother" : { "firstname" : "Gabiele", "surname" : "Giuliani", "age" : 87 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4a06e9112505eb8cd2b2"), "firstname" : "Mirko", "mother" : { "firstname" : "Mirko", "surname" : "Mariani", "age" : 39 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4a0ee9112505eb8cdfa7"), "firstname" : "Filipo", "mother" : { "firstname" : "Filipo", "surname" : "De Angelis", "age" : 20 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4a0fe9112505eb8ce2ed"), "firstname" : "Giusy", "mother" : { "firstname" : "Giusy", "surname" : "Vitale", "age" : 47 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4ca3c9d7b9bfa2536534"), "firstname" : "Paolo", "mother" : { "firstname" : "Paolo", "surname" : "Palumbo", "age" : 96 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4ca4c9d7b9bfa2536717"), "firstname" : "Angela", "mother" : { "firstname" : "Angela", "surname" : "Neri", "age" : 37 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4caac9d7b9bfa2536f3b"), "firstname" : "Chiara", "mother" : { "firstname" : "Chiara", "surname" : "Bellini", "age" : 38 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4cb1c9d7b9bfa2537bd6"), "firstname" : "Gianni", "mother" : { "firstname" : "Gianni", "surname" : "Santoro", "age" : 87 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4cb3c9d7b9bfa2537e41"), "firstname" : "Massimiliano", "mother" : { "firstname" : "Massimiliano", "surname" : "De Rosa", "age" : 83 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4ccdbb206c9231198366"), "firstname" : "Cinzia", "mother" : { "firstname" : "Cinzia", "surname" : "Montanari", "age" : 82 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4ccebb206c92311985b3"), "firstname" : "Gianluca", "mother" : { "firstname" : "Gianluca", "surname" : "Palumbo", "age" : 56 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4cd0bb206c92311989cf"), "firstname" : "Giorgia", "mother" : { "firstname" : "Giorgia", "surname" : "Longo", "age" : 56 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4cd0bb206c92311989dc"), "firstname" : "Cristina", "mother" : { "firstname" : "Cristina", "surname" : "Barbieri", "age" : 58 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4cd1bb206c9231198a17"), "firstname" : "Angela", "mother" : { "firstname" : "Angela", "surname" : "Ferrara", "age" : 73 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4cd2bb206c9231198ce2"), "firstname" : "Federico", "mother" : { "firstname" : "Federico", "surname" : "Pagano", "age" : 71 }, "isEqual" : 0 }
{ "_id" : ObjectId("5e8a4cd7bb206c92311997ed"), "firstname" : "Dario", "mother" : { "firstname" : "Dario", "surname" : "Sala", "age" : 44 }, "isEqual" : 0 }
Type "it" for more



```

18. Utilizando aggregate framework, faça uma lista de nomes única de nomes. Faça isso usando apenas o primeiro nome

```shell script
> db.italians.aggregate( [ { $group : { _id : "$firstname" } } ] )
{ "_id" : "Pietro" }
{ "_id" : "Dario" }
{ "_id" : "Massimiliano" }
{ "_id" : "Giovanni" }
{ "_id" : "Carlo" }
{ "_id" : "Ilaria" }
{ "_id" : "Alex" }
{ "_id" : "Laura" }
{ "_id" : "Raffaele" }
{ "_id" : "Enzo " }
{ "_id" : "Paolo" }
{ "_id" : "Pasquale" }
{ "_id" : "Luigi" }
{ "_id" : "Federica" }
{ "_id" : "Daniele" }
{ "_id" : "Fabio" }
{ "_id" : "Michele" }
{ "_id" : "Valentina" }
{ "_id" : "Vincenzo" }
{ "_id" : "Giusy" }
Type "it" for more
```


19. Agora faça a mesma lista do item acima, considerando nome completo.


```shell script
> db.italians.aggregate( [ { $group : { _id : {"firstName": "$firstname", "surname": "$surname" } }} ] )
{ "_id" : { "firstName" : "Andrea", "surname" : "Greco" } }
{ "_id" : { "firstName" : "Rosa", "surname" : "De Santis" } }
{ "_id" : { "firstName" : "Paolo", "surname" : "Santoro" } }
{ "_id" : { "firstName" : "Mattia", "surname" : "Fontana" } }
{ "_id" : { "firstName" : "Enrico", "surname" : "Lombardi" } }
{ "_id" : { "firstName" : "Serena", "surname" : "De Angelis" } }
{ "_id" : { "firstName" : "Rita", "surname" : "Grasso" } }
{ "_id" : { "firstName" : "Barbara", "surname" : "Villa" } }
{ "_id" : { "firstName" : "Pasquale", "surname" : "Rossetti" } }
{ "_id" : { "firstName" : "Elisa", "surname" : "Mazza" } }
{ "_id" : { "firstName" : "Maria", "surname" : "Fontana" } }
{ "_id" : { "firstName" : "Davide", "surname" : "Bernardi" } }
{ "_id" : { "firstName" : "Massimiliano", "surname" : "Basile" } }
{ "_id" : { "firstName" : "Giulia", "surname" : "D’Amico" } }
{ "_id" : { "firstName" : "Domenico", "surname" : "Longo" } }
{ "_id" : { "firstName" : "Mauro", "surname" : "Parisi" } }
{ "_id" : { "firstName" : "Elisabetta", "surname" : "De Santis" } }
{ "_id" : { "firstName" : "Massimo", "surname" : "Martino" } }
{ "_id" : { "firstName" : "Alessandro", "surname" : "Coppola" } }
{ "_id" : { "firstName" : "Lucia", "surname" : "Santoro" } }
Type "it" for more
```


20. Procure pessoas que gosta de Banana ou Maçã, tenham cachorro ou gato, mais de 20 e menos de 60 anos.

```shell script
> db.italians.aggregate([ { $match :  { "$and" : [ 
{ "favFruits": {"$in" : ["Maça","Banana"]} },
{ "$or" : [{"dog" : { "$exists" : false}},{"cat" : { "$exists" : false}}]},
{ "age" : {"$lte" : 60, "$gte" : 20}}] 
}}] )
{ "_id" : ObjectId("5e8a49fce9112505eb8cc078"), "firstname" : "Mario", "surname" : "Lombardo", "username" : "user105", "age" : 44, "email" : "Mario.Lombardo@hotmail.com", "bloodType" : "A-", "id_num" : "518273038610", "registerDate" : ISODate("2014-04-12T14:00:33.421Z"), "ticketNumber" : 2136, "jobs" : [ "Gestão de Recursos Humanos" ], "favFruits" : [ "Banana", "Tangerina" ], "movies" : [ { "title" : "Cidade de Deus (2002)", "rating" : 0.67 }, { "title" : "O Silêncio dos Inocentes (1991)", "rating" : 3.99 } ], "father" : { "firstname" : "Nicola", "surname" : "Lombardo", "age" : 69 }, "cat" : { "name" : "Paola", "age" : 10 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0b5"), "firstname" : "Sara", "surname" : "Fontana", "username" : "user166", "age" : 50, "email" : "Sara.Fontana@hotmail.com", "bloodType" : "O-", "id_num" : "368516844446", "registerDate" : ISODate("2015-12-05T21:07:34.163Z"), "ticketNumber" : 3996, "jobs" : [ "Fotografia", "Marketing" ], "favFruits" : [ "Banana", "Melancia", "Mamão" ], "movies" : [ { "title" : "O Resgate do Soldado Ryan (1998)", "rating" : 0.44 }, { "title" : "Os Sete Samurais (1954)", "rating" : 3.17 }, { "title" : "A Viagem de Chihiro (2001)", "rating" : 2.78 }, { "title" : "O Silêncio dos Inocentes (1991)", "rating" : 0.3 }, { "title" : "Harakiri (1962)", "rating" : 4.38 } ], "dog" : { "name" : "Roberta", "age" : 11 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0ce"), "firstname" : "Laura", "surname" : "Barbieri", "username" : "user191", "age" : 28, "email" : "Laura.Barbieri@outlook.com", "bloodType" : "AB-", "id_num" : "871562736656", "registerDate" : ISODate("2018-09-18T23:24:09.230Z"), "ticketNumber" : 9808, "jobs" : [ "Odontologia", "Informática Biomédica" ], "favFruits" : [ "Banana", "Mamão", "Uva" ], "movies" : [ { "title" : "Um Sonho de Liberdade (1994)", "rating" : 4.38 }, { "title" : "Matrix (1999)", "rating" : 0.67 } ], "cat" : { "name" : "Mauro", "age" : 3 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0d3"), "firstname" : "Elisa", "surname" : "Moretti", "username" : "user196", "age" : 48, "email" : "Elisa.Moretti@yahoo.com", "bloodType" : "A+", "id_num" : "767087446303", "registerDate" : ISODate("2016-04-02T10:40:27.958Z"), "ticketNumber" : 9187, "jobs" : [ "Gestão Desportiva e de Lazer", "Geoprocessamento" ], "favFruits" : [ "Melancia", "Banana", "Laranja" ], "movies" : [ { "title" : "Parasita (2019)", "rating" : 3.44 } ], "cat" : { "name" : "Mirko", "age" : 14 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0d6"), "firstname" : "Gabiele", "surname" : "Fiore", "username" : "user199", "age" : 33, "email" : "Gabiele.Fiore@gmail.com", "bloodType" : "O-", "id_num" : "168364261622", "registerDate" : ISODate("2012-10-16T18:22:43.019Z"), "ticketNumber" : 6861, "jobs" : [ "Biblioteconomia" ], "favFruits" : [ "Banana" ], "movies" : [ { "title" : "Parasita (2019)", "rating" : 1.27 }, { "title" : "Os Bons Companheiros (1990)", "rating" : 2.07 }, { "title" : "Os Bons Companheiros (1990)", "rating" : 1.18 }, { "title" : "O Senhor dos Anéis: As Duas Torres (2002)", "rating" : 4.03 }, { "title" : "Os Sete Samurais (1954)", "rating" : 4.19 } ], "dog" : { "name" : "Simone", "age" : 7 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0d8"), "firstname" : "Alessandra", "surname" : "Mazza", "username" : "user201", "age" : 28, "email" : "Alessandra.Mazza@live.com", "bloodType" : "A+", "id_num" : "361322780857", "registerDate" : ISODate("2008-08-28T09:44:27.840Z"), "ticketNumber" : 2493, "jobs" : [ "Jornalismo", "Engenharia Ambiental e Sanitária" ], "favFruits" : [ "Banana" ], "movies" : [ { "title" : "A Vida é Bela (1997)", "rating" : 4.06 } ] }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0de"), "firstname" : "Angelo", "surname" : "Martinelli", "username" : "user207", "age" : 57, "email" : "Angelo.Martinelli@outlook.com", "bloodType" : "B+", "id_num" : "538110463478", "registerDate" : ISODate("2010-12-08T06:30:50.276Z"), "ticketNumber" : 5182, "jobs" : [ "Engenharia de Alimentos", "Sistemas Embarcados" ], "favFruits" : [ "Banana" ], "movies" : [ { "title" : "Gladiador (2000)", "rating" : 4.26 }, { "title" : "12 Homens e uma Sentença (1957)", "rating" : 4.77 }, { "title" : "Cidade de Deus (2002)", "rating" : 3.65 }, { "title" : "Clube da Luta (1999)", "rating" : 0.66 } ], "dog" : { "name" : "Elisa", "age" : 18 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0e3"), "firstname" : "Gianni", "surname" : "Morelli", "username" : "user212", "age" : 46, "email" : "Gianni.Morelli@yahoo.com", "bloodType" : "AB-", "id_num" : "513036270348", "registerDate" : ISODate("2014-02-24T19:08:18.256Z"), "ticketNumber" : 2157, "jobs" : [ "Agronegócios e Agropecuária" ], "favFruits" : [ "Melancia", "Tangerina", "Banana" ], "movies" : [ { "title" : "Um Sonho de Liberdade (1994)", "rating" : 4.84 }, { "title" : "Forrest Gump: O Contador de Histórias (1994)", "rating" : 2.78 }, { "title" : "Os Bons Companheiros (1990)", "rating" : 4.37 }, { "title" : "À Espera de um Milagre (1999)", "rating" : 2.83 }, { "title" : "O Senhor dos Anéis: As Duas Torres (2002)", "rating" : 1.54 } ], "cat" : { "name" : "Giusy", "age" : 2 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0ee"), "firstname" : "Fabio", "surname" : "Romano", "username" : "user223", "age" : 26, "email" : "Fabio.Romano@outlook.com", "bloodType" : "B+", "id_num" : "848722481652", "registerDate" : ISODate("2017-04-30T21:42:48.135Z"), "ticketNumber" : 1083, "jobs" : [ "Gestão Pública", "Engenharia de Software" ], "favFruits" : [ "Tangerina", "Banana", "Goiaba" ], "movies" : [ { "title" : "Star Wars, Episódio V: O Império Contra-Ataca (1980)", "rating" : 3.81 } ], "dog" : { "name" : "Massimiliano", "age" : 16 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0f0"), "firstname" : "Angela", "surname" : "Lombardi", "username" : "user225", "age" : 32, "email" : "Angela.Lombardi@live.com", "bloodType" : "O+", "id_num" : "816757150172", "registerDate" : ISODate("2020-02-22T19:53:28.173Z"), "ticketNumber" : 5381, "jobs" : [ "Estudos de Gênero e Diversidade", "Produção de Bebidas" ], "favFruits" : [ "Banana" ], "movies" : [ { "title" : "Batman: O Cavaleiro das Trevas (2008)", "rating" : 4.5 }, { "title" : "12 Homens e uma Sentença (1957)", "rating" : 1.79 } ], "cat" : { "name" : "Patrizia", "age" : 2 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc0ff"), "firstname" : "Gabiele", "surname" : "Palumbo", "username" : "user240", "age" : 20, "email" : "Gabiele.Palumbo@uol.com.br", "bloodType" : "AB+", "id_num" : "006222486075", "registerDate" : ISODate("2009-04-10T03:03:32.410Z"), "ticketNumber" : 174, "jobs" : [ "Engenharia Civil", "Geografia" ], "favFruits" : [ "Melancia", "Banana" ], "movies" : [ { "title" : "A Viagem de Chihiro (2001)", "rating" : 1.29 }, { "title" : "A Vida é Bela (1997)", "rating" : 4.19 } ], "cat" : { "name" : "Mario", "age" : 14 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc10d"), "firstname" : "Michela", "surname" : "De Rosa", "username" : "user254", "age" : 32, "email" : "Michela.De Rosa@live.com", "bloodType" : "O-", "id_num" : "743483756772", "registerDate" : ISODate("2010-10-25T03:48:43.889Z"), "ticketNumber" : 8479, "jobs" : [ "Engenharia de Materiais" ], "favFruits" : [ "Banana" ], "movies" : [ { "title" : "Interestelar (2014)", "rating" : 1.57 } ], "cat" : { "name" : "Fabrizio", "age" : 4 } }
{ "_id" : ObjectId("5e8a49fce9112505eb8cc12d"), "firstname" : "Pasquale", "surname" : "Rizzi", "username" : "user286", "age" : 39, "email" : "Pasquale.Rizzi@live.com", "bloodType" : "B+", "id_num" : "572177045455", "registerDate" : ISODate("2019-01-16T17:31:59.524Z"), "ticketNumber" : 2000, "jobs" : [ "Silvicultura", "Engenharia de Produção" ], "favFruits" : [ "Banana" ], "movies" : [ { "title" : "Vingadores: Ultimato (2019)", "rating" : 0.21 } ], "cat" : { "name" : "Valentina", "age" : 7 } }
{ "_id" : ObjectId("5e8a49fde9112505eb8cc153"), "firstname" : "Roberto", "surname" : "Monti", "username" : "user324", "age" : 50, "email" : "Roberto.Monti@yahoo.com", "bloodType" : "A+", "id_num" : "201746167757", "registerDate" : ISODate("2013-08-23T15:21:52.281Z"), "ticketNumber" : 5676, "jobs" : [ "Eventos" ], "favFruits" : [ "Laranja", "Banana", "Banana" ], "movies" : [ { "title" : "Os Sete Samurais (1954)", "rating" : 4.04 }, { "title" : "Gladiador (2000)", "rating" : 0.14 }, { "title" : "Pulp Fiction: Tempo de Violência (1994)", "rating" : 4.52 }, { "title" : "A Felicidade Não se Compra (1946)", "rating" : 2.62 }, { "title" : "O Senhor dos Anéis: O Retorno do Rei (2003)", "rating" : 1.29 } ], "father" : { "firstname" : "Giacomo", "surname" : "Monti", "age" : 87 } }
{ "_id" : ObjectId("5e8a49fde9112505eb8cc171"), "firstname" : "Alessandra", "surname" : "Costatini", "username" : "user354", "age" : 44, "email" : "Alessandra.Costatini@uol.com.br", "bloodType" : "A+", "id_num" : "461820303704", "registerDate" : ISODate("2018-09-04T20:00:08.694Z"), "ticketNumber" : 2745, "jobs" : [ "Música" ], "favFruits" : [ "Pêssego", "Maçã", "Banana" ], "movies" : [ { "title" : "A Vida é Bela (1997)", "rating" : 3.11 }, { "title" : "O Senhor dos Anéis: As Duas Torres (2002)", "rating" : 2.71 }, { "title" : "A Vida é Bela (1997)", "rating" : 4.41 } ] }
{ "_id" : ObjectId("5e8a49fde9112505eb8cc174"), "firstname" : "Mario", "surname" : "De Luca", "username" : "user357", "age" : 23, "email" : "Mario.De Luca@hotmail.com", "bloodType" : "O-", "id_num" : "552220724580", "registerDate" : ISODate("2010-07-30T01:44:59.520Z"), "ticketNumber" : 8866, "jobs" : [ "Design de Moda" ], "favFruits" : [ "Pêssego", "Melancia", "Banana" ], "movies" : [ { "title" : "A Viagem de Chihiro (2001)", "rating" : 4.45 }, { "title" : "Gladiador (2000)", "rating" : 1.6 }, { "title" : "Um Estranho no Ninho (1975)", "rating" : 4.83 }, { "title" : "O Resgate do Soldado Ryan (1998)", "rating" : 2.38 } ], "dog" : { "name" : "Giorgio", "age" : 1 } }
{ "_id" : ObjectId("5e8a49fde9112505eb8cc175"), "firstname" : "Valeira", "surname" : "Testa", "username" : "user358", "age" : 24, "email" : "Valeira.Testa@outlook.com", "bloodType" : "O-", "id_num" : "172854857028", "registerDate" : ISODate("2012-05-27T13:34:05.606Z"), "ticketNumber" : 2354, "jobs" : [ "Engenharia Mecatrônica" ], "favFruits" : [ "Banana", "Uva" ], "movies" : [ { "title" : "Os Sete Samurais (1954)", "rating" : 3.29 }, { "title" : "Os Sete Samurais (1954)", "rating" : 1.1 }, { "title" : "O Senhor dos Anéis: O Retorno do Rei (2003)", "rating" : 1.2 } ], "mother" : { "firstname" : "Giusy", "surname" : "Testa", "age" : 55 }, "dog" : { "name" : "Valeira", "age" : 18 } }
{ "_id" : ObjectId("5e8a49fde9112505eb8cc196"), "firstname" : "Enrico", "surname" : "Gentile", "username" : "user391", "age" : 31, "email" : "Enrico.Gentile@live.com", "bloodType" : "O-", "id_num" : "762283180267", "registerDate" : ISODate("2013-02-02T13:52:11.320Z"), "ticketNumber" : 6805, "jobs" : [ "Biotecnologia", "Comunicação das Artes do Corpo" ], "favFruits" : [ "Maçã", "Uva", "Banana" ], "movies" : [ { "title" : "Forrest Gump: O Contador de Histórias (1994)", "rating" : 0.18 }, { "title" : "O Silêncio dos Inocentes (1991)", "rating" : 1.6 }, { "title" : "Forrest Gump: O Contador de Histórias (1994)", "rating" : 4.07 }, { "title" : "Guerra nas Estrelas (1977)", "rating" : 2.47 } ] }
{ "_id" : ObjectId("5e8a49fde9112505eb8cc1b4"), "firstname" : "Stefano", "surname" : "De Santis", "username" : "user421", "age" : 40, "email" : "Stefano.De Santis@gmail.com", "bloodType" : "O+", "id_num" : "106004101274", "registerDate" : ISODate("2011-10-08T01:26:19.147Z"), "ticketNumber" : 7114, "jobs" : [ "Mineração", "Musicoterapia" ], "favFruits" : [ "Pêssego", "Uva", "Banana" ], "movies" : [ { "title" : "O Senhor dos Anéis: As Duas Torres (2002)", "rating" : 1.48 }, { "title" : "Parasita (2019)", "rating" : 4.37 } ], "father" : { "firstname" : "Claudia", "surname" : "De Santis", "age" : 76 } }
{ "_id" : ObjectId("5e8a49fde9112505eb8cc1bf"), "firstname" : "Fabio", "surname" : "Ferrari", "username" : "user432", "age" : 45, "email" : "Fabio.Ferrari@uol.com.br", "bloodType" : "B+", "id_num" : "877081726644", "registerDate" : ISODate("2017-08-06T09:49:34.021Z"), "ticketNumber" : 1409, "jobs" : [ "Ecologia" ], "favFruits" : [ "Uva", "Banana" ], "movies" : [ { "title" : "Harakiri (1962)", "rating" : 1.87 }, { "title" : "À Espera de um Milagre (1999)", "rating" : 0.42 }, { "title" : "Os Bons Companheiros (1990)", "rating" : 0.35 }, { "title" : "Interestelar (2014)", "rating" : 0.41 } ], "mother" : { "firstname" : "Fabrizio", "surname" : "Ferrari", "age" : 74 }, "cat" : { "name" : "Laura", "age" : 1 } }
Type "it" for more
```


## Exercício 3 - Stockbrokers

Importe o arquivo stocks.json do repositório Downloads NoSQL FURB. Esses dados
são dados reais da bolsa americana de 2015. A importação do arquivo JSON é um
pouco diferente da execução de um script:

```shell script
mongoimport --db stocks --collection stocks --file stocks.json
```

Analise um pouco a estrutura dos dados novamente e em seguida, responda as
seguintes perguntas:
1. Liste as ações com profit acima de 0.5 (limite a 10 o resultado)

```shell script
> db.stocks.find({"Profit Margin": {"$gte": 0.5}}).sort({"Profit Margin": 1}).limit(10)
{ "_id" : ObjectId("5285380bbb1177ca391c2cab"), "Ticker" : "SAR", "Profit Margin" : 0.5, "Institutional Ownership" : 0.522, "EPS growth past 5 years" : 0.21, "Total Debt/Equity" : 0.79, "Return on Assets" : 0.056, "Sector" : "Financial", "P/S" : 3.66, "Change from Open" : 0.0019, "Performance (YTD)" : 0.2, "Performance (Week)" : -0.042, "Insider Transactions" : 0.7326, "P/B" : 0.67, "EPS growth quarter over quarter" : -1.008, "Payout Ratio" : 1.923, "Performance (Quarter)" : 0.0076, "Forward P/E" : 7.75, "P/E" : 7.22, "200-Day Simple Moving Average" : 0.0337, "Shares Outstanding" : 4.73, "52-Week High" : -0.1233, "P/Cash" : 2.68, "Change" : 0.0025, "Analyst Recom" : 2, "Volatility (Week)" : 0.0449, "Country" : "USA", "Return on Equity" : 0.094, "50-Day Low" : 0.0283, "Price" : 16, "50-Day High" : -0.1233, "Return on Investment" : 0.041, "Shares Float" : 4.62, "Industry" : "Diversified Investments", "Beta" : 1.37, "Sales growth quarter over quarter" : 0.286, "Operating Margin" : 0.447, "EPS (ttm)" : 2.21, "Float Short" : 0.0008, "52-Week Low" : 0.2562, "Average True Range" : 0.53, "EPS growth next year" : 0.0842, "Sales growth past 5 years" : -0.045, "Company" : "Saratoga Investment Corp.", "Gap" : 0.0006, "Relative Volume" : 1.04, "Volatility (Month)" : 0.0259, "Market Cap" : 75.49, "Volume" : 11453, "Gross Margin" : 0.806, "Short Ratio" : 0.29, "Performance (Half Year)" : 0.0031, "Relative Strength Index (14)" : 41.2, "Insider Ownership" : 0.006, "20-Day Simple Moving Average" : -0.0362, "Performance (Month)" : -0.0545, "Institutional Transactions" : -0.0566, "Performance (Year)" : 0.1779, "LT Debt/Equity" : 0.79, "Average Volume" : 12.17, "EPS growth this year" : -0.083, "50-Day Simple Moving Average" : -0.0265 }
{ "_id" : ObjectId("52853801bb1177ca391c1af0"), "Ticker" : "BPO", "Profit Margin" : 0.503, "Institutional Ownership" : 0.958, "EPS growth past 5 years" : 0.354, "Total Debt/Equity" : 1.15, "Current Ratio" : 1, "Return on Assets" : 0.043, "Sector" : "Financial", "P/S" : 4.04, "Change from Open" : 0.001, "Performance (YTD)" : 0.1519, "Performance (Week)" : -0.0052, "Quick Ratio" : 1, "P/B" : 0.9, "EPS growth quarter over quarter" : -0.415, "Payout Ratio" : 0.235, "Performance (Quarter)" : 0.1825, "Forward P/E" : 18.74, "P/E" : 8.65, "200-Day Simple Moving Average" : 0.1124, "Shares Outstanding" : 505, "Earnings Date" : ISODate("2011-02-11T13:30:00Z"), "52-Week High" : -0.022, "P/Cash" : 22.13, "Change" : 0.0021, "Analyst Recom" : 3.1, "Volatility (Week)" : 0.0127, "Country" : "USA", "Return on Equity" : 0.115, "50-Day Low" : 0.1976, "Price" : 19.15, "50-Day High" : -0.022, "Return on Investment" : 0.015, "Shares Float" : 504.86, "Dividend Yield" : 0.0293, "EPS growth next 5 years" : 0.0735, "Industry" : "Property Management", "Beta" : 1.64, "Sales growth quarter over quarter" : 0.01, "Operating Margin" : 0.552, "EPS (ttm)" : 2.21, "PEG" : 1.18, "Float Short" : 0.0062, "52-Week Low" : 0.2728, "Average True Range" : 0.23, "EPS growth next year" : -0.105, "Sales growth past 5 years" : -0.043, "Company" : "Brookfield Properties Corporation", "Gap" : 0.001, "Relative Volume" : 0.17, "Volatility (Month)" : 0.0112, "Market Cap" : 9650.55, "Volume" : 249482, "Gross Margin" : 0.621, "Short Ratio" : 1.9, "Performance (Half Year)" : 0.0269, "Relative Strength Index (14)" : 62.08, "Insider Ownership" : 0.4972, "20-Day Simple Moving Average" : 0.012, "Performance (Month)" : 0.0154, "Institutional Transactions" : -0.004, "Performance (Year)" : 0.2482, "LT Debt/Equity" : 1.15, "Average Volume" : 1650.73, "EPS growth this year" : -0.212, "50-Day Simple Moving Average" : 0.0538 }
{ "_id" : ObjectId("52853803bb1177ca391c1f9e"), "Ticker" : "EQM", "Profit Margin" : 0.506, "Institutional Ownership" : 0.494, "EPS growth past 5 years" : 0.016, "Total Debt/Equity" : 0, "Current Ratio" : 2, "Return on Assets" : 0.121, "Sector" : "Basic Materials", "P/S" : 13.44, "Change from Open" : 0.0294, "Performance (YTD)" : 0.774, "Performance (Week)" : 0.007, "Quick Ratio" : 2, "Insider Transactions" : 0.0621, "P/B" : 3.22, "EPS growth quarter over quarter" : 0.5, "Payout Ratio" : 0.523, "Performance (Quarter)" : 0.1765, "Forward P/E" : 21.14, "P/E" : 34, "200-Day Simple Moving Average" : 0.2305, "Shares Outstanding" : 44.53, "Earnings Date" : ISODate("2013-10-24T12:30:00Z"), "52-Week High" : 0.0055, "P/Cash" : 76.67, "Change" : 0.0294, "Analyst Recom" : 1.7, "Volatility (Week)" : 0.0331, "Country" : "USA", "Return on Equity" : 0.153, "50-Day Low" : 0.181, "Price" : 54.95, "50-Day High" : 0.0055, "Return on Investment" : 0.087, "Shares Float" : 26.81, "Dividend Yield" : 0.0322, "EPS growth next 5 years" : 0.374, "Industry" : "Major Integrated Oil & Gas", "Sales growth quarter over quarter" : 0.33, "Operating Margin" : 0.604, "EPS (ttm)" : 1.57, "PEG" : 0.91, "Float Short" : 0.019, "52-Week Low" : 1.0532, "Average True Range" : 1.66, "EPS growth next year" : 0.0907, "Sales growth past 5 years" : 0.148, "Company" : "EQT Midstream Partners, LP", "Gap" : 0, "Relative Volume" : 0.39, "Volatility (Month)" : 0.0336, "Market Cap" : 2376.8, "Volume" : 55022, "Short Ratio" : 3.28, "Performance (Half Year)" : 0.1257, "Relative Strength Index (14)" : 67.51, "Insider Ownership" : 0.1833, "20-Day Simple Moving Average" : 0.0584, "Performance (Month)" : 0.1015, "Institutional Transactions" : 0.0552, "Performance (Year)" : 0.8769, "LT Debt/Equity" : 0, "Average Volume" : 155.01, "EPS growth this year" : -0.521, "50-Day Simple Moving Average" : 0.0963 }
{ "_id" : ObjectId("5285380bbb1177ca391c2d17"), "Ticker" : "SFL", "Profit Margin" : 0.506, "Institutional Ownership" : 0.309, "EPS growth past 5 years" : -0.007, "Total Debt/Equity" : 1.45, "Current Ratio" : 0.6, "Return on Assets" : 0.048, "Sector" : "Services", "P/S" : 5.14, "Change from Open" : 0.0065, "Performance (YTD)" : 0.0657, "Performance (Week)" : 0.006, "Quick Ratio" : 0.6, "P/B" : 1.2, "EPS growth quarter over quarter" : -0.623, "Payout Ratio" : 0.912, "Performance (Quarter)" : 0.0779, "Forward P/E" : 11.65, "P/E" : 9.87, "200-Day Simple Moving Average" : 0.0723, "Shares Outstanding" : 86.14, "Earnings Date" : ISODate("2013-11-18T05:00:00Z"), "52-Week High" : -0.0097, "P/Cash" : 13.5, "Change" : 0.0047, "Analyst Recom" : 2.1, "Volatility (Week)" : 0.0157, "Country" : "Bermuda", "Return on Equity" : 0.137, "50-Day Low" : 0.1383, "Price" : 16.96, "50-Day High" : 0.0006, "Return on Investment" : 0.072, "Shares Float" : 58.7, "Dividend Yield" : 0.0924, "EPS growth next 5 years" : -0.018, "Industry" : "Shipping", "Beta" : 1.29, "Sales growth quarter over quarter" : -0.211, "Operating Margin" : 0.428, "EPS (ttm)" : 1.71, "Float Short" : 0.0647, "52-Week Low" : 0.3298, "Average True Range" : 0.27, "EPS growth next year" : 0.2156, "Sales growth past 5 years" : -0.043, "Company" : "Ship Finance International Limited", "Gap" : -0.0018, "Relative Volume" : 0.42, "Volatility (Month)" : 0.0151, "Market Cap" : 1453.98, "Volume" : 235866, "Gross Margin" : 0.656, "Short Ratio" : 6.14, "Performance (Half Year)" : 0.0305, "Relative Strength Index (14)" : 69.43, "Insider Ownership" : 0.4006, "20-Day Simple Moving Average" : 0.0262, "Performance (Month)" : 0.089, "Institutional Transactions" : -0.0169, "Performance (Year)" : 0.2569, "LT Debt/Equity" : 1.22, "Average Volume" : 618.23, "EPS growth this year" : 0.37, "50-Day Simple Moving Average" : 0.0694 }
{ "_id" : ObjectId("52853805bb1177ca391c238b"), "Ticker" : "IBN", "Profit Margin" : 0.507, "Institutional Ownership" : 0.278, "EPS growth past 5 years" : 0.231, "Total Debt/Equity" : 2.29, "Return on Assets" : 0.03, "Sector" : "Financial", "P/S" : 3.96, "Change from Open" : -0.0102, "Performance (YTD)" : -0.2197, "Performance (Week)" : -0.0622, "P/B" : 2.13, "EPS growth quarter over quarter" : 0.933, "Payout Ratio" : 0.103, "Performance (Quarter)" : 0.0645, "Forward P/E" : 10.5, "P/E" : 19.71, "200-Day Simple Moving Average" : -0.1092, "Shares Outstanding" : 568.99, "Earnings Date" : ISODate("2011-01-24T05:00:00Z"), "52-Week High" : -0.289, "P/Cash" : 0.51, "Change" : 0.0119, "Analyst Recom" : 1, "Volatility (Week)" : 0.0237, "Country" : "India", "Return on Equity" : 0.303, "50-Day Low" : 0.1886, "Price" : 33.9, "50-Day High" : -0.1091, "Return on Investment" : 0.048, "Shares Float" : 577.01, "Dividend Yield" : 0.02, "EPS growth next 5 years" : 0.22, "Industry" : "Money Center Banks", "Beta" : 2.13, "Sales growth quarter over quarter" : 0.202, "Operating Margin" : 0.279, "EPS (ttm)" : 1.7, "PEG" : 0.9, "Float Short" : 0.0063, "52-Week Low" : 0.3593, "Average True Range" : 0.97, "EPS growth next year" : 0.1728, "Sales growth past 5 years" : 0.057, "Company" : "ICICI Bank Ltd.", "Gap" : 0.0224, "Relative Volume" : 0.51, "Volatility (Month)" : 0.0215, "Market Cap" : 19061.3, "Volume" : 1210132, "Short Ratio" : 1.39, "Performance (Half Year)" : -0.2912, "Relative Strength Index (14)" : 44.04, "20-Day Simple Moving Average" : -0.0414, "Performance (Month)" : 0.0207, "Institutional Transactions" : -0.0365, "Performance (Year)" : -0.124, "LT Debt/Equity" : 2.29, "Average Volume" : 2615.94, "EPS growth this year" : 0.425, "50-Day Simple Moving Average" : 0.0193 }
{ "_id" : ObjectId("5285380dbb1177ca391c2f68"), "Ticker" : "TPRE", "Profit Margin" : 0.507, "Institutional Ownership" : 0.562, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 0, "Return on Assets" : 0.178, "Sector" : "Financial", "P/S" : 3.06, "Change from Open" : -0.018, "Performance (YTD)" : 0.1746, "Performance (Week)" : -0.026, "Insider Transactions" : 0.2342, "P/B" : 1.25, "EPS growth quarter over quarter" : 1.75, "Payout Ratio" : 0, "Performance (Quarter)" : 0.1746, "Forward P/E" : 8.02, "P/E" : 6.7, "200-Day Simple Moving Average" : 0.041, "Shares Outstanding" : 79.05, "Earnings Date" : ISODate("2013-11-12T13:30:00Z"), "52-Week High" : -0.0901, "P/Cash" : 37.2, "Change" : -0.0218, "Analyst Recom" : 2.1, "Volatility (Week)" : 0.0482, "Country" : "Bermuda", "Return on Equity" : 0.288, "50-Day Low" : 0.165, "Price" : 15.01, "50-Day High" : -0.0901, "Return on Investment" : 0.116, "Shares Float" : 102.07, "EPS growth next 5 years" : 0.15, "Industry" : "Property & Casualty Insurance", "Operating Margin" : 0.513, "EPS (ttm)" : 2.29, "PEG" : 0.45, "Float Short" : 0.0054, "52-Week Low" : 0.2249, "Average True Range" : 0.51, "EPS growth next year" : 0.0247, "Company" : "Third Point Reinsurance Ltd.", "Gap" : -0.0039, "Relative Volume" : 0.31, "Volatility (Month)" : 0.0299, "Market Cap" : 1212.69, "Volume" : 170761, "Short Ratio" : 0.92, "Relative Strength Index (14)" : 44.51, "Insider Ownership" : 0.009, "20-Day Simple Moving Average" : -0.0403, "Performance (Month)" : -0.0007, "LT Debt/Equity" : 0, "Average Volume" : 603.79, "50-Day Simple Moving Average" : 0.0164 }
{ "_id" : ObjectId("52853805bb1177ca391c22f2"), "Ticker" : "HLSS", "Profit Margin" : 0.513, "Institutional Ownership" : 0.854, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 4.52, "Current Ratio" : 122.1, "Return on Assets" : 0.021, "Sector" : "Financial", "P/S" : 8.29, "Change from Open" : 0.0013, "Performance (YTD)" : 0.3024, "Performance (Week)" : -0.0111, "Quick Ratio" : 122.1, "Insider Transactions" : 0.1181, "P/B" : 1.34, "EPS growth quarter over quarter" : 0.324, "Payout Ratio" : 0.903, "Performance (Quarter)" : -0.0077, "Forward P/E" : 11.29, "P/E" : 12.52, "200-Day Simple Moving Average" : 0.0296, "Shares Outstanding" : 71.02, "Earnings Date" : ISODate("2013-10-17T12:30:00Z"), "52-Week High" : -0.0724, "P/Cash" : 7.12, "Change" : 0.0047, "Analyst Recom" : 1.7, "Volatility (Week)" : 0.0228, "Country" : "Cayman Islands", "Return on Equity" : 0.096, "50-Day Low" : 0.1069, "Price" : 23.28, "50-Day High" : -0.0279, "Return on Investment" : 0.014, "Shares Float" : 70, "Dividend Yield" : 0.0777, "EPS growth next 5 years" : 0.05, "Industry" : "Mortgage Investment", "Sales growth quarter over quarter" : 4.095, "Operating Margin" : 0.95, "EPS (ttm)" : 1.85, "PEG" : 2.5, "Float Short" : 0.0219, "52-Week Low" : 0.4431, "Average True Range" : 0.47, "EPS growth next year" : 0.0715, "Company" : "Home Loan Servicing Solutions, Ltd.", "Gap" : 0.0035, "Relative Volume" : 0.4, "Volatility (Month)" : 0.0196, "Market Cap" : 1645.46, "Volume" : 198033, "Short Ratio" : 2.83, "Performance (Half Year)" : -0.0064, "Relative Strength Index (14)" : 53.39, "Insider Ownership" : 0.002, "20-Day Simple Moving Average" : 0.0007, "Performance (Month)" : 0.0275, "P/Free Cash Flow" : 2.43, "Institutional Transactions" : 0.0067, "Performance (Year)" : 0.3324, "LT Debt/Equity" : 4.52, "Average Volume" : 542.18, "EPS growth this year" : 0.231, "50-Day Simple Moving Average" : 0.0252 }
{ "_id" : ObjectId("52853806bb1177ca391c2552"), "Ticker" : "KFN", "Profit Margin" : 0.518, "Institutional Ownership" : 0.526, "EPS growth past 5 years" : -0.047, "Total Debt/Equity" : 2.32, "Return on Assets" : 0.033, "Sector" : "Financial", "P/S" : 3.63, "Change from Open" : 0.0052, "Performance (YTD)" : -0.0113, "Performance (Week)" : -0.0113, "Insider Transactions" : 0.1791, "P/B" : 0.79, "EPS growth quarter over quarter" : -0.738, "Payout Ratio" : 0.618, "Performance (Quarter)" : -0.0504, "Forward P/E" : 8.37, "P/E" : 6.91, "200-Day Simple Moving Average" : -0.0526, "Shares Outstanding" : 204.13, "Earnings Date" : ISODate("2013-10-23T20:30:00Z"), "52-Week High" : -0.1194, "P/Cash" : 8.83, "Change" : 0.0062, "Analyst Recom" : 1.8, "Volatility (Week)" : 0.0182, "Country" : "USA", "Return on Equity" : 0.121, "50-Day Low" : 0.0277, "Price" : 9.66, "50-Day High" : -0.0996, "Return on Investment" : 0.042, "Shares Float" : 202.79, "Dividend Yield" : 0.0917, "EPS growth next 5 years" : 0.1, "Industry" : "Mortgage Investment", "Beta" : 2.09, "Sales growth quarter over quarter" : -0.102, "Operating Margin" : 0.313, "EPS (ttm)" : 1.39, "PEG" : 0.69, "Float Short" : 0.0061, "52-Week Low" : 0.1303, "Average True Range" : 0.16, "EPS growth next year" : -0.1311, "Sales growth past 5 years" : -0.086, "Company" : "KKR Financial Holdings LLC", "Gap" : 0.001, "Relative Volume" : 1.23, "Volatility (Month)" : 0.0146, "Market Cap" : 1959.69, "Volume" : 999030, "Gross Margin" : 0.631, "Short Ratio" : 1.4, "Performance (Half Year)" : -0.0661, "Relative Strength Index (14)" : 40.86, "Insider Ownership" : 0.001, "20-Day Simple Moving Average" : -0.0239, "Performance (Month)" : -0.0607, "Institutional Transactions" : -0.0077, "Performance (Year)" : 0.0549, "LT Debt/Equity" : 0, "Average Volume" : 887.93, "EPS growth this year" : 0.069, "50-Day Simple Moving Average" : -0.0364 }
{ "_id" : ObjectId("52853802bb1177ca391c1c51"), "Ticker" : "CIT", "Profit Margin" : 0.519, "Institutional Ownership" : 0.963, "EPS growth past 5 years" : -0.224, "Total Debt/Equity" : 2.42, "Return on Assets" : 0.018, "Sector" : "Financial", "P/S" : 6.29, "Change from Open" : 0.0088, "Performance (YTD)" : 0.2704, "Performance (Week)" : 0.0285, "Insider Transactions" : -0.0833, "P/B" : 1.11, "EPS growth quarter over quarter" : 1.859, "Payout Ratio" : 0, "Performance (Quarter)" : -0.0047, "Forward P/E" : 12.12, "P/E" : 12.21, "200-Day Simple Moving Average" : 0.0699, "Shares Outstanding" : 200.81, "Earnings Date" : ISODate("2013-10-22T12:30:00Z"), "52-Week High" : -0.0382, "P/Cash" : 1.65, "Change" : 0.0094, "Analyst Recom" : 1.9, "Volatility (Week)" : 0.0183, "Country" : "USA", "Return on Equity" : 0.095, "50-Day Low" : 0.0592, "Price" : 49.55, "50-Day High" : -0.0341, "Return on Investment" : -0.05, "Shares Float" : 199.61, "Dividend Yield" : 0.0081, "EPS growth next 5 years" : 0.1, "Industry" : "Credit Services", "Beta" : 1.31, "Sales growth quarter over quarter" : 0.336, "EPS (ttm)" : 4.02, "PEG" : 1.22, "Float Short" : 0.0147, "52-Week Low" : 0.3756, "Average True Range" : 0.79, "EPS growth next year" : 0.109, "Sales growth past 5 years" : -0.18, "Company" : "CIT Group Inc.", "Gap" : 0.0006, "Relative Volume" : 0.55, "Volatility (Month)" : 0.0163, "Market Cap" : 9857.81, "Volume" : 580248, "Short Ratio" : 2.53, "Performance (Half Year)" : 0.1182, "Relative Strength Index (14)" : 56.38, "Insider Ownership" : 0.004, "20-Day Simple Moving Average" : 0.0146, "Performance (Month)" : -0.0184, "Institutional Transactions" : 0.0233, "Performance (Year)" : 0.3307, "LT Debt/Equity" : 2.42, "Average Volume" : 1157.92, "EPS growth this year" : 2.113, "50-Day Simple Moving Average" : 0.0105 }
{ "_id" : ObjectId("5285380bbb1177ca391c2cb0"), "Ticker" : "SB", "Profit Margin" : 0.522, "Institutional Ownership" : 0.15, "EPS growth past 5 years" : -0.199, "Total Debt/Equity" : 1.06, "Current Ratio" : 2.8, "Return on Assets" : 0.088, "Sector" : "Services", "P/S" : 3.25, "Change from Open" : 0.0052, "Performance (YTD)" : 1.3221, "Performance (Week)" : -0.078, "Quick Ratio" : 2.8, "P/B" : 1.17, "EPS growth quarter over quarter" : 0.143, "Payout Ratio" : 0.248, "Performance (Quarter)" : 0.3422, "Forward P/E" : 9.52, "P/E" : 6.26, "200-Day Simple Moving Average" : 0.3936, "Shares Outstanding" : 76.68, "Earnings Date" : ISODate("2013-11-04T21:30:00Z"), "52-Week High" : -0.1489, "P/Cash" : 8.82, "Change" : 0.0119, "Analyst Recom" : 1.8, "Volatility (Week)" : 0.0424, "Country" : "Greece", "Return on Equity" : 0.212, "50-Day Low" : 0.2335, "Price" : 7.66, "50-Day High" : -0.1489, "Return on Investment" : 0.108, "Shares Float" : 30.22, "Dividend Yield" : 0.0264, "EPS growth next 5 years" : 0.05, "Industry" : "Shipping", "Beta" : 1.66, "Sales growth quarter over quarter" : -0.119, "Operating Margin" : 0.584, "EPS (ttm)" : 1.21, "PEG" : 1.25, "Float Short" : 0.0182, "52-Week Low" : 1.5266, "Average True Range" : 0.39, "EPS growth next year" : 0.1072, "Sales growth past 5 years" : 0.021, "Company" : "Safe Bulkers, Inc.", "Gap" : 0.0066, "Relative Volume" : 1.09, "Volatility (Month)" : 0.044, "Market Cap" : 580.46, "Volume" : 464401, "Gross Margin" : 0.735, "Short Ratio" : 1.18, "Performance (Half Year)" : 0.4699, "Relative Strength Index (14)" : 50.64, "Insider Ownership" : 0.6057, "20-Day Simple Moving Average" : -0.0075, "Performance (Month)" : 0.0876, "Institutional Transactions" : 0.0588, "Performance (Year)" : 0.7523, "LT Debt/Equity" : 1.03, "Average Volume" : 468.74, "EPS growth this year" : -0.016, "50-Day Simple Moving Average" : 0.0533 }
```

2. Liste as ações com perdas (limite a 10 novamente)

```shell script
> db.stocks.find({"Change from Open": {"$lt": 0}}).sort({"Change from Open": 1}).limit(10)
{ "_id" : ObjectId("5285380dbb1177ca391c2fb4"), "Ticker" : "TTS", "Profit Margin" : -0.53, "Institutional Ownership" : 0.454, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 1.06, "Current Ratio" : 2, "Return on Assets" : -0.578, "Sector" : "Services", "P/S" : 5.33, "Change from Open" : -0.3877, "Performance (YTD)" : 0.2608, "Performance (Week)" : -0.0846, "Quick Ratio" : 0.7, "Insider Transactions" : -0.7811, "P/B" : 15.05, "EPS growth quarter over quarter" : -0.788, "Performance (Quarter)" : -0.1913, "Forward P/E" : 34.9, "200-Day Simple Moving Average" : -0.475, "Shares Outstanding" : 51.56, "Earnings Date" : ISODate("2013-10-30T20:30:00Z"), "52-Week High" : -0.5806, "P/Cash" : 287.9, "Change" : -0.3897, "Analyst Recom" : 1.9, "Volatility (Week)" : 0.0516, "Country" : "USA", "Return on Equity" : -8.704, "50-Day Low" : -0.3307, "Price" : 12.95, "50-Day High" : -0.5719, "Return on Investment" : 1.217, "Shares Float" : 25.24, "EPS growth next 5 years" : 0.231, "Industry" : "Home Improvement Stores", "Sales growth quarter over quarter" : 0.255, "Operating Margin" : 0.173, "EPS (ttm)" : -2.58, "Float Short" : 0.0825, "52-Week Low" : -0.0189, "Average True Range" : 1.2, "EPS growth next year" : 0.385, "Company" : "Tile Shop Holdings, Inc.", "Gap" : -0.0033, "Relative Volume" : 40.37, "Volatility (Month)" : 0.0593, "Market Cap" : 1094.02, "Volume" : 17224344, "Gross Margin" : 0.715, "Short Ratio" : 4.42, "Performance (Half Year)" : -0.1867, "Relative Strength Index (14)" : 15.68, "Insider Ownership" : 0.025, "20-Day Simple Moving Average" : -0.4436, "Performance (Month)" : -0.2023, "Institutional Transactions" : 0.0084, "Performance (Year)" : 0.5707, "LT Debt/Equity" : 1, "Average Volume" : 471.43, "EPS growth this year" : -2.351, "50-Day Simple Moving Average" : -0.4958 }
{ "_id" : ObjectId("52853803bb1177ca391c1f9b"), "Ticker" : "EPZM", "Profit Margin" : -0.518, "Institutional Ownership" : 0.603, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 0, "Current Ratio" : 4.7, "Return on Assets" : -0.212, "Sector" : "Healthcare", "P/S" : 21.92, "Change from Open" : -0.3853, "Performance (YTD)" : 0.3793, "Performance (Week)" : -0.0568, "Quick Ratio" : 4.7, "P/B" : 10.33, "EPS growth quarter over quarter" : -2.03, "Performance (Quarter)" : 0.0183, "200-Day Simple Moving Average" : -0.4096, "Shares Outstanding" : 28.41, "Earnings Date" : ISODate("2013-10-22T04:00:00Z"), "52-Week High" : -0.5704, "P/Cash" : 6.45, "Change" : -0.3806, "Analyst Recom" : 1.4, "Volatility (Week)" : 0.0771, "Country" : "USA", "Return on Equity" : -0.681, "50-Day Low" : -0.3242, "Price" : 19.64, "50-Day High" : -0.5402, "Return on Investment" : 0.016, "Shares Float" : 28.02, "Industry" : "Biotechnology", "Sales growth quarter over quarter" : -0.451, "Operating Margin" : -0.582, "EPS (ttm)" : -5.83, "Float Short" : 0.0434, "52-Week Low" : 0.0559, "Average True Range" : 2.22, "EPS growth next year" : 0.61, "Company" : "Epizyme, Inc.", "Gap" : 0.0076, "Relative Volume" : 31.04, "Volatility (Month)" : 0.0584, "Market Cap" : 900.75, "Volume" : 3752542, "Short Ratio" : 9.18, "Relative Strength Index (14)" : 18.87, "20-Day Simple Moving Average" : -0.4634, "Performance (Month)" : -0.0736, "Institutional Transactions" : 0.0102, "LT Debt/Equity" : 0, "Average Volume" : 132.52, "EPS growth this year" : 0.951, "50-Day Simple Moving Average" : -0.4574 }
{ "_id" : ObjectId("52853808bb1177ca391c2890"), "Ticker" : "NPTN", "Profit Margin" : -0.086, "Institutional Ownership" : 0.562, "EPS growth past 5 years" : 0.182, "Total Debt/Equity" : 0.24, "Current Ratio" : 2.6, "Return on Assets" : -0.072, "Sector" : "Technology", "P/S" : 0.87, "Change from Open" : -0.1738, "Performance (YTD)" : 0.2735, "Performance (Week)" : 0.0167, "Quick Ratio" : 1.9, "Insider Transactions" : 0.0132, "P/B" : 1.2, "EPS growth quarter over quarter" : -1.385, "Performance (Quarter)" : 0.1246, "Forward P/E" : 208.86, "200-Day Simple Moving Average" : -0.1186, "Shares Outstanding" : 30.78, "Earnings Date" : ISODate("2013-05-09T20:30:00Z"), "52-Week High" : -0.3869, "P/Cash" : 3.01, "Change" : -0.1806, "Analyst Recom" : 2.3, "Volatility (Week)" : 0.0551, "Country" : "USA", "Return on Equity" : -0.113, "50-Day Low" : -0.1126, "Price" : 5.99, "50-Day High" : -0.2494, "Return on Investment" : -0.077, "Shares Float" : 24.52, "EPS growth next 5 years" : 0.15, "Industry" : "Semiconductor - Broad Line", "Sales growth quarter over quarter" : 0.19, "Operating Margin" : -0.077, "EPS (ttm)" : -0.72, "Float Short" : 0.0291, "52-Week Low" : 0.2611, "Average True Range" : 0.32, "EPS growth next year" : 1.098, "Sales growth past 5 years" : 0.238, "Company" : "NeoPhotonics Corporation", "Gap" : -0.0082, "Relative Volume" : 7.13, "Volatility (Month)" : 0.0476, "Market Cap" : 225, "Volume" : 696319, "Gross Margin" : 0.236, "Short Ratio" : 6.62, "Performance (Half Year)" : 0.1422, "Relative Strength Index (14)" : 31.93, "Insider Ownership" : 0.005, "20-Day Simple Moving Average" : -0.1807, "Performance (Month)" : 0.031, "Institutional Transactions" : 0.0177, "Performance (Year)" : 0.3587, "LT Debt/Equity" : 0.14, "Average Volume" : 107.98, "EPS growth this year" : 0.572, "50-Day Simple Moving Average" : -0.1795 }
{ "_id" : ObjectId("5285380bbb1177ca391c2c16"), "Ticker" : "RMGN", "Profit Margin" : -0.958, "Institutional Ownership" : 0.172, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 0.11, "Current Ratio" : 1.2, "Return on Assets" : -0.189, "Sector" : "Services", "P/S" : 2.99, "Change from Open" : -0.1595, "Performance (YTD)" : -0.1927, "Performance (Week)" : 0, "Quick Ratio" : 1.1, "Insider Transactions" : 0.1899, "P/B" : 1.83, "Performance (Quarter)" : -0.0419, "200-Day Simple Moving Average" : -0.4255, "Shares Outstanding" : 6.29, "Earnings Date" : ISODate("2013-11-14T13:30:00Z"), "52-Week High" : -0.7384, "P/Cash" : 8.38, "Change" : -0.2887, "Analyst Recom" : 2, "Volatility (Week)" : 0.0118, "Country" : "USA", "Return on Equity" : -0.259, "50-Day Low" : -0.1895, "Price" : 5.69, "50-Day High" : -0.3177, "Shares Float" : 16.18, "EPS growth next 5 years" : 0.5, "Industry" : "Business Services", "Operating Margin" : -0.137, "EPS (ttm)" : -3.12, "Float Short" : 0.0014, "52-Week Low" : -0.1895, "Average True Range" : 0.12, "Company" : "RMG Networks Holding Corporation", "Gap" : -0.1538, "Relative Volume" : 5.47, "Volatility (Month)" : 0.0147, "Market Cap" : 50.29, "Volume" : 72351, "Short Ratio" : 1.53, "Performance (Half Year)" : -0.3289, "Relative Strength Index (14)" : 12.21, "Insider Ownership" : 0.031, "20-Day Simple Moving Average" : -0.2866, "Performance (Month)" : 0.0336, "Institutional Transactions" : -0.102, "Performance (Year)" : -0.1911, "LT Debt/Equity" : 0.02, "Average Volume" : 14.61, "EPS growth this year" : -5.7, "50-Day Simple Moving Average" : -0.2788 }
{ "_id" : ObjectId("52853803bb1177ca391c1f0e"), "Ticker" : "EFOI", "Profit Margin" : -0.2263, "Institutional Ownership" : 0.1421, "Total Debt/Equity" : 0.67, "Current Ratio" : 0.95, "Return on Assets" : -0.3877, "Sector" : "Industrial Goods", "P/S" : 0.6, "Change from Open" : -0.1579, "Performance (YTD)" : 3.75, "Performance (Week)" : 0.1343, "Quick Ratio" : 0.64, "Insider Transactions" : -0.7789, "P/B" : 5.43, "EPS growth quarter over quarter" : 0.4094, "Performance (Quarter)" : 0.8095, "200-Day Simple Moving Average" : 0.6805, "Shares Outstanding" : 24.85, "Earnings Date" : ISODate("2013-05-15T20:30:00Z"), "52-Week High" : -0.2727, "P/Cash" : 20.75, "Change" : -0.1579, "Volatility (Week)" : 0.057, "Country" : "USA", "Return on Equity" : -1.1776, "50-Day Low" : 1.0645, "Price" : 0.64, "50-Day High" : -0.2727, "Return on Investment" : -0.8277, "Shares Float" : 17.78, "Industry" : "Industrial Electrical Equipment", "Beta" : 0.05, "Sales growth quarter over quarter" : -0.0854, "Operating Margin" : -0.2075, "EPS (ttm)" : -0.3, "Float Short" : 0.0117, "52-Week Low" : 3, "Average True Range" : 0.07, "Sales growth past 5 years" : 0.0439, "Company" : "Energy Focus, Inc.", "Gap" : 0, "Relative Volume" : 2.57, "Volatility (Month)" : 0.0783, "Market Cap" : 18.89, "Volume" : 169795, "Gross Margin" : 0.1953, "Short Ratio" : 2.86, "Performance (Half Year)" : 1.2353, "Relative Strength Index (14)" : 44.07, "Insider Ownership" : 0.0457, "20-Day Simple Moving Average" : -0.1478, "Performance (Month)" : -0.0732, "Institutional Transactions" : -0.1284, "Performance (Year)" : 2.619, "LT Debt/Equity" : 0.5, "Average Volume" : 72.45, "EPS growth this year" : 0.3998, "50-Day Simple Moving Average" : 0.0674 }
{ "_id" : ObjectId("52853803bb1177ca391c1f1e"), "Ticker" : "EGLE", "Profit Margin" : -0.31, "Institutional Ownership" : 0.369, "EPS growth past 5 years" : -0.268, "Total Debt/Equity" : 1.93, "Current Ratio" : 3.4, "Return on Assets" : -0.036, "Sector" : "Services", "P/S" : 0.39, "Change from Open" : -0.1553, "Performance (YTD)" : 2.12, "Performance (Week)" : 0.0108, "Quick Ratio" : 2.9, "Insider Transactions" : -0.2207, "P/B" : 0.13, "EPS growth quarter over quarter" : 0.931, "Performance (Quarter)" : 0.2615, "200-Day Simple Moving Average" : -0.1893, "Shares Outstanding" : 16.97, "Earnings Date" : ISODate("2013-11-13T21:30:00Z"), "52-Week High" : -0.624, "P/Cash" : 1.48, "Change" : -0.297, "Analyst Recom" : 3.7, "Volatility (Week)" : 0.1013, "Country" : "USA", "Return on Equity" : -0.106, "50-Day Low" : -0.2034, "Price" : 3.29, "50-Day High" : -0.624, "Return on Investment" : -0.021, "Shares Float" : 16.3, "Industry" : "Shipping", "Beta" : 2.97, "Sales growth quarter over quarter" : -0.563, "Operating Margin" : -0.025, "EPS (ttm)" : -3.8, "Float Short" : 0.2028, "52-Week Low" : 1.4925, "Average True Range" : 0.56, "EPS growth next year" : 0.319, "Sales growth past 5 years" : 0.089, "Company" : "Eagle Bulk Shipping, Inc.", "Gap" : -0.1677, "Relative Volume" : 5.06, "Volatility (Month)" : 0.103, "Market Cap" : 79.41, "Volume" : 5974047, "Gross Margin" : 0.45, "Short Ratio" : 2.55, "Performance (Half Year)" : -0.146, "Relative Strength Index (14)" : 30.71, "Insider Ownership" : 0.031, "20-Day Simple Moving Average" : -0.4188, "Performance (Month)" : -0.3208, "P/Free Cash Flow" : 10.31, "Institutional Transactions" : -0.0123, "Performance (Year)" : 0.7269, "LT Debt/Equity" : 1.93, "Average Volume" : 1294.3, "EPS growth this year" : -5.632, "50-Day Simple Moving Average" : -0.4699 }
{ "_id" : ObjectId("5285380cbb1177ca391c2dce"), "Ticker" : "SORL", "Profit Margin" : 0.065, "Institutional Ownership" : 0.115, "EPS growth past 5 years" : 0.023, "Total Debt/Equity" : 0.13, "Current Ratio" : 4.2, "Return on Assets" : 0.052, "Sector" : "Consumer Goods", "P/S" : 0.46, "Change from Open" : -0.1372, "Performance (YTD)" : 0.8975, "Performance (Week)" : -0.0191, "Quick Ratio" : 2.7, "P/B" : 0.5, "EPS growth quarter over quarter" : 0.313, "Payout Ratio" : 0, "Performance (Quarter)" : 0.3862, "Forward P/E" : 5.72, "P/E" : 7.02, "200-Day Simple Moving Average" : 0.233, "Shares Outstanding" : 19.31, "Earnings Date" : ISODate("2013-11-14T13:30:00Z"), "52-Week High" : -0.2353, "P/Cash" : 2.35, "Change" : -0.113, "Analyst Recom" : 2, "Volatility (Week)" : 0.0792, "Country" : "China", "Return on Equity" : 0.074, "50-Day Low" : 0.2636, "Price" : 4.11, "50-Day High" : -0.2353, "Return on Investment" : 0.071, "Shares Float" : 7.95, "EPS growth next 5 years" : 0.05, "Industry" : "Auto Manufacturers - Major", "Beta" : 1.4, "Sales growth quarter over quarter" : 0.104, "Operating Margin" : 0.074, "EPS (ttm)" : 0.66, "PEG" : 1.4, "Float Short" : 0.0069, "52-Week Low" : 0.9556, "Average True Range" : 0.34, "EPS growth next year" : 0.2273, "Sales growth past 5 years" : 0.107, "Company" : "SORL Auto Parts, Inc.", "Gap" : 0.0281, "Relative Volume" : 4.15, "Volatility (Month)" : 0.08, "Market Cap" : 89.38, "Volume" : 248347, "Gross Margin" : 0.276, "Short Ratio" : 0.83, "Performance (Half Year)" : 0.7341, "Relative Strength Index (14)" : 43.73, "Insider Ownership" : 0.5884, "20-Day Simple Moving Average" : -0.1287, "Performance (Month)" : 0.1404, "P/Free Cash Flow" : 4.97, "Institutional Transactions" : -0.1027, "Performance (Year)" : 1.1636, "LT Debt/Equity" : 0.05, "Average Volume" : 66.19, "EPS growth this year" : -0.233, "50-Day Simple Moving Average" : 0.0034 }
{ "_id" : ObjectId("5285380dbb1177ca391c2f1a"), "Ticker" : "THST", "Profit Margin" : -0.035, "Institutional Ownership" : 0.452, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 1.43, "Current Ratio" : 2.5, "Return on Assets" : -0.027, "Sector" : "Consumer Goods", "P/S" : 0.7, "Change from Open" : -0.1336, "Performance (YTD)" : -0.2165, "Performance (Week)" : -0.09, "Quick Ratio" : 1.3, "P/B" : 1.6, "EPS growth quarter over quarter" : 0, "Performance (Quarter)" : -0.162, "Forward P/E" : 68.46, "200-Day Simple Moving Average" : -0.2864, "Shares Outstanding" : 2.7, "52-Week High" : -0.3733, "P/Cash" : 1.05, "Change" : -0.1551, "Analyst Recom" : 1, "Volatility (Week)" : 0.0433, "Country" : "USA", "Return on Equity" : -0.1, "50-Day Low" : -0.1174, "Price" : 3.76, "50-Day High" : -0.3733, "Return on Investment" : -0.016, "Shares Float" : 2.53, "Industry" : "Beverages - Wineries & Distillers", "Sales growth quarter over quarter" : 0.163, "Operating Margin" : 0, "EPS (ttm)" : 0, "Float Short" : 0.0118, "52-Week Low" : -0.1174, "Average True Range" : 0.24, "EPS growth next year" : 1.7, "Company" : "Truett-Hurst, Inc.", "Gap" : -0.0247, "Relative Volume" : 3.56, "Volatility (Month)" : 0.0355, "Market Cap" : 12.01, "Volume" : 35950, "Gross Margin" : 0.331, "Short Ratio" : 2.68, "Relative Strength Index (14)" : 26.83, "Insider Ownership" : 0.004, "20-Day Simple Moving Average" : -0.2154, "Performance (Month)" : -0.1046, "Institutional Transactions" : -0.0025, "LT Debt/Equity" : 0.47, "Average Volume" : 11.16, "EPS growth this year" : 0, "50-Day Simple Moving Average" : -0.2609 }
{ "_id" : ObjectId("52853808bb1177ca391c27e8"), "Ticker" : "NAII", "Profit Margin" : 0.025, "Institutional Ownership" : 0.118, "EPS growth past 5 years" : 0.121, "Total Debt/Equity" : 0, "Current Ratio" : 6, "Return on Assets" : 0.035, "Sector" : "Healthcare", "P/S" : 0.67, "Change from Open" : -0.1267, "Performance (YTD)" : 0.2191, "Performance (Week)" : 0, "Quick Ratio" : 4.3, "Insider Transactions" : 0.1518, "P/B" : 1.04, "EPS growth quarter over quarter" : -0.667, "Payout Ratio" : 0, "Performance (Quarter)" : 0.2047, "P/E" : 26.61, "200-Day Simple Moving Average" : 0.044, "Shares Outstanding" : 6.83, "Earnings Date" : ISODate("2013-12-16T21:30:00Z"), "52-Week High" : -0.1748, "P/Cash" : 2.5, "Change" : -0.1438, "Volatility (Week)" : 0.011, "Country" : "USA", "Return on Equity" : 0.04, "50-Day Low" : 0.1149, "Price" : 5.24, "50-Day High" : -0.1748, "Return on Investment" : 0.042, "Shares Float" : 3.44, "Industry" : "Drug Related Products", "Beta" : 0.45, "Sales growth quarter over quarter" : -0.561, "Operating Margin" : 0.035, "EPS (ttm)" : 0.23, "Float Short" : 0.0035, "52-Week Low" : 0.3067, "Average True Range" : 0.1, "Sales growth past 5 years" : -0.051, "Company" : "Natural Alternatives International Inc.", "Gap" : -0.0196, "Relative Volume" : 11.05, "Volatility (Month)" : 0.0179, "Market Cap" : 41.78, "Volume" : 122204, "Gross Margin" : 0.188, "Short Ratio" : 0.98, "Performance (Half Year)" : 0.2911, "Relative Strength Index (14)" : 24.98, "Insider Ownership" : 0.02, "20-Day Simple Moving Average" : -0.1399, "Performance (Month)" : 0.0217, "P/Free Cash Flow" : 14.92, "Institutional Transactions" : 0.0075, "Performance (Year)" : -0.0129, "LT Debt/Equity" : 0, "Average Volume" : 12.12, "EPS growth this year" : -0.61, "50-Day Simple Moving Average" : -0.0853 }
{ "_id" : ObjectId("52853802bb1177ca391c1cd6"), "Ticker" : "COSI", "Profit Margin" : -0.089, "Institutional Ownership" : 0.167, "EPS growth past 5 years" : 0.296, "Total Debt/Equity" : 0, "Current Ratio" : 1.3, "Return on Assets" : -0.288, "Sector" : "Services", "P/S" : 0.45, "Change from Open" : -0.1244, "Performance (YTD)" : -0.266, "Performance (Week)" : 0.0178, "Quick Ratio" : 1.2, "Insider Transactions" : 0.0054, "P/B" : 4.49, "Performance (Quarter)" : 0.0178, "200-Day Simple Moving Average" : -0.2538, "Shares Outstanding" : 17.99, "Earnings Date" : ISODate("2013-11-14T13:30:00Z"), "52-Week High" : -0.5153, "P/Cash" : 3.43, "Change" : -0.1703, "Analyst Recom" : 2.3, "Volatility (Week)" : 0.0427, "Country" : "USA", "Return on Equity" : -0.648, "50-Day Low" : -0.0952, "Price" : 1.9, "50-Day High" : -0.2963, "Return on Investment" : -0.321, "Shares Float" : 11.63, "EPS growth next 5 years" : 0.15, "Industry" : "Restaurants", "Beta" : 1.31, "Sales growth quarter over quarter" : -0.11, "Operating Margin" : -0.076, "EPS (ttm)" : -0.46, "Float Short" : 0.042, "52-Week Low" : 0, "Average True Range" : 0.09, "EPS growth next year" : 0.739, "Sales growth past 5 years" : -0.061, "Company" : "Cosi Inc.", "Gap" : -0.0524, "Relative Volume" : 6.76, "Volatility (Month)" : 0.0389, "Market Cap" : 41.19, "Volume" : 641266, "Gross Margin" : 0.766, "Short Ratio" : 4.69, "Performance (Half Year)" : -0.2367, "Relative Strength Index (14)" : 31.66, "Insider Ownership" : 0.009, "20-Day Simple Moving Average" : -0.1381, "Performance (Month)" : 0.0457, "Institutional Transactions" : -0.0128, "Performance (Year)" : -0.1192, "LT Debt/Equity" : 0, "Average Volume" : 104.02, "EPS growth this year" : 0.431, "50-Day Simple Moving Average" : -0.1686 }
```

3. Liste as 10 ações mais rentáveis

```shell script
> db.stocks.find({"Performance (Year)" : {"$exists": true}}).sort({"Performance (Year)": -1}).limit(10)
{ "_id" : ObjectId("52853801bb1177ca391c187f"), "Ticker" : "AFFM", "Profit Margin" : -0.6574, "Institutional Ownership" : 0.7579, "EPS growth past 5 years" : -0.1334, "Return on Assets" : -0.2759, "Sector" : "Financial", "P/S" : 0.19, "Change from Open" : -0.0656, "Performance (YTD)" : 20.7857, "Performance (Week)" : -0.0469, "Insider Transactions" : 0.0156, "EPS growth quarter over quarter" : -1.9068, "Performance (Quarter)" : 1.1786, "200-Day Simple Moving Average" : 1.4685, "Shares Outstanding" : 15.41, "Earnings Date" : ISODate("2012-06-19T04:00:00Z"), "52-Week High" : -0.2083, "P/Cash" : 1.65, "Change" : -0.0656, "Volatility (Week)" : 0.0553, "Country" : "USA", "Return on Equity" : -15.1338, "50-Day Low" : 0.9521, "Price" : 2.85, "50-Day High" : -0.1739, "Shares Float" : 15.2, "EPS growth next 5 years" : 0.1, "Industry" : "Property & Casualty Insurance", "Beta" : 1.4, "Sales growth quarter over quarter" : -0.3021, "Operating Margin" : -0.6072, "EPS (ttm)" : -10.7, "Float Short" : 0.0002, "52-Week Low" : 27.5, "Average True Range" : 0.18, "Sales growth past 5 years" : -0.069, "Company" : "Affirmative Insurance Holdings Inc.", "Gap" : 0, "Relative Volume" : 2.61, "Volatility (Month)" : 0.0576, "Market Cap" : 47, "Volume" : 31375, "Short Ratio" : 0.21, "Performance (Half Year)" : 7.0263, "Relative Strength Index (14)" : 52.43, "Insider Ownership" : 0.0018, "20-Day Simple Moving Average" : 0.0455, "Performance (Month)" : 0.2348, "Institutional Transactions" : -0.0026, "Performance (Year)" : 20.7857, "Average Volume" : 13.2, "EPS growth this year" : -0.8472, "50-Day Simple Moving Average" : 0.1705 }
{ "_id" : ObjectId("52853802bb1177ca391c1d35"), "Ticker" : "CSIQ", "Profit Margin" : -0.186, "Institutional Ownership" : 0.301, "EPS growth past 5 years" : -0.2267, "Total Debt/Equity" : 3.85, "Current Ratio" : 1, "Return on Assets" : -0.103, "Sector" : "Technology", "P/S" : 1.11, "Change from Open" : -0.0281, "Performance (YTD)" : 8.4118, "Performance (Week)" : 0.173, "Quick Ratio" : 0.9, "P/B" : 5.06, "EPS growth quarter over quarter" : 0.508, "Performance (Quarter)" : 2.0075, "Forward P/E" : 14.75, "200-Day Simple Moving Average" : 1.8843, "Shares Outstanding" : 43.91, "Earnings Date" : ISODate("2013-11-13T13:30:00Z"), "52-Week High" : -0.0136, "P/Cash" : 9.94, "Change" : -0.0063, "Analyst Recom" : 2, "Volatility (Week)" : 0.0757, "Country" : "Canada", "Return on Equity" : -0.728, "50-Day Low" : 1.3731, "Price" : 31.8, "50-Day High" : -0.0136, "Return on Investment" : -0.1, "Shares Float" : 32.37, "EPS growth next 5 years" : 0.1, "Industry" : "Semiconductor - Specialized", "Beta" : 3.54, "Sales growth quarter over quarter" : 0.092, "Operating Margin" : -0.076, "EPS (ttm)" : -3.83, "Float Short" : 0.0697, "52-Week Low" : 15.3077, "Average True Range" : 2, "EPS growth next year" : 5.0278, "Sales growth past 5 years" : 0.337, "Company" : "Canadian Solar Inc.", "Gap" : 0.0225, "Relative Volume" : 1.86, "Volatility (Month)" : 0.0731, "Market Cap" : 1405.09, "Volume" : 6080909, "Gross Margin" : 0.076, "Short Ratio" : 0.63, "Performance (Half Year)" : 3.5198, "Relative Strength Index (14)" : 72.85, "Insider Ownership" : 0.3109, "20-Day Simple Moving Average" : 0.2391, "Performance (Month)" : 0.5392, "Institutional Transactions" : 0.1105, "Performance (Year)" : 11.3077, "LT Debt/Equity" : 0.92, "Average Volume" : 3588.24, "EPS growth this year" : -1.147, "50-Day Simple Moving Average" : 0.5744 }
{ "_id" : ObjectId("52853803bb1177ca391c1e6a"), "Ticker" : "DQ", "Institutional Ownership" : 0.13, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 1.76, "Current Ratio" : 0.3, "Return on Assets" : -0.226, "Sector" : "Technology", "P/S" : 4.62, "Change from Open" : -0.0823, "Performance (YTD)" : 4.848, "Performance (Week)" : 0.2279, "Quick Ratio" : 0.2, "P/B" : 2.11, "EPS growth quarter over quarter" : -4.456, "Performance (Quarter)" : 3.9364, "200-Day Simple Moving Average" : 2.1946, "Shares Outstanding" : 6.92, "Earnings Date" : ISODate("2013-11-27T05:00:00Z"), "52-Week High" : -0.1445, "P/Cash" : 48.04, "Change" : -0.0995, "Volatility (Week)" : 0.1751, "Country" : "China", "Return on Equity" : -0.883, "50-Day Low" : 3.1629, "Price" : 41.92, "50-Day High" : -0.1445, "Return on Investment" : -0.186, "Shares Float" : 2.73, "Industry" : "Semiconductor Equipment & Materials", "Sales growth quarter over quarter" : 0.007, "Operating Margin" : -0.889, "EPS (ttm)" : -20.6, "Float Short" : 0.0854, "52-Week Low" : 11.3294, "Average True Range" : 4.44, "Company" : "Daqo New Energy Corp.", "Gap" : -0.0187, "Relative Volume" : 0.65, "Volatility (Month)" : 0.1302, "Market Cap" : 321.89, "Volume" : 101372, "Gross Margin" : -0.647, "Short Ratio" : 1.37, "Performance (Half Year)" : 4.7398, "Relative Strength Index (14)" : 61.31, "Insider Ownership" : 0.0689, "20-Day Simple Moving Average" : 0.186, "Performance (Month)" : 0.4055, "P/Free Cash Flow" : 2.03, "Institutional Transactions" : -0.0323, "Performance (Year)" : 10.6375, "LT Debt/Equity" : 0.94, "Average Volume" : 169.82, "EPS growth this year" : -3.808, "50-Day Simple Moving Average" : 0.5474 }
{ "_id" : ObjectId("52853800bb1177ca391c1822"), "Ticker" : "ACAD", "Institutional Ownership" : 0.929, "EPS growth past 5 years" : 0.25, "Total Debt/Equity" : 0, "Current Ratio" : 26.5, "Return on Assets" : -0.221, "Sector" : "Healthcare", "P/S" : 407.82, "Change from Open" : -0.0431, "Performance (YTD)" : 3.9419, "Performance (Week)" : 0.0943, "Quick Ratio" : 26.5, "Insider Transactions" : 0.2417, "P/B" : 10.54, "EPS growth quarter over quarter" : -0.1, "Performance (Quarter)" : 0.1713, "200-Day Simple Moving Average" : 0.4127, "Shares Outstanding" : 83.41, "Earnings Date" : ISODate("2013-11-06T21:30:00Z"), "52-Week High" : -0.2311, "P/Cash" : 9.33, "Change" : -0.0052, "Analyst Recom" : 1.9, "Volatility (Week)" : 0.0625, "Country" : "USA", "Return on Equity" : -0.268, "50-Day Low" : 0.1634, "Price" : 22.86, "50-Day High" : -0.2311, "Return on Investment" : -0.246, "Shares Float" : 90.65, "EPS growth next 5 years" : 0.2, "Industry" : "Biotechnology", "Beta" : 2.68, "Sales growth quarter over quarter" : -0.167, "EPS (ttm)" : -0.34, "Float Short" : 0.099, "52-Week Low" : 11.7, "Average True Range" : 1.8, "EPS growth next year" : -0.205, "Sales growth past 5 years" : -0.084, "Company" : "ACADIA Pharmaceuticals, Inc.", "Gap" : 0.0396, "Relative Volume" : 0.46, "Volatility (Month)" : 0.0706, "Market Cap" : 1916.76, "Volume" : 1066506, "Short Ratio" : 3.51, "Performance (Half Year)" : 0.7663, "Relative Strength Index (14)" : 50.31, "Insider Ownership" : 0.001, "20-Day Simple Moving Average" : 0.0006, "Performance (Month)" : -0.0065, "Institutional Transactions" : 0.0151, "Performance (Year)" : 9.891, "LT Debt/Equity" : 0, "Average Volume" : 2558.02, "EPS growth this year" : 0.136, "50-Day Simple Moving Average" : -0.0391 }
{ "_id" : ObjectId("52853802bb1177ca391c1d19"), "Ticker" : "CRRS", "Profit Margin" : 0.007, "EPS growth past 5 years" : 0.185, "Total Debt/Equity" : 0.86, "Current Ratio" : 1, "Return on Assets" : 0.098, "Sector" : "Services", "P/S" : 0.78, "Change from Open" : -0.0472, "Performance (YTD)" : 7.6, "Performance (Week)" : -0.0051, "Quick Ratio" : 1, "P/B" : 25.8, "EPS growth quarter over quarter" : 1.5, "Payout Ratio" : 0, "Performance (Quarter)" : 0.3163, "P/E" : 96.75, "200-Day Simple Moving Average" : 0.4325, "Shares Outstanding" : 156.75, "52-Week High" : -0.3897, "P/Cash" : 3033.19, "Change" : -0.1137, "Volatility (Week)" : 0.0535, "Country" : "USA", "Return on Equity" : 0.274, "50-Day Low" : 0.6411, "Price" : 3.43, "50-Day High" : -0.3897, "Return on Investment" : 0.115, "Shares Float" : 58.32, "Industry" : "Staffing & Outsourcing Services", "Beta" : 0.14, "Sales growth quarter over quarter" : 0.298, "Operating Margin" : 0.014, "EPS (ttm)" : 0.04, "Float Short" : 0.0061, "52-Week Low" : 8.8, "Average True Range" : 0.27, "Sales growth past 5 years" : 0.26, "Company" : "Corporate Resource Services, Inc.", "Gap" : -0.0698, "Relative Volume" : 0.76, "Volatility (Month)" : 0.072, "Market Cap" : 606.64, "Volume" : 189816, "Gross Margin" : 0.115, "Short Ratio" : 1.3, "Performance (Half Year)" : 1.4187, "Relative Strength Index (14)" : 40.38, "Insider Ownership" : 0.007, "20-Day Simple Moving Average" : -0.0785, "Performance (Month)" : 0.2132, "Performance (Year)" : 7.6, "LT Debt/Equity" : 0.09, "Average Volume" : 275.26, "EPS growth this year" : 1.333, "50-Day Simple Moving Average" : -0.1114 }
{ "_id" : ObjectId("52853810bb1177ca391c324b"), "Ticker" : "ZHNE", "Profit Margin" : 0.03, "Institutional Ownership" : 0.224, "EPS growth past 5 years" : 0.062, "Total Debt/Equity" : 0.28, "Current Ratio" : 2.2, "Return on Assets" : 0.057, "Sector" : "Technology", "P/S" : 1.07, "Change from Open" : -0.0224, "Performance (YTD)" : 7.5106, "Performance (Week)" : 0.0471, "Quick Ratio" : 1.5, "Insider Transactions" : -0.0348, "P/B" : 3.57, "EPS growth quarter over quarter" : 1.357, "Payout Ratio" : 0, "Performance (Quarter)" : 0.5267, "P/E" : 36.36, "200-Day Simple Moving Average" : 1.1282, "Shares Outstanding" : 31.48, "Earnings Date" : ISODate("2013-10-17T20:30:00Z"), "52-Week High" : -0.1441, "P/Cash" : 8.81, "Change" : -0.02, "Analyst Recom" : 2, "Volatility (Week)" : 0.0725, "Country" : "USA", "Return on Equity" : 0.108, "50-Day Low" : 0.4962, "Price" : 3.92, "50-Day High" : -0.1441, "Return on Investment" : -0.212, "Shares Float" : 29.78, "EPS growth next 5 years" : 0.23, "Industry" : "Communication Equipment", "Beta" : 1.98, "Sales growth quarter over quarter" : 0.079, "Operating Margin" : 0.033, "EPS (ttm)" : 0.11, "PEG" : 1.58, "Float Short" : 0.0272, "52-Week Low" : 8.8, "Average True Range" : 0.29, "Sales growth past 5 years" : -0.08, "Company" : "Zhone Technologies Inc.", "Gap" : 0.0025, "Relative Volume" : 0.27, "Volatility (Month)" : 0.0808, "Market Cap" : 125.92, "Volume" : 212517, "Gross Margin" : 0.376, "Short Ratio" : 0.92, "Performance (Half Year)" : 3.4944, "Relative Strength Index (14)" : 52.9, "Insider Ownership" : 0.015, "20-Day Simple Moving Average" : -0.0131, "Performance (Month)" : 0.3746, "Institutional Transactions" : 0.0241, "Performance (Year)" : 7.5106, "LT Debt/Equity" : 0, "Average Volume" : 876.5, "EPS growth this year" : 0.237, "50-Day Simple Moving Average" : 0.1266 }
{ "_id" : ObjectId("5285380cbb1177ca391c2dd8"), "Ticker" : "SPCB", "Institutional Ownership" : 0.079, "EPS growth past 5 years" : 0.156, "Total Debt/Equity" : 0, "Current Ratio" : 1.4, "Return on Assets" : 3.033, "Sector" : "Technology", "P/S" : 17.52, "Change from Open" : 0.0138, "Performance (YTD)" : 6.0678, "Performance (Week)" : 0, "Quick Ratio" : 1.3, "P/B" : 83.4, "EPS growth quarter over quarter" : 2, "Payout Ratio" : 0, "Performance (Quarter)" : 0.127, "P/E" : 37.91, "200-Day Simple Moving Average" : 0.6753, "Shares Outstanding" : 36.98, "52-Week High" : -0.304, "P/Cash" : 1541.94, "Change" : 0.0211, "Volatility (Week)" : 0.0473, "Country" : "Israel", "50-Day Low" : 0.1665, "Price" : 4.26, "50-Day High" : -0.2012, "Return on Investment" : 6.5, "Shares Float" : 3.31, "Industry" : "Security Software & Services", "Beta" : 0.81, "Sales growth quarter over quarter" : -0.091, "Operating Margin" : 0.273, "EPS (ttm)" : 0.11, "Float Short" : 0.0004, "52-Week Low" : 100.0325, "Average True Range" : 0.33, "Sales growth past 5 years" : -0.061, "Company" : "SuperCom Ltd.", "Gap" : 0.0072, "Relative Volume" : 0.77, "Volatility (Month)" : 0.081, "Market Cap" : 154.19, "Volume" : 12947, "Gross Margin" : 0.886, "Short Ratio" : 0.07, "Performance (Half Year)" : 2.5042, "Relative Strength Index (14)" : 46.11, "20-Day Simple Moving Average" : -0.0183, "Performance (Month)" : 0.0425, "P/Free Cash Flow" : 48.19, "Performance (Year)" : 7.1765, "LT Debt/Equity" : 0, "Average Volume" : 18.68, "EPS growth this year" : 0.556, "50-Day Simple Moving Average" : -0.0692 }
{ "_id" : ObjectId("52853806bb1177ca391c24fc"), "Ticker" : "JKS", "Profit Margin" : -0.164, "Institutional Ownership" : 0.087, "EPS growth past 5 years" : -0.583, "Total Debt/Equity" : 3.88, "Current Ratio" : 0.7, "Return on Assets" : -0.097, "Sector" : "Industrial Goods", "P/S" : 0.72, "Change from Open" : -0.0295, "Performance (YTD)" : 3.7005, "Performance (Week)" : 0.1593, "Quick Ratio" : 0.6, "P/B" : 3.03, "EPS growth quarter over quarter" : 1.157, "Performance (Quarter)" : 1.0542, "Forward P/E" : 59.57, "200-Day Simple Moving Average" : 1.3708, "Shares Outstanding" : 22.2, "Earnings Date" : ISODate("2013-11-18T13:30:00Z"), "52-Week High" : -0.0085, "P/Cash" : 2.67, "Change" : 0.0034, "Analyst Recom" : 2, "Volatility (Week)" : 0.0903, "Country" : "China", "Return on Equity" : -0.588, "50-Day Low" : 0.7827, "Price" : 29.29, "50-Day High" : -0.0085, "Return on Investment" : -0.226, "Shares Float" : 26.65, "EPS growth next 5 years" : 0.1, "Industry" : "Industrial Electrical Equipment", "Beta" : 3.88, "Sales growth quarter over quarter" : 0.426, "Operating Margin" : -0.106, "EPS (ttm)" : -6.63, "Float Short" : 0.0333, "52-Week Low" : 8.2107, "Average True Range" : 2.21, "EPS growth next year" : -0.569, "Sales growth past 5 years" : 0.466, "Company" : "JinkoSolar Holding Co., Ltd.", "Gap" : 0.0339, "Relative Volume" : 0.84, "Volatility (Month)" : 0.0878, "Market Cap" : 648.11, "Volume" : 1198309, "Gross Margin" : 0.117, "Short Ratio" : 0.57, "Performance (Half Year)" : 3.1229, "Relative Strength Index (14)" : 63.87, "20-Day Simple Moving Average" : 0.1702, "Performance (Month)" : 0.2831, "Institutional Transactions" : 0.0121, "Performance (Year)" : 6.7633, "LT Debt/Equity" : 0.3, "Average Volume" : 1564.49, "EPS growth this year" : 1.107, "50-Day Simple Moving Average" : 0.3102 }
{ "_id" : ObjectId("5285380ebb1177ca391c30dc"), "Ticker" : "VIPS", "Profit Margin" : 0.018, "Institutional Ownership" : 0.29, "EPS growth past 5 years" : 0, "Total Debt/Equity" : 0, "Current Ratio" : 1.5, "Return on Assets" : 0.045, "Sector" : "Services", "P/S" : 4.3, "Change from Open" : -0.0229, "Performance (YTD)" : 3.8761, "Performance (Week)" : 0.1902, "Quick Ratio" : 1.2, "P/B" : 24.5, "EPS growth quarter over quarter" : 2.455, "Payout Ratio" : 0, "Performance (Quarter)" : 1.1989, "Forward P/E" : 45.78, "P/E" : 189.11, "200-Day Simple Moving Average" : 1.1371, "Shares Outstanding" : 55.28, "Earnings Date" : ISODate("2013-11-11T21:30:00Z"), "52-Week High" : -0.0485, "P/Cash" : 12.51, "Change" : -0.0177, "Analyst Recom" : 2, "Volatility (Week)" : 0.1085, "Country" : "China", "Return on Equity" : 0.146, "50-Day Low" : 1.0125, "Price" : 85.45, "50-Day High" : -0.0485, "Return on Investment" : -0.154, "Shares Float" : 18.92, "EPS growth next 5 years" : 0.625, "Industry" : "Discount, Variety Stores", "Sales growth quarter over quarter" : 1.596, "Operating Margin" : 0.013, "EPS (ttm)" : 0.46, "PEG" : 3.03, "Float Short" : 0.1163, "52-Week Low" : 7.1459, "Average True Range" : 5.91, "EPS growth next year" : 1.0085, "Company" : "Vipshop Holdings Limited", "Gap" : 0.0053, "Relative Volume" : 1.11, "Volatility (Month)" : 0.0832, "Market Cap" : 4808.98, "Volume" : 1046761, "Gross Margin" : 0.232, "Short Ratio" : 2.1, "Performance (Half Year)" : 1.6218, "Relative Strength Index (14)" : 64.82, "Insider Ownership" : 0.1872, "20-Day Simple Moving Average" : 0.1635, "Performance (Month)" : 0.2779, "Institutional Transactions" : -0.1087, "Performance (Year)" : 6.6778, "LT Debt/Equity" : 0, "Average Volume" : 1045.85, "EPS growth this year" : 0.935, "50-Day Simple Moving Average" : 0.3282 }
{ "_id" : ObjectId("5285380cbb1177ca391c2df7"), "Ticker" : "SPWR", "Profit Margin" : -0.09, "Institutional Ownership" : 0.293, "EPS growth past 5 years" : -0.603, "Total Debt/Equity" : 1.04, "Current Ratio" : 1.6, "Return on Assets" : -0.068, "Sector" : "Technology", "P/S" : 1.55, "Change from Open" : -0.0071, "Performance (YTD)" : 4.8078, "Performance (Week)" : -0.0034, "Quick Ratio" : 1.3, "Insider Transactions" : -0.2788, "P/B" : 4.12, "EPS growth quarter over quarter" : 1.211, "Performance (Quarter)" : 0.5799, "Forward P/E" : 27.43, "200-Day Simple Moving Average" : 0.6796, "Shares Outstanding" : 120.94, "Earnings Date" : ISODate("2013-10-30T20:05:00Z"), "52-Week High" : -0.0517, "P/Cash" : 5.8, "Change" : 0.0282, "Analyst Recom" : 2.6, "Volatility (Week)" : 0.065, "Country" : "USA", "Return on Equity" : -0.228, "50-Day Low" : 0.5324, "Price" : 33.56, "50-Day High" : -0.0517, "Return on Investment" : -0.166, "Shares Float" : 42.03, "EPS growth next 5 years" : 0.3, "Industry" : "Semiconductor - Specialized", "Beta" : 2.21, "Sales growth quarter over quarter" : -0.033, "Operating Margin" : -0.023, "EPS (ttm)" : -1.93, "Float Short" : 0.2813, "52-Week Low" : 7.6051, "Average True Range" : 2.04, "EPS growth next year" : -0.1427, "Sales growth past 5 years" : 0.256, "Company" : "SunPower Corporation", "Gap" : 0.0355, "Relative Volume" : 1.41, "Volatility (Month)" : 0.0618, "Market Cap" : 3947.58, "Volume" : 4271276, "Gross Margin" : 0.114, "Short Ratio" : 3.53, "Performance (Half Year)" : 0.6361, "Relative Strength Index (14)" : 57.99, "Insider Ownership" : 0.004, "20-Day Simple Moving Average" : 0.0325, "Performance (Month)" : 0.056, "P/Free Cash Flow" : 19.99, "Institutional Transactions" : -0.0142, "Performance (Year)" : 6.4862, "LT Debt/Equity" : 0.75, "Average Volume" : 3346.09, "EPS growth this year" : 0.521, "50-Day Simple Moving Average" : 0.1723 }
>
```

4. Qual foi o setor mais rentável?

```shell script
	db.stocks.aggregate(
		[ 
			{
				$group:
				{
					_id: { sector: "$Sector" },
					totalAmount: { $sum: "$Profit Margin" }
				}
			},
			{
				$sort:{ totalAmount:-1 }
			},
			{
				$limit: 1
			}
		]
	)
```

5. Ordene as ações pelo profit e usando um cursor, liste as ações.

```shell script
	var cursor = db.stocks.find({}).sort({'Profit Margin':-1});
	while (cursor.hasNext()) {
	   var atual = cursor.next();
	   printjson(atual);
	}
```

6. Renomeie o campo “Profit Margin” para apenas “profit”.

```shell script
	db.stocks.updateMany({}, {$rename:{"Profit Margin": "profit"}})
```

7. Agora liste apenas a empresa e seu respectivo resultado

```shell script
	db.stocks.find({},{'Company':1,'profit':1})
```

8. Analise as ações. É uma bola de cristal na sua mão... Quais as três ações
você investiria?

```shell script
	db.stocks.find({}).sort({'20-Day Simple Moving Average':-1}).limit(3)
```

9. Liste as ações agrupadas por setor


```shell script
db.stocks.aggregate([
   {$group : {_id : "$Sector"}}
])
```


### Exercício 3 – Fraude na Enron!


1. Liste as pessoas que enviaram e-mails (de forma distinta, ou seja, sem repetir). Quantas pessoas são?
```shell script
	db.enron.distinct("sender")
```


2. Contabilize quantos e-mails tem a palavra “fraud”
```shell script
	db.enron.find({text: {$regex: /fraud/i}}).count()
```