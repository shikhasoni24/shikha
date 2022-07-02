package com.shikha.core.service;

import java.util.List;
import org.apache.sling.api.resource.ResourceResolver;

public interface HotelAreaService {
	
	public List<String> getHotelAreaInfo(ResourceResolver resourceResolver);
	public List<String> getSelectedPages(String selected);

	

}
