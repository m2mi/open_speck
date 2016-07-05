
package com.m2mi.speck.client.rs;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class ResponseCorsFilter implements ContainerResponseFilter {

	static {
		System.out.println("Response CORS Filter loaded.");
	}
	
	@Override
	public void filter(ContainerRequestContext reqContext, ContainerResponseContext respContext) throws IOException {
				
		respContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		respContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
		
		String reqHead= reqContext.getHeaderString("Access-Control-Request-Headers"); 
		if(null != reqHead && !reqHead.equals("")){
			respContext.getHeaders().add("Access-Control-Allow-Headers",reqHead);
		}
		
	}

}