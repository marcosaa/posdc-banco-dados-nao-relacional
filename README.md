# posdc-banco-dados-nao-relacional
Banco de dados não relacional

## data
04/04/2020
Marcio Jasinski
47 99653-4899


## Exercício

### REDIS

#### fontes

* https://github.com/rafaelmatiello/posdc-banco-dados-nao-relacional/tree/master/workspace/redinsgo

#### Como utilizar
* Executa o arquivo em java	/redinsgo/src/main/java/com/github/rafaelmatiello/redinsgo/App.java

#### print da execução

* https://github.com/rafaelmatiello/posdc-banco-dados-nao-relacional/tree/master/workspace/redinsgo/SaidaDoSistema.png


#### console

Exemplo de saida | 
------------ |
Ganhadores: 
user:36
Cartela: 7 10 13 18 34 37 38 41 47 54 56 64 80 81 96 
Bolar sorteadas (69): 1 2 3 4 5 7 8 9 10 11 12 13 15 16 17 18 21 23 25 26 29 30 31 32 33 34 36 37 38 39 41 42 44 45 47 49 50 52 54 55 56 58 60 61 62 63 64 65 66 67 69 70 71 76 78 79 80 81 82 83 85 86 89 92 94 95 96 97 98 |


## material
* http://bit.ly/furb-nosql-202004
* https://go.microsoft.com/fwlink/?linkid=2062135
* https://www.dropbox.com/sh/45stbqgbj2nzuy5/AADmx9ytxMNIWk_9UwqyqlDHa?dl=0

## open source
## Teorema cap
 - 

## NoSQL, 

### caracteristica

- escala
 * Geral
	- Data center diferentes, Brasil, EUA e Alemanha.
 * Relacional:
	- escala a máquina.
	- transação, deve se comunicar entre todos os servidores, a latencia impacta na performance. 
	- Tempo de resposta.
	- Custo de licenciamente, muito alto, porque precisa uma licensa para cada servidores.
	- Quantidade de nós distribuídas.
 * Não relacional:
	- deve ser escalado horizontalmente.
	- considera que pode acontecer erro em nós, imaginando vários datacentes, sempre algum vai falhar.
	- Quando um nos, falhar, descarta e carrega um novo, nós
	- Nem tudo precisa de transação, imagina conversar com todos os nós, para locar.
	- Sem custo de licensa, 
	
- distribuição de dados (particionamento)
 * Sharding
  
- Consistência - ACID
 * Atomicity, ou tudo ou nada
 * Consistency, consitencia dos dados 
 * Isolation, isolado outra transação não pode atrapalhar
 * Durability, uma vez feito em disco não volta
 
  - problemas
	* nem tudo precisa ser ACID; 
	* Atender volume e velocidade;
	* alternativa SAGA, não apaga a situação, defaz, exemplo banco adicina, valor, negativa valor;

- Base
	* 

- Schemaless
 * Menor foco na estrutura dos dados;
 * relaciona, tudo começa pela strutura dos dados.
 * diferentes schemas na mesma strutura.
 * alterar schema é normal
 * Schema on write vs Schema on read
	- relacional, on write, se não tiver a estrutura não grava
	- não relacional, você informa o que quer ler, por não ter uma lista de filhos.

- diferentes forma de acesso
 * API
 * Rest
 * linguagem própria de consulta
 * SQL, casandra ou Hbase
 
- Teorema de cap
 * Quanto preparado a falhar o sistema esta?
 * Não ter 3 ao mesmo tempo, consitência, disponibilidade e tolerância a falha, ao mesmo tempo.
 * algoritmo de corum, consetimento.
 * consistência
	* W 5, 5 nós precisa confirmar para confirmar a escrita, depois replica para todos
	* R 3, 3 nos tem que confirmar a informação para depois retornar. Se um falhar, passa para outro.
 * disponiblidade
	* sempre retornar um informação, 
 * tolerancia a particionamento
	* Não vai falhar se um nó cair,
	* vai se recuperar a trabalhar para retornar.

 * P -> C, a aplicação aguarda restabecer o banco
 * p -> A, 
 
