package com.avinty.poc.clientauth;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedewerkerRestController {

	private final QueryableStoreRegistry registry;

	public MedewerkerRestController(QueryableStoreRegistry registry) {
		this.registry = registry;
	}

	@GetMapping("/medewerkers/{id}")
	public String medewerker(@PathVariable String id) {

		ReadOnlyKeyValueStore<String, String> queryableStoreType = this.registry
				.getQueryableStoreType(MedewerkerBinding.MEDEWERKERS_MV, QueryableStoreTypes.keyValueStore());

		return queryableStoreType.get(id);
	}

	@GetMapping("/medewerkers")
	public Map<String, String> medewerkers() {
		Map<String, String> medewerkers = new HashMap<>();

		ReadOnlyKeyValueStore<String, String> queryableStoreType = this.registry
				.getQueryableStoreType(MedewerkerBinding.MEDEWERKERS_MV, QueryableStoreTypes.keyValueStore());

		KeyValueIterator<String, String> iter = queryableStoreType.all();

		while (iter.hasNext()) {
			KeyValue<String, String> value = iter.next();
			medewerkers.put(value.key, value.value);
		}

		return medewerkers;
	}

}
