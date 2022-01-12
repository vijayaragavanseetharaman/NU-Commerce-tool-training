package com.commercetools.config;

import io.sphere.sdk.client.BlockingSphereClient;
import io.sphere.sdk.client.SphereClient;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.client.SphereClientFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Configuration
public class CommercetoolsConfig {		

    @Bean(destroyMethod = "close")
    public BlockingSphereClient sphereClient(          
    		  @Value("${ct.projectKey}") final String projectKey,
              @Value("${ct.clientId}") final String clientId,
              @Value("${ct.clientSecret}") final String clientSecret,
              @Value("${ct.authUrl}") final String authUrl,
              @Value("${ct.apiUrl}") final String apiUrl
    ) throws IOException {
        final SphereClientConfig config = SphereClientConfig.of(projectKey, clientId, clientSecret, authUrl, apiUrl);
        final SphereClient asyncClient = SphereClientFactory.of().createClient(config);
        return BlockingSphereClient.of(asyncClient, 20, TimeUnit.SECONDS);
    }
}