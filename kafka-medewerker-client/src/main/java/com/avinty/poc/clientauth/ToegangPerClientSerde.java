package com.avinty.poc.clientauth;

import org.apache.kafka.common.serialization.Serde;
import org.springframework.kafka.support.serializer.JsonSerde;

public class ToegangPerClientSerde extends JsonSerde<ToegangPerClient> {

	static public Serde<ToegangPerClient> instance() {
		return new ToegangPerClientSerde();
	}
	
	
}