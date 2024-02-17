import java.util.Hashtable;

public class Customer {
	private String Name = "";
	private boolean onLine = false;		// boolean on whether the customer is in line to check out or "still shopping" (though modifications may be made while "in line" or at the register)
	// HT for storing the contents of customer's cart
	private Hashtable<Integer,Integer> shopping_cart = new Hashtable<Integer,Integer>();

	// Constructor - sets cust. name
	public Customer(String name) { 
		set_name(name);
	}
	
	// Negative Quantity = subtraction.  0 total removes from memory. This method performs a search operations to find the Item if it is already in cart
	public void modify_cart(Item item, int Quantity) {
		if(shopping_cart.containsKey(item.get_item_number())) {
			Quantity += shopping_cart.get(item.get_item_number());	//adds to pre-existing quantity
		}
		if(Quantity <= 0 && shopping_cart.containsKey(item.get_item_number())) {	// if new qty <= 0 removes product from cart memory
			shopping_cart.remove(item.get_item_number());
		}
		if(Quantity > 0) {			
			shopping_cart.put(item.get_item_number(), Quantity);   	// places new distinct item in cart, if other 2 tests ^ failed
		}
	}
	
	// Method returning HT of shopping cart contents ==> used for processing
	public Hashtable<Integer,Integer> get_cart_contents() { 
		return shopping_cart;
	}
	
	// Counts items in customer's cart
	public int cart_item_count() { 
		return shopping_cart.size(); 
	}
	
	// Setter and getter for on_line boolean and name
	public boolean is_on_line() { 
		return onLine;
	}
	public void set_on_line(boolean yorn) { 
		onLine = yorn; 
	}
	public String get_name() {  
		return Name;
	}
	public void set_name(String name) { 
		Name = name; 
	}
}