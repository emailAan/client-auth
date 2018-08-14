package com.avinty.poc.clientauth;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class MedewerkEventProducer {
	@StreamListener
	@SendTo(MedewerkerBinding.MEDEWERKER_COUNT_OUT)
	public KStream<String, Long> process(@Input(MedewerkerBinding.MEDEWERKER_IN) KStream<String, String> events) {

		events.map((key, value) -> new KeyValue<>(Medewerker.fromJson(value).getId(), value))//
				.groupByKey()//
				.aggregate(() -> "", (aggKey, newValue, aggValue) -> newValue,
						Materialized.as(MedewerkerBinding.MEDEWERKERS_MV));

		return events//
				.map((key, value) -> new KeyValue<>(Medewerker.fromJson(value).getId(), ""))//
				.groupByKey()//
				.count(Materialized.as(MedewerkerBinding.MEDEWERKERS_COUNT_MV)).toStream();
	}
}
