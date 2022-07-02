package com.shikha.core.service.serviceimpl;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import javax.jcr.Session;
import javax.jcr.security.AccessControlManager;
import javax.jcr.security.Privilege;
import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.JackrabbitAccessControlList;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.jackrabbit.authorization.AccessControlUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.service.ModifyNodePermissionService;
import com.shikha.core.utility.ResourceResolverUtil;

@Component(service = ModifyNodePermissionService.class, immediate = true)
public class ModifyNodePermissionServiceImpl implements ModifyNodePermissionService {

	@Reference
	ResourceResolverFactory factory;

	public void modifyNodePermission(String nodePath, String groupName) {
		Session session = null;

		try {
			ResourceResolver resolver = ResourceResolverUtil.newResolver(factory);
			session = resolver.adaptTo(Session.class);
			Node node = resolver.getResource(nodePath).adaptTo(Node.class);

			AccessControlManager acm = session.getAccessControlManager();
			UserManager um = ((JackrabbitSession) session).getUserManager();
			
			Authorizable authorizable = um.getAuthorizable(groupName);
			JackrabbitAccessControlList acl = AccessControlUtils.getAccessControlList(session, nodePath);
			Privilege[] privilege = AccessControlUtils.privilegesFromNames(session, Privilege.JCR_READ);
			if (node.hasProperty("accessLevel")) {
				if (node.getProperty("accessLevel").getValue().toString().equalsIgnoreCase("public")) {
					acl.addEntry(authorizable.getPrincipal(), privilege, true);
					acm.setPolicy(acl.getPath(), acl);

				} else if (node.getProperty("accessLevel").getValue().toString().equalsIgnoreCase("hide from public")) {
					acl.addEntry(authorizable.getPrincipal(), privilege, false);
					acm.setPolicy(acl.getPath(), acl);

				}
			}

			session.save();

		} catch (LoginException | RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
