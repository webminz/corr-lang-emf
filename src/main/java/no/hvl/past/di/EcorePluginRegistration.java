package no.hvl.past.di;

import no.hvl.past.emf.EMFAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EcorePluginRegistration {


    @Bean
    public EMFAdapterFactory emfAdapterFactory() {
        return new EMFAdapterFactory();
    }

}
