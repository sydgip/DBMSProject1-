
import org.junit.Test;
import static java.lang.System.out;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class TableUnitTest {
  @Test 
  public void tableTest(){
    
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

       // movie.save();
       // cinema.save();
        //movieStar.save();
       // starsIn.save();
        //movieExec.save();
        //studio.save();

        
    
   }
@Test
    public void projectTest() {
         var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

        //movie.save();
       // cinema.save();
       // movieStar.save();
        //starsIn.save();
       // movieExec.save();
       // studio.save();

        out.println("project test 1--------------------------------------|");
        var t_project = movie.project("title year");
        t_project.print();
      out.println("project test 2----------------------------------------|");
        var t2_project = studio.project("name address");
        t2_project.print();  
       out.println("project test 3----------------------------------------|");
        var t3_project = movieExec.project("address");
        t3_project.print();  
    }
@Test
    public void selectPredicateTest() {
         var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

       // movie.save();
       // cinema.save();
       // movieStar.save();
        //starsIn.save();
       //movieExec.save();
        //studio.save();

        out.println("select predicate test ------------------|");
        var t_select = movie.select(t -> t[movie.col("title")].equals("Star_Wars") ==
                t[movie.col("year")].equals(1977));
        t_select.print();
    }
@Test
    public void selectOperatorTest() {
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};
        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.insert(film4);
        movie.print();

        
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        var star3 = new Comparable[]{"Ariana_Grande", "Hollywood", 'F', "6/26/93"};
        var star4 = new Comparable[]{"Tobey_Maguire", "Brentwood", 'M', "6/27/75"};
        var star5 = new Comparable[]{"Elton_John", "Beverly_Hills", 'M', "3/25/47"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.insert(star3);
        movieStar.insert(star4);
        movieStar.insert(star5);

        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

       // movie.save();
        //cinema.save();
       // movieStar.save();
       // starsIn.save();
       // movieExec.save();
       // studio.save();

        out.println("Select operator 1-------------------|");
        var t_select2A = movie.select(t -> {
                int year = (Integer) t[movie.col("year")];
                return year == 1980 || year == 1977;
        });
        t_select2A.print();

        out.println("Select operator 2-------------------|");
        var t_select2b = movie.select(t -> (Integer) t[movie.col("year")] != 1980);
        t_select2b.print();
        out.println("Select operator 3-------------------|");
        var t_select2c = movie.select(t -> (Integer) t[movie.col("year")] >= 1980);
        t_select2c.print();
        out.println("Select operator 4-------------------|");
        var t_select2d = movie.select(t -> (Integer) t[movie.col("year")] <= 1980);
        t_select2d.print();
        out.println("Select operator 5-------------------|");
        var t_select2e = movie.select(t -> (Integer) t[movie.col("year")] > 1980);
        t_select2e.print();
        out.println("Select operator 6-------------------|");
        var t_select2f = movie.select(t -> (Integer) t[movie.col("year")] < 1980);
        t_select2f.print();
        out.println("Select operator 7-------------------|");
        var t_select2g = movieStar.select(t -> (Character) t[movieStar.col("gender")] == 'F');
        t_select2g.print();

    }
@Test
    public void unionTest() {
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

       // movie.save();
        //cinema.save();
        //movieStar.save();
        //starsIn.save();
        //movieExec.save();
        //studio.save();

        out.println("union test 1 --------------|");
        var t_unionA = movie.union(cinema);
        t_unionA.print();
         out.println("union test 2 --------------|");
        var t_unionB = movie.union(studio);
        t_unionB.print();
    }
@Test
    public void minusTest() {
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

       // movie.save();
        //cinema.save();
        //movieStar.save();
        //starsIn.save();
        //movieExec.save();
        //studio.save();

        out.println("minus test 1----------|");
        var t_minus1 = cinema.minus(movie);
        t_minus1.print();
        out.println("minus test 2----------|");
        var t_minus2 = movie.minus(studio);
        t_minus2.print();
    }
@Test
    public void equijoinTest() {
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

        //movie.save();
        //cinema.save();
        //movieStar.save();
       // starsIn.save();
       // movieExec.save();
      //  studio.save();

        out.println("equi join test 1");
        var t_joinA = movie.join("studioName", "name", studio);
        t_joinA.print();
        out.println("equi join test 2");
        var t_joinb = movieExec.join("name", "name", studio);
        t_joinb.print(); 

    }
@Test
public void thetajoinTest() {
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

        //movie.save();
        //cinema.save();
        //movieStar.save();
       // starsIn.save();
       // movieExec.save();
      //  studio.save();

        out.println("theta join test 1-------------|");
        var t_joinA = movie.join ("title == title", cinema);
        t_joinA.print();
         out.println("theta join test 2------------|");
        var t_joinB = movie.join ("year == year", cinema);
        t_joinB.print();

    }
 @Test
public void naturaljoinTest() {
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

 
        out.println("Natural join test 1 ----------------------|");
        var t_join2 = movie.join(cinema);
        t_join2.print();
        out.println("Natural join test 2 ----------------------|");
        var t_join3 = starsIn.join(studio);
        t_join3.print();
    }
@Test
 public void naturalJoinTest2() {
    var song = new Table("song", "title year genre artistName", "String Integer String String", "title year ");
    var concert = new Table("concert", "title year genre artistName", "String Integer String String", "title year ");
    var artist = new Table("artist", "artistName address genre", "String String String", "artistName");

    var song1 = new Comparable[]{"Bohemian Rhapsody", 1975, "Rock", "Queen"};
    var song2 = new Comparable[]{"Hotel California", 1977, "Rock", "Eagles"};
    var song3 = new Comparable[]{"Thriller", 1982, "Pop", "Michael Jackson"};

    out.println();
    song.insert(song1);
    song.insert(song2);
    song.insert(song3);
    song.print();

    var concert1 = new Comparable[]{"Live Aid", 1985, "Rock", "Queen"};
    var concert2 = new Comparable[]{"The Eagles World Tour", 2022, "Rock", "Eagles"};

    out.println();
    concert.insert(concert1);
    concert.insert(concert2);
    concert.print();

    var artist1 = new Comparable[]{"Queen", "London", "Rock"};
    var artist2 = new Comparable[]{"Eagles", "Los Angeles", "Rock"};
    var artist3 = new Comparable[]{"Michael Jackson", "Gary, Indiana", "Pop"};

    out.println();
    artist.insert(artist1);
    artist.insert(artist2);
    artist.insert(artist3);
    artist.print();

    out.println("Natural join test 1 ----------------------|");
    var t_join1 = song.join(concert);
    t_join1.print();

    out.println("Natural join test 2 ----------------------|");
    var t_join2 = song.join(artist);
    t_join2.print();
}
@Test
public void indexSelectTest(){
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", "name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "address");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

 
        Table emptyTable = new Table("emptyTable", "col1 col2", "String Integer", "col1");
       
         
        // nonexisting keyval
          out.println("Index select test Nonexisting  ----------------------|");
          // Output prints an empty table b/c nothing is found 
          var t_iselect = studio.select (new KeyType ("Disney"));
          t_iselect.print ();
        // empty table 
         out.println("Index select test Empty Table ----------------------|");
         // Output prints an empty table b/c nothing is found 
          var t_iselect2 = emptyTable.select (new KeyType ("SomeKey"));
          t_iselect2.print (); // ask what they want to happen in this instance.

        // single key match 
        //Output only work with the primary key and not if there is multiple primary keys. 
        //If you only put the value of one primary key then there is an array out of bounds error
        out.println(" Index Select Test single match ");
        var t_iselect3 = movieExec.select (new KeyType ("Rocky"));// Nothing prints out even when there is a match 
          t_iselect3.print (); 
        // multiple matches 
        // output is perfect it works 
        out.println("index select Multiple Match");
        var t_iselect4 = studio.select (new KeyType ("Universal_City"));// Works  
          t_iselect4.print ();
        
        //case sensitive 
        // output is an empty table not sure if yall want to account for case sensitive for not 
         out.println("Index select test Case Sensitive ----------------------|");
        var t_iselect5 = studio.select (new KeyType ("UniVersaL_CiTy"));// Works  
          t_iselect5.print ();
       
        // numerical value  
        // Output is perfect it  works  
         out.println("Index select test Numerical  ----------------------|");
       var t_iselect6 = cinema.select (new KeyType (1999));// Works  
          t_iselect6.print ();
    
        }
