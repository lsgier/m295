@Validated
@ConfigurationProperties(prefix = "example")
public class Properties {

  private final String sampleProperty;
  private final int sampleNumber;
  @Pattern(regexp = "^\\+41 7[0-9] [0-9]{3} [0-9]{2} [0-9]{2}$")
  private final String phoneNumber;

  public Properties(String sampleProperty, int sampleNumber, String phoneNumber) {
    this.sampleProperty = sampleProperty;
    this.sampleNumber = sampleNumber;
    this.phoneNumber = phoneNumber;
  }

  public String getSampleProperty() {
    return sampleProperty;
  }

  public int getSampleNumber() {
    return sampleNumber;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }
}
