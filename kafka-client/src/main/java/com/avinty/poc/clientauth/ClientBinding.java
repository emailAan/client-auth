package com.avinty.poc.clientauth;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface ClientBinding {
	String CLIENT_OUT = "clout";
	String CLIENT_IN = "clin";
	String CLIENTS_MV = "clientmv";
	String CLIENT_COUNT_IN = "clcin";
	String CLIENT_COUNT_OUT = "clcout";

	@Output(CLIENT_COUNT_OUT)
	KStream<String,Long> clientCountOut();
	
	@Output(CLIENT_OUT)
	MessageChannel clientOut();
	
	@Input(CLIENT_IN)
	KStream<String, String> clientIn();

}
