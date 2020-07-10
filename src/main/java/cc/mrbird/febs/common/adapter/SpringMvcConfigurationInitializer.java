package cc.mrbird.febs.common.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringMvcConfigurationInitializer extends WebMvcConfigurerAdapter {

    @Value("${image.path.upload}")
    private String realpath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/uploadfile/**").addResourceLocations("file:" + realpath);
        super.addResourceHandlers(registry);
    }
}
