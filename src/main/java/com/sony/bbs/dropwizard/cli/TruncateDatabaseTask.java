package com.sony.bbs.dropwizard.cli;

import io.dropwizard.servlets.tasks.Task;

import java.io.PrintWriter;

import org.mongodb.morphia.Datastore;

import com.google.common.collect.ImmutableMultimap;

public class TruncateDatabaseTask extends Task {

	private final Datastore datastore;
	
	public TruncateDatabaseTask(Datastore datastore) {
		super("truncateDatabase");
		this.datastore = datastore;
	}
	
	@Override
	public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
		// equals as removing everything since MongoDB creates DB if not exists
		datastore.getDB().dropDatabase();
	}

}
