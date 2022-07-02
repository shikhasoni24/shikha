package com.shikha.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.shikha.core.dao.UserDao;
import com.shikha.core.entity.User;
import com.shikha.core.exception.exceptionimpl.DAOException;

//@Component(service = Servlet.class, immediate = true, property = { "sling.servlet.paths=/bin/data/custompermissions",
	//	"sling.servlet.methods=GET" })
public class CustomPermissionServlet extends SlingSafeMethodsServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(CustomPermissionServlet.class);

	@Reference
	UserDao userDao;
	
	private static final String RFG_COMPANIES_INCLUDED_LIST = "rfgCompaniesIncludedList";
	private static final String RFG_COMPANIES_EXCLUDED_LIST = "rfgCompaniesExcludedList";

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse resp) {
		try {
			String path = request.getParameter("pathname");
			boolean isRFGSelected = false;
			List<String> rfgCompaniesIncludedList = new ArrayList<>();
			List<String> rfgCompaniesExcludedList = new ArrayList<>();

			String currentPagePath = path.substring(path.indexOf("?item=") + 6, path.length());
			if (currentPagePath.contains("%2F"))
				currentPagePath = currentPagePath.replaceAll("%2F", "/");
			 ResourceResolver resourceResolver = request.getResourceResolver();

		        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
			Page page = pageManager.getPage(currentPagePath);
			ValueMap vm = page.getProperties();
			if (vm.containsKey("regions")) {
				String allRegion = vm.get("regions").toString();
				String regionArray[] = allRegion.split(",");
				List<String> regionsList = Arrays.asList(regionArray);
				if (regionsList.contains("rfg")) {
					isRFGSelected = true;
					if (vm.containsKey(RFG_COMPANIES_INCLUDED_LIST)) {
						String usersId = vm.get(RFG_COMPANIES_INCLUDED_LIST).toString();
						if (usersId.equalsIgnoreCase("ALL"))
							rfgCompaniesIncludedList.add("all");
						else {
							String str[] = usersId.split(",");
							rfgCompaniesIncludedList = Arrays.asList(str);
						}
					}
					if (vm.containsKey(RFG_COMPANIES_EXCLUDED_LIST)) {
						String usersId = vm.get(RFG_COMPANIES_EXCLUDED_LIST).toString();
						if (usersId.equalsIgnoreCase("ALL"))
							rfgCompaniesExcludedList.add("all");
						else {
							String str[] = usersId.split(",");
							rfgCompaniesExcludedList = Arrays.asList(str);
						}
					}

				}
				if (regionsList.contains("AllUnchecked")) {
					isRFGSelected = false;
				} else
					isRFGSelected = true;
			}

			log.info("calling custom permission");
			List<User> user = userDao.getUserData();
			JSONObject ob = new JSONObject();
			ob.put("userdata", user);
			ob.put("rfgCompaniesIncludedList", rfgCompaniesIncludedList);
			ob.put("rfgCompaniesExcludedList", rfgCompaniesExcludedList);
			ob.put("isRFGSelected", isRFGSelected);

			resp.getWriter().write(ob.toString());

		} catch (IOException | DAOException | JSONException e) {
			log.error("error  ", e);
			e.printStackTrace();
		}

	}
}
