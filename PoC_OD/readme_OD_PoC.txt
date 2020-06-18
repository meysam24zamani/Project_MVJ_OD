This java code can execute the 3 recomenders that we have defined in the project

To do so, you'll have to make a neo4j project,set it up with the csvs and the code that is included,
then when the database is online and filled with the csvs, in lines 140,158,176 you will have to write
the uri of the server, the username and the password to do the connection to it. 

Once you have done that, running the program will make the program ask what recommender do you want to
try in the console. Choosing 1 will use the first recommender, choosing 2 will use the second recommender
and choosing 3 will use the third recommender. For the first recommender you won't need any input argument,
but for the second one you'll need a id of a reviewer and for the third one you will need a movie title.

The results once choosed a recommender and it's input will be shown in the console.