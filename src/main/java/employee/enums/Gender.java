package employee.enums;

public enum Gender {
	/** Male. */
	M("M"),
	/** Female. */
	F("F");

	private final String name;

	private Gender(String name) {
		this.name = name;
	}


	public String getName() {
		return this.name;
	}

	public String setName() {
		return this.name;
	}
}