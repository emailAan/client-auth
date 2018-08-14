package com.avinty.poc.clientauth;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.QueryableStoreRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MedewerkerToegangRestController {

	private final QueryableStoreRegistry registry;

	public MedewerkerToegangRestController(QueryableStoreRegistry registry) {
		this.registry = registry;
	}

	@GetMapping("/toegang")
	public Map<String, ToegangPerClient> toegangPerClient() {
		Map<String, ToegangPerClient> toegang = new HashMap<>();

		ReadOnlyKeyValueStore<String, ToegangPerClient> queryableStoreType = this.registry.getQueryableStoreType(
				MedewerkerToegangBinding.TOEGANG_PER_CLIENT_MV, QueryableStoreTypes.keyValueStore());

		queryableStoreType.all().forEachRemaining((value) -> toegang.put(value.key, value.value));

		return toegang;
	}

}
