package com.bham.pij.assignments.candidates;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class CleaningUp {
	public void cleanUpFile(){
		try {
			Path file = Paths.get("dirtycv.txt");
			InputStream input = null;
			input = Files.newInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = reader.readLine();
			
			String content = new String(Files.readAllBytes(file)); //places all the contents of the file into a string
			//System.out.println(content);
			String str1=content.replaceAll("[\r\n]+", " "); //removes all new lines and places all onto one line
            
            String tempStr1 = str1;
            //to count the number of CV's
            int numOfCVs = 0;
            int index = str1.indexOf("CV");
            while (index != -1) {
            	numOfCVs++;
            	tempStr1 = tempStr1.substring(index + 1);
            	index = tempStr1.indexOf("CV");
            }
            //splits the line which contains all the CV's into an array split at each occurance of "CV"
            String str[] = str1.split("CV");
            
            
            //Creating the file cleancv if it doesnt already exist and opening the bufferedWriter 
            File filee = new File("cleancv.txt");
        	BufferedWriter writer = new BufferedWriter(new FileWriter(filee));
            if (!filee.exists()) {
            	filee.createNewFile();
            }
            
            //this section puts each cleanString (correctly formatted) into an arrayList
            ArrayList<String> eachLine = new ArrayList<String>();
            
    		for (int ind = 1; ind < str.length; ind++) {
            
    			String linee = str[ind];
    			
    			
    			eachLine.add(run(linee, str, str1, ind));
    			
    			
    		}
    		//Adds each element of the eachLine arraylist into one string seperated by a line and then write this to the file
            StringBuffer ss = new StringBuffer();
            for (String s : eachLine) {
            	ss.append(s);
            	ss.append("\n");
            }
            String finalStr = ss.toString();
            //System.out.println(finalStr);
            writer.write(finalStr);
            writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String run(String linee, String[] str, String str1, int ind) {

        
		//creating arraylists to hold all the relevant information for each CV
        ArrayList<String> surname = new ArrayList<String>();
        ArrayList<String> eMail = new ArrayList<String>();
        ArrayList<String> address = new ArrayList<String>();
        ArrayList<String> qualification = new ArrayList<String>();
        ArrayList<String> positionOne = new ArrayList<String>();
        ArrayList<String> experienceOne = new ArrayList<String>();
        ArrayList<String> positionTwo = new ArrayList<String>();
        ArrayList<String> experienceTwo = new ArrayList<String>();
        
        //if the CV contains the key pieces of information, add them to the corresponding arrayList
        if (str[ind].contains("Surname")){
        	surname.add(str[ind].substring(str[ind].indexOf("Surname:") + 8, str[ind].indexOf(" First")) + "000" + ind);
        }
        
        

        if (str[ind].contains("Qualification")){
        	if (str[ind].contains("Science")) {
        	qualification.add(str[ind].substring(str[ind].indexOf("Qualification:") + 14, str[ind].indexOf(" Science") + 8));
        	}
        	if (str[ind].contains("None")) {
        		qualification.add("None");
        	}
        }
        
        if (str[ind].contains("eMail")){
        	eMail.add(str[ind].substring(str[ind].indexOf("eMail:") + 6, str[ind].indexOf(".com") + 4));
        }
        
        //counting the number of Positions occuring in the CV
        int t = str[ind].indexOf("Position");
        int count = 0;
        String strr = str[ind];
        while (t != -1) {
            count++;
            strr = strr.substring(t + 1);
            t = strr.indexOf("Position");
        }
        
        //TO COUNT HOW MANY POSITIONS AND EXPERIENCES THEY HAVE AND ADD TO THE ARRAYLISTS ACCORDINGLY
        if (count == 1) {
            if (str[ind].contains("Position")){
            	positionOne.add(str[ind].substring(str[ind].indexOf("Position:") + 9, str[ind].indexOf(" Experience")));
            	experienceOne.add(str[ind].substring(str[ind].indexOf("Experience:") + 11, str[ind].indexOf(" eMail")));
            }
        	
        }
        if (count == 2) {
        	int firstPos  = str[ind].indexOf("Position:");
        	int nextPos = str[ind].indexOf("Position:", firstPos+1);

        	int firstExp  = str[ind].indexOf("Experience:");
        	int nextExp = str[ind].indexOf("Experience:", firstExp+1);


        	positionOne.add(str[ind].substring(str[ind].indexOf("Position:") + 9, str[ind].indexOf(" Experience")));
        	experienceOne.add(str[ind].substring(str[ind].indexOf("Experience:") + 11, str[ind].lastIndexOf(" Position")));
        	
        	positionTwo.add(str[ind].substring(nextPos + 9, str[ind].lastIndexOf(" Experience")));
        	//SORT THIS OUT ITS SAYING THE INDEXES ARE MESSED UP **************************************************
        	experienceTwo.add(str[ind].substring(nextExp+ 11, str[ind].indexOf(" eMail")));
        	
        }

        
        
        StringBuffer sb = new StringBuffer();
        ArrayList<String> clean = new ArrayList<String>();
        
        if (surname.size() == 0) {}
        else {
        	clean.add(surname.get(0));
        }
        
        if (qualification.size() == 0) {}
        else {
        	clean.add(qualification.get(0));
        }
    
        if (count == 1) {
        	clean.add(positionOne.get(0));
        	clean.add(experienceOne.get(0));
        }

        
        if (count == 2) {
        	clean.add(positionOne.get(0));
        	clean.add(experienceOne.get(0));
        	clean.add(positionTwo.get(0));
        	clean.add(experienceTwo.get(0));
        }

        
        if (eMail.size() == 0) {}
        else {
        	clean.add(eMail.get(0));
        }
        
        for (String s : clean) {
        	sb.append(s);
        	sb.append(",");
        }
        String cleanString = sb.toString();
        //System.out.println(cleanString);
        
        return cleanString;
	}

}
