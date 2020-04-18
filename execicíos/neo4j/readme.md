# Atividade Prática – Neo4j

## Exercício 1- Retrieving Nodes


1. Retrieve all nodes from the database.

    ``` shell
   MATCH (n) RETURN n
    ```

2. Examine the data model for the graph.
    ``` shell
   call db.schema.visualization()
    ```

3. Retrieve all Person nodes.
    ``` shell
    MATCH (p:Person) RETURN p
    ```

4. Retrieve all Movie nodes.

    ``` shell
   MATCH (m:Movie) RETURN m
    ```

## Exercício 2 – Filtering queries using property values

1. Retrieve all movies that were released in a specific year.
    ``` shell
    MATCH (m:Movie {released:2003}) RETURN m
    ```
2. View the retrieved results as a table.
    ``` shell
    MATCH (n:Movie) RETURN n
    ```
   
    ``` json
       {
         "title": "The Matrix",
         "tagline": "Welcome to the Real World",
         "released": 1999
       }
    ```
   
    
    
3. Query the database for all property keys.
    ``` shell
    CALL db.propertyKeys
    ```
   
4. Retrieve all Movies released in a specific year, returning their titles.
    ``` shell
    MATCH (m:Movie {released: 2006}) RETURN m.title
    ```
5. Display title, released, and tagline values for every Movie node in the graph.
    ``` shell
   MATCH (m:Movie ) RETURN m.title, m.released,m.tagline
    ```
   
6. Display more user-friendly headers in the table
    ``` shell
    MATCH (m:Movie) RETURN m.title AS `Título`, m.released AS `Ano de Lançamento`, m.tagline AS `Slogan`
    ```

## Exercício 3 - Filtering queries using relationships

1. Display the schema of the database.
    ``` shell
    call db.schema.visualization()
    ```
2. Retrieve all people who wrote the movie Speed Racer.
    ``` shell
    MATCH (p:Person)-[:WROTE]->(:Movie {title: 'Speed Racer'}) RETURN p.name
    ```
    Retrieve people who have acted in a particular movie.
    ``` shell
       MATCH (p:Person)-[:ACTED_IN]->(:Movie {title: 'Speed Racer'}) RETURN p.name
       MATCH (p:Person)-[:DIRECTED]->(:Movie {title: 'Speed Racer'}) RETURN p.name
    ```
   
    Retrieve people who have directed a particular movie.
    ``` shell
          MATCH (p:Person)-[:DIRECTED]->(:Movie {title: 'Speed Racer'}) RETURN p.name
    ```
   
3. Retrieve all movies that are connected to the person, Tom Hanks.
    Minha   
    ``` shell
    MATCH (p:Person  {name: 'Tom Hanks'})-->(m:Movie) RETURN m.title
    ``` 
    Exercício
    
    ``` shell
    MATCH (m:Movie)<--(:Person {name: 'Tom Hanks'}) RETURN m.title
    ```
    Retrieve all movies connected with another actor.
    ``` shell
    MATCH (m:Movie)<--(:Person {name: 'Hugo Weaving'}) RETURN m.title
    ```
   
    Retrieve all people connected with a particular movie.
    ``` shell
    MATCH (m:Movie {title: 'The Matrix'})<--(p:Person) RETURN p.name
    ```
   
   
4. Retrieve information about the relationships Tom Hanks had with the set of movies retrieved earlier.
    ``` shell
    MATCH (m:Movie)-[rel]-(:Person {name: 'Tom Hanks'}) RETURN m.title, type(rel)
    ```
   
5. Retrieve information about the roles that Tom Hanks acted in.
    ``` shell
    MATCH (m:Movie)-[rel:ACTED_IN]-(:Person {name: 'Tom Hanks'}) RETURN m.title, rel.roles
    ```
   Retrieve all roles played for a particular movie.
   ``` shell
   MATCH (m:Movie {title:'The Matrix'})-[rel]-(:Person) RETURN m.title, rel.roles
   ```

## Exercício 4 – Filtering queries using WHERE clause

