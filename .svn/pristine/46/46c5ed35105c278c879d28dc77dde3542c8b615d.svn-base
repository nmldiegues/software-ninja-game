package com.codebits.softwareninja.domain.type;

public class Variable {

	private Identifier name;
	private LanguageType type;

	public Variable(Identifier name, LanguageType type) {
		this.setName(name);
		this.setType(type);
	}

	public Identifier getName() {
		return name;
	}

	public void setName(Identifier name) {
		this.name = name;
	}

	public LanguageType getType() {
		return type;
	}

	public void setType(LanguageType type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Variable) {
			return this.getName().getId().equals(((Variable) other).getName().getId());
		}
		return false;
	}

	public boolean equals(Variable other) {
		return this.getName().getId().equals(other.getName().getId());
	}

	@Override
	public int hashCode() {
		return this.getName().getId().hashCode();
	}

}
