@EnableConfigurationProperties(Properties.class)
@Configuration
public class SpringConfiguration {

  @Bean
  SampleBean sampleBean(Properties properties) {
    return new SampleBean(properties);
  }

}

