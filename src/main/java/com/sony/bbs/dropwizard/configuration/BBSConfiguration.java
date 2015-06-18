package com.sony.bbs.dropwizard.configuration;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sony.bbs.dropwizard.core.DataSource;

public class BBSConfiguration extends Configuration {

	@NotNull
	private String organiser;

	@JsonProperty("mongodb")
	private DataSource dataSource;

	@JsonProperty
	public String getOrganiser() {
		return organiser;
	}

	@JsonProperty
	public void setOrganiser(String organiser) {
		this.organiser = organiser;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

}
