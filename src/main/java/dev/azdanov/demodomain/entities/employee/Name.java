package dev.azdanov.demodomain.entities.employee;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.join;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import java.io.Serializable;
import java.util.List;

public record Name(String last, String first, String middle) implements Serializable {

  public Name(String last, String first, String middle) {
    if (isBlank(last)) {
      throw new IllegalArgumentException("Last name is empty");
    }
    if (isBlank(first)) {
      throw new IllegalArgumentException("First name is empty");
    }

    this.last = trimToEmpty(last);
    this.first = trimToEmpty(first);
    this.middle = trimToEmpty(middle);
  }

  public String full() {
    return join(List.of(first, middle, last), ' ');
  }
}
