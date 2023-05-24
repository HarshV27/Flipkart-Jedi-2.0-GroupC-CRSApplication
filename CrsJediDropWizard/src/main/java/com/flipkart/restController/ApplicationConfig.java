package com.flipkart.restController;

import org.glassfish.jersey.server.ResourceConfig;



public class ApplicationConfig extends ResourceConfig {

	public ApplicationConfig() {

	register(StudentRestApi.class);
	register(CRSApplicationRestApi.class);
	register(ProfessorRestApi.class);
	register(AdminRestApi.class);

	}

}