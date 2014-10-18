package assign3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import assign3.Entry.Match;
import assign3.Entry.Population;

public class TestEntry {
	Entry eExact, ePartial, eOnlyPopulation, eNoPopulation;
	Entry e1, e2;

	@Before
	public void setUp() throws Exception {
		eExact = new Entry("Mumbai", "", "2000000", Population.LARGER, Match.EXACT);
		ePartial = new Entry("", "si", "2000000", Population.SMALLER, Match.PARTIAL);
		eOnlyPopulation = new Entry("", "", "2000000", Population.LARGER, Match.PARTIAL);
		eNoPopulation = new Entry("Mumbai", "Asia", "", Population.LARGER, Match.PARTIAL);
		
		e1 = new Entry("Mumbai", "Asia", "200000");
		e2 = new Entry("", "Asia", "20000");
	}

	@Test
	public void testGetQueryStatement() {
		assertEquals("SELECT * FROM metropolises WHERE metropolis = \"Mumbai\" AND population > 2000000;",
				eExact.getQueryStatement("metropolises"));
		assertEquals("SELECT * FROM metropolises WHERE continent LIKE \"%si%\" AND population < 2000000;",
				ePartial.getQueryStatement("metropolises"));
		assertEquals("SELECT * FROM metropolises WHERE population > 2000000;",
				eOnlyPopulation.getQueryStatement("metropolises"));
		assertEquals("SELECT * FROM metropolises WHERE metropolis LIKE \"%Mumbai%\" AND continent LIKE \"%Asia%\";",
				eNoPopulation.getQueryStatement("metropolises"));
	}

	@Test
	public void testGetInsertStatement() {
		assertEquals("INSERT INTO metropolises VALUES(\"Mumbai\", \"Asia\", 200000);",
				e1.getInsertStatement("metropolises"));
		assertEquals(null, e2.getInsertStatement("metropolises"));
	}

}
