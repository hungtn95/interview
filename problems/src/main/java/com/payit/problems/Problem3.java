package com.payit.problems;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.payit.problems.models.Customer;


/**
 * We have a list of customers and we are fairly certain
 * that there are duplicates in the list.
 *
 * You must count the number of duplicates and possible duplicates.
 *
 * A duplicate is a customer where their first name, last name, and email address all match.
 * A possible duplicate is where their first name and last name match but they have different email addresses.
 *
 * The list of customers is found in the json document 'customers.json' found in the resources directory.
 * You must load and deserialize the document into a list of Customer objects. (The customer object is found in the models package)
 * Feel free to use any open source library you would like to aid you in the deserialization. (Hint: Gradle is your friend)
 * Once you have a list of customer objects count the number of duplicates and the number of possible duplicates.
 * Print your totals on the console.
 *
 * Other Notes:
 * A matching pair is considered 1 duplicate. Three of the same is considered two matches. (i.e. N - 1)
 *
 */
public class Problem3 {

    public void findDuplicates() {
    	Gson gson = new Gson();
    	FileReader reader;
    	int duplicates = 0, possible_duplicates = 0; 
		try {
			reader = new FileReader(this.getClass().getClassLoader().getResource("file/customers.json").getPath());
			Type listType = new TypeToken<List<Customer>>(){}.getType();
			List<Customer> customers = gson.fromJson(reader, listType);
			Comparator<Customer> comparator1 = new Comparator<Customer>() {
		        @Override
		        public int compare(Customer c1, Customer c2) {
		            // TODO Auto-generated method stub
		            return (c1.getFirstName().equals(c2.getFirstName()) && c1.getLastName().equals(c2.getLastName()) && c1.getEmailAddress().equals(c2.getEmailAddress())) ? 0 : 1;
		        }
			};
			Comparator<Customer> comparator2 = new Comparator<Customer>() {
		        @Override
		        public int compare(Customer c1, Customer c2) {
		            // TODO Auto-generated method stub
		            return (c1.getFirstName().equals(c2.getFirstName()) && c1.getLastName().equals(c2.getLastName()) && !c1.getEmailAddress().equals(c2.getEmailAddress())) ? 0 : 1;
		        }
			};
			Set<Customer> unique = new TreeSet<Customer>(comparator1);
			for (Customer customer : customers) {
				if (unique.contains(customer)) {
					duplicates++;
				} else {
					unique.add(customer);
				}
			}
			unique = new TreeSet<Customer>(comparator2);
			for (Customer customer : customers) {
				if (unique.contains(customer)) {
					possible_duplicates++;
				} else {
					unique.add(customer);
				}
			}
			System.out.println(duplicates+possible_duplicates);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
		}

    }

}
