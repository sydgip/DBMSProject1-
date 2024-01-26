No Special Compliation: javac Table.java 
To Run : java MovieDB or java MovieDB2
Ensure that Table.java and database/Tester are all in the same package. 
Compatibility is defined as same arity, cardinality, and domain. 
In the Compatibility method it checks for same arity and domain.

Overall Summary of Table.java 
    Java implementation of relational algebra :
        Project: Sarayu Ramachandra
            - use a for each loop to check that the attributes given by the user are valid attributes in the table
            - use a for each loop to go through each of the tuples in the table
            - use a nested for each loop to go through each of the tuples in rows
            - compare the each of the keys in each tuple and only add to rows if it is not already in it
        Select Operator:
        Union: Sumin Lee
            - check that if table and table2 are compatible
            - use a for loop to go through each tuples in a table
            - compare both tables and output is mutual attributes and duplicates are deleted.
        Minus: Sarayu Ramachandra 
            - check that table1 and table2 are compatible
            - use a for each loop to go through the tuples in table 1
            - use a nested for each loop to go through the tuples in table 2
            - for each attribute, check that the tuple in table 1 matches the value in that attribute in the tuple in table 2
            - if they don't match (they are not duplicates), and it is not in table2 then add it to rows
        Equi-Join: Katie Jordan	
	        - gets the index of the attribute from the parameters
            - uses a for loop to disambiguate the common attribute names
            - checks to make sure the index is valid 
 	        - uses a for loop to go through the table 1 tuples
            - uses a for loop to go through the table 2 tuples
	        - compares the values at the indices and adds to rows accordingly
        Natural Join: Katie Jordan + Sarayu Ramachandra
	        - uses a for loop to add all the attribute names from table 1
	        - uses a for loop to add the unique attribute names from table 2 to a list and the common attributes to a separate list so we can differentiate
            - uses a for loop to go through the tuples in table 1
            - uses a for loop to go through the tuples in table 2
            - loops through the dupAttrs list and counts the number of common values
            - if the number of common values matches the length of the dupAttrs then we extract the tuple from each table based on the attributes and concat them together and add it to rows
            - use a for loop and concats the primary keys together and then gets rid of the duplicates
        Theta Join: Katie Jordan
            - split the condition string into a string array
 	        - uses a for loop to disambiguate the attribute names
            - uses a for loop to go through the table 1 tuples
	        - uses a for loop to go through the table 2 tuples
            - uses a separate method to check if the condition passes
	        - concatenates the tuples together
	        - the condition check takes in the tuple and 2 string arrays, makes the string the proper format and passes to the thetaJoinHelper
            - the thetaJoinHelper checks to see if the condition passes and returns
            - the concatenated tuple is added to row if the condition passes
	        - uses a for loop to return the proper primary key


