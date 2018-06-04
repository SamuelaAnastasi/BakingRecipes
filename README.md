# Popular Movies - Stage 2

This project is part of [**Android Developer Nanodegree Scholarship Program**](https://www.udacity.com/google-scholarships) by
**Udacity** and **Google**. The project's purpose is to build an app that retrieves
movie data from The Movie Database API [TMDb](https://www.themoviedb.org/documentation/api).
## Features
The project presents the users a grid arrangement of movie elements each including the movie poster, title and genres. The user may choose through a _BottomNavigationView_ to retrieve either Popular or Top Rated movies.
Selecting a movie will open a _Movie Info_ screen with more detailed
information like: movie title, overview, release date, genres and user ratings.
In this final part of the project the _Movie Info_ activity includes also:
* A list of YouTube trailers with image preview that can be watched on-line.
* A list of member reviews: Each list item is expandable on-click.
* A FAB button to toggle `insert/delete` from a local SQLite Database.

A list of all database movies and relative poster, title and genres can be retrieved through _BottomNavigationView_ Favorites button

## Screenshots

![Baking Recipes  Phone](https://raw.githubusercontent.com/SamuelaAnastasi/BakingRecipes/master/previews/preview_phone.jpg)  
![Baking Recipes  Tablet](https://raw.githubusercontent.com/SamuelaAnastasi/BakingRecipes/master/previews/preview_tablet.jpg)

## Instructions
After creating a free account you need to request a API KEY from [TMDb](https://www.themoviedb.org/documentation/api). For more instructions see the [API FAQs](https://www.themoviedb.org/faq/api). Then in **gradle.properties** file in your project place the API key:
```
    API_KEY="your_api_key"
```
## Libraries
The project uses the following libraries:
* [Butter Knife](http://jakewharton.github.io/butterknife/)
* [Picasso](http://square.github.io/picasso/)

### Project License
This project is licensed under the [MIT Licence](https://opensource.org/licenses/MIT)

Copyright &copy; 2018 - Samuela Anastasi
