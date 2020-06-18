import org.neo4j.driver.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.neo4j.driver.Values.parameters;
import static jdk.nashorn.internal.objects.NativeMath.round;

public class films implements AutoCloseable {

    private final Driver driver;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public films( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    public List<String> getRecommender1()
    {
        try ( Session session = driver.session() )
        {
            return session.readTransaction( new TransactionWork<List<String>>()
            {
                @Override
                public List<String> execute(Transaction tx )
                {
                    return rec1( tx );
                }
            } );
        }
    }

    private static List<String> rec1( Transaction tx )
    {
        List<String> names = new ArrayList<>();
        Result result = tx.run( "MATCH (r:Reviewer)-[ra:rates]->(m:Movie)\n" +
                "WHERE m.avgRating>4\n" +
                "RETURN m.title,count(ra) AS NumberofReviews, m.avgRating AS AverageRating, count(ra)*m.avgRating as score\n" +
                "ORDER BY score desc limit 20" );
        while ( result.hasNext() )
        {
           Record aux= result.next();
            names.add( aux.get( "m.title" ).toString()+"/"+aux.get( "NumberofReviews" ).asInt()+"/"+df2.format(aux.get( "AverageRating" ).asDouble())+"/"+Math.round(aux.get( "score" ).asDouble()));
        }
        return names;
    }

    public List<String> getRecommender2(int id)
    {
        try ( Session session = driver.session() )
        {
            return session.readTransaction( new TransactionWork<List<String>>()
            {
                @Override
                public List<String> execute(Transaction tx )
                {
                    return rec2( tx,id);
                }
            } );
        }
    }

    private static List<String> rec2( Transaction tx,int id )
    {
        List<String> names = new ArrayList<>();
        Result result = tx.run( "MATCH (r:Reviewer{ID:\""+id+"\"})-[v:rates]->(m:Movie)\n" +
                "WITH r,m,v.rating -r.avgRating as rRates\n" +
                "MATCH (o:Reviewer)-[w:rates]->(m)\n" +
                "WHERE NOT o.ID=\"999\"\n" +
                "WITH r,m,rRates, o, w.rating - o.avgRating as oRates\n" +
                "WITH r,rRates,o,oRates,m, rRates*oRates as mult\n" +
                "WITH o,sum(mult)/(r.squareRates*o.squareRates) as cos\n" +
                "ORDER BY cos DESC LIMIT 25\n" +
                "MATCH (m1:Movie) \n" +
                "WHERE NOT (:Reviewer{ID:\"999\"})-[:rates]->(m1:Movie) AND m1.nRatings > 0\n" +
                "MATCH (o:Reviewer)-[x:rates]->(m1)\n" +
                "WITH m1, avg(x.rating) as suggestedRating, count(x.rating) as nORating, (avg(x.rating)*0.8 + (1.0*count(x.rating)/25)*5*0.2) as score\n" +
                "ORDER BY score DESC\n" +
                "WHERE nORating >= 3 AND suggestedRating >= 3\n" +
                "RETURN m1.title, m1.nRatings , m1.avgRating, suggestedRating, nORating, score");
        while ( result.hasNext() )
        {
            Record aux= result.next();
            names.add( aux.get( "m1.title" ).toString()+"/"+ df2.format(aux.get( "suggestedRating" ).asDouble())+"/"+df2.format(aux.get( "nORating" ).asDouble())+"/"+df2.format(aux.get( "score" ).asDouble()));
        }
        return names;
    }

    public List<String> getRecommender3(String title)
    {
        try ( Session session = driver.session() )
        {
            return session.readTransaction( new TransactionWork<List<String>>()
            {
                @Override
                public List<String> execute(Transaction tx )
                {
                    return rec3( tx,title );
                }
            } );
        }
    }

    private static List<String> rec3( Transaction tx,String title )
    {
        List<String> names = new ArrayList<>();
        Result result = tx.run( "MATCH (y1:Year)<-[:filmedYear]-(m1:Movie{title:'"+title+"'})-[:hasGenre]->(g1:Genre)\n" +
                "MATCH (w1:Filmer)<-[:writtenBy]-(m1)-[:directedBy]->(d1:Filmer)\n" +
                "MATCH (c1:Company)<-[:producedBy]-(m1)-[:filmedIn]->(p1:Country)\n" +
                "MATCH (y2:Year)<-[:filmedYear]-(m2:Movie)-[:hasGenre]->(g2:Genre) WHERE m1 <> m2\n" +
                "MATCH (w2:Filmer)<-[:writtenBy]-(m2)-[:directedBy]->(d2:Filmer)\n" +
                "MATCH (c2:Company)<-[:producedBy]-(m2)-[:filmedIn]->(p2:Country)\n" +
                "WITH m1,g1,m2,g2, (CASE WHEN g1 = g2 THEN 1 ELSE 0 END)*0.4 AS g, (CASE WHEN d1 = d2 THEN 1 ELSE 0 END)*0.2 AS d, \n" +
                "(CASE WHEN w1 = w2 THEN 1 ELSE 0 END)*0.1 AS w, (CASE WHEN c1 = c2 THEN 1 ELSE 0 END)*0.1 AS c, \n" +
                "(CASE WHEN p1 = p2 THEN 1 ELSE 0 END)*0.1 AS p, (CASE WHEN abs(y1.year-y2.year) > 10 THEN 0.0 ELSE 10-abs(y1.year-y2.year) END)*0.01 AS y,\n" +
                "(CASE WHEN m2.avgRating IS NULL OR m2.nRatings < 5 THEN 0 ELSE m2.avgRating END)/5 AS avgRat \n" +
                "RETURN m2.title AS movie,g2.name AS genere,g+d+w+c+p+y+ avgRat AS score1\n" +
                "ORDER BY score1 DESC\n" +
                "LIMIT 100" );
        while ( result.hasNext() )
        {
            Record aux= result.next();
            names.add( aux.get( "movie" ).toString()+"/"+aux.get( "genere" ).toString()+"/"+df2.format(aux.get( "score1" ).asDouble()));
        }
        return names;

    }

    public static void main( String... args ) throws Exception
    {

        System.out.println("choose what recommender you want to test out: 1 , 2 , 3");
        Scanner keyboard = new Scanner(System.in);
        String option = keyboard.nextLine();
        if(option.equals("1")) {


            try (films greeter = new films("bolt://localhost:7687", "neo4j", "a")) {
               List<String> values = greeter.getRecommender1();
               String [] data;
                System.out.println("------------------------------------------------------");
               System.out.println("|Title name|Number of reviews|Average rating|score");
               System.out.println("------------------------------------------------------");
               for(int i=0;i<values.size();i++){

                   data=values.get(i).split("/");
                   System.out.println("|"+data[0]+"|"+data[1]+"|"+data[2]+"|"+data[3]);
                   System.out.println("------------------------------------------------------");
               }
            }
        }
        if(option.equals("2")) {
            Integer id=0;
            System.out.println("Write a id of a reviewer");
            id=keyboard.nextInt();
            try (films greeter = new films("bolt://localhost:7687", "neo4j", "a")) {
             List<String> values = greeter.getRecommender2(id);
                String [] data;
                System.out.println("------------------------------------------------------");
                System.out.println("|Title name|suggested Rating|nORating|score");
                System.out.println("------------------------------------------------------");
                for(int i=0;i<values.size();i++){

                    data=values.get(i).split("/");
                    System.out.println("|"+data[0]+"|"+data[1]+"|"+data[2]+"|"+data[3]);
                    System.out.println("------------------------------------------------------");
                }
            }
        }
        if(option.equals("3")) {
            System.out.println("Write a title of a film");
            String title="";
            title=keyboard.nextLine();
            try (films greeter = new films("bolt://localhost:7687", "neo4j", "a")) {
                List<String> values = greeter.getRecommender3(title);
                String [] data;
                System.out.println("------------------------------------------------------");
                System.out.println("|Title name|Genere|score|");
                System.out.println("------------------------------------------------------");
                for(int i=0;i<values.size();i++){

                    data=values.get(i).split("/");
                    System.out.println("|"+data[0]+"|"+data[1]+"|"+data[2]+"|");
                    System.out.println("------------------------------------------------------");
                }
            }
        }
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }
}
