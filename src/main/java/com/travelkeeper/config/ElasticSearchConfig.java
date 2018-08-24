package com.travelkeeper.config;

import org.apache.commons.lang3.math.NumberUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ElasticSearch configuration
 *
 * Created by netocris on 24/08/2018
 */
@Configuration
@EnableElasticsearchRepositories("com.travelkeeper.repository")
public class ElasticSearchConfig {

    private static final String CLUSTER_NAME = "elasticsearch.clustername";
    private static final String HOST = "elasticsearch.host";
    private static final String PORT = "elasticsearch.port";

    private final Environment env;

    public ElasticSearchConfig(final Environment env) {
        this.env = env;
    }

    @Bean
    public Client buildClient() throws UnknownHostException {

        final Settings settings = Settings.builder()
                .put("cluster.name", this.env.getProperty(CLUSTER_NAME))
                .build();

        return new PreBuiltTransportClient(settings)
                .addTransportAddress(
                        new InetSocketTransportAddress(
                                InetAddress.getByName(this.env.getProperty(HOST)),
                                NumberUtils.toInt(this.env.getProperty(PORT)))
                );

    }

    public ElasticsearchOperations buildTemplate() throws UnknownHostException {
        return new ElasticsearchTemplate(buildClient());
    }

}
