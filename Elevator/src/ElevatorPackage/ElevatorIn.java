package ElevatorPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.*;
import ElevatorPackage.*;
import cmsc420.xml.XmlUtility;

public class ElevatorIn {
	
	protected static TreeSet<Person> persons = new TreeSet<Person>(personComp());
	protected static ArrayList<Elevator> elevators = new ArrayList<Elevator>();
	protected static HashSet<String> names = new HashSet<String>();
	protected static HashMap<Integer, TreeSet<Person>> personFloor = new HashMap<Integer, TreeSet<Person>>();
	protected static int maxFloor = 1;
	protected static int minFloor = 0;
	
	
	/*
    public static Comparator<Person> personComp(){
		return new Comparator<Person>(){

			@Override
			public int compare(Person o1, Person o2) {
				if (o1.getHour() > o2.getHour()){
					return 1;
				} else if (o1.getHour() == o2.getHour() && o1.getMin() > o2.getMin()){
					return 1;
				}else if (o1.getHour() == o2.getHour() && o1.getMin() == o2.getMin() && o1.getSec() > o2.getSec()){
					return 1;
				}else if (o1.getHour() == o2.getHour() && o1.getMin() == o2.getMin() && o1.getSec() == o2.getSec()){
					return 1;
				} else {
					return -1;
				}
			}

		};
	}
    */
    
	
	public static Comparator<Person> personComp(){
		return new Comparator<Person>(){

			@Override
			public int compare(Person o1, Person o2) {
				 if (o1.getTime().compareTo(o2.getTime())>=0){
					 return 1;
				 } else {
					 return -1;
				 }
			}

		};
	}
	
	
	public static void main(String[] args) {

		FileInputStream xmlFile = null;
		try {
			xmlFile = new FileInputStream("createPerson1.input.xml");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}

		try {
			Document doc = XmlUtility.validateNoNamespace(xmlFile);

			Element commandNode = doc.getDocumentElement();
			Element docRoot = doc.getDocumentElement();

			Node rootNode = docRoot.getFirstChild().getParentNode();
			NamedNodeMap commandAttr = rootNode.getAttributes();
			maxFloor = Integer.parseInt(commandAttr.getNamedItem("floors").getNodeValue());
			

			final NodeList nl = commandNode.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == Document.ELEMENT_NODE) {
					commandNode = (Element) nl.item(i);
					Node commandIn = nl.item(i);
					commandAttr = commandIn.getAttributes();
					if (commandIn.getNodeName().equals("createPerson")) {
						String name = commandAttr.getNamedItem("name").getNodeValue();
						int dest = Integer.parseInt(commandAttr.getNamedItem("dest").getNodeValue());
						int source = Integer.parseInt(commandAttr.getNamedItem("source").getNodeValue());
						int hour = Integer.parseInt(commandAttr.getNamedItem("hour").getNodeValue());
						int min = Integer.parseInt(commandAttr.getNamedItem("minute").getNodeValue());
						int sec = Integer.parseInt(commandAttr.getNamedItem("second").getNodeValue());
						int weight = Integer.parseInt(commandAttr.getNamedItem("weight").getNodeValue());
						Time t = new Time(hour, min, sec);
						Person newPerson = new Person (name, source, dest, weight, t);
						names.add(name);
					
						persons.add(newPerson);
						if (personFloor.containsKey(source)){
							TreeSet<Person> list = personFloor.get(source);
							list.add(newPerson);
							personFloor.put(source,list);
						} else {
							TreeSet<Person> list = new TreeSet<Person>(personComp());
							list.add(newPerson);
							personFloor.put(source,list);
						}
						
						
					} else if (commandIn.getNodeName().equals("createElevator")) {
						String name = commandAttr.getNamedItem("name").getNodeValue();
						int initial = Integer.parseInt(commandAttr.getNamedItem("initial").getNodeValue());
						int waitSec = Integer.parseInt(commandAttr.getNamedItem("waitSec").getNodeValue());
						int maxSec = Integer.parseInt(commandAttr.getNamedItem("maxSec").getNodeValue());
						int maxW = Integer.parseInt(commandAttr.getNamedItem("maxWeight").getNodeValue());
						Elevator newelt = new Elevator (name, initial, waitSec, maxSec, maxW);
						Elevator.importPersons(persons);
						elevators.add(newelt);
					}

				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {

			/* TODO: Process fatal error here */

		} finally {
			/*
			 * try { XmlUtility.print(results); } catch (TransformerException e)
			 * { e.printStackTrace(); }
			 */
			System.out.println(persons.size());
			for (Person p:persons){
				System.out.println(p.getName() + " " + p.getTime().toString() + "  initialFloor:" + p.getSource());
			}
		
			for (int i = minFloor; i <= maxFloor; i++ ){
				if (personFloor.containsKey(i)){
					System.out.print(i + ": ");
					for (Person p:personFloor.get(i)){
						System.out.print(p.getName());
					}
					System.out.println();
				}
			}
			
			for (Elevator e: elevators){
				System.out.print(e.getName()+" ");
			}
			System.out.println();
			
			
			Control c = new Control(persons, elevators, personFloor, maxFloor, minFloor, names);
			c.run();
			 
			
		}
	}
}
