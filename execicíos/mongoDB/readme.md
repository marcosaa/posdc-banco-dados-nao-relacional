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
```


14. Infelizmente o Leão comeu o italiano. Remova essa pessoa usando o Id.

``` shell
```


15. Passou um ano. Atualize a idade de todos os italianos e dos bichanos em 1.


``` shell
```


16. O Corona Vírus chegou na Itália e misteriosamente atingiu pessoas somente com gatos e de 66 anos. Remova esses italianos.

``` shell
```


17. Utilizando o framework agregate, liste apenas as pessoas com nomes iguais a sua respectiva mãe e que tenha gato ou cachorro.


``` shell
```

18. Utilizando aggregate framework, faça uma lista de nomes única de nomes. Faça isso usando apenas o primeiro nome

``` shell
```


19. Agora faça a mesma lista do item acima, considerando nome completo.


``` shell
```


20. Procure pessoas que gosta de Banana ou Maçã, tenham cachorro ou gato, mais de 20 e menos de 60 anos.


``` shell
```

