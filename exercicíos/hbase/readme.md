# Hbase

## script docker
```shell script
docker run --name hbase-furb dajobe/hbase
```
```shell script
docker exec -it hbase-furb /bin/bash
hbase shell
```

## script incialiação do banco
```shell script
hbase(main):001:0> create 'italians','personal-data', 'professional-data'
```
```shell script
root@7d695d30cbea:/tmp# hbase shell /tmp/italians.txt
```

## Exercícios

1. Adicione mais 2 italianos mantendo adicionando informações como data de nascimento nas informações pessoais e um atributo de anos de
experiência nas informações profissionais;

    ```shell script
   hbase(main):007:0> put 'italians', '11', 'personal-data:name',  'Rafael Matiello'
   Took 0.0144 seconds
   hbase(main):008:0> put 'italians', '11', 'personal-data:city',  'Florence'
   Took 0.0047 seconds
   hbase(main):009:0> put 'italians', '11', 'personal-data:birth',  '1983'
   Took 0.0061 seconds
   hbase(main):010:0> put 'italians', '11', 'professional-data:role',  'Libras'
   Took 0.0054 seconds
   hbase(main):011:0> put 'italians', '11', 'professional-data:salary',  '12500'
   Took 0.0032 seconds
   hbase(main):012:0> put 'italians', '11', 'professional-data:experienceYear',  '10'
   Took 0.0041 seconds
   hbase(main):013:0> put 'italians', '12', 'personal-data:name',  'Jonas Miglioranza'
   Took 0.0058 seconds
   hbase(main):014:0> put 'italians', '12', 'personal-data:city',  'Milan'
   Took 0.0057 seconds
   hbase(main):015:0> put 'italians', '12', 'personal-data:birth',  '1992'
   Took 0.0029 seconds
   hbase(main):016:0> put 'italians', '12', 'professional-data:role',  'Engenharia de Minas'
   Took 0.0062 seconds
   hbase(main):017:0> put 'italians', '12', 'professional-data:salary',  '1200'
   Took 0.0028 seconds
   hbase(main):018:0> put 'italians', '12', 'professional-data:experienceYear',  '3'
   Took 0.0093 seconds
    ```
2. Adicione o controle de 5 versões na tabela de dados pessoais.
    ```shell script
      hbase(main):019:0> alter 'italians', NAME => 'personal-data', VERSIONS => 5
      Updating all regions with the new schema...
      1/1 regions updated.
      Done.
      Took 2.4958 seconds
    ```

3. Faça 5 alterações em um dos italianos;
   

    ```shell script
       hbase(main):020:0> put 'italians', '11', 'personal-data:city',  'Florence'
       Took 0.0033 seconds
       hbase(main):021:0> put 'italians', '11', 'personal-data:city',  'Milan'
       Took 0.0027 seconds
       hbase(main):022:0> put 'italians', '11', 'personal-data:city',  'Trieste'
       Took 0.0078 seconds
       hbase(main):023:0> put 'italians', '11', 'personal-data:city',  'Rome'
       Took 0.0046 seconds
       hbase(main):024:0> put 'italians', '11', 'personal-data:city',  'Verona'
       Took 0.0031 seconds
    ```

4. Com o operador get, verifique como o HBase armazenou o histórico.
    ```shell script
    hbase(main):025:0> get 'italians', '11', {COLUMN => 'personal-data:city', VERSIONS => 5}
    COLUMN                          CELL
     personal-data:city             timestamp=1587241891647, value=Verona
     personal-data:city             timestamp=1587241887229, value=Rome
     personal-data:city             timestamp=1587241882791, value=Trieste
     personal-data:city             timestamp=1587241878595, value=Milan
     personal-data:city             timestamp=1587241874213, value=Florence
    1 row(s)
    Took 0.0571 seconds
    ```

