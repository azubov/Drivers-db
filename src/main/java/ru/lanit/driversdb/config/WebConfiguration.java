package ru.lanit.driversdb.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lanit.driversdb.filter.CountryFilter;

@Configuration
public class WebConfiguration {

    @Bean
    public FilterRegistrationBean<CountryFilter> countryFilter() {
        FilterRegistrationBean<CountryFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CountryFilter());
        registrationBean.addUrlPatterns("/db/*");
        return registrationBean;
    }
}
