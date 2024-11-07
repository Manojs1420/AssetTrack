package com.titan.irgs.security.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.titan.irgs.user.service.IUserService;




@Configuration
@EnableWebSecurity
public class WebConfiguration extends WebSecurityConfigurerAdapter {
	
	

	@Autowired
    IUserService UserService;
 
    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;
    
    @Autowired
    private AccessdeniedHandler accessDeniedHandler;
    
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;
    
    @Autowired
    private  UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
   
    @Autowired
    private  UrlAccessDecisionManager urlAccessDecisionManager;


 
    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }
   
    
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider);
        AuthenticationManager authenticationManager = new ProviderManager(providers);
        return  authenticationManager;
    }
    
    
   

    @Override
    protected void configure(AuthenticationManagerBuilder builder) {
        builder.parentAuthenticationManager(authenticationManagerBean());
    }
	 
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**").authorizeRequests();

        http.csrf().disable()
        		//.authorizeRequests()
                //.antMatchers("/user/signin","/user/login","/user/forgotpassword","/v2/api-docs", "/swagger-resources/**", "/configuration/security","/webjars/**","/swagger-ui.html").permitAll()
                //.antMatchers("**").permitAll()

                //.anyRequest().authenticated()
                //.and()
                // Adding this filter to capture ONLY login requests
                // Adding this filter to handle token based Authentication
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                // Going Stateless as Scaling could be a Concern//
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
            
		        registry.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
		            @Override
		            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
		                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
		                o.setAccessDecisionManager(urlAccessDecisionManager);
		                return o;
		            }
		        });
		       
		       //registry.antMatchers("/user/signin","/user/login","/user/forgotpassword","/v2/api-docs", "/swagger-resources/**", "/configuration/security","/webjars/**","/swagger-ui.html").permitAll();
		       registry.anyRequest().authenticated();

		       	http .exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
                http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        }
    
   
   
}
	
	
	

