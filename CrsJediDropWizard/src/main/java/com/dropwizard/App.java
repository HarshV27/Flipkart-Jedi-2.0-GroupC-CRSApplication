package com.dropwizard;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.dropwizard.rest.EmployeeRESTController;
//import com.dropwizard.rest.EmployeeRESTController;
import com.dropwizard.rest.HelloRestController;
//import com.flipkart.restController.StudentRestApi;
import com.flipkart.restController.*;

/**
 * Hello world!
 *
 */


public class App extends Application<Configuration> {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
 
    @Override
    public void initialize(Bootstrap<Configuration> b) {
    }
 
    @Override
    public void run(Configuration c, Environment e) throws Exception {
        LOGGER.info("Registering REST resources");
      //  e.jersey().register(new EmployeeRESTController(e.getValidator()));
        e.jersey().register(new HelloRestController());
        e.jersey().register(new StudentRestApi(e.getValidator()));
        e.jersey().register(new CRSApplicationRestApi(e.getValidator()));
      e.jersey().register(new AdminRestApi(e.getValidator()));
      e.jersey().register(new ProfessorRestApi(e.getValidator()));
//        e.jersey().register(new EmployeeRESTController(e.getValidator()));

        //here we need to register all the rest controllers..one by one 
    }
 
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }
}