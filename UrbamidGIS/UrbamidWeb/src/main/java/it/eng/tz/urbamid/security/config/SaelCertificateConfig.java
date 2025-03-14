package it.eng.tz.urbamid.security.config;
import java.security.KeyStore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
public class SaelCertificateConfig {
    @Value("${urbamid.spid.jks.name}")
    private String keyStoreName;
    @Value("${urbamid.spid.jks.password}")
    private String keyStorePassword;
    @Value("${urbamid.spid.jks.type}")
    private String keyStoreType;
    @Value("${urbamid.spid.jks.alias}")
    private String keyStoreAlias;
    @Value("${urbamid.spid.jks.key.password}")
    private String keyStoreKeyPassword;
    
    @Bean(name = "urbamidKeyStore")
    public KeyStore urbamidKeyStore() throws Exception{
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        Resource ksRes = new ClassPathResource(keyStoreName);
        keyStore.load(ksRes.getInputStream(), keyStorePassword.toCharArray());
        return keyStore;
    }
}