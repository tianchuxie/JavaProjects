package com.mkyong.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mkyong.utils.CSVUtils;
public class CSVReader {
	 public static void main(String[] args) throws Exception {

	        String csvFile = "FluNet_6"+ ".csv";
		   
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";
	        String c1 = "final6.csv";
	        FileWriter writer = new FileWriter(c1);
	        TreeMap<String, Integer> coulist = new TreeMap<String, Integer>();
	        int cn = 111;
	        TreeMap<String, Integer> reglist = new TreeMap<String, Integer> ();
	        TreeMap<Integer, Integer> arr = new TreeMap<Integer, Integer>();
	        reglist.put("African Region of WHO",3);
	        reglist.put("Eastern Mediterranean Region of WHO",4);
	        reglist.put("European Region of WHO",2);
	        reglist.put("Region of the Americas of WHO",1);
	        reglist.put("South-East Asia Region of WHO", 6);
	        reglist.put("Western Pacific Region of WHO", 5);
	        
	        int rn = 6;

	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            line = br.readLine();
	            line = br.readLine();
	            line = br.readLine();
	            line = br.readLine();
	            
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] l = line.split(cvsSplitBy);
	                if (l.length > 3){
	                String il = "0";
	                if (l[21].equals("Sporadic")){
	                	il = "1";
	                } else if (l[21].equals("Local Outbreak")){
	                	il = "2";
	                } else if (l[21].equals("Regional Outbreak")){
	                	il = "3";
	                } else if (l[21].equals("Widespread Outbreak")){
	                	il = "4";
	                }
	                
	                String rname = new String();
	                rname = "";
	                if (reglist.containsKey(l[1])){
	                	rname = String.valueOf(reglist.get(l[1]));
	                } else {
	                	rn++;
	                	reglist.put(l[1],rn);
	                	rname = String.valueOf(rn);
	                }
	                
	                String cname = new String();
	                cname = "";
	                if (coulist.containsKey(l[0])){
	                	cname = String.valueOf(coulist.get(l[0]));
	                } else {
	                	cn++;
	                	coulist.put(l[0],cn);
	                	cname = String.valueOf(cn);
	                	arr.put(cn,reglist.get(l[1]));
	                }
	                
	                
	               // char[] newk = l[5].toCharArray();
	                //System.out.println(newk);
	               // k = "" + 
	               // if (newk[1] != '/')
	               
	                
	                CSVUtils.writeLine(writer, Arrays.asList(cname, rname, l[3], l[4], l[5].substring(5,7),il));
	                }

	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            
	            
	            writer.flush();
	            writer.close();
	            for(Map.Entry<String,Integer> entry : coulist.entrySet()) {
	            	  String key = entry.getKey();
	            	  Integer value = entry.getValue();

	            	 // System.out.println("\""+ value + "\": \"" + key +"\",");
	            	}
	            
	            for(Map.Entry<String,Integer> entry : reglist.entrySet()) {
	            	  String key = entry.getKey();
	            	  Integer value = entry.getValue();
	            	

	            	//  System.out.println("\""+ value + "\": \"" + key +"\",");
	            	}
	            for(Map.Entry<Integer,Integer> entry : arr.entrySet()) {
	            	  Integer key = entry.getKey();
	            	  Integer value = entry.getValue();

	            	  System.out.println("\""+ key + "\": \"" + value +"\",");
	            	}
	        }
	        

	    }
}
