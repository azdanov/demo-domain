package dev.azdanov.demodomain.entities.employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class Phones implements Serializable {

  private final List<Phone> phones = new ArrayList<>();

  public Phones(Phone... phones) {
    if (phones.length == 0) {
      throw new IllegalArgumentException("Phones are empty");
    }

    for (var phone : phones) {
      add(phone);
    }
  }

  public void add(Phone phone) {
    if (phones.contains(phone)) {
      throw new IllegalStateException("Duplicate phone found");
    }

    phones.add(phone);
  }

  public Phone remove(int index) {
    if (index < 0 || index >= phones.size()) {
      throw new RuntimeException("Phone was not found");
    }
    if (phones.size() == 1) {
      throw new IllegalStateException("Cannot remove the last phone");
    }

    return phones.remove(index);
  }

  public List<Phone> getAll() {
    return List.copyOf(phones);
  }
}
