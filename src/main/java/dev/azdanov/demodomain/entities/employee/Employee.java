package dev.azdanov.demodomain.entities.employee;

import dev.azdanov.demodomain.entities.employee.events.EmployeeAddressChanged;
import dev.azdanov.demodomain.entities.employee.events.EmployeeArchived;
import dev.azdanov.demodomain.entities.employee.events.EmployeeCreated;
import dev.azdanov.demodomain.entities.employee.events.EmployeePhoneAdded;
import dev.azdanov.demodomain.entities.employee.events.EmployeePhoneRemoved;
import dev.azdanov.demodomain.entities.employee.events.EmployeeReinstated;
import dev.azdanov.demodomain.entities.employee.events.EmployeeRemoved;
import dev.azdanov.demodomain.entities.employee.events.EmployeeRenamed;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

public class Employee implements EmployeeAggregateRoot {

  private final Id id;
  private final Phones phones;
  private final LocalDateTime createDate;
  private final ArrayDeque<Status> statuses = new ArrayDeque<>();
  private Name name;
  private Address address;

  public Employee(Id id, Name name, Address address, Phones phones, LocalDateTime createDate) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phones = phones;
    this.createDate = createDate;
    addStatus(StatusValue.ACTIVE, createDate);
    recordEvent(new EmployeeCreated(id, createDate));
  }

  public void rename(Name name) {
    this.name = name;
    this.recordEvent(new EmployeeRenamed(id, name));
  }

  public void changeAddress(Address address) {
    this.address = address;
    this.recordEvent(new EmployeeAddressChanged(id, address));
  }

  public void addPhone(Phone phone) {
    phones.add(phone);
    this.recordEvent(new EmployeePhoneAdded(id, phone));
  }

  public void removePhone(int index) {
    var phone = this.phones.remove(index);
    this.recordEvent(new EmployeePhoneRemoved(id, phone));
  }

  public void archive(LocalDateTime date) {
    if (this.isArchived()) {
      throw new IllegalStateException("Employee is already archived");
    }
    this.addStatus(StatusValue.ARCHIVED, date);
    this.recordEvent(new EmployeeArchived(id, date));
  }

  public void reinstate(LocalDateTime date) {
    if (!this.isArchived()) {
      throw new IllegalStateException("Employee is not archived");
    }
    this.addStatus(StatusValue.ACTIVE, date);
    this.recordEvent(new EmployeeReinstated(id, date));
  }

  public void remove() {
    if (!this.isArchived()) {
      throw new IllegalStateException("Cannot remove active employee");
    }
    this.recordEvent(new EmployeeRemoved(id));
  }

  public boolean isActive() {
    return this.currentStatus().isActive();
  }

  public boolean isArchived() {
    return this.currentStatus().isArchived();
  }

  private Status currentStatus() {
    return this.statuses.getLast();
  }

  private void addStatus(StatusValue status, LocalDateTime date) {
    this.statuses.add(new Status(status, date));
  }

  public Id id() {
    return id;
  }

  public Phones phones() {
    return phones;
  }

  public LocalDateTime createDate() {
    return createDate;
  }

  public Deque<Status> statuses() {
    return statuses.clone();
  }

  public Name name() {
    return name;
  }

  public Address address() {
    return address;
  }
}