1. Retrieve all movies that Tom Cruise acted in.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WHERE a.name = 'Tom Cruise'
   RETURN m.title
    ```
2. Retrieve all people that were born in the 70’s.
    ``` shell
    MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
    WHERE a.born >= 1970 and a.born < 1980
    RETURN a.name as Name, a.born as Born
    ```   
   
3. Retrieve the actors who acted in the movie The Matrix who were born after 1960.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WHERE m.title = 'The Matrix' and a.born > 1960
   RETURN a.name as Name, a.born as Born
    ```
4. Retrieve all movies by testing the node label and a property.
    ``` shell
    MATCH (m)
    WHERE m:Movie AND m.released = 2000
    RETURN m.title
    ```
5. Retrieve all people that wrote movies by testing the relationship between two nodes.
    ``` shell
   MATCH (a)-[rel]->(m)
   WHERE a:Person AND type(rel) = 'WROTE' AND m:Movie
   RETURN a.name as Name, m.title as Movie
    ```
   
   
6. Retrieve all people in the graph that do not have a property.
    ``` shell
   MATCH (a:Person)
   WHERE not exists(a.born)
   RETURN a.name as Name
    ```

7. Retrieve all people related to movies where the relationship has a property.
    ``` shell
   MATCH (a:Person)-[rel]->(m:Movie)
   WHERE exists(rel.rating)
   RETURN a.name as Name, m.title as Movie, rel.rating as Rating
    ```
   
8. Retrieve all actors whose name begins with James.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(:Movie)
   WHERE a.name STARTS WITH 'James'
   RETURN a.name
    ```

9. Retrieve all all REVIEW relationships from the graph with filtered results.
    ``` shell
   MATCH (:Person)-[r:REVIEWED]->(m:Movie)
   WHERE toLower(r.summary) CONTAINS 'fun'
   RETURN  m.title as Movie, r.summary as Review, r.rating as Rating
    ```

10. Retrieve all people who have produced a movie, but have not directed a movie.
    ``` shell
    MATCH (a:Person)-[:PRODUCED]->(m:Movie)
    WHERE NOT ((a)-[:DIRECTED]->(:Movie))
    RETURN a.name, m.title
    ```

11. Retrieve the movies and their actors where one of the actors also directed the movie.
    ``` shell
    MATCH (a1:Person)-[:ACTED_IN]->(m:Movie)<-[:ACTED_IN]-(a2:Person)
    WHERE exists( (a2)-[:DIRECTED]->(m) )
    RETURN  a1.name as Actor, a2.name as `Actor/Director`, m.title as Movie
    ```

12. Retrieve all movies that were released in a set of years.
    ``` shell
    MATCH (m:Movie)
    WHERE m.released in [2000, 2004, 2008]
    RETURN m.title, m.released
    ```

13. Retrieve the movies that have an actor’s role that is the name of the movie.
    ``` shell
    MATCH (a:Person)-[r:ACTED_IN]->(m:Movie)
    WHERE m.title in r.roles
    RETURN  m.title as Movie, a.name as Actor
    ```

## Exercício 5 – Controlling query processing

1. Retrieve data using multiple MATCH patterns.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)<-[:DIRECTED]-(d:Person),
         (a2:Person)-[:ACTED_IN]->(m)
   WHERE a.name = 'Gene Hackman'
   RETURN m.title as movie, d.name AS director , a2.name AS `co-actors`
    ```
   
2. Retrieve particular nodes that have a relationship.
    ``` shell
   MATCH (p1:Person)-[:FOLLOWS]-(p2:Person)
   WHERE p1.name = 'James Thompson'
   RETURN p1, p2
    ```
   
3. Modify the query to retrieve nodes that are exactly three hops away.
    ``` shell
   MATCH (p1:Person)-[:FOLLOWS*3]-(p2:Person)
   WHERE p1.name = 'James Thompson'
   RETURN p1, p2
    ```
   
4. Modify the query to retrieve nodes that are one and two hops away.
    ``` shell
   MATCH (p1:Person)-[:FOLLOWS*1..2]-(p2:Person)
   WHERE p1.name = 'James Thompson'
   RETURN p1, p2
    ```
   
