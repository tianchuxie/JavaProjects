package ElevatorPackage;
import ElevatorPackage.*;

public class Person {
	
	protected String name;
	
	protected int source, dest, weight;
	protected static boolean inside;
	protected Time time; 
	
	public Person(){
		
	}
	
	public Person(String name, int source, int dest, int weight, Time time){
		this.source = source;
		this.dest = dest;
		this.name = name;
		this.inside = false;
		this.weight = weight;
		this.time = time;
	}
	
	public Time getTime(){
		return this.time;
	}
	
	public int getWeight(){
		return this.weight;
	}
	


	
	public String getName(){
		return this.name;
	}
	
	public int getSource(){
		return this.source;
	}
	
	public int getDest(){
		return this.dest;
	}
	
	public boolean isUp(){
		return this.dest - this.source > 0;
	}
	/*
	public String timeToString(){
		String h = "";
		String m = "";
		String s = "";
		if (this.hour < 10){
			h = "0";
		}
		if (this.min < 10) {
			m = "0";
		}
		
		if (this.sec < 10){
			s = "0";
		}
		
		return h + this.hour +":" + m + this.min + ":" + s + this.sec;
	}
	*/

}
