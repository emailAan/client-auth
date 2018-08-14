package com.avinty.poc.clientauth;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MedewerkerToegangBinding {
	String MEDEWERKER_TOEGANG_OUT = "mtout";
	String CLIENT_IN = "clin";
	String CLIENT_OUT = "clout";
	
	@Output(CLIENT_OUT)	
	KStream<String,Long> clientOut();
	
	@Output(MEDEWERKER_TOEGANG_OUT)
	MessageChannel medewerkerToegangOut();
	
	@Input(CLIENT_IN)
	KStream<String, String> clientIn();
	
}
