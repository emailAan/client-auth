package com.avinty.poc.clientauth;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MedewerkerBinding {
	String MEDEWERKER_OUT = "mdout";
	String MEDEWERKER_IN = "mdin";
	String MEDEWERKERS_MV = "medewerkermv";
	String MEDEWERKERS_COUNT_MV = "medewerkercountmv";
	String MEDEWERKER_COUNT_IN = "mdcin";
	String MEDEWERKER_COUNT_OUT = "mdcout";
	
	@Output(MEDEWERKER_COUNT_OUT)	
	KStream<String,Long> medewerkerCountOut();
	
	@Output(MEDEWERKER_OUT)
	MessageChannel medewerkerOut();
	
	@Input(MEDEWERKER_IN)
	KStream<String, String> medewerkerIn();
}
