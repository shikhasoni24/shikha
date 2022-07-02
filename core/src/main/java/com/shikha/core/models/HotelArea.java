package com.shikha.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.shikha.core.service.HotelAreaService;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class })

public class HotelArea {

	@SlingObject
	ResourceResolver resolver;

	@OSGiService
	HotelAreaService hotelAreaService;

	private List<String> datalist;

	public List<String> getDatalist() {
		return datalist;
	}

	public void setDatalist(List<String> datalist) {
		this.datalist = datalist;
	}

	@PostConstruct
	protected void init() {
		datalist = hotelAreaService.getHotelAreaInfo(resolver);

	}
}
