package org.agmip.translators.acmo.apsim;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TranslatorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExecute() {
		Translator.execute("src/test/resources/");
	}

}
