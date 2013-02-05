package org.agmip.translators.acmo.apsim.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReaderTest {
	MetaReader meta ;
	@Before
	public void setUp() throws Exception {
		meta = new MetaReader("src/test/resources/ACMO_meta.dat");
//		r.printMe();
	}

	@Test
	public void test() {
		meta.printMe();
		assertTrue(true);
	}

	
	@Test
	public void testListExperiments() {
		List<String> list = meta.listExperiments();
		String [] expected = {"UFGA8201_1","UFGA8201_2","UFGA8201_3","UFGA8201_4","UFGA8201_5","UFGA8201_6"};
		assertEquals(list, Arrays.asList(expected));
	}
	
	@Test
	public void testGetLine() {
		String[] result = meta.getLine("UFGA8201_5");
		assertEquals("VEG STRESS LOW NITROGEN", result[6]);
	}
	
	
	

}
