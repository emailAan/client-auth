package com.avinty.poc.clientauth;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(MedewerkerBinding.class)
public class MedewerkerCreateSimulator implements ApplicationRunner {

	private Log log = LogFactory.getLog(MedewerkerCreateSimulator.class);
	private MessageChannel medewerkerOut;

	public MedewerkerCreateSimulator(MedewerkerBinding binding) {
		this.medewerkerOut = binding.medewerkerOut();
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Runnable runnable = () -> {
			Medewerker medewerker = Medewerker.generateMedewerker();
			log.info(medewerker);
			Message<String> message = MessageBuilder.withPayload(medewerker.toJson())
					.setHeader(KafkaHeaders.MESSAGE_KEY, medewerker.toJson().getBytes()).build();

			try {
				medewerkerOut.send(message);
			} catch (Exception e) {
				log.error(e);
			}
		};

		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);

	}

}
