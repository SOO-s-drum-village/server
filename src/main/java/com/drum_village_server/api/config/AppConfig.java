package com.drum_village_server.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "drum-village")
public class AppConfig {

  public Map<String, String> portOne;
}
