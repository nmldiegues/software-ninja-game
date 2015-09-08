package com.codebits.softwareninja.domain.type;

public class Identifier {

	private final String id;
	
	public Identifier(String id) {
		this.id = id;
	}

	public boolean checkValidName() {
		for (CharSequence possibleType : LanguageType.getTypesStr()) {
			if (possibleType.equals(this.id)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean equals(String otherId) {
		return this.id == otherId;
	}
	
	public String toString() {
		return this.id;
	}
	
	public String getId() {
		return this.id;
	}
	
}
