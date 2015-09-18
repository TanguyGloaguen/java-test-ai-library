package com.gloaguen2u.antFood.test;

public class FailedTestException extends Exception {
	/**
	 * The random UID
	 */
	private static final long serialVersionUID = 3990260399204916579L;
	private final String name;
	private final String description;

	public FailedTestException(String title, String string2) {
		super("Failed test " + title);
		name = title;
		description = string2;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}
}