5. Utilize o scan para mostrar apenas o nome e profissão dos italianos.
    ```shell script
    hbase(main):028:0> scan 'italians', {COLUMNS => ['personal-data:name','professional-data:role']}
    ROW                             COLUMN+CELL
     1                              column=personal-data:name, timestamp=1587240786396, value=Paolo Sorrentino
     1                              column=professional-data:role, timestamp=1587240786441, value=Gestao Comercial
     10                             column=personal-data:name, timestamp=1587240786687, value=Giovanna Caputo
     10                             column=professional-data:role, timestamp=1587240786702, value=Comunicacao Institucional
     11                             column=personal-data:name, timestamp=1587241563398, value=Rafael Matiello
     11                             column=professional-data:role, timestamp=1587241577680, value=Libras
     12                             column=personal-data:name, timestamp=1587241598816, value=Jonas Miglioranza
     12                             column=professional-data:role, timestamp=1587241610957, value=Engenharia de Minas
     2                              column=personal-data:name, timestamp=1587240786459, value=Domenico Barbieri
     2                              column=professional-data:role, timestamp=1587240786470, value=Psicopedagogia
     3                              column=personal-data:name, timestamp=1587240786495, value=Maria Parisi
     3                              column=professional-data:role, timestamp=1587240786516, value=Optometria
     4                              column=personal-data:name, timestamp=1587240786530, value=Silvia Gallo
     4                              column=professional-data:role, timestamp=1587240786542, value=Engenharia Industrial Madeireira
     5                              column=personal-data:name, timestamp=1587240786558, value=Rosa Donati
     5                              column=professional-data:role, timestamp=1587240786573, value=Mecatronica Industrial
     6                              column=personal-data:name, timestamp=1587240786587, value=Simone Lombardo
     6                              column=professional-data:role, timestamp=1587240786602, value=Biotecnologia e Bioquimica
     7                              column=personal-data:name, timestamp=1587240786611, value=Barbara Ferretti
     7                              column=professional-data:role, timestamp=1587240786622, value=Libras
     8                              column=personal-data:name, timestamp=1587240786634, value=Simone Ferrara
     8                              column=professional-data:role, timestamp=1587240786644, value=Engenharia de Minas
     9                              column=personal-data:name, timestamp=1587240786659, value=Vincenzo Giordano
     9                              column=professional-data:role, timestamp=1587240786675, value=Marketing
    12 row(s)
    Took 0.1298 seconds
    ```

6. Apague os italianos com row id ímpar
    
    Não encontrei como fazer por script

    ```shell script
   hbase(main):029:0> deleteall 'italians', '1'
   Took 0.0184 seconds
   hbase(main):030:0> deleteall 'italians', '3'
   Took 0.0026 seconds
   hbase(main):031:0> deleteall 'italians', '5'
   Took 0.0114 seconds
   hbase(main):032:0> deleteall 'italians', '7'
   Took 0.0042 seconds
   hbase(main):033:0> deleteall 'italians', '9'
   Took 0.0046 seconds
   hbase(main):034:0> deleteall 'italians', '11'
   Took 0.0025 seconds
    ```

7. Crie um contador de idade 55 para o italiano de row id 5
    
    * não consegui fazer sem map reduce.
    
    ```shell script
   
    ```

8. Incremente a idade do italiano em 1
    ```shell script
   hbase(main):005:0> incr 'italians', '1', 'personal-data:birth'
   COUNTER VALUE = 1
   Took 0.0469 seconds
   hbase(main):010:0> incr 'italians', '1', 'personal-data:birth'
   COUNTER VALUE = 2
   Took 0.0104 seconds
   hbase(main):011:0> get 'italians', '1'
   COLUMN                          CELL
    personal-data:birth            timestamp=1587243193742, value=\x00\x00\x00\x00\x00\x00\x00\x02
    personal-data:city             timestamp=1587242857136, value=Verona
    personal-data:name             timestamp=1587242857080, value=Paolo Sorrentino
    professional-data:role         timestamp=1587242857144, value=Gestao Comercial
    professional-data:salary       timestamp=1587242857153, value=2394
   1 row(s)
   Took 0.0188 seconds
    ```
