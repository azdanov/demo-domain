package dev.azdanov.demodomain.entities.employee;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.commons.lang3.SerializationUtils;

public class EmployeeBuilder implements Serializable {

  private final Deque<Status> statuses = new ArrayDeque<>();
  private final LocalDateTime createDate;
  private final Name name;
  private final Address address;

  private Id id;
  private Phone[] phones;
  private boolean archived = false;

  public EmployeeBuilder() {
    this.id = Id.next();
    this.createDate = LocalDateTime.now();
    this.name = new Name("Michael", "Marcus", "Salomon");
    this.address =
        new Address("USA", "Pennsylvania", "South Canaan", "Conference Center Way", "369");
    this.phones = new Phone[] {new Phone("1", "570", "9370315")};
  }

  public EmployeeBuilder withId(Id id) {
    var clone = SerializationUtils.clone(this);
    clone.id = id;
    return clone;
  }

  public EmployeeBuilder withPhones(Phone... phones) {
    var clone = SerializationUtils.clone(this);
    clone.phones = phones;
    return clone;
  }

  public EmployeeBuilder archived() {
    var clone = SerializationUtils.clone(this);
    clone.archived = true;
    return clone;
  }

  public Employee build() {
    var employee =
        new Employee(this.id, this.name, this.address, new Phones(this.phones), this.createDate);

    if (this.archived) {
      employee.archive(LocalDateTime.now());
    }
    return employee;
  }

}
