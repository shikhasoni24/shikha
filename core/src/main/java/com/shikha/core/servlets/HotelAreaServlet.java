package com.shikha.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.shikha.core.service.HotelAreaService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=hotelarea Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/shikha/hotelarea" })
public class HotelAreaServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;


@Reference 
HotelAreaService hoteAreaService;

	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		
		
		ResourceResolver resourceResolver = req.getResourceResolver();
	List<String> list=hoteAreaService.getHotelAreaInfo(resourceResolver);
		
		List<Resource> resList = new ArrayList<>();
		
		for (String str : list) {

			ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
			vm.put("text", str);
			vm.put("value", str);
			resList.add(new ValueMapResource(resourceResolver, new ResourceMetadata(), "nt:unstructured", vm));
		}
		DataSource dataSource = new SimpleDataSource(resList.iterator());
		req.setAttribute(DataSource.class.getName(), dataSource);


	}
}