- Chave valor
 * simples
 * alta performance
 * conceito de Mapeamento Hash Distribuído.
 * valor não é indexavél, somente a chave, todo o dado é via chave
 - Caracteristicas
	* escalabilidade
	* produtividade
	* performance, opera em memória
	* baixa complexibilidade
	* schameless
	* funcionalidade limita a chave-valor
 - Evitar
	* dados não indexados, e precisam buscar.
	* Não é indicado relacionando entre os dados.
	* Não deve ter transação, não é tão bom
	* busca sobre conteúdo de dados.
 - Usos indicados
	* armazenar sessão
	* compras carrinhos
	* perfil e preferências.
	
- Banco de dados por documento
 * documento, no centro
 * cada documento é um linha, com todos os join possíveis.
 * cada documento é associado a 
 * dbrefm evutar utiliza
 * indexar, keys, values
 * caracteristicas
	* escalabilidade( master write)
	* alta produtivdade
	* performance
	* baixa complexibilidade
 * indicados
	* Armazenamento de notas.
	* Armazenamento de eventos com detalhes.
	* Ambiente de gestão de conteúdo
	* solução de esquemas bem dinâmicos
	* detalhamento produtos.
	* schema bem dinâmicos
 * evitar utilizar
	* relacionamento de dados
	* precisar transações
	* buscas complexas com Join

Diferente chave valor x documento.	
Documento: 
	* tipagem
	* json faz parte dos metadatos
	* operações facilitadas


- Banco de grafos
 * neo for j
 * conceito de grafos
 * modelo proximo a redes social.
 * caracteristica
	- neo4j, escala utilizando sharding.
	- ACID-compliant
	- alta disponibilidade, sem dificultado na replica
	- algoritmos padrões, já otimizados
 * utilização
	- uso: modelagem de ambiente de redes
	- usu: custo de chamar a um servidor
	- usu: jogos, busca 
 * não recomemdado
	- se for difícil de modelar em grafos
	- solução clássicas em relacional
	- relatórios em geral

- Familia de colunas
 * cassandra
 * parecido com relacional
 * Flexivel no strutura de colunas
 * problemas do google e facebook, muito informações e distruído.
	- todo mundo que deu like em um noticia.
	- contabilizar, likes, idade...
 * foco em DISTRIBUÍDOS	
	* particionar processo para cada um pegar sou pedaço
 * Tabelas espaças
	- muitos nulls
 * vantagens
	* apenas valores que são necessárias
 * caracteristicas
	* altas escalabilidade alto volume
	* mais simples que conhece o relacional
	* não indicado para prototipagem
 * onde utilizar
	* demanda de escla
	* solução analítica
	* alta performance
	
 * onde não utilizar
 
- outros
 - Livros contábeis (QLDB)
	* operações funcionassem com estorico
 - Blockchain( hyperledger, ethereum)
	* dados só podem ser adicionados
 - Séries temporáis
	* todos os registros datas 
 - im memória,
	* parecido com chave val9or
 - Files system(HDfs)
 
* persistência poliglota
 - banco espeficio para cada problema.


## Key value

	* baseado em conceito HASH.
		* sem hash o custo é O(n), n é o tamanho da estrutura.
	* derivar um número pela posição
	* hash mod 11, da a posição aonde vai inserir, o indice.
	* dicionários de tabela hash, distribuído entre os nós, este pode se memória ou persististido.

### REDIS
	* baseado em HASH
	* performance, acessar o dado pela chave? sim, top
	* utilização
		- cache de dados
		- publish/Subscriber e filas,
		- contadores.
	* perdeu a chave,
		- busca sequencial
	* comandos
		- set key value
		- get key
			- não existe nil
		- del key...
		- incr key
			- incrementa de forma atomica.
			- erro se não for um número
			- exemplo: limite de chamadas por tempo, 10 chamada por segundo
		- incrby key 10
			-  somenta mais 10, dinamicamente.
		- Listas
			- rpush, lpush, llen, lrange
			- rpush
				- rpush lista "hello", final
				- lpush lista "helow", inicio
			- lrange lista 0 -1, lista do inicio ao final da lista
			- rrange lista 0 -1, lista ao contrário
			- lne
	