5. Modify the query to retrieve particular nodes that are connected no matter how many hops are required.
    ``` shell
   MATCH (p1:Person)-[:FOLLOWS*]-(p2:Person)
   WHERE p1.name = 'James Thompson'
   RETURN p1, p2
    ```
6. Specify optional data to be retrieved during the query.
    ``` shell
   MATCH (p:Person)
   WHERE p.name STARTS WITH 'Tom'
   OPTIONAL MATCH (p)-[:DIRECTED]->(m:Movie)
   RETURN p.name, m.title
    ```
   
7. Retrieve nodes by collecting a list.
    ``` shell
   MATCH (p:Person)-[:ACTED_IN]->(m:Movie)
   RETURN p.name as actor, collect(m.title) AS `movie list`
    ```
8. Retrieve all movies that Tom Cruise has acted in and the co-actors that acted in the same movie by collecting a list 
    ``` shell
   MATCH (p:Person)-[:ACTED_IN]->(m:Movie)<-[:ACTED_IN]-(p2:Person)
   WHERE p.name ='Tom Cruise'
   RETURN m.title as movie, collect(p2.name) AS `co-actors`
    ```

9. Retrieve nodes as lists and return data associated with the corresponding lists.
    ``` shell
   MATCH (p:Person)-[:REVIEWED]->(m:Movie)
   RETURN m.title as movie, count(p) as numReviews, collect(p.name) as reviewers
    ```
10. Retrieve nodes and their relationships as lists.
    ``` shell
    MATCH (d:Person)-[:DIRECTED]->(m:Movie)<-[:ACTED_IN]-(a:Person)
    RETURN d.name AS director, count(a) AS `number actors` , collect(a.name) AS `actors worked with`
    ```
11. Retrieve the actors who have acted in exactly fivemovies.
    ``` shell
    MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
    WITH  a, count(a) AS numMovies, collect(m.title) AS movies
    WHERE numMovies = 5
    RETURN a.name, movies
    ```
12. Retrieve the movies that have at least 2 directors with other optional data.
    ``` shell
    MATCH (m:Movie)
    WITH m, size((:Person)-[:DIRECTED]->(m)) AS directors
    WHERE directors >= 2
    OPTIONAL MATCH (p:Person)-[:REVIEWED]->(m)
    RETURN  m.title, p.name
    ```

## Exercício 6 – Controlling results returned

1. Execute a query that returns duplicate records.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WHERE m.released >= 1990 AND m.released < 2000
   RETURN DISTINCT m.released, m.title, collect(a.name)
    ```
2. Modify the query to eliminate duplication.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WHERE m.released >= 1990 AND m.released < 2000
   RETURN  m.released, collect(m.title), collect(a.name)
    ```
   
3. Modify the query to eliminate more duplication.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WHERE m.released >= 1990 AND m.released < 2000
   RETURN  m.released, collect(DISTINCT m.title), collect(a.name)
    ```
   
4. Sort results returned.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WHERE m.released >= 1990 AND m.released < 2000
   RETURN  m.released, collect(DISTINCT m.title), collect(a.name)
   ORDER BY m.released DESC
    ```
   
5. Retrieve the top 5 ratings and their associated movies.
    ``` shell
   MATCH (:Person)-[r:REVIEWED]->(m:Movie)
   RETURN  m.title AS movie, r.rating AS rating
   ORDER BY r.rating DESC LIMIT 5
    ```
6. Retrieve all actors that have not appeared in more than 3 movies.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WITH  a,  count(a) AS countFimes, collect(m.title) AS filmes
   WHERE countFimes <= 3
   RETURN a.name, filmes
    ```
   
## Exercício 7 – Working with cypher data

1. Collect and use lists.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie), (m)<-[:PRODUCED]-(p:Person)
   WITH  m, collect(DISTINCT a.name) AS cast, collect(DISTINCT p.name) AS producers
   RETURN DISTINCT m.title, cast, producers
   ORDER BY size(cast)
    ```
   
2. Collect a list.
    ``` shell
   MATCH (p:Person)-[:ACTED_IN]->(m:Movie)
   WITH p, collect(m) AS movies
   WHERE size(movies)  > 5
   RETURN p.name, movies
    ```
   
