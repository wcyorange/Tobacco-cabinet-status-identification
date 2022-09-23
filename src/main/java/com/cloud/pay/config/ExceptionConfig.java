package com.cloud.pay.config;

//import org.apache.jasper.EmbeddedServletOptions;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Set;


@Configuration
public class ExceptionConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> error(){
        WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer = new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {

            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/WEB-INF/page/400.jsp");
                ErrorPage errorPage401 = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/page/401.jsp");
                ErrorPage errorPage403 = new ErrorPage(HttpStatus.FORBIDDEN, "/WEB-INF/page/403.jsp");
                ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/page/404.jsp");
                ErrorPage errorPage405 = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/page/405.jsp");
                ErrorPage errorPage500 = new ErrorPage(HttpStatus.EXPECTATION_FAILED, "/ss2");
                ErrorPage errorPage501 = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/page/501.jsp");
                ErrorPage errorPage502 = new ErrorPage(HttpStatus.NOT_FOUND, "/WEB-INF/page/502.jsp");
                HashSet<ErrorPage> pages = new HashSet<>();
                pages.add(errorPage400);
                pages.add(errorPage401);
                pages.add(errorPage403);
                pages.add(errorPage404);
                pages.add(errorPage405);
                pages.add(errorPage500);
                pages.add(errorPage501);
                pages.add(errorPage502);
                factory.setErrorPages(pages);
            }
        };
        return webServerFactoryCustomizer;
    }
}
