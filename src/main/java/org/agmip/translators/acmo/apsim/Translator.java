package org.agmip.translators.acmo.apsim;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.agmip.core.types.TranslatorInput;
import org.agmip.translators.acmo.apsim.core.MetaReader;
import org.agmip.translators.acmo.apsim.core.OutFileReader;

import com.google.common.collect.Lists;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author <a href="mailto:ioannis@athanasiadis.info">Ioannis N.
 *         Athanasiadis</a>
 * 
 */
public class Translator {
	public static boolean execute(String folder) {
		try {
			MetaReader meta = new MetaReader(folder + "/ACMO_meta.dat");

			CSVWriter writer = new CSVWriter(new FileWriter(folder
					+ "/apsim-results.csv"), ',');
			writer.writeAll(meta.getHeader());

			for (String exp : meta.getRuns()) {
				try {
					OutFileReader out = new OutFileReader(folder + "/" + exp + " ACMO.out");

					List<String> data = new ArrayList<String>();
					data.addAll(Arrays.asList(meta.getData(exp)));

					while (data.size() < 39) {
						data.add("");
					}

					data.set(37, "APSIM");
					data.set(38, out.getVersion());

					data.addAll(Arrays.asList(out.getData()));

					writer.writeNext(data.toArray(new String[data.size()]));
					System.out.println("Processed output for " + exp);
				} catch (Exception e) {
					// e.printStackTrace();
					// cant read this result file - skip
					System.out.println("Error: No output found for " + exp);
				}

			}

			writer.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
}