3. Unwind a list.
    ``` shell
   MATCH (p:Person)-[:ACTED_IN]->(m:Movie)
   WITH p, collect(m) AS movies
   WHERE size(movies)  > 5
   WITH p, movies UNWIND movies AS movie
   RETURN p.name, movie.title
    ```
   
4. Perform a calculation with the date type.
    ``` shell
   MATCH (a:Person)-[:ACTED_IN]->(m:Movie)
   WHERE a.name = 'Tom Hanks'
   RETURN  m.title, m.released, date().year  - m.released as yearsAgoReleased, m.released  - a.born AS `age of Tom`
   ORDER BY yearsAgoReleased
    ```
   
## Exercício 8 – Creating nodes

1. Create a Movie node.
    ``` shell
   CREATE (:Movie {title: 'john wick 3: parabellum'})
    ```
2. Retrieve the newly-created node.
    ``` shell
   MATCH (m:Movie)
   WHERE m.title = 'john wick 3: parabellum'
   RETURN m
    ```
3. Create a Person node.
    ``` shell
   create(:Person{name:'Robin Wright'})
    ```
4. Retrieve the newly-created node.
    ``` shell
   MATCH (p:Person)
   WHERE p.name = 'Robin Wright'
   RETURN p
    ```
5. Add a label to a node.
    ``` shell
   MATCH (m:Movie)
   WHERE m.released < 2010
   SET m:OlderMovie
   RETURN DISTINCT labels(m)
    ```
6. Retrieve the node using the new label.
    ``` shell
   MATCH (m:OlderMovie)
   RETURN m.title, m.released
    ```
7. Add the Female label to selected nodes.
    ``` shell
   MATCH (p:Person)
   WHERE p.name STARTS WITH 'Robin'
   SET p:Female
    ```
8. Retrieve all Female nodes.
    ``` shell
   MATCH (p:Female)
   RETURN p.name
    ```
9. Remove the Female label from the nodes that have this label.
    ``` shell
   MATCH (p:Female)
   REMOVE p:Female
    ```
10. View the current schema of the graph.
    ``` shell
    call db.schema.visualization()
    ```
11. Add properties to a movie.
    ``` shell
    MATCH (m:Movie)
    WHERE m.title = 'Forrest Gump'
    SET m:OlderMovie,
        m.released = 1994,
        m.tagline = "Life is like a box of chocolates...you never know what you're gonna get.",
        m.lengthInMinutes = 142
    ```
12. Retrieve an OlderMovie node to confirm the label and properties.
    ``` shell
    MATCH (m:OlderMovie)
    WHERE m.title = 'Forrest Gump'
    RETURN m
    ```
13. Add properties to the person, Robin Wright.
    ``` shell
    MATCH (p:Person)
    WHERE p.name = 'Robin Wright'
    SET p.born = 1966, p.birthPlace = 'Dallas'
    ```
14. Retrieve an updated Person node.
    ``` shell
    MATCH (p:Person)
    WHERE p.name = 'Robin Wright'
    RETURN p
    ```
15. Remove a property from a Movie node.
    ``` shell
    MATCH (m:Movie)
    WHERE m.title = 'Forrest Gump'
    set m.lengthInMinutes= null
    ```
16. Retrieve the node to confirm that the property has been removed.
    ``` shell
    MATCH (m:Movie)
    WHERE m.title = 'Forrest Gump'
    RETURN m
    ```
17. Remove a property from a Person node.
    ``` shell
    MATCH (p:Person)
    WHERE p.name = 'Robin Wright'
    REMOVE p.birthPlace
    ```
18. Retrieve the node to confirm that the property has been removed.
    ``` shell
    MATCH (p:Person)
    WHERE p.name = 'Robin Wright'
    RETURN p
    ```
    
## Exercício 9 – Creating relationships

1. Create ACTED_IN relationships.
    ``` shell
   MATCH (m:Movie)
   WHERE m.title = 'Forrest Gump'
   MATCH (p:Person)
   WHERE p.name = 'Tom Hanks' OR p.name = 'Robin Wright' OR p.name = 'Gary Sinise'
   CREATE (p)-[:ACTED_IN]->(m)
    ```
   
