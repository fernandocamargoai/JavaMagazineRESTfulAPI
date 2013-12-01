package br.com.devmedia.javamagazine.restfulapi.rest.config;

import br.com.devmedia.javamagazine.restfulapi.rest.error.DefaultExceptionMapper;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.ws.rs.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

@Configuration
@ComponentScan("br.com.devmedia.javamagazine.restfulapi.rest.controller")
@ImportResource({"classpath:META-INF/cxf/cxf.xml"})
public class JaxRsServerConfig {

    @Autowired
    private ApplicationContext ctx;

    @Bean
    public Server jaxRsServer(){
        List<ResourceProvider> resourceProviders = new LinkedList<ResourceProvider>();
        for (String beanName : ctx.getBeanDefinitionNames()) {
            if (ctx.findAnnotationOnBean(beanName, Path.class) != null) {
                SpringResourceFactory resourceFactory = new SpringResourceFactory(beanName);
                resourceFactory.setApplicationContext(ctx);
                resourceProviders.add(resourceFactory);
            }
        }

        JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setBus(ctx.getBean(SpringBus.class));

        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        ObjectMapper objectMapper = new ObjectMapper();
        //set up ISO 8601 date/time stamp format:
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        objectMapper.setDateFormat(df);
        jacksonJsonProvider.setMapper(objectMapper);

        factory.setProviders(Arrays.asList(jacksonJsonProvider, new DefaultExceptionMapper()));
        factory.setResourceProviders(resourceProviders);

        return factory.create();
    }

}
