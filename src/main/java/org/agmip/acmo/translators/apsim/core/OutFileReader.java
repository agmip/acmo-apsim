package org.agmip.acmo.translators.apsim.core;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class OutFileReader {

    private String version;
    private String title;
    private LinkedHashMap<String, String> data = new LinkedHashMap();

    public enum ApsimOutputTitle {

        yield("HWAH_S"),
        biomass("CWAH_S"),
        AnthesisDate("ADAT_S"),
        MaturityDate("MDAT_S"),
        HarvestDate("HADAT_S"),
        LAIMax("LAIX_S"),
        InCropRain("PRCP_S"),
        SumET("ETCP_S"),
        SumNO3Uptake("NUCM_S"),
        SumLeachNO3("NLCM_S"),
        SumEP("EPCP_S"),
        SumES("ESCP_S"),
        AverageRadn("SRAA_S"),
        AverageMaxT("TMAXA_S"),
        AverageMinT("TMINA_S"),
        AverageMeanT("TAVGA_S"),
        AverageCo2("CO2D_S"),
        countIrrigation("IR#C_S"),
        totalIrrigation("IR_TOT_S"),
        count("IR#C_S"),
        tot_irrig("IR_TOT_S");

        private final String acmoTitle;

        private ApsimOutputTitle(String acmoTitle) {
            this.acmoTitle = acmoTitle;
        }

        public String getAcmoTitle() {
            return this.acmoTitle;
        }
    }

    public OutFileReader(String file) throws IOException {
        CSVReader reader = new CSVReader(new FileReader(file), ',');
        List<String[]> myEntries = reader.readAll();
        version = ((myEntries.get(0))[0]).split("=")[1].trim();
        title = ((myEntries.get(2))[0]).split("=")[1];
        title = title.substring(0, title.lastIndexOf(" ")).trim();

        String[] headers = myEntries.get(3);
        String[] values = myEntries.get(5);
        if (values.length >= headers.length) {
            int i = 0;
            for (; i < headers.length; i++) {
                data.put(getAcmoHeader(headers[i]), values[i]);
            }
            for (; i < values.length; i++) {
                data.put(i + "", values[i]);
            }
        } else {
            int i = 0;
            for (; i < values.length; i++) {
                data.put(getAcmoHeader(headers[i]), values[i]);
            }
            for (; i < headers.length; i++) {
                data.put(getAcmoHeader(headers[i]), "");
            }
        }
        removeMark(data);
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

    public ArrayList<String> getData(String... headers) {
        ArrayList ret = new ArrayList();
        LinkedHashSet<String> vars = new LinkedHashSet(data.keySet());
        for (String header : headers) {
            if (vars.contains(header)) {
                ret.add(data.get(header));
                vars.remove(header);
            }
        }
        for (String var : vars) {
            ret.add(data.get(var));
        }
        return ret;
    }

    public void setData(HashMap<String, String> data) {
        removeMark(data);
        this.data = new LinkedHashMap(data);
    }

    private void removeMark(HashMap<String, String> data) {
        for (String key : data.keySet()) {
            if (data.get(key).trim().equals("?")) {
                data.put(key, "");
            }
        }
    }

    private String getAcmoHeader(String header) {
        try {
            return ApsimOutputTitle.valueOf(header).getAcmoTitle();
        } catch (IllegalArgumentException ex) {
            return header;
        }
    }

}
