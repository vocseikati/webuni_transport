package hu.katka.transport.configurations;

import java.time.Duration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "transport")
@Data
public class TransportConfigurationProperties {

  Reducing reducing = new Reducing();

  JwtData jwtData = new JwtData();

  @Data
  public static class Reducing {

    private Integer limit1;
    private Integer limit2;
    private Integer limit3;
    private Integer percent1;
    private Integer percent2;
    private Integer percent3;

  }

  @Data
  public static class JwtData {
    private String issuer;
    private String secret;
    private String alg;
    private Duration duration;
  }


}