@Test
public void indexJoinTest(){
    var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", " title year");

        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        var movieStar = new Table("movieStar", "name address gender birthdate",
                "String String Character String", " name");

        var starsIn = new Table("starsIn", "movieTitle movieYear starName",
                "String Integer String", " movieYear ");

        var movieExec = new Table("movieExec", "certNo name address fee",
                "Integer String String Float", "certNo");

        var studio = new Table("studio", "name address presNo",
                "String String Integer", "name");

        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};

        out.println();
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        movie.print();

        var film4 = new Comparable[]{"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};
        out.println();
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);
        cinema.print();

        var star0 = new Comparable[]{"Carrie_Fisher", "Hollywood", 'F', "9/9/99"};
        var star1 = new Comparable[]{"Mark_Hamill", "Brentwood", 'M', "8/8/88"};
        var star2 = new Comparable[]{"Harrison_Ford", "Beverly_Hills", 'M', "7/7/77"};
        out.println();
        movieStar.insert(star0);
        movieStar.insert(star1);
        movieStar.insert(star2);
        movieStar.print();

        var cast0 = new Comparable[]{"Star_Wars", 1977, "Carrie_Fisher"};
        out.println();
        starsIn.insert(cast0);
        starsIn.print();

        var exec0 = new Comparable[]{9999, "S_Spielberg", "Hollywood", 10000.00};
        out.println();
        movieExec.insert(exec0);
        movieExec.print();

        var studio0 = new Comparable[]{"Fox", "Los_Angeles", 7777};
        var studio1 = new Comparable[]{"Universal", "Universal_City", 8888};
        var studio2 = new Comparable[]{"DreamWorks", "Universal_City", 9999};
        out.println();
        studio.insert(studio0);
        studio.insert(studio1);
        studio.insert(studio2);
        studio.print();

  Table emptyTable = new Table("emptyTable", "col1 col2", "String Integer", "col1");
  Table empty2Table = new Table("emptyTable2", "row1 row2", "String Integer", "row1");
//empty table 
//Output is  an empty table ,as it should be for empty tables joined another table whether empty or not 
 out.println("Index Join test 1 ( empty tables ) ----------------------|");
 var t_join1a = movie.i_join ("col1", "title", emptyTable);
 t_join1a.print ();
// nonexistent attribute 
//output is an empty table without the nonexistent attribute im 
 out.println("Index select test 2 nonexistent attribute ----------------------|");
  var t_join2 = movieStar.i_join ("month", "gender", movieExec);
 t_join2.print ();
//single match 
//Output getting a negative index for certain searches
out.println("Index select test 2 single match ----------------------|");
var t_join3 = movie.i_join ("movieYear", "year", starsIn);
t_join3.print ();
//multiple matches 
//Output is perfect works well 
out.println ("Index select test 2 multiple match ----------------------|");
var t_join = movie.i_join ("studioName", "name", studio);
t_join.print ();


    }    


}
