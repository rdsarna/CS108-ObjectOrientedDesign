package assign3;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/* Class that represents a single spot
 * on the grid of the Sudoku game.
 * Each Spot object knows its place on the Sudoku grid as
 * it stores the row and column number as fields.
 */
public class Spot implements Comparable<Spot> {
    private final int row, col;
    private int value;
    private int part;

    /* Stores all possible values for a Spot that is empty
     * according to the rules of the game */
    private Set<Integer> possibleValues;

    Spot(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.part = getPart(row, col);

        possibleValues = new HashSet<>();
    }

    Spot(Spot s) {
        this(s.row, s.col, s.value);
        this.part = s.part;
        possibleValues = new HashSet<>(s.possibleValues);
    }


    /* Returns true iff this Spot is not filled */
    boolean isEmpty() {
        return value == 0;
    }

    /* Returns a HashSet of all legal values that can be
     * filled in this Spot.
     */
    Optional<Set<Integer>> getPossibleValues(List<HashSet<Integer>> valInRows, List<HashSet<Integer>> valInCols,
											 List<HashSet<Integer>> valInParts) {
		if (value != 0) {
			return Optional.empty();
		}

        /* temporarily assign all 9 numbers */
		for (int i = 1; i <= Sudoku.SIZE; i++) {
			possibleValues.add(i);
		}

        /* Remove all the values that cannot be placed at this Spot */
		possibleValues.removeAll(valInRows.get(row));
		possibleValues.removeAll(valInCols.get(col));
		possibleValues.removeAll(valInParts.get(part));
		return Optional.of(possibleValues);
	}


    @Override
    public int compareTo(Spot that) {
        return this.possibleValues.size() - that.possibleValues.size();
    }

    /* Two Spots are equal if their row and column number are equal */
    @Override
    public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    /* Helper method that returns the Part in which the
     * coordinates x and y belong on the grid.
     */
    private int getPart(int x, int y) {
        int partitionSize = (int) Math.sqrt(Sudoku.SIZE);
        int xGroup = x / partitionSize;
        int yGroup = y / partitionSize;
        return xGroup * partitionSize + yGroup;
    }

    /* Returns the row number this Spot belongs to */
    int getRow() {
        return row;
    }
    /* Returns the column number this Spot belongs to */
    int getCol() {
        return col;
    }
    /* Sets the value for this Spot on the Solution Grid (solutionGrid) */
    void setValue(int val) {
        value = val;
    }
    /* Returns the part of the grid where this Spot belongs */
    int getPartForSpot() {
        return part;
    }

    void setEmpty() {
        value = 0;
    }
    /* Returns the value of this Spot */
    int getValue() {
        return value;
    }
}
