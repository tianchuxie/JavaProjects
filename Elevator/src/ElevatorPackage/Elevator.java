package ElevatorPackage;
import ElevatorPackage.*;
import java.util.*;

public class Elevator {
	protected String name;
	
	protected int floor, destination = -200;
	protected int waitSec, maxSec, maxWeight, operateSec = 5;
	protected static HashMap<String, Person> namePerson= new HashMap<String, Person>();
	protected static List<String> personCarry = new ArrayList<String>();
	protected int weights = 0;
	
	
	/*
    public static Comparator<Person> destComp(){
		return new Comparator<Person>(){

			@Override
			public int compare(Person o1, Person o2) {
				if (o1.getDest() > o2.getDest()){
					return -1;
				} else {
					return 1;
				}
			}

		};
    }
    */
	
	public static void importPersons(TreeSet<Person> persons){
		for (Person p: persons){
			namePerson.put(p.getName(), p);
		}
	}
    
    public boolean canEnter(Person p){
    	return weights + p.getWeight() <= maxWeight;
    }
    
    public String personDisplay(){
    	String result = "";
    	for (String p:personCarry){
    		result += p + " ";
    	}
    	return result;
    }
    
    
    public void operate(){
    	if (isUp()){
    		floor ++;
    	} else {
    		floor --;
    	}
    }
    
    public void enterPerson(Person p){
    	weights += p.getWeight();
    	if (destination == -200 ){
    		destination = p.getDest();
    	} else if (p.isUp() && p.getDest() > destination){
    		destination = p.getDest();
    	} else if (!p.isUp() && p.getDest() < destination){
    		destination = p.getDest();
    	}
    	
    	personCarry.add(p.getName());
    	
    }
    
    public boolean isEmpty(){
    	return personCarry.isEmpty();
    }
    
    public ArrayList<String> exitPerson(){
    	ArrayList<String> result = new ArrayList<String>();
    	ArrayList<String> after = new ArrayList<String>();
    	if (personCarry.size() == 0){
    		return result;
    	}
    	
    	
    	for (String s: personCarry){
    		Person p = namePerson.get(s);
    		if (p.getDest() == floor ){
    			result.add(s);
    			weights -= p.getWeight();
    		} else {
    		after.add(s);
    		}
    	}
    	
    	if (weights == 0){
    		destination = -200;
    	}
    	
    	personCarry = after;
    	
    	return result;
    }
	
	
	public Elevator(String name, int floor, int waitSec, int maxSec, int maxWeight){
		this.name = name;
		this.floor = floor;
		this.waitSec = waitSec;
		this.maxSec = maxSec;
		this.maxWeight = maxWeight;
		
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getFloor(){
		return this.floor;
	}
	
	public int getWaitSec(){
		return this.waitSec;
	}
	
	public int getMaxSec(){
		return this.maxSec;
	}
	
	public int getOperateSec(){
		return this.operateSec;
	}
	
	/* didn't deal with the empty cause, but for the empty it is still up*/
	public boolean isUp(){
		return destination - floor > 0 ;
	}
	
	public String prtDirection(){
		if (destination - floor > 0 ){
			return "up";
		} else if (destination - floor < 0 ){
			return "down";
		} else {
			return "";
		}
	}

	public int getWeight() {
		// TODO Auto-generated method stub
		return weights;
	}

}
