# Proof of Concept (POC)

This java code can execute the 3 recomenders that we have defined in the project

To do so, you'll have to make a neo4j project,set it up with the CSVs and the code that is included,
then when the database is online and filled with the CSVs, in lines 140, 158, and 176, you have to write
the uri of the server, the username and the password to do the connection to it. 

Once you have done that, running the program will make the program ask what recommender do you want to
try in the console. Choosing 1 will use the first recommender, choosing 2 will use the second recommender
and choosing 3 will use the third recommender. For the first recommender you won't need any input argument,
but for the second one you'll need an id of a reviewer and for the third one you'll need a movie title.

The results once choosed a recommender and it's input will be shown in the console.
