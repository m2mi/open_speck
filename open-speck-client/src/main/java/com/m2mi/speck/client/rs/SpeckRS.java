package com.m2mi.speck.client.rs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.m2mi.speck.jni.SpeckTesterJNI;

@Path("v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SpeckRS {

	@GET
	@Path("hello")
	public Response sayHello(@Context HttpServletRequest hsr) { 
		return Response.status(200).entity("Hello Speck!").build();
	}
	
	@GET
	@Path("{blockSize: [0-9]*}/{keySize: [0-9]*}/test")
	public Response testSpeed(
			@Context HttpServletRequest hsr, 
			@PathParam("blockSize") int blockSize,
			@PathParam("keySize") int keySize,
			@DefaultValue("cbc") @QueryParam("mode") String mode,
			@DefaultValue("1") @QueryParam("mb") int mb) { 
		
		long[] result = null;
		int code = 200;
		Map<String,Object> outcome = new HashMap<String,Object>();
		
		SpeckTesterJNI tester = new SpeckTesterJNI();
		if("cbc".equals(mode.toLowerCase())) {
			result = tester.testCBCSpeed(mb, blockSize, keySize);
			if(result[0] == -1) {
				code = 500;
				outcome.put("success", false);
				outcome.put("error", "Unexpected error.");
			}
			else {
				outcome.put("success", true);
				outcome.put("mb", mb);
				outcome.put("encryption", result[0]);
				outcome.put("decrytpion", result[1]);
			}
		}
		else {
			code = 400;
			outcome.put("success", false);
			outcome.put("error", "Unknown mode.");
		}
		
		return Response.status(code).entity(outcome).build();
	}
	
	@GET
	@Path("{blockSize: [0-9]*}/{keySize: [0-9]*}/test")
	public Response encrypt(
			@Context HttpServletRequest hsr, 
			@PathParam("blockSize") int blockSize,
			@PathParam("keySize") int keySize,
			@DefaultValue("cbc") @QueryParam("mode") String mode,
			@DefaultValue("1") @QueryParam("mb") int mb) { 
		
	}
	
}
