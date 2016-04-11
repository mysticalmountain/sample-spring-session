package com.sample.spring.config;

import com.sample.spring.filter.SessionFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * Created by andongxu on 16-3-29.
 */
public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
        webApplicationContext.register(AppConfig.class);
        webApplicationContext.register(WebConfig.class);

        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
        servletRegistration.addMapping("/");

        SessionFilter sessionFilter = new SessionFilter();
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("sessionFilter", sessionFilter);
        EnumSet es = EnumSet.noneOf(DispatcherType.class);
        es.add(DispatcherType.REQUEST);
//        es.add(DispatcherType.FORWARD);
        filterRegistration.addMappingForUrlPatterns(es, true, "/*");
//        filterRegistration.addMa

    }
}
