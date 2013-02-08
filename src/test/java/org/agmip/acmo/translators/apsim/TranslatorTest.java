package org.agmip.acmo.translators.apsim;

import org.agmip.acmo.translator.AcmoTranslator;
import org.agmip.acmo.translators.apsim.ApsimAcmo;
import org.junit.Before;
import org.junit.Test;

public class TranslatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExecute() {
	    AcmoTranslator acmo = new ApsimAcmo();
		acmo.execute("src/test/resources/", "src/test/resources/");
	}

}