package com.samuelbraga.authentication.configs.h2;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

@Configuration
public class WebConfiguration {

  @Bean
  ServletRegistrationBean<Servlet> h2servletRegistration() {
    ServletRegistrationBean<Servlet> registrationBean = new ServletRegistrationBean<>(
      new WebServlet()
    );
    registrationBean.addUrlMappings("/console/*");
    return registrationBean;
  }
}
