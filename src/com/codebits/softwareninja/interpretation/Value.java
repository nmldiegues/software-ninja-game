package com.codebits.softwareninja.interpretation;

import java.util.ArrayList;

public abstract class Value {
	
	public Integer getInt() {
		throw new RuntimeException("Wrong cast: tried to get an Integer out of " + this.getClass().getSimpleName());
	}
	
	public Boolean getBool() {
		throw new RuntimeException("Wrong cast: tried to get a Boolean out of " + this.getClass().getSimpleName());
	}
	
	public ArrayList<Integer> getIntArray() {
		throw new RuntimeException("Wrong cast: tried to get an Integer Array out of " + this.getClass().getSimpleName());
	}
	
	public ArrayList<Boolean> getBoolArray() {
		throw new RuntimeException("Wrong cast: tried to get a Bool Array out of " + this.getClass().getSimpleName());
	}
}
