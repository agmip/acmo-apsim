package org.agmip.acmo.translators.apsim.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class OutFileReader {
	private String version;
	private String title;
	private String[] data;

	public OutFileReader(String file) throws IOException{
		CSVReader reader = new CSVReader(new FileReader(file),',');
		List<String[]> myEntries = reader.readAll();
		version = ((myEntries.get(0))[0]).split("=")[1].trim();
		title = ((myEntries.get(2))[0]).split("=")[1];
		title = title.substring(0, title.lastIndexOf(" ")).trim();
		data = myEntries.get(5);
		reader.close();
		
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}

	
}
