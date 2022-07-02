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
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.google.gson.Gson;
import com.shikha.core.service.HotelAreaService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=hotelareasearchpage Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/shikha/searchpage" })
public class HotelAreaSearchPageServlet extends SlingSafeMethodsServlet {
	private static final long serialVersionUID = 1L;


@Reference 
HotelAreaService hoteAreaService;


@Override
protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		
		
	String selected=req.getParameter("selectedvalue");
	List<String> list=hoteAreaService.getSelectedPages(selected);
	JSONObject obj=new JSONObject();
	try {
		Gson gson=new Gson();
		String s=gson.toJson(list);
		obj.put("pagepath", s);
		resp.getWriter().write(obj.toString());
	} catch (JSONException e) {
		e.printStackTrace();
	}
  }
}
