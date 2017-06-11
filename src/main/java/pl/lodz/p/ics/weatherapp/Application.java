package pl.lodz.p.ics.weatherapp;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;

import com.lyncode.jtwig.mvc.JtwigViewResolver;

import pl.lodz.p.ics.weatherapp.services.UserService;
import pl.lodz.p.ics.weatherapp.services.WeatherService;

@SpringBootApplication
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableTransactionManagement
public class Application {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US"));
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ViewResolver viewResolver() {
        JtwigViewResolver viewResolver = new JtwigViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".twig");
        return viewResolver;
    }

    @Bean
    public UserService getUserService() {
        return this.userService;
    }

    @Bean
    public WeatherService getWeatherService() {
        return this.weatherService;
    }
}
