package com.m2mi.speck.client;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.servlet.ServletContainer;

public class SpeckClient {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SpeckClient.class);
	
	private static final boolean DEBUG = true;
	
	/* Servlet */
	private static String HOSTNAME = "localhost";
	private static int PORT = 9000;
	private static final String CONTEXT_PATH = "/speck";
	private static final String SERVLET_PATH = "/api";
	
	private static SpeckClient instance;
	
	private HttpServer server;
	
	private SpeckClient() {
		
	}
	
	public static synchronized SpeckClient getInstance() {
		if(instance == null) {
			instance = new SpeckClient();
		}
		return instance;
	}
	
	private URI getBaseUri() {
		return UriBuilder.fromUri("http://" + HOSTNAME).port(PORT).build();
	}
	
	/**
	 * Parses the input parameters.
	 * <p>
	 * Accepted parameters are:
	 *  <ul>
	 * 	<li>-h : hostname
	 * 	<li>-p : port
	 * </ul>
	 * 
	 * @param args
	 * @return
	 * @throws InvalidInputException
	 */
	private Map<String,Object> parseInput(String[] args) {
		
		Map<String,Object> input = new HashMap<String,Object>();
		
		if(args != null && args.length > 0) {
			/* Help required */
			if(args[0].equals("--help")) {
				System.out.println("Possible arguments for the Speck Client:");
				System.out.println("");
				System.out.println("-h : 'hostname' as string.");
				System.out.println("-p : 'port' as integer.");
				
				System.exit(0);
			}
			
			for(int i = 0; i < args.length; i +=2) {
				if(args[i].equals("-h")) {
					input.put("hostname", args[i+1]);
					continue;
				}
				if(args[i].equals("-p")) {
					input.put("port", Integer.valueOf(args[i+1]));
					continue;
				}
			}
			
			logger.info(String.format("Node started with arguments [%s].", input.keySet()));
		}
		
		return input;
	}
	
	private void start(String[] args) throws IOException {
		
		logger.info("Starting Speck Client...");
		
		/* Parse input */
		Map<String,Object> input = this.parseInput(args);
		if(input.containsKey("hostname")) {
			HOSTNAME = (String)input.get("hostname");
		}
		if(input.containsKey("port")) {
			PORT = (Integer)input.get("port");
		}
		
		/* Start server */
		WebappContext context = new WebappContext("speck",CONTEXT_PATH);
		ServletRegistration registration = context.addServlet(ServletContainer.class.getName(), ServletContainer.class);
		registration.setInitParameter("jersey.config.server.provider.packages",
				"com.m2mi.speck.client.rs;com.fasterxml.jackson.jaxrs.json");
		registration.addMapping(SERVLET_PATH + "/*"); 
		
		server = GrizzlyHttpServerFactory.createHttpServer(getBaseUri());
		context.deploy(server);
	}
	
	public static void main(String[] args) {
		
		try {			
			System.out.println("starting server");
			SpeckClient.getInstance().start(args);
			if(DEBUG) {
		        System.out.println("Press any key to stop the service...");
		        System.in.read();
		        System.exit(0);
			}
		} catch(Exception e) {
			System.out.println("Failed to start Speck Client");
			e.printStackTrace();
			System.exit(0);
	    }
		
	}
}
