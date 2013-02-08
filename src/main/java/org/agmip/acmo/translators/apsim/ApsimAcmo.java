package org.agmip.acmo.translators.apsim;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.agmip.acmo.translator.AcmoTranslator;
import org.agmip.acmo.translators.apsim.core.MetaReader;
import org.agmip.acmo.translators.apsim.core.OutFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author <a href="mailto:ioannis@athanasiadis.info">Ioannis N.
 *         Athanasiadis</a>
 * 
 */
public class ApsimAcmo implements AcmoTranslator {
	private static final Logger log = LoggerFactory.getLogger(ApsimAcmo.class);
	public boolean execute(String sourceFolder, String destFolder) {
		try {
			MetaReader meta = new MetaReader(sourceFolder + "/ACMO_meta.dat");

			CSVWriter writer = new CSVWriter(new FileWriter(destFolder
					+ "/ACMO_APSIM.csv"), ',');
			writer.writeAll(meta.getHeader());

			for (String exp : meta.getRuns()) {
				try {
					OutFileReader out = new OutFileReader(destFolder + "/" + exp + " ACMO.out");

					List<String> data = new ArrayList<String>();
					data.addAll(Arrays.asList(meta.getData(exp)));

					while (data.size() < 39) {
						data.add("");
					}

					data.set(37, "APSIM");
					data.set(38, out.getVersion());

					data.addAll(Arrays.asList(out.getData()));

					writer.writeNext(data.toArray(new String[data.size()]));
					log.debug("Processed output for {}",exp);
				} catch (Exception e) {
					// e.printStackTrace();
					// cant read this result file - skip
					log.error("No output found for {}", exp);
//					System.out.println("Error: No output found for " + exp);
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
