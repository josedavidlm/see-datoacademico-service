package pe.com.cayetano.see.dataacademico.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(ignoreResourceNotFound = true, value = "classpath:messages/messagestransactions.properties")
public class ConfigMessageProperty {
    @Autowired
    private Environment env;

    public String getMessage(String pPropertyKey) {
        return env.getProperty(pPropertyKey);
    }
}
