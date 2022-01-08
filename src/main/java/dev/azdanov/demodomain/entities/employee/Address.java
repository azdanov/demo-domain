package dev.azdanov.demodomain.entities.employee;

import java.io.Serializable;
import org.springframework.util.Assert;

public record Address(String country, String region, String city, String street, String house)
    implements Serializable {

  public Address {
    Assert.hasText(country, "Country is empty");
    Assert.hasText(city, "City is empty");
  }
}
