package com.avinty.poc.clientauth;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MedewerkerToegangBinding.class)
public class MedewerkerToegangEventProducer {

	@SuppressWarnings("deprecation")
	@StreamListener
	@SendTo(MedewerkerToegangBinding.TOEGANG_PER_CLIENT)
	public KStream<String, ToegangPerClient> processGroupToegang(
			@Input(MedewerkerToegangBinding.MEDEWERKER_TOEGANG_IN) KStream<String, String> toegangStream) {

		return toegangStream.map((key, value) -> {
			MedewerkerToegang mt = MedewerkerToegang.fromJson(value);
			return KeyValue.pair(mt.getClientId(), mt.getMedewerkerId());
		}).groupByKey()
				.aggregate(() -> ToegangPerClient.instance(),
						(cId, mId, toegang) -> toegang.addToegang(cId, mId),
						ToegangPerClientSerde.instance(), MedewerkerToegangBinding.TOEGANG_PER_CLIENT_MV)
				.toStream();
	}

}
