package org.agmip.translators.acmo.apsim.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class MetaReader {
	List<String[]> data = new ArrayList<String[]>();
	List<String[]> header=new ArrayList<String[]>();
	List<String> experiments = new ArrayList<String>();
	
	
	public MetaReader(String file) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(file),',','"');
		header.add(reader.readNext());
		header.add(reader.readNext());
		header.add(reader.readNext());

		data = reader.readAll();
		for(String[] entry:data){
			experiments.add(entry[1]);
		}
	}
	
	public List<String[]> getHeader(){
		return header;
	}
	
	
	public String[] getData(String exp){
		int line = experiments.indexOf(exp);
		return data.get(line);
	}
	
	public List<String> getExperiments(){
		return experiments;
	}
	
	
	
	public void printMe(){
		for (String[] line:data){
			for(String element:line){
				System.out.print(element +", ");
			}
			System.out.println();
		}
	}
	

}
