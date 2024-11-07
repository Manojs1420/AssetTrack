/*
  package com.titan.irgs.security.configuration;
  
  import java.io.IOException;
  
  import javax.servlet.Filter; import javax.servlet.FilterChain; import
  javax.servlet.FilterConfig; import javax.servlet.ServletException; import
  javax.servlet.ServletRequest; import javax.servlet.ServletResponse; import
  javax.servlet.http.HttpServletRequest; import
  javax.servlet.http.HttpServletResponse;
  
  import org.slf4j.Logger; import org.slf4j.LoggerFactory;
  
  public class CorsFilter implements Filter {
  
  Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Override public void init(FilterConfig filterConfig) throws ServletException
  { System.out.println("Init Called"); }
  
  @Override public void doFilter(ServletRequest req, ServletResponse
  res,FilterChain chain) throws IOException, ServletException {
  System.out.println("Request Came");
  
  HttpServletRequest request =(HttpServletRequest) req; HttpServletResponse
  response =(HttpServletResponse) res;
  
  response.setHeader("Access-Control-Allow-Origin", "*");
  response.setHeader("Access-Control-Allow-Methods","GET,POST,HEAD,DELETE,OPTIONS,PUT");
  response.setHeader("Access-Control-Max-Age", "3600");
  response.setHeader("Access-Control-Request-Headers","*");
  response.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,Bearer"); 
  response.setHeader("Access-Control-Expose-Headers","Access-Control-Allow-Origin,Access-Control-Allow-Credentials,Authorization,Date,Bearer");
  
//headers set by madhu
  response.setHeader("X-Frame-Options", "SAMEORIGIN");
  response.setHeader("X-content-type-options", "nosniff");
  response.setHeader("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains");
  response.setHeader("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
  response.setHeader("X-Xss-Protection","1; mode=block");
  response.setHeader("Referrer-Policy", "same-origin");
  
  if ("OPTIONS".equals(request.getMethod())) { try { response.flushBuffer(); }
  catch (IOException e) { logger.error(e.getLocalizedMessage(), e); throw e; }
  }
  
  else chain.doFilter(request, response); }
  
  @Override public void destroy() {
  
  }
  
  }
 */