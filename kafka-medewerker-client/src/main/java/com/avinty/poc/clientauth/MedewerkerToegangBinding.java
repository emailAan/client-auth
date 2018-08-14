package com.avinty.poc.clientauth;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface MedewerkerToegangBinding {
	String MEDEWERKER_TOEGANG_IN = "mtin";
	String TOEGANG_PER_CLIENT_MV = "tpclientmv";
	String TOEGANG_PER_CLIENT = "tpclient";
	
	@Output(TOEGANG_PER_CLIENT)	
	KStream<String,String> toegangPerClient();
	
	@Input(MEDEWERKER_TOEGANG_IN)
	KStream<String, String> medewerkerToegangIn();
	
}
