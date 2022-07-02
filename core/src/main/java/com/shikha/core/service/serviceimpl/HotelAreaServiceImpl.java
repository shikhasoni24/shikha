package com.shikha.core.service.serviceimpl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.shikha.core.service.HotelAreaService;

@Component(service = HotelAreaService.class, name = "Hotel Service", immediate = true)
public class HotelAreaServiceImpl implements HotelAreaService {

	@Reference
	QueryBuilder querybuilder;
	@Reference
	ResourceResolverFactory resourceResolverFactory;

	@Override
	public List<String> getHotelAreaInfo(ResourceResolver resourceResolver) {
		Resource res = resourceResolver.resolve("/content/shikha/welcome/jcr:content/hotelareas");
		List<String> list = new ArrayList<>();
		Iterator<Resource> itr = res.listChildren();
		while (itr.hasNext()) {
			ValueMap child = itr.next().getValueMap();
			list.add(child.get("hotelarea", ""));

		}
		return list;
	}

	public Map<String, String> getQuery() {
		Map<String, String> map = new HashMap<>();
		map.put("path", "/content/shikha/welcome");
		map.put("type", "cq:Page");

		return map;
	}

	public List<String> getSelectedPages(String selected) {
		List<String> l = new ArrayList<>();
		try {
			Resource resource = null;
			Map<String, Object> params = new HashMap<>();
			params.put(ResourceResolverFactory.SUBSERVICE, "myUser");
			ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(params);
			Session session = resolver.adaptTo(Session.class);
			Query query = querybuilder.createQuery(PredicateGroup.create(getQuery()), session);
			SearchResult result = query.getResult();
			List<Hit> hits = result.getHits();

			for (Hit hit : hits) {

				resource = resolver.getResource(hit.getPath() + "/jcr:content");
				Node node = resource.adaptTo(Node.class);
				if (node.hasProperty("dropdowncomp")) {
					if (node.getProperty("dropdowncomp").isMultiple()) {
						Value[] valueMap = node.getProperty("dropdowncomp").getValues();
						for (Value val : valueMap) {
							if (val.toString().equalsIgnoreCase(selected)) {
								l.add(hit.getPath());
							}
						}
					} else {
						if (node.getProperty("dropdowncomp").toString().contains(selected)) {
							l.add(hit.getPath());
						}
					}
				}
			}
		} catch (LoginException | RepositoryException e) {
			e.printStackTrace();
		}

		return l;

	}

}
