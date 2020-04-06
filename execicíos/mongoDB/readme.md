## Exercícios sobre MongoDB


## Exercício 1- Aquecendo com os pets

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


## Exercício 2 – Mama mia!
Importe o arquivo dos italian-people.js do seguinte endereço: Downloads NoSQL
FURB. Em seguida, importe o mesmo com o seguinte comando:
mongo italian-people.js

Analise um pouco a estrutura dos dados e em seguida responda:

1. Liste/Conte todas as pessoas que tem exatamente 99 anos. Você pode
usar um count para indicar a quantidade.

``` shell
> db.italians.find({"age":99})
> db.italians.find({"age":99}).count()
0
```

2. Identifique quantas pessoas são elegíveis atendimento prioritário
(pessoas com mais de 65 anos)

``` shell
> db.italians.find({"age" : {"$gte" : 65}}).count()
5214
```


3. Identifique todos os jovens (pessoas entre 12 a 18 anos).

``` shell
> db.italians.find({"age" : {"$gte" : 12, "$lte" : 18}}).count()
2502
```


4. Identifique quantas pessoas tem gatos, quantas tem cachorro e quantas
não tem nenhum dos dois

``` shell
> db.italians.find({"cat" : { "$exists" : true} }).count()
16994
> db.italians.find({"dog" : { "$exists" : true} }).count()
11411
> db.italians.find({"dog" : { "$exists" : false}, "cat" : { "$exists" : false} }).count()
6774
```


5. Liste/Conte todas as pessoas acima de 60 anos que tenham gato

``` shell
> db.italians.find({"age" : { "$gt" : 60}, "cat" : { "$exists" : true} }).count()
3938
```


6. Liste/Conte todos os jovens com cachorro

``` shell
> db.italians.find({"age" : { "$gte" : 14, "$lte": 29 }, "dog" : { "$exists" : true} }).count()
2259
```


7. Utilizando o $where, liste todas as pessoas que tem gato e cachorro

``` shell
> db.italians.find({ "$where": "this.dog != null && this.cat != null"}).count()
6906
```


8. Liste todas as pessoas mais novas que seus respectivos gatos.

``` shell
> db.italians.find({ "$and" : [ {"cat": {$exists: true}}, {"$where" : "this.age < this.cat.age" } ]}).count()
1887
```


9. Liste as pessoas que tem o mesmo nome que seu bichano (gatou ou cachorro)

``` shell
> db.italians.findOne( { "$or" :[ { "$and" : [{"cat": { "$exists": true}}, {"$where" : "this.name == this.cat.name" }]},  { "$and" : [{"dog": { "$exists": true}}, {"$where" : "this.name == this.dog.name" }]} ]})
null
```


10. Projete apenas o nome e sobrenome das pessoas com tipo de sangue de fator RH negativo

``` shell
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

``` shell
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

``` shell
> db.italians.find({ "surname": "Rossi"}).sort({ "age": -1}).limit(5)
{ "_id" : ObjectId("5e8a4ca9c9d7b9bfa2536eef"), "firstname" : "Stefano", "surname" : "Rossi", "username" : "user3580", "age" : 79, "email" : "Stefano.Rossi@uol.com.br", "bloodType" : "B+", "id_num" : "801060025566", "registerDate" : ISODate("2018-10-21T06:38:06.120Z"), "ticketNumber" : 5721, "jobs" : [ "Ciências Sociais e Humanas", "Gestão de Recursos Humanos" ], "favFruits" : [ "Tangerina", "Mamão", "Uva" ], "movies" : [ { "title" : "O Resgate do Soldado Ryan (1998)", "rating" : 3.93 }, { "title" : "Seven: Os Sete Crimes Capitais (1995)", "rating" : 2.35 }, { "title" : "O Resgate do Soldado Ryan (1998)", "rating" : 2.22 } ], "cat" : { "name" : "Daniele", "age" : 17 }, "dog" : { "name" : "Sonia", "age" : 0 } }
{ "_id" : ObjectId("5e8a4cb3c9d7b9bfa2537ef3"), "firstname" : "Valeira", "surname" : "Rossi", "username" : "user7680", "age" : 79, "email" : "Valeira.Rossi@uol.com.br", "bloodType" : "AB-", "id_num" : "084563063048", "registerDate" : ISODate("2018-08-10T18:55:36.610Z"), "ticketNumber" : 5760, "jobs" : [ "Psicologia" ], "favFruits" : [ "Banana", "Goiaba", "Pêssego" ], "movies" : [ { "title" : "Matrix (1999)", "rating" : 3.6 }, { "title" : "Star Wars, Episódio V: O Império Contra-Ataca (1980)", "rating" : 1.58 } ] }
{ "_id" : ObjectId("5e8a4cd3bb206c9231199037"), "firstname" : "Elisabetta", "surname" : "Rossi", "username" : "user4728", "age" : 79, "email" : "Elisabetta.Rossi@hotmail.com", "bloodType" : "A+", "id_num" : "070152032267", "registerDate" : ISODate("2014-08-21T08:49:41.378Z"), "ticketNumber" : 3626, "jobs" : [ "Construção Civil", "Farmácia" ], "favFruits" : [ "Tangerina", "Tangerina" ], "movies" : [ { "title" : "A Viagem de Chihiro (2001)", "rating" : 0.4 }, { "title" : "Gladiador (2000)", "rating" : 1.74 }, { "title" : "O Senhor dos Anéis: As Duas Torres (2002)", "rating" : 0.79 }, { "title" : "O Poderoso Chefão II (1974)", "rating" : 4.35 } ], "dog" : { "name" : "Alessandra", "age" : 7 } }
{ "_id" : ObjectId("5e8a4a00e9112505eb8cc705"), "firstname" : "Monica", "surname" : "Rossi", "username" : "user1782", "age" : 78, "email" : "Monica.Rossi@outlook.com", "bloodType" : "O-", "id_num" : "863318766101", "registerDate" : ISODate("2008-09-21T00:40:00.724Z"), "ticketNumber" : 7254, "jobs" : [ "Eventos" ], "favFruits" : [ "Uva", "Melancia" ], "movies" : [ { "title" : "Star Wars, Episódio V: O Império Contra-Ataca (1980)", "rating" : 4.69 } ] }
{ "_id" : ObjectId("5e8a4ca2c9d7b9bfa2536409"), "firstname" : "Maurizio", "surname" : "Rossi", "username" : "user790", "age" : 78, "email" : "Maurizio.Rossi@outlook.com", "bloodType" : "B-", "id_num" : "486404162370", "registerDate" : ISODate("2008-09-05T10:14:16.745Z"), "ticketNumber" : 9185, "jobs" : [ "Ciências Naturais e Exatas" ], "favFruits" : [ "Maçã" ], "movies" : [ { "title" : "Clube da Luta (1999)", "rating" : 3.05 }, { "title" : "A Felicidade Não se Compra (1946)", "rating" : 1.96 } ], "cat" : { "name" : "Domenico", "age" : 11 }, "dog" : { "name" : "Roberta", "age" : 8 } }
>
```