### MONGODB
	* orientado a documento.
	* relaciomento exitem, não utilizar
	* dados AGREGADOS
	* ausencia de schema
	* simples remover e adicionar campo
	* validação fica para aplicação
	* escala horizontalmente
	* facilmente espelhar documentos
	* balanciamento de carga, e distribuição de dados
	* positivo
		* escala
	* negativo
		* geranciar os nós
	
	* perde
		* join
		* dados tabulados
		* operações em colunas
		* manipulação de dados
		* formalismo, busca de colunas e as colunas não existem mais.
			* erra o nome da coluna na gravação, começa a criar documentos com colunas erradas.
		* atributo com nome errado, é mais lento, porque ele procura o dado em cada registro.
	* Documento é sempre um JSON
	* chave é sempre string, . e $ não deve ser utilizado
	* armazendo é bson
	* case sensitive
	* não pode duplicar chaves, no mesmo json
	* chaves e valor, são ordenandos
	* coleções, são as tabelas
		* cada documento pode ter objetos diferentes
	* porque separar em coleções
		* coleção de pessoas, e colocar as pessoas juntos.
			* mais organizados
		* Se não tiver em uma validação de leitura tem que saber qual o tipo de registro
		* performance, mais interessante agrupar os dados próximos, mais rápido de consultar
		* muitos documentos e campos, muito indices e buscar, dificulta performance e 
	* coleção	
		* nomes,
		*proibidos
			* system.user
			* system.namesspace
		* pode usar . para organização, mas não muda nada para o banco, é pessoal, ex "blog.sistes"
		* subcoleções
		* arquivos grande 16 mb, 
	* database
		* diversos databases
		* não ultrapassar 64 bits
	* database
		* arquivo com o nome
	* command
		* interpretador javascript
		* 3 enter sai do modelo.
	* new Date()	
		* utilizar para data
		ISODate("")
	* mongorx.js
		* dentro do home, 
		* script de inicialição
	* operações atomicos
		*$unset
		* $set
		* $inc
	* update
		* mata o documento anterior
	
	*chocolatey, docker, 
	* mod divisão
	* perl para like
	* evitar utilizar o where, mais lenta.
	
## Grafos
	* http://www.inf.ufsc.br/grafos/definicoes/definicao.html
	* Não tem schema
		* pode misturar o que quiser
	* vantagens	
		* simplifica os relacionamentos

### Neo4j
	* Node - vertices
	* relationshiphs (edges) - arestas
	* Grafos sempre são rotulados- Label
	* pode ter mais atrestas ligando a um vertices	
		* pessoa, mora da cara e dona da casa
	* arestas tem rotulos e valores
		* cassado
			* since: 2005-02-14
	* node
		* tem propriedades
		* Person
			* name: DAN
			* born: 1975
	* relacional
		* row -> nodes
		* join -> Relationships
		* table -> labels
		* columns -> properties
	* Labels
		* não precisam ter as mesmas propriedades
	* Join
		* são armazenados na disco
	* Linha Pertence a uma tabela	
		* node pode ter multiplos nodos
	* relacional, monta o scheme depois usa. Neo, flexibiliza para evoluir
	* relacional, abstrai o objeto/classe, Neo, modela a partir dos dados.
	* ACID
	* read_comited
		* só le o que foi comitada
	* cypher
		* (vertice)
		* -> arresta
		* (A) - [:LIKES] -> (B), A deu LIKES em B
		* () vertica anônimo
		* (n) Vertice representando para n
	* install docker
		*  docker run -it --rm --publish=7474:7474 --publish=7687:7687-e NEO4J_dbms_connector_https_advertised__address="localhost:7473" -e NEO4J_dbms_connector_http_advertised__address="localhost:7474" -e NEO4J_dbms_connector_bolt_advertised__address="localhost:7687" --env=NEO4J_AUTH=none neo4j
	
## Colunas

### HBase
	* Bigtable depois for renomeado para HBase
	* hadoop - processamento distribuído.
	* capacidade de banco,
	* concorrente do casandra.
	* baixa latência
	* Hive, drill e phoenix
	* Não tem muita consistência
	* Não da para criar novas familias de colunas
	* row key - 039.786.879-09
		* person-data
			* name
				* rafael v1
			*andress
				* Xaxim - v1
				* Faxinal - v2
				* Xanxerê - v3
				* Blumenau - v4
		* professional-data
			* job
				* programador v1
				* projetista v2
				* Arquiteto v3
			*salary
				* 7500 v1
	* não pode perder a chave
	* não é tipadoe nem tamanho
	* Não pode mexer na familia de colunas
	* disable -> altera -> enable
	* não pode renomear colunas
	
	
	
 

 
 