2. Create DIRECTED relationships.
    ``` shell
   MATCH (m:Movie)
   WHERE m.title = 'Forrest Gump'
   MATCH (p:Person)
   WHERE p.name = 'Robert Zemeckis'
   CREATE (p)-[:DIRECTED]->(m)
    ```
3. Create a HELPED relationship.
    ``` shell
   MATCH (pth:Person)
   WHERE pth.name = 'Tom Hanks'
   MATCH (pgs:Person)
   WHERE pgs.name = 'Gary Sinise'
   CREATE (pth)-[:HELPED ]->(pgs)
    ```
4. Query nodes and new relationships.
    ``` shell
   MATCH (p:Person)-[rel]-(m:Movie)
   WHERE m.title = 'Forrest Gump'
   RETURN p, rel, m
    ```
5. Add properties to relationships.
    ``` shell
   MATCH (p:Person)-[rel:ACTED_IN]->(m:Movie)
   WHERE m.title = 'Forrest Gump'
   SET rel.roles =
   CASE p.name
     WHEN 'Tom Hanks' THEN ['Forrest Gump']
     WHEN 'Robin Wright' THEN ['Jenny Curran']
     WHEN 'Gary Sinise' THEN ['Lieutenant Dan Taylor']
   END
    ```
6. Add a property to the HELPED relationship.
    ``` shell
   MATCH (p1:Person)-[rel:HELPED]->(p2:Person)
   WHERE p1.name = 'Tom Hanks' AND p2.name = 'Gary Sinise'
   SET rel.research = 'war history'
    ```
7. View the current list of property keys in the graph.
    ``` shell
    call db.propertyKeys
    ```
8. View the current schema of the graph.
    ``` shell
   call db.schema.visualization()
    ```
9. Retrieve the names and roles for actors.
    ``` shell
   MATCH (p:Person)-[rel:ACTED_IN]->(m:Movie)
   WHERE m.title = 'Forrest Gump'
   RETURN p.name, rel.roles
    ```
10. Retrieve information about any specific relationships.
    ``` shell
    MATCH (p1:Person)-[rel:HELPED]-(p2:Person)
    RETURN p1.name, rel, p2.name
    ```
11. Modify a property of a relationship.
    ``` shell
    MATCH (p:Person)-[rel:ACTED_IN]->(m:Movie)
    WHERE m.title = 'Forrest Gump' AND p.name = 'Gary Sinise'
    SET rel.roles =['Lt. Dan Taylor']
    ```
12. Remove a property from a relationship.
    ``` shell
    MATCH (p1:Person)-[rel:HELPED]->(p2:Person)
    WHERE p1.name = 'Tom Hanks' AND p2.name = 'Gary Sinise'
    REMOVE rel.research
    ```
13. Confirm that your modifications were made to the graph.
    ``` shell
    MATCH (p:Person)-[rel:ACTED_IN]->(m:Movie)
    WHERE m.title = 'Forrest Gump'
    return p, rel, m
    ```

## Exercício 10 – Deleting nodes and relationships

1. Delete a relationship.
    ``` shell
   MATCH (:Person)-[rel:HELPED]-(:Person)
   DELETE rel
    ```
2. Confirm that the relationship has been deleted.
    ``` shell
   MATCH (:Person)-[rel:HELPED]-(:Person)
   RETURN rel
    ```
3. Retrieve a movie and all of its relationships.
    ``` shell
   MATCH (p:Person)-[rel]-(m:Movie)
   WHERE m.title = 'Forrest Gump'
   RETURN p, rel, m
    ```
4. Try deleting a node without detaching its relationships.
    ``` shell
   MATCH (m:Movie)
   WHERE m.title = 'Forrest Gump'
   DELETE m
    ```
5. Delete a Movie node, along with its relationships.
    ``` shell
   MATCH (m:Movie)
   WHERE m.title = 'Forrest Gump'
   DETACH DELETE m
    ```
6. Confirm that the Movie node has been deleted.
    ``` shell
   MATCH (p:Person)-[rel]-(m:Movie)
   WHERE m.title = 'Forrest Gump'
   RETURN p, rel, m
    ```
