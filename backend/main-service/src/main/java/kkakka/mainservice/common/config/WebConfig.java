package kkakka.mainservice.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class WebConfig {

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customizer(){
        return pageSizeConfig -> {
            pageSizeConfig.setOneIndexedParameters(true);
            pageSizeConfig.setMaxPageSize(2);
        };
    }
}
