# Book Collection Management Google Assistant Application

## Project Overview

Book Collection Management Google Assistant Application is implemented with Java Spring Boot and backed by a MySql database. I worked on this project since I love books and thought an application to read short summaries of books would be useful. Book Collection Management lets users to search for books by publication year, title, and author. A user can also retrieve details and short summaries of a specific book of their choice. 

## Project Demo

[See project](https://www.youtube.com/watch?v=0rr6iPiiqUU&feature=youtu.be)


## Scope of Functionality

The application supports:
 - adding new books' data into database
 - listing all available authors in database
 - search a book based on title, author, publication year
 - retrieve details and short summary of a selected book
 - listing all available features
 
The application does not support:
 - automatic retrieval of new book data from the internet and addition to database

## Technologies Used
 
 - Java & Spring Boot: for implementing intent functionality and controlling application flow as a whole
 - MySql: for database that stores book data
 - Ngrok: for connecting Java Spring Boot application with Actions on Google
 - Actions on Google: for Google Assistant App development (create intents, manage the flow of intents, train phrases)
