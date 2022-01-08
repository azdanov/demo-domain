package dev.azdanov.demodomain.entities.employee;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.Serializable;

public record Phone(String country, String code, String number) implements Serializable {

  public Phone {
    if (isBlank(country)) {
      throw new IllegalArgumentException("Country number is empty");
    }
    if (isBlank(code)) {
      throw new IllegalArgumentException("Code number is empty");
    }
    if (isBlank(number)) {
      throw new IllegalArgumentException("Phone number is empty");
    }
  }

  public String full() {
    return "+%s (%s) %s".formatted(country, code, number);
  }
}
