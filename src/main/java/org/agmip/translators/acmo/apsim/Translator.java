package org.agmip.translators.acmo.apsim;

import java.util.LinkedHashMap;
import java.util.Map;

import org.agmip.core.types.TranslatorInput;

/**
 * @author <a href="mailto:ioannis@athanasiadis.info">Ioannis N. Athanasiadis</a>
 *
 */
public class Translator implements TranslatorInput {
    public Map readFile(String fileName) {
        Map map = new LinkedHashMap();
        
        String filePath = fileName;
        return map;
    }
}
