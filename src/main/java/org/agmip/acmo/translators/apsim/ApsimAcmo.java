package org.agmip.acmo.translators.apsim;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.agmip.acmo.translators.AcmoTranslator;
import org.agmip.acmo.translators.apsim.core.MetaReader;
import org.agmip.acmo.translators.apsim.core.OutFileReader;
//import static org.agmip.common.Functions.getStackTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;
import org.agmip.acmo.translators.AcmoVersionRecordable;
import org.agmip.acmo.util.AcmoUtil;
import org.agmip.common.Functions;

/**
 * @author <a href="mailto:ioannis@athanasiadis.info">Ioannis N.
 *         Athanasiadis</a>
 * 
 */
public class ApsimAcmo implements AcmoTranslator, AcmoVersionRecordable {
	private static final Logger log = LoggerFactory.getLogger(ApsimAcmo.class);
        private String acmoVer = "";
	public File execute(String sourceFolder, String destFolder) {
		try {
			MetaReader meta = new MetaReader(sourceFolder + "/ACMO_meta.dat");
                        meta.addAcmoVersion(acmoVer);

                        File output = AcmoUtil.createCsvFile(destFolder, "APSIM", sourceFolder + "/ACMO_meta.dat");
			CSVWriter writer = new CSVWriter(new FileWriter(output), ',');
			writer.writeAll(meta.getHeader());

			for (String exp : meta.getRuns()) {
				try {
					OutFileReader out = new OutFileReader(destFolder + "/" + reiviseName(exp) + " ACMO.out");

					List<String> data = new ArrayList<String>();
					data.addAll(Arrays.asList(meta.getData(exp)));

                                        // Since QuadUI will guarantee generating the meta data until Model the column, no check is required any more
//					while (data.size() < 39) {
//						data.add("");
//					}
//
//					data.set(37, "APSIM");
//					data.set(38, out.getVersion());
                                        data.add(out.getVersion());

					data.addAll(Arrays.asList(out.getData()));

					writer.writeNext(data.toArray(new String[data.size()]));
					log.debug("Processed output for {}",exp);
				} catch (Exception e) {
					// e.printStackTrace();
					// cant read this result file - skip
					log.error("No output found for {}", exp);
//					System.out.println("Error: No output found for " + exp);
                                        writer.writeNext(meta.getData(exp));
				}

			}

			writer.close();
			return output;
		} catch (Exception e) {
			log.error(Functions.getStackTrace(e));
			return null;
		}

	}
        
    private String reiviseName(String exp) {
        exp = exp.replaceAll("[/\\\\*|?:<>\"]", "_");
        return exp;
    }

    public void recordAcmoVersion(String acmoVer) {
        this.acmoVer = acmoVer;
    }
}
