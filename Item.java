public class Item {
	private int ItemNumber = 0;		// Identifies Item from inventory - grocery stores may have a vast amount of different products
	private String Name = "";		// Item name
	private Double UnitCost = 0.0;
	
	// Basic constructor with only an item number
	public Item(int item_number) { 
		set_item_number(item_number); 
	}
	// Constructor with more parameters
	public Item(int item_number, String name, Double cost) {
		set_item_number(item_number); set_name(name); set_unit_cost(cost);
	}
	
	//Setters and Getters
	public int get_item_number() { 
		return ItemNumber; 
	}
	public void set_item_number(int number) {
		ItemNumber = number; 
	}
	public String get_name() {  
		return Name;
	}
	public void set_name(String name) {
		Name = name; 
	}
	public Double get_unit_cost() { 
		return UnitCost;
	}
	public void set_unit_cost(Double cost) throws NumberFormatException { 
		if(cost < 0.0) { 
			throw new NumberFormatException(); 
		}
		UnitCost = cost; 
	}
	
}