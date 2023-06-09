package net.ivandev.sovereign.empiremech;

public enum EmpireIdeology {

	ANARCHIST(0, "Anarchist", "Free Territory of "), // Anarchism
	COMMUNIST(1, "Communist", "Commune of "), // Communism
	DEMOCRATIC(2, "Democratic", "Republic of "), // Democracy
	MONARCHIST(3, "Monarchist", "Kingdom of "), // Monarchism
	FASCIST(4, "Fascist", "Unitary State of "); // Fascism

	public static final EmpireIdeology[] SPECTRUM = { ANARCHIST, COMMUNIST, DEMOCRATIC, MONARCHIST, FASCIST };

	public int id;
	public String name;
	public String prefix;

	private EmpireIdeology(int id, String name, String prefix) {
		this.id = id;
		this.name = name;
		this.prefix = prefix;
	}

	public Object[] deconstruct() {
		Object[] contents = { name, prefix };
		return contents;
	}

	public EmpireIdeology driftLeft() {
		try {
			return SPECTRUM[id - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public EmpireIdeology driftRight() {
		try {
			return SPECTRUM[id + 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
}
