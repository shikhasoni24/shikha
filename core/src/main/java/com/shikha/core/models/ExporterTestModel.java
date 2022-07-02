package com.shikha.core.models;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;

/**
 * Multifield Example model.
 * @author suraj.kamdi
 */
@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = ComponentExporter.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
    resourceType = ExporterTestModel.RESOURCE_TYPE)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class ExporterTestModel  {

  protected static final String RESOURCE_TYPE = "/apps/learning/components/content/etouchmulti";

  
    
  //Multifield Child Resource for social links
  @ChildResource(name = "products")
  Collection<SocialLinksModel> socialLinks;
  
  @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
  public class SocialLinksModel {

    @ValueMapValue(name = "title")
    private String title;

    @ValueMapValue(name = "imagepath")
    private String imagepath;
    

    @ValueMapValue(name = "description")
    private String desc;


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getImagepath() {
		return imagepath;
	}


	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}
    
    
  }

  @PostConstruct
  protected void init() {
    socialLinks = CollectionUtils.emptyIfNull(this.socialLinks);
  }

  

  public Collection<SocialLinksModel> getSocialLinks() {
    return socialLinks;
  }


}