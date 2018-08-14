package com.avinty.poc.clientauth;

import java.util.Random;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Client {

	private String id;

	private String firstName;

	private String lastName;

	private static Gson gson = new GsonBuilder().create();

	public Client() {
	}

	public Client(String id, String firstName, String lastName) {
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
		return gson.toJson(this);
	}

	public static Client fromJson(String json) {
		return gson.fromJson(json, Client.class);
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

	public static Client generateClient() {
		return new Client(UUID.randomUUID().toString(), capitalize(generateRandomWord(4)),
				capitalize(generateRandomWord(8)));
	}

}
