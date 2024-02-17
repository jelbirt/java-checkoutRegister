import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class RegisterLine {
	// ArrayList to contain all open/active registers
	private ArrayList<CheckOutRegister> lines = new ArrayList<>();
	 // HT containing customer info<ID,Customer>
	private Hashtable<Integer,Customer> customers = new Hashtable<>();
	// HT containing Product info <Item id #, Item Name>
	private Hashtable<Integer,Item> inventory = new Hashtable<Integer,Item>(); 
	
	private Random RND = new Random(System.currentTimeMillis());
	
	public static void main(String[] args) {
		new RegisterLine();
	}
	
	public RegisterLine() { 
		// NOTE: For a real scenario, info could be input using Scanner class, for this run through we simply use example values:
		// Setup the Register lines
		lines.add(new CheckOutRegister());
		lines.add(new CheckOutRegister());
		lines.add(new CheckOutRegister());
		
		// setup some example items/Prices
		inventory.put(1,new Item(1,"Product 1",0.10));
		inventory.put(2,new Item(2,"Product 2",0.20));
		inventory.put(3,new Item(3,"Product 3",0.30));
		inventory.put(4,new Item(4,"Product 4",0.40));
		inventory.put(5,new Item(5,"Product 5",0.50));
		inventory.put(6,new Item(6,"Product 6",0.60));
		
		// Create some example Customers
		customers.put(1, new Customer("Jake"));
		customers.put(2, new Customer("Prof. Al-Faris"));
		customers.put(3, new Customer("Ben"));
		customers.put(4, new Customer("Zoey"));
		customers.put(5, new Customer("Pinky"));
		customers.put(6, new Customer("Fiona"));
		customers.put(7, new Customer("Peanut"));
		
		// Place a random item of random quantity in a random Customer's cart 100 times
		// Meant to simulate filling shopping carts with various items
		for(int i=0;i<100;i++) {
			int inv_id = RND.nextInt(inventory.size()) + 1; // Random Item to add to the customer cart
			int qty = RND.nextInt(10) + 1; // Random QTY of item to add to the customer cart
			int randoCustomer = RND.nextInt(customers.size()) + 1; // Random Customer to assign values to
			
			// Test for memory (does not doing line 45 result in an issue)
			Customer c = customers.get(randoCustomer);
			c.modify_cart(inventory.get(inv_id), qty);
			customers.put(randoCustomer, c);
		}
		
		// Randomly Assign Customers to Lines
		// NOTE: rather than random, could alternatively find the line with the shortest line_count and assign the customer there.
		for(int i=1;i<=customers.size();i++) {
			Customer c = customers.get(i); // get the customer in question
			int regID = RND.nextInt(lines.size()); // Pick a random register
			CheckOutRegister register = lines.get(regID);
			register.add_to_line(c);
			lines.set(regID, register); // verify necessary for memory update
		}

		// Process the register lines
		// This is sequential - If using multi-threaded processing, a separate thread could be used for each register
		// as it queues independent of other lines.
		for(int i=0;i<lines.size();i++) {
			CheckOutRegister register = lines.get(i);
			while(register.line_count() > 0) {
				System.out.println("Register Line List : " + register.line_list());
				// Object HT --> cast elements to their data type upon calling
				Hashtable<String,Object> results = register.checkout(inventory);	
				System.out.println("Register : " + (i+1));
				System.out.println("Customer : " + (String)results.get("Customer"));	// prints cust. name
				ArrayList<String> out = (ArrayList<String>)results.get("Receipt");
				for(int j=0;j<out.size();j++) {
					System.out.println("\t" + out.get(j));
				}
				System.out.println("Total : $ " + (Double)results.get("TotalCost"));
				System.out.println("\n");
			}
		}
	}
	
	// Method to call modify cart from Customer class, effectively modifying the cart "at the register"
	public void modify_cart(Customer c, Item item, int quantity) {
		c.modify_cart(item, quantity);
	}

	// Method to join the passed customer into the waiting line for the passed register
	public void get_on_line(Customer c, CheckOutRegister r) {
		if(c.is_on_line() || c.cart_item_count() == 0)
			return;
		r.add_to_line(c);
	}

	// Method to search for a Customer by their name to see if they are in line or still shopping
	public boolean customer_in_line(String customerName) {
		if (!customers.containsKey(customerName)) {		// if there are no customers with the given name, they cannot be in line
			return false;
		}
		for(int i=0;i<lines.size();i++) {
			String lineList = lines.get(i).line_list();
			if (lineList.contains(customerName)) {		// if the list of customer names in the register's line contains the search name return true
				return true;
			}
		}
		return false;
	}

}