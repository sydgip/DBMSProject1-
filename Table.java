
/****************************************************************************************
 * @file  Table.java
 *
 * @author   John Miller
 */

import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.Boolean.*;
import static java.lang.System.arraycopy;
import static java.lang.System.out;

/****************************************************************************************
 * The Table class implements relational database tables (including attribute names, domains
 * and a list of tuples.  Five basic relational algebra operators are provided: project,
 * select, union, minus and join.  The insert data manipulation operator is also provided.
 * Missing are update and delete data manipulation operators.
 */
public class Table implements Serializable {

    /** Relative path for storage directory.
     */
    private static final String DIR = "store" + File.separator;

    /** Filename extension for database files.
     */
    private static final String EXT = ".dbf";

    /** Counter for naming temporary tables.
     */
    private static int count = 0;

    /** Table name.
     */
    private final String name;

    /** Array of attribute names.
     */
    private final String [] attribute;

    /** Array of attribute domains: a domain may be
     *  integer types: Long, Integer, Short, Byte
     *  real types: Double, Float
     *  string types: Character, String.
     */
    private final Class [] domain;

    /** Collection of tuples (data storage).
     */
    private final List <Comparable []> tuples;

    /** Primary key (the attributes forming). 
     */
    private final String [] key;

    /** Index into tuples (maps key to tuple).
     */
    private final Map <KeyType, Comparable []> index;

    /** The supported map types.
     */
    private enum MapType { NO_MAP, TREE_MAP, LINHASH_MAP, BPTREE_MAP }

    /** The map type to be used for indices.  Change as needed.
     */
    private static final MapType mType = MapType.LINHASH_MAP;

    /************************************************************************************
     * Make a map (index) given the MapType.
     * @return a Map with the KeyType and tuple
     */
    private static Map <KeyType, Comparable []> makeMap () {
        return switch (mType) {
       // case TREE_MAP    -> new TreeMap <> ();
        case LINHASH_MAP -> new LinHashMap <> (KeyType.class, Comparable [].class);
//      case BPTREE_MAP  -> new BpTreeMap <> (KeyType.class, Comparable [].class);
        default          -> null;
        }; // switch
    } // makeMap

    /************************************************************************************
     * Concatenate two arrays of type T to form a new wider array.
     *
     * @see http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java
     *
     * @param arr1  the first array
     * @param arr2  the second array
     * @param <T> a generic type
     * @return  a wider array containing all the values from arr1 and arr2
     */
    public static <T> T [] concat (T [] arr1, T [] arr2) {
        T [] result = Arrays.copyOf (arr1, arr1.length + arr2.length);
        arraycopy (arr2, 0, result, arr1.length, arr2.length);
        return result;
    } // concat

    //-----------------------------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------------------------

    /************************************************************************************
     * Construct an empty table from the meta-data specifications.
     *
     * @param _name       the name of the relation
     * @param _attribute  the string containing attributes names
     * @param _domain     the string containing attribute domains (data types)
     * @param _key        the primary key
     */  
    public Table (String _name, String [] _attribute, Class [] _domain, String [] _key) {
        name      = _name;
        attribute = _attribute;
        domain    = _domain;
        key       = _key;
        tuples    = new ArrayList <> ();
        index     = makeMap ();
    } // primary constructor

    /************************************************************************************
     * Construct a table from the meta-data specifications and data in _tuples list.
     *
     * @param _name       the name of the relation
     * @param _attribute  the string containing attributes names
     * @param _domain     the string containing attribute domains (data types)
     * @param _key        the primary key
     * @param _tuples     the list of tuples containing the data
     */  
    public Table (String _name, String [] _attribute, Class [] _domain, String [] _key,
                  List <Comparable []> _tuples) {
        name      = _name;
        attribute = _attribute;
        domain    = _domain;
        key       = _key;
        tuples    = _tuples;
        index     = makeMap ();
    } // constructor

    /************************************************************************************
     * Construct an empty table from the raw string specifications.
     *
     * @param _name       the name of the relation
     * @param attributes  the string containing attributes names
     * @param domains     the string containing attribute domains (data types)
     * @param _key        the primary key
     */
    public Table (String _name, String attributes, String domains, String _key) {
        this (_name, attributes.split (" "), findClass (domains.split (" ")), _key.split(" "));

        out.println ("DDL> create table " + name + " (" + attributes + ")");
    } // constructor

