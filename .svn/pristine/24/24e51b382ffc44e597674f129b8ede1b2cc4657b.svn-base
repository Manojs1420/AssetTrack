/*
 * package com.titan.irgs.security.configuration;
 * 
 * import java.util.List; import java.util.stream.Collectors; import
 * java.util.stream.Stream;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.web.cors.CorsConfiguration; import
 * org.springframework.web.cors.UrlBasedCorsConfigurationSource; import
 * org.springframework.web.filter.CorsFilter;
 * 
 * @Configuration public class ApplicationWebConfiguration {
 * 
 * private final static boolean ALLOW_CREDENTIALS = true;
 * 
 * @Bean public CorsFilter corsFilter() { final UrlBasedCorsConfigurationSource
 * source = new UrlBasedCorsConfigurationSource(); final CorsConfiguration
 * config = new CorsConfiguration();
 * config.setAllowedHeaders(this.getAllowedHeaders());
 * config.setAllowedOrigins(this.getAllowedOrigins());
 * config.setAllowedMethods(this.getAllowedMethods());
 * config.setExposedHeaders(this.getExposedHeaders());
 * config.setAllowCredentials(this.allowCredentials());
 * config.setMaxAge(this.getMaxAge()); source.registerCorsConfiguration("/**",
 * config); return new CorsFilter(source); }
 * 
 * Set the list of headers that a pre-flight request can list as allowed for use
 * during an actual request. The special value "*" allows actual requests to
 * send any header. A header name is not required to be listed if it is one of:
 * Cache-Control, Content-Language, Expires, Last-Modified, or Pragma. private
 * List<String> getAllowedHeaders() { return Stream.of("Content-Type\n" +
 * "X-Requested-With\n" + "accept\n" + "Origin\n" +
 * "Access-Control-Request-Method\n" + "Access-Control-Request-Headers\n" +
 * "Authorization\n" + "Bearer\n") .collect(Collectors.toList()); }
 * 
 * Set the origins to allow, e.g. "http://domain1.com". The special value "*"
 * allows all domains. private List<String> getAllowedOrigins() { return
 * Stream.of("*") .collect(Collectors.toList()); }
 * 
 * Set the HTTP methods to allow, e.g. "GET", "POST", "PUT", etc. The special
 * value "*" allows all methods. private List<String> getAllowedMethods() {
 * return Stream.of("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
 * .collect(Collectors.toList()); }
 * 
 * Set the list of response headers other than simple headers (i.e.
 * Cache-Control, Content-Language, Content-Type, Expires, Last-Modified, or
 * Pragma) that an actual response might have and can be exposed. Note that "*"
 * is not a valid exposed header value. private List<String> getExposedHeaders()
 * { return Stream.of("Access-Control-Allow-Origin\n" +
 * "Access-Control-Allow-Credentials\n" + "Authorization\n" + "Date\n" +
 * "Bearer\n") .collect(Collectors.toList()); }
 * 
 * //Whether user credentials are supported. private boolean allowCredentials()
 * { return ALLOW_CREDENTIALS; }
 * 
 * Configure how long, in seconds, the response from a pre-flight request can be
 * cached by clients. private Long getMaxAge() { return 3600L; }
 * 
 * 
 * }
 */