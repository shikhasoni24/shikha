package com.shikha.core.utility;

	import org.apache.sling.api.resource.LoginException;
	import org.apache.sling.api.resource.ResourceResolver;
	import org.apache.sling.api.resource.ResourceResolverFactory;

	import java.util.HashMap;
	import java.util.Map;


	/**
	 *  resource resolver factory helper class
	 */
	public final class ResourceResolverUtil {

	    
	    public static ResourceResolver newResolver( ResourceResolverFactory resourceResolverFactory ) throws LoginException {
	        final Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put( ResourceResolverFactory.SUBSERVICE, "shikhaUser");

	        // fetches the admin service resolver using service user.
	        ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
	        return resolver;
	    }
	    
		
	}
