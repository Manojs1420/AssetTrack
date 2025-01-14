package com.titan.irgs.security.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.titan.irgs.WebConstantUrl.WebConstantUrl;

@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	
	
 
    /***
     * @author hari.k  
     * Return the user permission information required by the url
     * @param object: store request url information
     * @return: null: the logo can be accessed without any permission
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
                 // Get the current request url
         String requestUrl = ((FilterInvocation) object).getRequestUrl();
        
      // TODO ignores the URL, please put it here to filter and release
		if(requestUrl.equals(WebConstantUrl.USER+WebConstantUrl.SIGNIN) || 
			 requestUrl.equals("/v2/api-docs")||
			 requestUrl.equals( "/swagger-ui.html")||
			 requestUrl.startsWith("/webjars") || 
			 requestUrl.startsWith("/swagger-resources")  || 
			 requestUrl.equals("/configuration/security") ||
			 requestUrl.equals("/future/save") ||
			 requestUrl.equals("/future/update") ||
			 requestUrl.startsWith("/future/getById") ||
			 requestUrl.equals("/future/getAll") ||
		     requestUrl.equals("/brands/exportExcel") ||
		     requestUrl.equals("/user/forgotPassword") ||
		     requestUrl.equals("/user/getAll") ||
		     requestUrl.equals("/user/exportExcel")) 
			
				{
		          return null;
				}
           
		
		//remove queryString append to url i.e,, /url?pag=1..
		if(((FilterInvocation) object).getHttpRequest().getQueryString()!=null) {
            	requestUrl=requestUrl.replaceAll("\\?"+((FilterInvocation) object).getHttpRequest().getQueryString(), "");
            }
        
			
            
            //removing any parameter attached to url i.e,, /url/1..
            List<String> splitApi=new LinkedList<>(Arrays.asList(requestUrl.substring(1).split("/")));
             
                if(splitApi.size()>2) {
       	         splitApi.remove(2);
                  
              }
            String uri=splitApi.stream().map(f->String.valueOf(f)).collect(Collectors.joining("/","/", ""));

        return SecurityConfig.createList(uri);
    }
 
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
 
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }

}
