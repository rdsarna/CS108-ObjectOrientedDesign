package assign3;

/**
 * Represents the abstraction for entries that are to be
 * added to the database or searched/queried from the database.
 * 
 * Provides methods for generating SQL statements based on the state
 * of this Entry object.
 */
public class Entry {
	
	/** 
	 * Represents the JCombobox data which can be larger than, smaller than
	 * or equal to the population given in the field.
	 */
	public enum Population {
		LARGER,
		SMALLER,
		EQUAL
	}
	
	/**
	 * Represents the JCombobox data from the GUI, which can be exact match
	 * or partial match.
	 */
	public enum Match {
		EXACT,
		PARTIAL
	}

	private String metropolis;
	private String continent;
	private String population;
	private Population populationCriteria;
	private Match matchCriteria;
//	private static final String TABLE_NAME = "metropolises";
	
	
	/**
	 * Construct an Entry/criteria to be searched/queried from
	 * the database. Any or all of the String parameters can be empty or 
	 * null if they are not to be included in the query condition.
	 * @param metropolis name of the metropolis to be included in the query,
	 * can be left empty
	 * @param continent name of the continent to be included in the query,
	 * can be left empty
	 * @param population population to be included in the query, can be left empty
	 * @param populationCriteria larger than, smaller than or equal to the given
	 * population parameter. Ignored if population field is empty
	 * @param matchFlag should be true if the option is ExactMatch otherwise false
	 */
	public Entry(String metropolis, String continent,
			String population, Population populationCriteria, Match matchCriteria) {
		this.metropolis = metropolis;
		this.continent = continent;
		this.population = population;
		this.populationCriteria = populationCriteria;
		this.matchCriteria = matchCriteria;
	}
	
	
	/**
	 * An entry that is constructed when it has to be added to the database.
	 * All the parameter cannot be null.  
	 * @param metropolis
	 * @param continent
	 * @param population
	 */
	public Entry(String metropolis, String continent, String population) {
		this(metropolis, continent, population, Population.EQUAL, Match.EXACT);
	}
	
	
	/**
	 * Generates and returns an SQL query statement as a String. Takes into
	 * account the state of the Entry object. All the properties of the object
	 * that have been supplied while constructing the Entry are used to generate the 
	 * statement.<br>
	 * <br>
	 * For example, if the Entry was created with:<br>
	 * Entry e = new Entry("", "", "200000",  Population.LARGER, Match.EXACT);<br>
	 * then the call<br>
	 * e.getQueryStatement(metropolises)<br>
	 * will return<br>
	 * "SELECT * FROM metropolises WHERE population > 200000;"<br>
	 * 
	 * @param tableName Name of the table from which data is to be queried
	 * @return SQL query statement according to the state of this object
	 */
	public String getQueryStatement(String tableName) {
		StringBuilder result = new StringBuilder("SELECT * FROM " + tableName);
		if (areAllFieldsEmpty(this)) return result.toString();
		result.append(" WHERE");	

		generateQuery(this, result);
		result.append(";");
		return result.toString();
	}
	
	/**
	 * Generates and returns an SQL insertion statement as a String. Takes into
	 * account the state of the Entry object. All the properties of the object
	 * that have been supplied while constructing the Entry are used to generate the 
	 * statement. <br>
	 * <br>
	 * If any of the fields are empty then null is returned.<br>
	 * <br>
	 * For example, if the entry was created with:<br>
	 * Entry e = new Entry("Mumbai", "Asia", "200000");<br>
	 * then the call<br>
	 * e.getInsertStatement(metropolises)<br>
	 * will return<br>
	 * "INSERT INTO metropolises VALUES("Mumbai", "Asia", "200000");"<br>
	 * <br>
	 * @param tableName Name of the table into which data is to be inserted
	 * @return SQL insert statement according to the state of this object OR
	 * null if any of the fields are empty
	 */
	public String getInsertStatement(String tableName) {
		if (anyFieldEmpty(this)) return null;
		String result = "INSERT INTO " + tableName + " VALUES("
				+ "\"" + this.metropolis + "\","
				+ " \"" + this.continent + "\"," 
				+ " " + this.population + ");";
		return result;
	}
	
	
	/* Returns true iff any of the fields of the entry is empty */
	private boolean anyFieldEmpty(Entry entry) {
		return entry.metropolis.isEmpty() || entry.continent.isEmpty()
				|| entry.population.isEmpty();
	}

	/* Returns iff all fields of the given entry are empty Strings */
	private boolean areAllFieldsEmpty(Entry entry) {
		return entry.metropolis.isEmpty() && entry.continent.isEmpty() 
				&& entry.population.isEmpty();
	}
	
	
	/* Generates a query SQL statement and appends it to the given result
	 * based on the properties of the given entry */
	private void generateQuery(Entry entry, StringBuilder result) {
		String populationOperator = getPopulationOperator(entry.populationCriteria);

		if (!entry.metropolis.isEmpty()) {
			result.append(entry.matchCriteria == Match.EXACT ? 
					" metropolis = \"" + entry.metropolis + "\"" : 
						" metropolis LIKE \"%" + entry.metropolis + "%\"");
			if (!entry.continent.isEmpty() || !entry.population.isEmpty())
				result.append(" AND");
		}
		if (!entry.continent.isEmpty()) {
			result.append(entry.matchCriteria == Match.EXACT ? 
					" continent = \"" + entry.continent + "\"" :
						" continent LIKE \"%" + entry.continent + "%\"");
			if (!entry.population.isEmpty())
				result.append(" AND");
		}
		result.append((entry.population.isEmpty()) ? 
				"" : " population " + populationOperator + " " + entry.population);
	}
	
	/**
	 * Returns true iff the given object is an Entry with all properties
	 * equal to this Entry object's properties.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (!(o instanceof Entry)) return false;
		Entry that = (Entry) o;
		
		return this.metropolis.equals(that.metropolis) &&
				this.continent.equals(that.continent) &&
				this.population.equals(that.population) &&
				this.populationCriteria.equals(that.populationCriteria) &&
				this.matchCriteria.equals(that.matchCriteria);
	}

	
	/* Returns the operator equivalent to the populationCriteria given */
	private String getPopulationOperator(Population populationCriteria) {
		switch (populationCriteria) {
		case LARGER: return ">";
		case SMALLER: return "<";
		default: return "=";
		}
	}

}
