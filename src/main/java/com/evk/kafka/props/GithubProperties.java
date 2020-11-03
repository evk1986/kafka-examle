package com.evk.kafka.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.net.URI;
import java.net.URL;

@ConfigurationProperties(prefix = "github.api")
@ConstructorBinding
@Data
public class GithubProperties {

    public URI url;

}
