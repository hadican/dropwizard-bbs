package com.sony.bbs.dropwizard.resources;

import io.dropwizard.jersey.caching.CacheControl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.mongodb.morphia.Datastore;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.sony.bbs.dropwizard.configuration.BBSConfiguration;
import com.sony.bbs.dropwizard.core.Presentation;
import com.sony.bbs.dropwizard.view.PresentationView;

@Path("/presentation")
@Produces(MediaType.APPLICATION_JSON)
public class PresentationResource {

	private final BBSConfiguration configuration;
	private final Datastore datastore;

	public PresentationResource(BBSConfiguration configuration, Datastore datastore) {
		this.datastore = datastore;
		this.configuration = configuration;
	}

	@GET
	@Timed
	@Path("/presenter/{presenter}")
	public List<Presentation> getPresentations(@PathParam("presenter") String presenter, @QueryParam("order") Optional<String> orderParameter) {
		// get order type if set
		String orderBy = orderParameter.or("id"); // or @DefaultValue("id")
		// find presentations of given presenter
		return datastore.find(Presentation.class, "presenter", presenter).order(orderBy).asList();
	}

	@PUT
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addPresentation(@Valid Presentation presentation) {
		// save presentation to DB and return 201
		datastore.save(presentation);
		return Response.created(UriBuilder.fromResource(this.getClass()).path("presenter").path(presentation.getPresenter()).build()).entity(presentation).build();
	}
	
	@GET
	@Path("/view/{presenter}")
	@Produces(MediaType.TEXT_HTML)
	public PresentationView view(@PathParam("presenter") String presenter){
		return new PresentationView(datastore.find(Presentation.class, "presenter", presenter).asList());
	}

	@GET
	@Path("/configuration")
	@CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.MINUTES)
	public BBSConfiguration getConfiguration() {
		return configuration;
	}
	
}
