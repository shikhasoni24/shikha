package com.shikha.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = SlingHttpServletRequest.class)
public class ErrorHandlerRequestModel {

private static final String DEFAULT_ERROR_PAGE = "/content/shikha/";
private static final String ERROR_CODE_404 = "404";
private static final int MAX_DEPTH = 2;
private static final String PATH_SEPERATOR = "/";

@Self
private SlingHttpServletRequest slingRequest;

@Inject
@Default(values = ERROR_CODE_404)
private String errorCode;

private String pagePath;

@PostConstruct
protected void init() {
 pagePath = DEFAULT_ERROR_PAGE + errorCode;
//   final String requestURI = slingRequest.getRequestPathInfo().getResourcePath();
//   if (requestURI!=null && !requestURI.equals("")) {
//    pagePath = getErrorPageFromRequestedUrl(errorCode, requestURI);
//  }
}

private String getErrorPageFromRequestedUrl(final String errorCode, final String requestURI) {
    final Page resolvedPage = getPageFromPath(requestURI);
    if (resolvedPage != null) {
        return getErrorPathFromPage(errorCode, resolvedPage);
    }
    return null;
}

private Page getPageFromPath(String requestURI) {
    final PageManager pageManager = slingRequest.getResourceResolver().adaptTo(PageManager.class);
    while (requestURI.contains(PATH_SEPERATOR)) {
        Page page = pageManager.getContainingPage(requestURI);
        if (page != null) {
            return page;
        } else {
            requestURI = requestURI.substring(0, requestURI.lastIndexOf(PATH_SEPERATOR));
        }
    }
    return null;
}

private String getErrorPathFromPage(final String errorCode, final Page resolvedPage) {
 if (resolvedPage.hasChild(errorCode)) {
     return resolvedPage.getPath() + PATH_SEPERATOR + errorCode;
    }
    if (resolvedPage.getParent() != null && resolvedPage.getDepth() >= MAX_DEPTH) {
        return getErrorPathFromPage(errorCode, resolvedPage.getParent());
    }
    return null;
}

public String getPagePath() {
    return pagePath;
}
}
