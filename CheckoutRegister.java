import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;



public class CheckOutRegister {
	// LinkedList Queue to contain customers and send them to registers in the order they enter
	private Queue<Customer> line = new LinkedList<Customer>();

	//Constructor:
	public CheckOutRegister() { 
	}
	
	// Method returning a String list of the names of each customer currently in line
	public String line_list() {
		String cust_in_line = "";
		
		for(Customer c : line) {
			cust_in_line = cust_in_line + "\t" + c.get_name();
		}
		return cust_in_line;
	}
	
	// Method to add the customer it is passed to the waiting line
	public void add_to_line(Customer c) {
		if(c.is_on_line()) {
			return;
		}
		c.set_on_line(true); 
		line.add(c);
	}
	
	// Method to move onto the next customer to begin Check-out, removing them from the line queue
	public Customer next_in_line() {
		if(!line.isEmpty()) {
			return line.poll();
		}
		return null;
	}
	
	// Method returning the size of the waiting line queue
	public int line_count() {
		return line.size();
	}
	
	// Method to check out a customer, returns HT containing customer's "receipt"
	public Hashtable<String,Object> checkout(Hashtable<Integer,Item> inventory) {
		// Object HT to contain AL of Strings for receipt as well as total cost value
		Hashtable<String,Object> results = new Hashtable<String,Object>();
		// String ArrayList to hold the strings containing receipt line data (product, quantity, cost)
		ArrayList<String> item_receipt = new ArrayList<String>();
		
		double total_cost = 0.0;
		Customer c = next_in_line();
		
		results.put("Customer",c.get_name());
		
		// HT containing Product ID as Integer Key and quantity Integer value
		Hashtable<Integer,Integer> cart = c.get_cart_contents();
		// Enumeration of cart keys (Product ID numbers) to loop through with while() 
		Enumeration<Integer> cart_keys = cart.keys();
		while(cart_keys.hasMoreElements()) {
			int item_id = cart_keys.nextElement();
			Item item = inventory.get(item_id);
			total_cost += cart.get(item_id) * item.get_unit_cost();
			item_receipt.add(item.get_name() + "\t" + cart.get(item_id) + " ($ " + item.get_unit_cost() + ")\t $ " + (cart.get(item_id) * item.get_unit_cost()));
		}
		results.put("Receipt",item_receipt);
		results.put("TotalCost",total_cost);
		
		return results;
	}
}