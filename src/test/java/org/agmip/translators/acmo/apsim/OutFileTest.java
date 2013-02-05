package org.agmip.translators.acmo.apsim;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.agmip.translators.acmo.apsim.core.MetaReader;
import org.agmip.translators.acmo.apsim.core.OutFileReader;
import org.junit.Before;
import org.junit.Test;

public class OutFileTest {

	private OutFileReader outFile;

	@Before
	public void setUp() throws Exception {
		outFile = new OutFileReader("src/test/resources/MACH0001_1__1 ACMO.out");

	}

	@Test
	public void test() {
		assertEquals("7.4",outFile.getVersion());
		assertEquals("MACH0001_1__1",outFile.getTitle());
        System.out.println(Arrays.asList(outFile.getData()));		
		
	}

}
