package com.avinty.poc.clientauth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MedewerkerToegangBinding.class)
public class MedewerkerToegangEventProducer {

	private Log log = LogFactory.getLog(MedewerkerToegangEventProducer.class);
	private MessageChannel medewerkerToegangOut;

	public MedewerkerToegangEventProducer(MedewerkerToegangBinding binding) {
		this.medewerkerToegangOut = binding.medewerkerToegangOut();
	}

	public void sendNewToegang(String clientId) {
		MedewerkerToegang toegang = new MedewerkerToegang();

		toegang.setMedewerkerId("fb72fc06-bdfc-49b6-8497-967bc2f08a58");
		toegang.setClientId(clientId);

		Message<String> message = MessageBuilder.withPayload(toegang.toJson())
				.setHeader(KafkaHeaders.MESSAGE_KEY, toegang.toJson().getBytes()).build();

		try {
			medewerkerToegangOut.send(message);
		} catch (Exception e) {
			log.error(e);
		}
	}


	@StreamListener
	@SendTo(MedewerkerToegangBinding.CLIENT_OUT)
	public KStream<String, String> process(
			@Input(MedewerkerToegangBinding.CLIENT_IN) KStream<String, String> clientStream) {

		clientStream.foreach((key, client) -> sendNewToegang(Client.fromJson(client).getId()));
		
		return clientStream;
	}
	
}
