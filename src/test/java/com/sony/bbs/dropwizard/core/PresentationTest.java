package com.sony.bbs.dropwizard.core;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import io.dropwizard.jackson.Jackson;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class PresentationTest {

	private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

	@Test
	public void serializationTest() throws Exception {
		
		Presentation person = new Presentation();
		person.setAudience("Developers");
		person.setTopic("Dropwizard"); 
		person.setRoom("Avrupa");
		person.setPresenter("hadican");
		person.setOrganiser("Umut ISIK");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(0);
		calendar.set(2015,5,11);
		Date date = calendar.getTime();
		person.setDate(date);

		String expected = MAPPER.writeValueAsString(MAPPER.readValue(fixture("fixtures/presentation.json"), Presentation.class));

		assertThat(MAPPER.writeValueAsString(person)).isEqualTo(expected);
	}
	
	@Test
	public void deserializationTest() throws Exception {
		
		Presentation person = new Presentation();
		person.setAudience("Developers");
		person.setTopic("Dropwizard"); 
		person.setRoom("Avrupa");
		person.setPresenter("hadican");
		person.setOrganiser("Umut ISIK");
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(0);
		calendar.set(2015,5,11);
		Date date = calendar.getTime();
		person.setDate(date);
		
		Presentation deserializedPerson = MAPPER.readValue(fixture("fixtures/presentation.json"), Presentation.class);
		assertThat(deserializedPerson.getPresenter()).isEqualTo(person.getPresenter());
	}

}
