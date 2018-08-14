package com.avinty.poc.clientauth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableBinding(ClientBinding.class)
public class ClientAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientAuthApplication.class, args);
	}

	@RestController
	public static class ClientRestController {

		private final QueryableStoreRegistry registry;

		public ClientRestController(QueryableStoreRegistry registry) {
			this.registry = registry;
		}

		@GetMapping("/clients/{id}")
		public String get(@PathVariable String id) {

			ReadOnlyKeyValueStore<String, String> queryableStoreType = this.registry
					.getQueryableStoreType(ClientBinding.CLIENTS_MV, QueryableStoreTypes.keyValueStore());

			return queryableStoreType.get(id);
		}

		@GetMapping("/clients")
		public Map<String, String> counts() {
			Map<String, String> clients = new HashMap<>();

			ReadOnlyKeyValueStore<String, String> queryableStoreType = this.registry
					.getQueryableStoreType(ClientBinding.CLIENTS_MV, QueryableStoreTypes.keyValueStore());

			KeyValueIterator<String, String> iter = queryableStoreType.all();

			while (iter.hasNext()) {
				KeyValue<String, String> value = iter.next();
				clients.put(value.key, value.value);
			}

			return clients;
		}

	}

	@Component
	public static class ClientEventProcessor {
		@StreamListener
		@SendTo(ClientBinding.CLIENT_COUNT_OUT)
		public KStream<String, Long> process(@Input(ClientBinding.CLIENT_IN) KStream<String, String> events) {

			events.map((key, value) -> new KeyValue<>(Client.fromJson(value).getId(), value))//
					.groupByKey()//
					.aggregate(() -> "", (aggKey, newValue, aggValue) -> newValue,
							Materialized.as(ClientBinding.CLIENTS_MV));

			return events//
					.map((key, value) -> new KeyValue<>(Client.fromJson(value).getId(), ""))//
					.groupByKey()//
					.count().toStream();
		}
	}

	@Component
	public static class ClientEventSource implements ApplicationRunner {

		private Log log = LogFactory.getLog(ClientEventSource.class);
		private MessageChannel clientsOut;

		public ClientEventSource(ClientBinding binding) {
			this.clientsOut = binding.clientOut();
		}

		@Override
		public void run(ApplicationArguments args) throws Exception {

			Runnable runnable = () -> {
				Client client = Client.generateClient();
				log.info(client);
				Message<String> message = MessageBuilder.withPayload(client.toJson())
						.setHeader(KafkaHeaders.MESSAGE_KEY, client.toJson().getBytes()).build();

				try {
					clientsOut.send(message);
				} catch (Exception e) {
					log.error(e);
				}
			};

			Executors.newScheduledThreadPool(1).scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);

		}
	}

}