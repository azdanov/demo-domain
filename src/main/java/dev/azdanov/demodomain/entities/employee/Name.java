package dev.azdanov.demodomain.entities.employee;

import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.Serializable;
import java.util.List;
import org.springframework.util.Assert;

public record Name(String last, String first, String middle) implements Serializable {

  public Name(String last, String first, String middle) {
    Assert.hasText(last, "Last name is empty");
    Assert.hasText(first, "First name is empty");

    this.last = trimToEmpty(last);
    this.first = trimToEmpty(first);
    this.middle = trimToEmpty(middle);
  }

  public String full() {
    return join(List.of(first, middle, last), ' ');
  }
}
