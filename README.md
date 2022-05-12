# iSpan_Java_Project1
## Who I am
I'm a student of iSpan EEIT Class.
This is my first project.


## Data resource
In this project,
I choose the data from Taiwan government open data website.
This is my raw data's url: https://data.gov.tw/dataset/146626


## Introduce of my codes
The pj1_newbornbaby109_final.csv is my raw data.
I do the normalization to clean the raw data.
So I got two tables names new_born_baby.csv and district.csv,respectively. 

The CommonFunc has some functions where can be reused.
The CommonConst has my database field name where can be reused.
The SQLStatement has all the SQL statement that I used in project.
The Project1Main is the only program that can be run.

The reading order of my code is
1. ParseCSV
2. InsertIntoSQL
3. JdbcCRUD
4. Output2


