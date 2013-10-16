package org.agmip.acmo.translators.apsim.core;

import static org.junit.Assert.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.agmip.acmo.translators.apsim.core.MetaReader;
import org.junit.Before;
import org.junit.Test;

public class ReaderTest {
	MetaReader meta ;
	@Before
	public void setUp() throws Exception {
		URL resource = this.getClass().getResource("/ACMO_meta.dat");
		meta = new MetaReader(resource.getFile());
	}

	
	@Test
	public void testListExperiments() {
		List<String> list = meta.getRuns();
		String [] expected = {"UFGA8201_1-RAINFED LOW NITROGEN","UFGA8201_2-RAINFED HIGH NITROGEN","UFGA8201_3-IRRIGATED LOW NITROGEN","UFGA8201_4-IRRIGATED HIGH NITROGEN","MACH0001_1__1","UFGA8201_6-VEG STRESS HIGH NITROGEN"};
		assertEquals(Arrays.asList(expected), list);
	}
	
	@Test
	public void testGetLine() {
		String[] result = meta.getData("UFGA8201_4-IRRIGATED HIGH NITROGEN");
		assertEquals("IRRIGATED HIGH NITROGEN", result[7]);
	}
	
	@Test
	public void testGetLineLength() {
		String[] result = meta.getData("Invalid Index");
		assertEquals(0, result.length);
	}
}