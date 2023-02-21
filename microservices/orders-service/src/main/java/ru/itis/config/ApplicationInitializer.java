package ru.itis.config;

import lombok.SneakyThrows;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {

    private final static Long MAX_FILE_SIZE = 1024*1024*50L;
    private final static Long MAX_REQUEST_SIZE = 1024*1024*54L;

    @SneakyThrows
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext springWebContext = new AnnotationConfigWebApplicationContext();

        springWebContext.register(WebMvcConfig.class);
        springWebContext.register(ApplicationConfig.class);
        servletContext.addListener(new ContextLoaderListener(springWebContext));

        ServletRegistration.Dynamic dispatcherServlet =
                servletContext.addServlet("dispatcher", new DispatcherServlet(springWebContext));
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.setMultipartConfig(new MultipartConfigElement("",MAX_FILE_SIZE, MAX_REQUEST_SIZE, 0));
        dispatcherServlet.addMapping("/");
    }

}
