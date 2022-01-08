package dev.azdanov.demodomain.entities.employee.events;

import dev.azdanov.demodomain.entities.employee.Address;
import dev.azdanov.demodomain.entities.employee.Id;

public final class EmployeeAddressChanged extends EmployeeEvent {

  public final Address address;

  public EmployeeAddressChanged(Id id, Address address) {
    super(id);
    this.address = address;
  }
}
