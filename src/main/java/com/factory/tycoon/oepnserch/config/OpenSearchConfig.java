package com.factory.tycoon.oepnserch.config;

import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.aws.AwsSdk2Transport;
import org.opensearch.client.transport.aws.AwsSdk2TransportOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;

@Configuration
public class OpenSearchConfig {

    @Value("${opensearch.endpoint}")
    private String openSearchEndpoint;

    @Value("${opensearch.region}")
    private String region;

    @Bean
    public OpenSearchClient openSearchClient() {
        AwsSdk2TransportOptions transportOptions = AwsSdk2TransportOptions.builder()
                .setCredentials(DefaultCredentialsProvider.create())
                .build();

        return new OpenSearchClient(
                new AwsSdk2Transport(
                        ApacheHttpClient.builder().build(),
                        openSearchEndpoint,
                        Region.of(region),
                        transportOptions
                )
        );
    }
}