    //----------------------------------------------------------------------------------
    // Public Methods
    //----------------------------------------------------------------------------------

    /************************************************************************************
     * Project the tuples onto a lower dimension by keeping only the given attributes.
     * Check whether the original key is included in the projection.
     *
     * #usage movie.project ("title year studioNo")
     *
     * @param attributes  the attributes to project onto
     * @return  a table of projected tuples
     */
    public Table project (String attributes) {
        out.println ("RA> " + name + ".project (" + attributes + ")");
        var attrs     = attributes.split (" ");
        var colDomain = extractDom (match (attrs), domain);

        boolean valid = false;
        for (String a : attrs) { // loop goes through each attribute the user wants projected
            for ( String b : this.attribute) { 
                if (a.equals(b)) { // makes sure the attributes given by the user are actual attributes
                    valid = true; 
                }
            }
            if (valid = false) {
                out.println("Invalid attribute name. Exiting program");
                System.exit(0);
            }
        }

        var newKey    = (Arrays.asList (attrs).containsAll (Arrays.asList (key))) ? key : attrs; 

        List <Comparable []> rows = new ArrayList <> ();

        boolean duplicate;

        for (Comparable[] t : tuples) { // goes through the table's tuples
            duplicate = false; // resets duplicate to false every iteration
            for (Comparable[] newtuple : rows) { // goes through the array list of tuples we are adding to
                for (String keyCheck : newKey) { // checks each key in each tuple
                     if (t[col(keyCheck)].compareTo(newtuple[col(keyCheck)]) == 0) { // compares the key in each tuple to find duplicates
                        duplicate = true; 
                    } else {
                        duplicate = false; 
                    }
                }
            } if (duplicate != true) { // only adds the tuple to rows if it is not already in the list
                rows.add(this.extract(t,attrs));
                out.println(rows);
            }
        }  

        return new Table (name + count++, attrs, colDomain, newKey, rows);
    } // project

    /************************************************************************************
     * Select the tuples satisfying the given predicate (Boolean function).
     *
     * #usage movie.select (t -> t[movie.col("year")].equals (1977))
     *
     * @param predicate  the check condition for tuples
     * @return  a table with tuples satisfying the predicate
     */
    public Table select (Predicate <Comparable []> predicate) {
        out.println ("RA> " + name + ".select (" + predicate + ")");

        return new Table (name + count++, attribute, domain, key,
                   tuples.stream ().filter (t -> predicate.test (t))
                                   .collect (Collectors.toList ()));
    } // select

    /************************************************************************************
     * Select the tuples satisfying the given simple condition on attributes/constants
     * compared using an "op" ==, !=, <, <=, >, >=.
     *
     * #usage movie.select ("year == 1977")
     *
     * @param condition  the check condition as a string for tuples
     * @return  a table with tuples satisfying the condition
     */
    public Table select (String condition) {
        out.println ("RA> " + name + ".select (" + condition + ")");

        List <Comparable []> rows = new ArrayList <> ();
 
        String[] split = condition.split(" "); //this splits the conditon into a String array 
       
        for (int i = 0; i < this.tuples.size(); i++) { //this goes through the tuples in the table and checks to see if the condtion passes
            Comparable[] temp = tuples.get(i);
            if (conditionCheck(temp, split)) { //a method to check the condition
                rows.add(temp);
            }
        }

        return new Table (name + count++, attribute, domain, key, rows);
    } // select

