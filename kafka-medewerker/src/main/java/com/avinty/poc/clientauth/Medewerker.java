package com.avinty.poc.clientauth;

import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Medewerker {

	private String id;

	private String firstName;

	private String lastName;

	public Medewerker() {
	}

	public Medewerker(String id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	String getLastName() {
		return lastName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}

	String getFirstName() {
		return firstName;
	}

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}

	public String toJson() {
		Gson gson = new GsonBuilder().create();
		return gson.toJson(this);
	}

	public static Medewerker fromJson(String json) {
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(json, Medewerker.class);
	}

	private static String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	private static String generateRandomWord(int length) {
		Random random = new Random();
		char[] word = new char[length];
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		return new String(word);
	}

	public static Medewerker generateMedewerker() {
		return new Medewerker(UUID.randomUUID().toString(), capitalize(generateRandomWord(4)), capitalize(generateRandomWord(8)));
	}
}
