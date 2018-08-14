package com.avinty.poc.clientauth;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ToegangPerClient {

	@JsonProperty("clientId")
	private String clientId;

	private String name;

	private List<String> medewerkers;

	private static Gson gson = new GsonBuilder().create();

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMedewerkers() {
		return medewerkers;
	}

	public void setMedewerkers(List<String> medewerkers) {
		this.medewerkers = medewerkers;
	}

	public ToegangPerClient() {
		this.medewerkers = new ArrayList<>();
	}

	public ToegangPerClient(String clientId, String name, List<String> medewerkers) {
		super();
		this.clientId = clientId;
		this.name = name;
		this.medewerkers = medewerkers == null ? new ArrayList<>() : medewerkers;
	}

	public ToegangPerClient(String clientId, String name) {
		super();
		this.clientId = clientId;
		this.name = name;
		this.medewerkers = new ArrayList<>();
	}

	public String toJson() {
		return gson.toJson(this);
	}
	
	public ToegangPerClient addToegang(String clientId, String medewerkerId) {
		this.setClientId(clientId);
		this.getMedewerkers().add(medewerkerId);
		return this;
	}

	public static ToegangPerClient instance() {
		return new ToegangPerClient();
	}
	
}
