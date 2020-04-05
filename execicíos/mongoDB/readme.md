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
```


11. Projete apenas os animais dos italianos. Devem ser listados os animais com nome e idade. Não mostre o identificado do mongo (ObjectId)

``` shell
```


12. Quais são as 5 pessoas mais velhas com sobrenome Rossi?

``` shell
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

