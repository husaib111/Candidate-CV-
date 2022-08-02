package com.bham.pij.assignments.candidates;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CandidatesToInterview {
	public void findCandidates() {
		String[] keywordsDegree = {"Degree in Computer Science", "Masters in Computer Science"};
		String[] keywordsExperience = {"Data Analyst", "Programmer", "Computer programmer", "Operator"};

		
		try {
			Path file = Paths.get("cleancv.txt");
			InputStream input = null;
			input = Files.newInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			String line = reader.readLine();
			
			String content = new String(Files.readAllBytes(file)); //places all the contents of the file into a string
			//System.out.println(content);
			String str1=content.replaceAll("[\r\n]+", " "); //removes all new lines and places all onto one line
	        //System.out.println(str1);
	        String tempStr1 = content;
	        //to count the number of CV's
	        int numOfCVs = 0;
	        int index = content.indexOf("Science");
	        while (index != -1) {
	        	numOfCVs++;
	        	tempStr1 = tempStr1.substring(index + 1);
	        	index = tempStr1.indexOf("Science");
	        }
	        //System.out.println(content.substring(content.indexOf(".com")+5));
	        String[] jah = str1.split(str1.substring(str1.indexOf(".com")+5));
	        String[] jahh = str1.split((".com"));
	        ArrayList<String> goodCandidates = new ArrayList<String>();
	        for(String st : jahh) {
	        	if ((st.contains(keywordsDegree[0]) || st.contains(keywordsDegree[1]) && (st.contains(keywordsExperience[0])) || st.contains(keywordsExperience[1]) ||st.contains(keywordsExperience[2]) || st.contains(keywordsExperience[3]))){
	        		//System.out.println(st);
	        		goodCandidates.add(st);
	        	}

	        }
	        
	        
	        //creating a new arraylist to replace all occurances of ", " located at the start of each line (due to the split at .com)
	        ArrayList<String> newGood = new ArrayList<String>();
	        for (int g = 0; g < goodCandidates.size(); g++) {
	        	newGood.add(goodCandidates.get(g).replace(", ", "")+ ".com");
	        	
	        }
	        //creating a string in which each candidate is seperated onto a new line
	        StringBuffer sb = new StringBuffer();

	        for (String s : newGood) {
	        	sb.append(s);
	        	sb.append("\n");
	        }
	        String cleanString = sb.toString();

	        cleanString = cleanString.replace(",", " ");
            File filee = new File("to-interview.txt");
        	BufferedWriter writer = new BufferedWriter(new FileWriter(filee));
            if (!filee.exists()) {
            	filee.createNewFile();
            }
            writer.write(cleanString);
            writer.close();
	        //System.out.println(cleanString);
		}catch(Exception e) {e.printStackTrace();}
	}
	public void candidatesWithExperience() {
		
	}

}
