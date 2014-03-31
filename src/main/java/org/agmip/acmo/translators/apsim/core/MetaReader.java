package org.agmip.acmo.translators.apsim.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class MetaReader {
	private static final Logger log = LoggerFactory.getLogger(MetaReader.class);
        private int exNameIdx = -1;
        private int trtNameIdx = -1;
	List<String[]> data = new ArrayList<String[]>();
	List<String[]> header=new ArrayList<String[]>();
	List<String>   runs = new ArrayList<String>();
	
	
	public MetaReader(String file) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(file),',','"');
		header.add(reader.readNext());
		header.add(reader.readNext());
                String[] titles = reader.readNext();
		header.add(titles);
                setIndex(titles);

		data = reader.readAll();
		for(String[] entry:data){
			if (entry[trtNameIdx].trim().equals("")) {
                            runs.add(entry[exNameIdx]);
                        } else {
                            runs.add(entry[exNameIdx]+"-"+entry[trtNameIdx]);
                        }
		}
		reader.close();
	}
        
        private void setIndex(String[] titles) {
            for (int i = 0; i < titles.length; i++) {
                if (exNameIdx >= 0 && trtNameIdx >= 0) {
                    return;
                } else if (titles[i].toUpperCase().equals("EXNAME")) {
                    exNameIdx = i;
                } else if (titles[i].toUpperCase().equals("TRT_NAME")) {
                    trtNameIdx = i;
                }
            }
            if (exNameIdx < 0) {
                exNameIdx = 2;  // For template version 4.0.1
            }
            if (trtNameIdx < 0) {
                trtNameIdx = 7;  // For template version 4.0.1
            }
        }
	
	public List<String[]> getHeader(){
		return header;
	}
	
	
	public String[] getData(String exp){
		// There needs to be a blank array or something returned here - CV
		int line = runs.indexOf(exp);
		if (line == -1) {
			log.error("Entry {} not found", exp);
			String [] blank = {};
			return blank;
		} else {
			return data.get(line);
		}
	}
	
	public List<String> getRuns(){
		return runs;
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
