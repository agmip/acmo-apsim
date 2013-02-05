package org.agmip.translators.acmo.apsim.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class MetaReader {
	List<String[]> myEntries;
	List<String> experiments = new ArrayList<String>();
	
	
	public MetaReader(String file) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(file),',','"',3);
		myEntries = reader.readAll();
		for(String[] entry:myEntries){
			experiments.add(entry[1]);
		}
	}
	
	
	public String[] getLine(String exp){
		int line = experiments.indexOf(exp);
		return myEntries.get(line);
	}
	
	public List<String> listExperiments(){
		return experiments;
	}
	
	
	
	public void printMe(){
		for (String[] line:myEntries){
			for(String element:line){
				System.out.print(element +", ");
			}
			System.out.println();
		}
	}
	

}
