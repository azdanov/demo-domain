package dev.azdanov.demodomain.entities.employee;

import java.io.Serializable;
import org.springframework.util.Assert;

public record Phone(String country, String code, String number) implements Serializable {

  public Phone {
    Assert.hasText(country, "Country number is empty");
    Assert.hasText(code, "Code number is empty");
    Assert.hasText(number, "Phone number is empty");
  }

  public String full() {
    return "+%s (%s) %s".formatted(country, code, number);
  }
}