13. Crie um italiano que tenha um leão como animal de estimação. Associe um nome e idade ao bichano

``` shell
> db.italians.insert({ "firstname" : "Josepe", "surname" : "Matiello", "username" : "Josepe0007", "age" : 50, "email" : "josepe.matiello@hotmail.com", "bloodType" : "A-", "id_num" : "123475327074", "registerDate" : ISODate("2011-02-04T05:04:49.828Z"), "ticketNumber" : 6118, "jobs" : [ "Estudos de Gênero e Diversidade" ], "favFruits" : [ "Laranja", "Uva" ], "movies" : [ { "title" : "Interestelar (2014)", "rating" : 2.02 } ], "leao" : { "name" : "Lion", "age": 7} })
WriteResult({ "nInserted" : 1 })
```


14. Infelizmente o Leão comeu o italiano. Remova essa pessoa usando o Id.

``` shell
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


``` shell
> db.italians.update({}, { "$inc" : { "age": 1 }}, {multi: true})
WriteResult({ "nMatched" : 28078, "nUpserted" : 0, "nModified" : 28078 })
> db.italians.update({"cat" : {"$exists" : true}}, { "$inc" : { "cat.age": 1 }}, {multi: true})
WriteResult({ "nMatched" : 16799, "nUpserted" : 0, "nModified" : 16799 })
> db.italians.update({"dog" : {"$exists" : true}}, { "$inc" : { "dog.age": 1 }}, {multi: true})
WriteResult({ "nMatched" : 11329, "nUpserted" : 0, "nModified" : 11329 })
```


16. O Corona Vírus chegou na Itália e misteriosamente atingiu pessoas somente com gatos e de 66 anos. Remova esses italianos.

``` shell
> db.italians.remove({ "$and" :[{"cat" : {"$exists" : true}}, { age :  66}]})
WriteResult({ "nRemoved" : 195 })
```


17. Utilizando o framework agregate, liste apenas as pessoas com nomes iguais a sua respectiva mãe e que tenha gato ou cachorro.


``` shell

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

``` shell
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


``` shell
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

``` shell
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

``` shell
mongoimport --db stocks --collection stocks --file stocks.json
```

Analise um pouco a estrutura dos dados novamente e em seguida, responda as
seguintes perguntas:
1. Liste as ações com profit acima de 0.5 (limite a 10 o resultado)

``` shell
```

2. Liste as ações com perdas (limite a 10 novamente)

``` shell
```

3. Liste as 10 ações mais rentáveis

``` shell
```

4. Qual foi o setor mais rentável?

``` shell
```

5. Ordene as ações pelo profit e usando um cursor, liste as ações.

``` shell
```

6. Renomeie o campo “Profit Margin” para apenas “profit”.

``` shell
```

7. Agora liste apenas a empresa e seu respectivo resultado

``` shell
```

8. Analise as ações. É uma bola de cristal na sua mão... Quais as três ações
você investiria?

``` shell
```

9. Liste as ações agrupadas por setor


``` shell
```

