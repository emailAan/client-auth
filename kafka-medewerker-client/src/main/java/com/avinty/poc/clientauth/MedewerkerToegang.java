package com.avinty.poc.clientauth;

import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MedewerkerToegang {

	private String id;

	private String clientId;

	private String medewerkerId;

	private static Gson gson = new GsonBuilder().create();

	public MedewerkerToegang(String clientId, String medewerkerId) {
		super();
		this.clientId = clientId;
		this.medewerkerId = medewerkerId;
		this.id = UUID.randomUUID().toString();
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getMedewerkerId() {
		return medewerkerId;
	}

	public void setMedewerkerId(String medewerkerId) {
		this.medewerkerId = medewerkerId;
	}

	public MedewerkerToegang() {
	}

	String getId() {
		return id;
	}

	void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return this.medewerkerId + "-" + this.clientId;
	}

	public String toJson() {
		return gson.toJson(this);
	}

	public static MedewerkerToegang fromJson(String json) {
		return gson.fromJson(json, MedewerkerToegang.class);
	}

}
