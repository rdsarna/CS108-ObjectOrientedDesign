package assign3;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import assign3.Entry.Match;
import assign3.Entry.Population;

public class TestDataClass {
	DataBaseConnection dc;
	Entry ePartial, eOnlyPopulation, eNoPopulation, eAll;
	Entry e1, e2;

	@Before
	public void setUp() throws Exception {
		dc = new DataBaseConnection();
		eOnlyPopulation = new Entry("", "", "20000000", Population.LARGER, Match.EXACT);
		ePartial = new Entry("San", "", "", Population.SMALLER, Match.PARTIAL);
		eAll = new Entry("", "", "", Population.LARGER, Match.PARTIAL);
		eNoPopulation = new Entry("", "Europe", "", Population.LARGER, Match.EXACT);
		
		e1 = new Entry("Mumbai", "Asia", "200000");
		e2 = new Entry("", "Asia", "20000");
	}

//	@Test
//	public void testRetrieveData() {
//		List<String> colNames = dc.retrieveColumnNames();
//		System.out.println(colNames.toString());
//		List<List<String>> rows = new ArrayList<>();
//		dc.retrieveData(eNoPopulation, rows);
//		for (List<String> row : rows)
//			System.out.println(row.toString());
//		
//		System.out.println();
//		dc.retrieveData(eOnlyPopulation, rows);
//		for (List<String> row : rows)
//			System.out.println(row.toString());
//		
//		System.out.println();
//		dc.retrieveData(ePartial, rows);
//		for (List<String> row : rows)
//			System.out.println(row.toString());
//		
//		System.out.println();
//		dc.retrieveData(eAll, rows);
//		for (List<String> row : rows)
//			System.out.println(row.toString());
//	}

	@Test
	public void testInsertData() {
		List<List<String>> rows = new ArrayList<>();
		dc.insertData(e1, rows);
		for (List<String> row : rows)
			System.out.println(row.toString());
		
		System.out.println();
		dc.retrieveData(eAll, rows);
		for (List<String> row : rows)
			System.out.println(row.toString());
		
		dc.insertData(e2, rows);
		for (List<String> row : rows)
			System.out.println(row.toString());
	}

}