    /**
     * This is a helper that checks if the condtion is true or not.
     * @param a the tuple to check the condition
     * @param b the condtion string
     * @return True if the condtion passes
     */
    private boolean selectHelper(Comparable[] a, String b) {
        String[] temp = b.split(" "); /// this splits the string given into the attribute, the op, and the value

        String at = temp[0];
        String op = temp[1];
        String cons = temp[2];
         
        int index = -1;

        for (int i = 0; i < this.attribute.length; i ++) { //this gets the index of the attribute
            if (at.equals(attribute[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        }

        Comparable value = a[index];
        Comparable c = null;

        //This section changes the value into the correct domain
        if (value instanceof Integer) { 
            c = Integer.valueOf(cons);    
        } else if (value instanceof Character) {
            c = Character.valueOf(cons.charAt(0));
        } else if (value instanceof String) {
            c = cons;
        } else if (value instanceof Long) {
            c = Long.valueOf(cons);
        } else if (value instanceof Short) {
            c = Short.valueOf(cons);
        } else if (value instanceof Byte) {
            c = Byte.valueOf(cons);
        } else if (value instanceof Double) {
            c = Double.valueOf(cons);
        } else if (value instanceof Float) {
            c = Float.valueOf(cons);
        }

        //this switches to the correct operator and returns
        switch (op) {
        case "==":
            return value.compareTo(c) == 0;   
        case "!=":
            return value.compareTo(c) != 0;
        case "<":
            return value.compareTo(c) < 0;
        case "<=" :
            return value.compareTo(c) <= 0;
        case ">":
            return value.compareTo(c) > 0;
        case ">=":
            return value.compareTo(c) >= 0;
        default:
            out.println("Invalid condition");
        }
        return false; 
    }

    /**
     * This method checks the string condtions for all conditions.
     * @param a The tuple to check 
     * @param b The condtions
     * @return true if the condtion passes
     */
    private boolean conditionCheck(Comparable[] a, String[] b) {    
        String c = b[0] + " " + b[1] + " " + b[2]; //this puts the String into the proper format
        boolean find = selectHelper(a, c); 

        for (int i = 3; i < b.length; i = i + 4) { //this loop gets the next condition to check
            String op = b[i];
            String findNext = b[i + 1] + " " + b[i + 2] + " " + b[i + 3];

            boolean next = selectHelper(a, findNext);


            if (op.equals("&&")) { //The and operator 
                find = find && next;
            } else if (op.equals("||")) { //the or operator 
                find = find || next;
            }
            
        }

        return find;
    }

    /************************************************************************************
     * Select the tuples satisfying the given key predicate (key = value).  Use an index
     * (Map) to retrieve the tuple with the given key value.  INDEXED SELECT ALGORITHM.
     *
     * @param keyVal  the given key value
     * @return  a table with the tuple satisfying the key predicate
     */
    public Table select (KeyType keyVal) {
        out.println ("RA> " + name + ".select (" + keyVal + ")");
     
        
        List <Comparable []> rows = new ArrayList <> ();
        for(var e : index.entrySet ()) {
            if (e.getKey().compareTo(keyVal) == 0) {
                rows.add(e.getValue());
            }
        }

        return new Table (name + count++, attribute, domain, key, rows);
    } // select

    /************************************************************************************
     * Union this table and table2.  Check that the two tables are compatible.
     *
     * #usage movie.union (show)
     *
     * @param table2  the rhs table in the union operation
     * @return  a table representing the union
     */
    public Table union (Table table2) {
        out.println ("RA> " + name + ".union (" + table2.name + ")");
        if (! compatible (table2)) { // makes sure the tables are compatible before starting
            return null;
        } //if

        List <Comparable []> rows = new ArrayList <> ();

        rows.addAll(this.tuples); // adds all the table1 tuples into rows
        
        for (int i = 0; i < table2.tuples.size(); i++) {  // goes through table2's tuples
            if (!rows.contains(table2.tuples.get(i))) { // only adds the table2 tuple if it is not already in rows
                rows.add(table2.tuples.get(i));
            }
        }
        //check for duplicates

        return new Table (name + count++, attribute, domain, key, rows);
    } // union

    /************************************************************************************
     * Take the difference of this table and table2.  Check that the two tables are
     * compatible.
     *
     * #usage movie.minus (show)
     *
     * @param table2  The rhs table in the minus operation
     * @return  a table representing the difference
     */
    public Table minus (Table table2) {
        out.println ("RA> " + name + ".minus (" + table2.name + ")");
        if (! compatible (table2)) {
            return null;
        } //if

        List <Comparable []> rows = new ArrayList <> ();

        boolean duplicate; 
        boolean clearTuple; 
        if (this.compatible(table2)) { // makes sure the tables are compatible before starting
            for (Comparable[] t : tuples) { // goes through the tuples in table 1
                clearTuple = true; 
                for (Comparable [] newTableTuple : table2.tuples) { // goes through the tuples in table 2
                    duplicate = true; 
                    for (String columnName : attribute) { //goes through the attributes
                        if (!(t[col(columnName)].equals(newTableTuple[col(columnName)]))) { // checks if the tuple in table 1 = tuple in table 2
                            duplicate = false; 
                        }
                    } 
                    if (duplicate == true) {
                        clearTuple = false; 
                    }
                } if (clearTuple) { // only add the tuple if it is not a duplicate
                    rows.add(t);
                }              
            }
        }

        return new Table (name + count++, attribute, domain, key, rows);
    } // minus

    /************************************************************************************
     * Join this table and table2 by performing an "equi-join".  Tuples from both tables
     * are compared requiring attributes1 to equal attributes2.  Disambiguate attribute
     * names by appending "2" to the end of any duplicate attribute name.  Implement using
     * a NESTED LOOP JOIN ALGORITHM.
     *
     * #usage movie.join ("studioName", "name", studio)
     *
     * @param attributes1  the attributes of this table to be compared (Foreign Key)
     * @param attributes2  the attributes of table2 to be compared (Primary Key)
     * @param table2       the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */
    public Table join (String attributes1, String attributes2, Table table2) {
        out.println ("RA> " + name + ".join (" + attributes1 + ", " + attributes2 + ", "
                                               + table2.name + ")");

        var t_attrs = attributes1.split (" ");
        var u_attrs = attributes2.split (" ");
        var rows    = new ArrayList <Comparable []> ();

        int index1 = -1;
        int index2 = -1;

        //this loop gets the first attributes index
        for (int i = 0; i < this.attribute.length; i++) {
            if (t_attrs[0].equals(this.attribute[i])) {
                index1 = i;
                break;
            }

        }

        //this loop gets the second attribute index
        for (int i = 0; i < table2.attribute.length; i++) {
            if (u_attrs[0].equals(table2.attribute[i])) {
                index2 = i;
                break;
            }       

        }

        //This disambiguates the attribute names
        for (int i = 0; i < table2.attribute.length; i++) {
            if (table2.attribute[i].equals(this.attribute[i])) {
                table2.attribute[i] = table2.attribute[i] + "2";
            }
        }
     
     
        if (index1 > -1 || index2 > -1) { //only goes if the index are bigger than 1
            for (int i = 0; i < this.tuples.size(); i++) { //loops through table 1 tuples 
                for (int k = 0; k < table2.tuples.size(); k++) { //loops through table 2 tuples
                    Comparable[] v = concat(this.tuples.get(i), table2.tuples.get(k));
                    Comparable[] a = this.tuples.get(i);
                    Comparable[] b = table2.tuples.get(k);
                    if (a[index1].compareTo(b[index2]) == 0) { //compares the values and adds
                        rows.add(v);
                    }       
                }
            }
        } else {
            out.println("Invalid Attribute Name.");
            System.exit(0);
        }
   

        return new Table (name + count++, concat (attribute, table2.attribute),
                                          concat (domain, table2.domain), table2.key, rows);
    } // join

    /************************************************************************************
     * Join this table and table2 by performing a "theta-us".  Tuples from both tables
     * are compared attribute1 "op" attribute2.  Disambiguate attribute names by appending "2"
     * to the end of any duplicate attribute name.  Implement using a Nested Loop Join algorithm.
     *
     * #usage movie.join ("studioName == name", studio)
     *
     * @param condition  the theta join condition
     * @param table2     the rhs table in the join operation
     * @return  a table with tuples satisfying the condition
     */
    public Table join (String condition, Table table2) {
        out.println ("RA> " + name + ".join (" + condition + ", " + table2.name + ")");

        var rows = new ArrayList <Comparable []> ();

        String[] conds = condition.split(" ");

     


        for (int i = 0; i < table2.attribute.length; i++) { //this disambiguates the attribute names
            if (table2.attribute[i].equals(this.attribute[i])) {
                table2.attribute[i] = table2.attribute[i] + "2";
            }
        }
       
        String[] combAtt = concat(attribute, table2.attribute); 

        for (int i = 0; i < this.tuples.size(); i++) { //this loops through table 1 tuples
            for (int k = 0; k < table2.tuples.size(); k++) { //this loops through table 2 tuples
                Comparable[] v = concat(this.tuples.get(i), table2.tuples.get(k));
                if (conditionCheck(v, conds, combAtt)) { //checks the condition
                    rows.add(v);
                }
            }
        }
        //This section checks the key to return
        ArrayList<String> tem = new ArrayList<>();
        for (int i = 0; i < conds.length; i++) { // turns the array into an array list 
            tem.add (conds[i]);
        }
        ArrayList<String> key1 = new ArrayList<>();
        ArrayList<String> key2 = new ArrayList<>();

        for (int i = 0; i < key.length; i++) { //turns the table 1 key into array list
            key1.add(key[i]);
        }
        for (int i = 0; i < table2.key.length; i++) { // turns the table 2 key into array list
            key2.add(table2.key[i]);
        }
        ArrayList<String> newKeyList = new ArrayList<>();

        for (int i = 0; i < tem.size(); i++) { //adds the keys if they are attributes
            if (key1.contains(tem.get(i))) {
                newKeyList.add(tem.get(i));
            } else if (key2.contains(tem.get(i))) {
                newKeyList.add(tem.get(i));
            }
        }

        ArrayList<String> tempArray = new ArrayList<>();
        tempArray.add(newKeyList.get(0));
        for(int i = 1; i < newKeyList.size(); i++) { //makes sure there are no duplicates
            if(!tempArray.contains(newKeyList.get(i))) {
                tempArray.add(newKeyList.get(i));
            }

        }  
        String[] newKey2 = tempArray.toArray(new String[tempArray.size()]);



        return new Table (name + count++, concat (attribute, table2.attribute),
                                          concat (domain, table2.domain), newKey2, rows);
    } // join


    /**
     * This method is a helper for the theta join method that checks the condition
     * and returns true if it passes.
    * 
    * @param t1 the tuple to check
    * @param b the condition string
    * @param c An array of combined attributes
    * @return a boolean
    */
    private boolean thetaJoinHelper(Comparable[] t1, String b, String[] c) {

        //splits the string into conditions
        String[] temp = b.split(" ");

        String at1 = temp[0];
        String op = temp[1];
        String at2 = " ";

        //out.println(temp[0]);
        //out.println(temp[2]);
        if (temp[0].equals(temp[2])) {
            at2 = temp[2] + "2";
        } else {
            at2 = temp[2];
        }

        //checks for the attribute index
        int index1 = col(at1);
        int index2 = -1;

        for (int i = 0; i < t1.length; i++) {
             
            if (at2.equals(c[i]) && i != index1 ) {
                
                index2 = i;
                break;
            }
        }
 

        if (index1 == -1 || index2 == -1) {
            return false;
        }

        Comparable value1 = t1[index1];
        Comparable value2 = t1[index2]; 

      

        //swwitches based on the operator 
        switch (op) {
        case "==":
            return value1.compareTo(value2) == 0;   
        case "!=":
            return value1.compareTo(value2) != 0;
        case "<":
            return value1.compareTo(value2) < 0;
        case "<=" :
            return value1.compareTo(value2) <= 0;
        case ">":
            return value1.compareTo(value2) > 0;
        case ">=":
            return value1.compareTo(value2) >= 0;
        default:
            out.println("Invalid condition");
        }

        return false;
    }

    /**
    *  This is used to check for more than one condition in the String. 
    * It is overloaded with the above method.
    * 
    * @param a a tuple to check
    * @param b a string array with the string condition
    * @param d a string array with attributes
    * @return a boolean if the condition passes
    */
    private boolean conditionCheck(Comparable[] a, String[] b, String[] d) {    
        if (b[0].equals(b[2])) {
            b[2] = b[2] + "2";
        }
        //pass through 2 tuples and then find the index
        String c = b[0] + " " + b[1] + " " + b[2];
        boolean find = thetaJoinHelper(a, c, d);

        for (int i = 3; i < b.length; i = i + 4) { //loops through the conditions
            String op = b[i];
            if (b[i + 1].equals(b[i + 3])) {
                b[i + 3] = b[i + 3] + "2";
            }
            String findNext = b[i + 1] + " " + b[i + 2] + " " + b[i + 3];

            boolean next = thetaJoinHelper(a, findNext, d);


            if (op.equals("&&")) {
                find = find && next;
            } else if (op.equals("||")) {
                find = find || next;
            }
            
        }

        return find;
    }


    /************************************************************************************
     * Join this table and table2 by performing an "equi-join".  Same as above equi-join,
     * but implemented using an INDEXED JOIN ALGORITHM.
     *
     * @param attributes1  the attributes of this table to be compared (Foreign Key)
     * @param attributes2  the attributes of table2 to be compared (Primary Key)
     * @param table2       the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */
    public Table i_join (String attributes1, String attributes2, Table table2) {
        out.println ("RA> " + name + ".join (" + attributes1 + ", " + attributes2 + ", "
                                               + table2.name + ")");

        var rows    = new ArrayList <Comparable []> ();


        //This disambiguates the attribute names
        for (int i = 0; i < table2.attribute.length; i++) {
            if (table2.attribute[i].equals(this.attribute[i])) {
                table2.attribute[i] = table2.attribute[i] + "2";
            }
        }
     
       for(var e : index.entrySet ()) {
            for(var x : table2.index.entrySet()) {
                Comparable[] temp1 = e.getValue();
                Comparable[] temp2 = x.getValue();
                int index1 = col(attributes1);
                int index2 = table2.col(attributes2);
                //out.println(index2);
                
                if(e.getValue()[col(attributes1)].compareTo(x.getValue()[table2.col(attributes2)]) == 0) {
                    rows.add(concat(e.getValue(), x.getValue()));
                }
                
            }
        }

         return new Table (name + count++, concat (attribute, table2.attribute),
                                          concat (domain, table2.domain), table2.key, rows);

    } // i_join

    /************************************************************************************
     * Join this table and table2 by performing an "equi-join".  Same as above, but implemented
     * using a Hash Join algorithm.
     *
     * @param attributes1  the attributes of this table to be compared (Foreign Key)
     * @param attributes2  the attributes of table2 to be compared (Primary Key)
     * @param table2       the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */
    public Table h_join (String attributes1, String attributes2, Table table2) {

        //  D O   N O T   I M P L E M E N T

        return null;
    } // h_join

    /************************************************************************************
     * Join this table and table2 by performing an "natural join".  Tuples from both tables
     * are compared requiring common attributes to be equal.  The duplicate column is also
     * eliminated.
     *
     * #usage movieStar.join (starsIn)
     *
     * @param table2  the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */
    public Table join (Table table2) {
        out.println ("RA> " + name + ".join (" + table2.name + ")");

        var rows = new ArrayList <Comparable []> (); 

        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        ArrayList<String> dupAttrs = new ArrayList<>();


        for (int i = 0; i < this.attribute.length; i++) { //adds all of the attribute names from table 1
            temp.add(this.attribute[i]);
        }

        for (int i = 0; i < table2.attribute.length; i++) { //adds the unique attrubutes from table 2 and adds the common ones to dupAttrs
            if (!temp.contains(table2.attribute[i])) {
                temp2.add(table2.attribute[i]);
            } else {
                dupAttrs.add(table2.attribute[i]);
            }
        }

        var attr1 = temp.toArray(new String[temp.size()]);
        var attr2 = temp2.toArray(new String[temp2.size()]);

        Class[] noDupDomains = removeDuplicateDomains(domain, table2.domain);
        var colDomain = extractDom (match (concat (attr1, attr2)), noDupDomains); 

        Comparable[] t= null;
        Comparable[] newtuple = null;

        for (int i = 0; i < this.tuples.size(); i++) { //loops through the table 1 tuples
            t = tuples.get(i);
            for (int j = 0; j < table2.tuples.size(); j++) { //loops through the table 2 tuples
                newtuple = table2.tuples.get(j);
                int number_of_duplicate_values=0;
                for (String attrCheck : dupAttrs) { //loops through the dupAttrs
                    if (t[col(attrCheck)].equals(newtuple[table2.col(attrCheck)])) {
                        number_of_duplicate_values++; //counts the number of duplicate values
                    }
                }
                if (number_of_duplicate_values==dupAttrs.size()) { //adds if the dup values match the dupAttrs size
                    rows.add(concat(this.extract(t,attr1),table2.extract(newtuple, attr2)));
                }
            }
        } 
        //concats the keys together
        ArrayList<String> newKey = new ArrayList<>();
        String[] t1 = concat(key, table2.key);
        for (int i = 0; i < t1.length; i++) {
            newKey.add(t1[i]);
        }
        //removes duplicate keys
        ArrayList<String> tempArray = new ArrayList<>();
        tempArray.add(newKey.get(0));
        for(int i = 1; i < newKey.size(); i++) {
            if(!tempArray.contains(newKey.get(i))) {
                tempArray.add(newKey.get(i));
            }

        }  
        String[] newKey2 = tempArray.toArray(new String[tempArray.size()]);

        

        return new Table (name + count++, concat(attr1, attr2),
                                          noDupDomains, newKey2, rows);
    } // join


    /**
     * This method is used to remove any duplicate domains in the joined tables.
     * 
     * @param a an array of domains 
    * @param b an array of domains
    * @return an array of domains with no duplicates
    */
    private Class[] removeDuplicateDomains(Class[] a, Class[] b) {
        ArrayList<Class> noDups = new ArrayList<>();

        for (int i = 0; i < a.length; i++) { //loops through the first domain of classes
            noDups.add(a[i]);
        }
        
        for (int i = 0; i < b.length; i++) {   //adds unique domains 
            if (!noDups.contains(b[i])) {
                noDups.add(b[i]);
            }
        }

        Class[] temp = noDups.toArray(new Class[noDups.size()]);
        return temp;

    }

    /************************************************************************************
     * Return the column position for the given attribute name or -1 if not found.
     *
     * @param attr  the given attribute name
     * @return  a column position
     */
    public int col (String attr) {
        for (var i = 0; i < attribute.length; i++) {
            if (attr.equals (attribute [i])) {
                return i;
            } //if
        } // for

        return -1;       // -1 => not found
    } // col

    /************************************************************************************
     * Insert a tuple to the table.
     *
     * #usage movie.insert ("Star_Wars", 1977, 124, "T", "Fox", 12345)
     *
     * @param tup  the array of attribute values forming the tuple
     * @return  whether insertion was successful
     */
    public boolean insert (Comparable [] tup) {
        out.println ("DML> insert into " + name + " values ( " + Arrays.toString (tup) + " )");

        if (typeCheck (tup)) {
            tuples.add (tup);
            var keyVal = new Comparable [key.length];
            var cols   = match (key);
            for (var j = 0; j < keyVal.length; j++) {
                keyVal [j] = tup [cols [j]];
            } //for
            if (mType != MapType.NO_MAP) {
                index.put (new KeyType (keyVal), tup);
            } //if
            return true;
        } else {
            return false;
        } // if
    } // insert

    /************************************************************************************
     * Get the name of the table.
     *
     * @return  the table's name
     */
    public String getName () {
        return name;
    } // getName

    /************************************************************************************
     * Print this table.
     */
    public void print () {
        out.println ("\n Table " + name);
        out.print ("|-");
        out.print ("---------------".repeat (attribute.length));
        out.println ("-|");
        out.print ("| ");
        for (var a : attribute) {
            out.printf ("%15s", a);
        } //for
        out.println (" |");
        out.print ("|-");
        out.print ("---------------".repeat (attribute.length));
        out.println ("-|");
        for (var tup : tuples) {
            out.print ("| ");
            for (var attr : tup) {
                out.printf ("%15s", attr);
            } //for
            out.println (" |");
        } // for
        out.print ("|-");
        out.print ("---------------".repeat (attribute.length));
        out.println ("-|");
    } // print

    /************************************************************************************
     * Print this table's index (Map).
     */
    public void printIndex () {
        out.println ("\n Index for " + name);
        out.println ("-------------------");
        if (mType != MapType.NO_MAP) {
            for (var e : index.entrySet ()) {
                out.println (e.getKey () + " -> " + Arrays.toString (e.getValue ()));
            } // for
        } // if
        out.println ("-------------------");
    } // printIndex

    /************************************************************************************
     * Load the table with the given name into memory. 
     *
     * @param name  the name of the table to load
     * @return Table a new Table to load 
     */
    public static Table load (String name) {
        Table tab = null;
        try {
            ObjectInputStream ois = new ObjectInputStream (new FileInputStream (DIR + name + EXT));
            tab = (Table) ois.readObject ();
            ois.close ();
        } catch (IOException ex) {
            out.println ("load: IO Exception");
            ex.printStackTrace ();
        } catch (ClassNotFoundException ex) {
            out.println ("load: Class Not Found Exception");
            ex.printStackTrace ();
        } // try
        return tab;
    } // load

    /************************************************************************************
     * Save this table in a file.
     */
    public void save () {
        try {
            var oos = new ObjectOutputStream (new FileOutputStream (DIR + name + EXT));
            oos.writeObject (this);
            oos.close ();
        } catch (IOException ex) {
            out.println ("save: IO Exception");
            ex.printStackTrace ();
        } // try
    } // save

    //----------------------------------------------------------------------------------
    // Private Methods
    //----------------------------------------------------------------------------------

    /************************************************************************************
     * Determine whether the two tables (this and table2) are compatible, i.e., have
     * the same number of attributes each with the same corresponding domain.
     *
     * @param table2  the rhs table
     * @return  whether the two tables are compatible
     */
    private boolean compatible (Table table2) {
        if (domain.length != table2.domain.length) {
            out.println ("compatible ERROR: table have different arity");
            return false;
        } // if
        for (var j = 0; j < domain.length; j++) {
            if (domain [j] != table2.domain [j]) {
                out.println ("compatible ERROR: tables disagree on domain " + j);
                return false;
            } // if
        } // for
        return true;
    } // compatible

    /************************************************************************************
     * Match the column and attribute names to determine the domains.
     *
     * @param column  the array of column names
     * @return  an array of column index positions
     */
    private int [] match (String [] column) {
        int [] colPos = new int [column.length];

        for (var j = 0; j < column.length; j++) {
            var matched = false;
            for (var k = 0; k < attribute.length; k++) {
                if (column [j].equals (attribute [k])) {
                    matched = true;
                    colPos [j] = k;
                } // for
            } // for
            if ( ! matched) {
                out.println ("match: domain not found for " + column [j]);
            } // if
        } // for

        return colPos;
    } // match

    /************************************************************************************
     * Extract the attributes specified by the column array from tuple t.
     *
     * @param t       the tuple to extract from
     * @param column  the array of column names
     * @return  a smaller tuple extracted from tuple t 
     */
    private Comparable [] extract (Comparable [] t, String [] column) {
        var tup    = new Comparable [column.length];
        var colPos = match (column);
        for (var j = 0; j < column.length; j++) {
            tup [j] = t [colPos [j]];
        } //for
        return tup;
    } // extract

    /************************************************************************************
     * Check the size of the tuple (number of elements in array) as well as the type of
     * each value to ensure it is from the right domain. 
     *
     * @param t  the tuple as a array of attribute values
     * @return  whether the tuple has the right size and values that comply
     *          with the given domains
     */
    private boolean typeCheck (Comparable [] t) { 
        if (this.domain.length != t.length) {
            return false;
        }
        for (int i = 0; i < t.length; i++) { //loops through the tuples to check domain
            if (!domain[i].isInstance(t[i])) {
                return false;
            }
        }
                            
        return true;      
    } // typeCheck

    /************************************************************************************
     * Find the classes in the "java.lang" package with given names.
     *
     * @param className  the array of class name (e.g., {"Integer", "String"})
     * @return  an array of Java classes
     */
    private static Class [] findClass (String [] className) {
        var classArray = new Class [className.length];

        for (var i = 0; i < className.length; i++) {
            try {
                classArray [i] = Class.forName ("java.lang." + className [i]);
            } catch (ClassNotFoundException ex) {
                out.println ("findClass: " + ex);
            } // try
        } // for

        return classArray;
    } // findClass

    /************************************************************************************
     * Extract the corresponding domains.
     *
     * @param colPos  the column positions to extract.
     * @param group   where to extract from
     * @return  the extracted domains
     */
    private Class [] extractDom (int [] colPos, Class [] group) {
        var obj = new Class [colPos.length];

        for (var j = 0; j < colPos.length; j++) {
            obj [j] = group [colPos [j]];
        } // for

        return obj;
    } // extractDom

} // Table class

