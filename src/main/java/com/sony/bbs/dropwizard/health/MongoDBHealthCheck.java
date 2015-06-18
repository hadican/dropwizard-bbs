package com.sony.bbs.dropwizard.health;

import org.mongodb.morphia.Datastore;

import com.codahale.metrics.health.HealthCheck;

public class MongoDBHealthCheck extends HealthCheck {

	private Datastore datastore;

	public MongoDBHealthCheck(Datastore datastore) {
		this.datastore = datastore;
	}

	@Override
	protected Result check() throws Exception {
		try {
			datastore.getDB().getStats().get("ok");
			return Result.healthy();
		} catch (Exception e) {
			return Result.unhealthy("Cannot access to DB!");
		}
	}

}
