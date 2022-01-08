package dev.azdanov.demodomain.entities.employee;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.Serializable;

public record Address(String country, String region, String city, String street, String house)
    implements Serializable {

  public Address {
    if (isBlank(country)) {
      throw new IllegalArgumentException("Country is empty");
    }
    if (isBlank(city)) {
      throw new IllegalArgumentException("City is empty");
    }
  }
}
