package com.titan.irgs.security.configuration;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.titan.irgs.accessPolicy.domain.BackEndApis;
import com.titan.irgs.accessPolicy.domain.Permission;
import com.titan.irgs.accessPolicy.repository.BackEndApisRepo;
import com.titan.irgs.accessPolicy.repository.PermissionRepo;

@Component
public class UrlAccessDecisionManager implements AccessDecisionManager {
	
	
 
    /**
     * @author hari.k
     * @param authentication: role information of the currently logged in user
     * @param object: request url information
     * @param collection: from the getAttributes method in `UrlFilterInvocationSecurityMetadataSource`, indicating the role required by the current request (there may be multiple)
     * @return: void
     */
	
	 @Autowired
	 PermissionRepo permissionRepo;
	 
	 @Autowired
	 BackEndApisRepo backEndApisRepo;
   
	 @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {
                 // traverse the role
    	
    	for (ConfigAttribute ca : collection) {
                // The permission required for the current url request
            String uri = ca.getAttribute();
   		        
          //check the url exists in db;
            BackEndApis backEndApis=backEndApisRepo.findByBackEndApiIdUrl(uri.trim());
           if(backEndApis==null) {
        	   if (authentication instanceof AnonymousAuthenticationToken) {
                   throw new BadCredentialsException("Not logged in!");
           			} else {
                   throw new AccessDeniedException("The url is not authorized!");
           			}
          
           
           }


                // In GrantedAuthority list of webRoleIds stored 
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
            	
                Permission permission=permissionRepo.checkApiAppendTorole(uri,Long.parseLong(authority.getAuthority()));
                if (permission!=null) {
                    return;
                }
                
            }
        }
                 throw new AccessDeniedException("Please contact the administrator to assign permission!");
    }
 
    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }
 
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
