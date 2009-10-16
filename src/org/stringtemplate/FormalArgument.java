package org.stringtemplate;

/** Represents the name of a formal argument
 *  defined in a template:
 *
 *  group test;
 *  test(a,b) : "$a$ $b$"
 *  t() : "blort"
 *
 *  Each template has a set of these formal arguments or uses
 *  a placeholder object: UNKNOWN (indicating that no arguments
 *  were specified such as when a template is loaded from a file.st).
 *
 *  Note: originally, I tracked cardinality as well as the name of an
 *  attribute.  I'm leaving the code here as I suspect something may come
 *  of it later.  Currently, though, cardinality is not used.
 */
public class FormalArgument {
    // the following represent bit positions emulating a cardinality bitset.
    public static final int OPTIONAL = 1;     // a?
    public static final int REQUIRED = 2;     // a
    public static final int ZERO_OR_MORE = 4; // a*
    public static final int ONE_OR_MORE = 8;  // a+

    public static final String[] suffixes = {
        null,
        "?",
        "",
        null,
        "*",
        null,
        null,
        null,
        "+"
    };

    public String name;
    //protected int cardinality = REQUIRED;

	/** If they specified name="value", store the template here */
	public String defaultValue;
    public CompiledST compiledDefaultValue;

    public FormalArgument(String name) {
		this.name = name;
	}

	public FormalArgument(String name, String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

    public static String getCardinalityName(int cardinality) {
        switch (cardinality) {
            case OPTIONAL : return "optional";
            case REQUIRED : return "exactly one";
            case ZERO_OR_MORE : return "zero-or-more";
            case ONE_OR_MORE : return "one-or-more";
            default : return "unknown";
        }
    }

    public int hashCode() {
        return name.hashCode() + defaultValue.hashCode();
    }

    public boolean equals(Object o) {
		if ( o==null || !(o instanceof FormalArgument) ) {
			return false;
		}
		FormalArgument other = (FormalArgument)o;
		if ( !this.name.equals(other.name) ) {
			return false;
		}
		// only check if there is a default value; that's all
		if ( (this.defaultValue !=null && other.defaultValue ==null) ||
			 (this.defaultValue ==null && other.defaultValue !=null) ) {
			return false;
		}
		return true;
	}

    public String toString() {
		if ( defaultValue !=null ) {
			return name+"="+ defaultValue;
		}
        return name;
    }
}