package io.corrlang.plugins;

import io.corrlang.emfintegration.EMFAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EcorePluginRegistration {


    @Bean
    public EMFAdapterFactory emfAdapterFactory() {
        return new EMFAdapterFactory();
    }

}
