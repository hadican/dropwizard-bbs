package com.sony.bbs.dropwizard.application;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.sony.bbs.dropwizard.cli.TruncateDatabaseTask;
import com.sony.bbs.dropwizard.configuration.BBSConfiguration;
import com.sony.bbs.dropwizard.core.DataSource;
import com.sony.bbs.dropwizard.filter.AdminServletFilter;
import com.sony.bbs.dropwizard.health.MongoDBHealthCheck;
import com.sony.bbs.dropwizard.resources.PresentationResource;

public class BBSApplication extends Application<BBSConfiguration> {

	public static void main(String[] args) throws Exception {
		new BBSApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<BBSConfiguration> bootstrap) {
		// serve static resources
		bootstrap.addBundle(new AssetsBundle("/assets/img", "/img"));
		bootstrap.addBundle(new ViewBundle<BBSConfiguration>());
	}

	@Override
	public void run(BBSConfiguration configuration, Environment environment) {
		// create data store from configuration
		DataSource dataSource = configuration.getDataSource();
		MongoClient mongoClient = new MongoClient(dataSource.getHost(), dataSource.getPort());
		Datastore datastore = new Morphia().createDatastore(mongoClient, "BBS");

		// create resources
		final PresentationResource presentationResource = new PresentationResource(configuration, datastore);
		final MongoDBHealthCheck mongoDBHealthCheck = new MongoDBHealthCheck(datastore);

		// register resources, tasks and filters
		environment.healthChecks().register("mondoDB", mongoDBHealthCheck);
		environment.jersey().register(presentationResource);
		environment.admin().addTask(new TruncateDatabaseTask(datastore));
		environment.servlets().addFilter("adminFilter", new AdminServletFilter()).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/admin/*");
	}

}
