package ru.meinone.tokinoi_gallery_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;

@Configuration
public class ElasticConfig extends ElasticsearchConfiguration {
    private final String elasticsearchUrl;
    private final String elasticsearchUsername;
    private final String elasticsearchPassword;
    private final SslBundles sslBundles;

    public ElasticConfig(@Value("${spring.elasticsearch.uris}") String elasticsearchUrl
            , @Value("${spring.elasticsearch.username}") String elasticsearchUsername
            , @Value("${spring.elasticsearch.password}") String elasticsearchPassword
            , SslBundles sslBundles) {
        this.elasticsearchUrl = elasticsearchUrl;
        this.elasticsearchUsername = elasticsearchUsername;
        this.elasticsearchPassword = elasticsearchPassword;
        this.sslBundles = sslBundles;
    }

    @Override
    public ClientConfiguration clientConfiguration() {
        SslBundle sslBundle = sslBundles.getBundle("elasticsearch");
        SSLContext sslContext = sslBundle.createSslContext();
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .usingSsl(sslContext)
                .withBasicAuth(elasticsearchUsername, elasticsearchPassword)
                .build();
    }
}
