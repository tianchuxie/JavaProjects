package ElevatorPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.Scanner; 

public class Control {
	
	protected TreeSet<Person> persons;
	protected HashSet<String> nameD = new HashSet<String>();
	protected ArrayList<Elevator> elevators;
	protected HashMap<Integer, TreeSet<Person>> personFloor;
	protected static HashMap<String, Person> namePerson= new HashMap<String, Person>();
	protected int maxFloor;
	protected int minFloor;
	protected static Time time;
	
	public Control(TreeSet<Person> p, ArrayList<Elevator> e, HashMap<Integer, TreeSet<Person>> pf, int max, int min, HashSet<String> n){
		persons = p;
		elevators = e;
		personFloor = pf;
		maxFloor = max;
		minFloor = min;

	}

	
	private Person getNextPerson(){
		Person p = persons.pollFirst();
		String k = p.getName();
		
		while (nameD.contains(k)){
			p = persons.pollFirst();
		}
		return p;
		

	}
	
	private Elevator getNextElevator(Person p){
		return elevators.get(0);
	}
	
	private void prtPersonIn(Person p, Elevator e){
		System.out.println( time.toString() + " " +  p.getName() + " enter " + e.getName() + " at Floor" + p.getSource()); 
	}
	
	private void prtPersonOut(String p, Elevator e){
		System.out.println( time.toString() + " " + p +" exit " + e.getName() + " at Floor" + e.getFloor()); 
	}
	
	private void elevatorOpen(Elevator e){
		System.out.println(time.toString() + " " + e.getName() +" open at Floor" + e.getFloor());
		if (!e.isEmpty()){
			System.out.print("command: ");
			commandOpen(e);
		}
		
		
		
	}
	
	private void elevatorClose(Elevator e){
		String s = e.personDisplay();
		System.out.println(time.toString() + " " + e.getName() +" close at Floor" + e.getFloor() + " going " + e.prtDirection() + " carrys on " + s + " total " + e.getWeight() + " weight");
		if (!s.equals("")){
		 System.out.print("command: ");
		commandClose(e);
		}
	}
	
	private void commandOpen(Elevator e){
		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();
		for (int i = 0; i < number; i++){
			String command = scan.next();
			if (command.equals("HoldDoor")){
				String personName = scan.next();
				int holdSec = scan.nextInt();
				if (holdSec > e.getMaxSec()){
					time.addSec(e.getMaxSec());
				} else {
					time.addSec(holdSec);
				}
			
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} else if (command.equals("blahblahblah")){
				
			} else {
				System.out.println("invalid command");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		} 
			time.addSec(e.getWaitSec());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		

		
	}
	
	private void commandClose(Elevator e){
		Scanner scan = new Scanner(System.in);
		int number = scan.nextInt();
		for (int i = 0; i < number; i++){
			
			String command = scan.next();
			if (command.equals("Emergency")){
				System.out.println("Emergency has called");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if (command.equals("blahblahblah")){
				
			} else {
				System.out.println("invalid command");
			}
		
		} 
			time.addSec(e.getOperateSec());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
	}
	
	
	// Because the element in persons, is not the same as element in personFloor, can only compare by its name
	
	private void deletePerson(Person p ){
       nameD.add(p.getName());
		
	}
	
	
	private ArrayList<Person> getfloorPersons(Elevator e){
		int currentFloor = e.getFloor();
		Time closeDoorTime = new Time(time);
		ArrayList<Person> list = new ArrayList<Person>();
		closeDoorTime.addSec(e.getWaitSec());
		if (personFloor.containsKey(currentFloor)){
			for (Person p : personFloor.get(currentFloor)){
				if (!nameD.contains(p.getName()) && closeDoorTime.compareTo(p.getTime()) >= 0  && p.isUp() == e.isUp() && e.canEnter(p)){
					list.add(p);
				}
			}
		}
		return list;
	}
	
	

	
	private void operateElevator(Elevator e){
		while (!e.isEmpty()){
			boolean exit = personExitElv(e);
			boolean enter = false;
			
		   
		
			ArrayList<Person> floorPersons = getfloorPersons(e);
			if (floorPersons.size() > 0){
				for (Person p: floorPersons ){
						personInElev(p, e);
						
				}
				enter = true;
			}
			
			if (exit || enter){
				elevatorOpen(e);
			}
			
			if (!e.isEmpty()){
				elevatorClose(e);
			}
			
			e.operate();
			
			
			
			
			
		}
	}
	
	private void personInElev(Person p, Elevator e){
		e.enterPerson(p);
		deletePerson(p);
		prtPersonIn(p, e);
	}
	
	private boolean personExitElv(Elevator e){
		ArrayList<String> exitPersonList = e.exitPerson();
		for (String p : exitPersonList){
			prtPersonOut(p,e);
		}
		
		if (exitPersonList.size() > 0 && !e.isEmpty()){
			return true;
		}
	    return false;
		
	}
	
	public void run(){ 
		System.out.println("run");
		System.out.println();
		Person firstPerson = getNextPerson();
		Elevator elev = getNextElevator(firstPerson);
		time = new Time(firstPerson.getTime());
		/**/
		
		personInElev(firstPerson, elev);
	//	elevatorOpen(elev);
		operateElevator(elev);
		
		
		while (persons.size() >0){
			time.addSec(10);
			Person nextPerson = getNextPerson();
			Elevator nextElevator = getNextElevator(nextPerson);
			prtPersonIn(nextPerson, nextElevator);
			personInElev(nextPerson, nextElevator);
			operateElevator(nextElevator);
		}
		
		System.out.println("done");
		/*
		Scanner scan = new Scanner(System.in);
		String s = scan.next();
		String s2 = scan.next();
		System.out.print(s);
		System.out.print(s2);
		*/
		
		
	}
}
