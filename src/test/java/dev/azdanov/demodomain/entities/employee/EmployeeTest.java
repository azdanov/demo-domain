package dev.azdanov.demodomain.entities.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import dev.azdanov.demodomain.entities.employee.events.EmployeeAddressChanged;
import dev.azdanov.demodomain.entities.employee.events.EmployeeArchived;
import dev.azdanov.demodomain.entities.employee.events.EmployeeCreated;
import dev.azdanov.demodomain.entities.employee.events.EmployeePhoneAdded;
import dev.azdanov.demodomain.entities.employee.events.EmployeePhoneRemoved;
import dev.azdanov.demodomain.entities.employee.events.EmployeeReinstated;
import dev.azdanov.demodomain.entities.employee.events.EmployeeRemoved;
import dev.azdanov.demodomain.entities.employee.events.EmployeeRenamed;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EmployeeTest {

  @Nested
  class ArchiveTest {

    @Test
    public void testSuccess() {
      var employee = (new EmployeeBuilder()).build();

      assertThat(employee.isActive()).isTrue();
      assertThat(employee.isArchived()).isFalse();

      var date = LocalDateTime.of(2011, 6, 15, 0, 0);
      employee.archive(date);

      assertThat(employee.isActive()).isFalse();
      assertThat(employee.isArchived()).isTrue();

      var statuses = employee.statuses();
      assertThat(statuses).isNotEmpty();
      assertThat(statuses.getLast().isArchived()).isTrue();

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeeArchived.class);
    }

    @Test
    public void testAlreadyArchived() {
      var employee = (new EmployeeBuilder()).archived().build();

      assertThatThrownBy(() -> employee.archive(LocalDateTime.now()))
          .hasMessage("Employee is already archived");
    }
  }

  @Nested
  class ChangeAddressTest {

    @Test
    public void testSuccess() {
      var employee = (new EmployeeBuilder()).build();

      var address = new Address("New", "Test", "Address", "Street", "25a");
      employee.changeAddress(address);
      assertThat(address).isEqualTo(employee.address());

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeeAddressChanged.class);
    }
  }

  @Nested
  class CreateTest {

    @Test
    public void testSuccess() {
      var id = Id.next();
      var date = LocalDateTime.now();
      var name = new Name("A", "B", "C");
      var phones = new Phones(new Phone("7", "920", "00000001"), new Phone("7", "910", "00000002"));
      var address = new Address("New", "Test", "Address", "Street", "25a");

      var employee = new Employee(id, name, address, phones, date);

      assertThat(id).isEqualTo(employee.id());
      assertThat(date).isEqualTo(employee.createDate());
      assertThat(name).isEqualTo(employee.name());
      assertThat(address).isEqualTo(employee.address());
      assertThat(phones).isEqualTo(employee.phones());

      assertThat(employee.createDate()).isNotNull();

      assertThat(employee.isActive()).isTrue();

      var statuses = employee.statuses();
      assertThat(statuses).hasSize(1);
      assertThat(statuses.getLast().isActive()).isTrue();

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeeCreated.class);
    }

    @Test
    public void testWithoutPhones() {
      assertThatThrownBy(
          () -> {
            var id = Id.next();
            var date = LocalDateTime.now();
            var name = new Name("A", "B", "C");
            var phones = new Phones();
            var address = new Address("New", "Test", "Address", "Street", "25a");

            new Employee(id, name, address, phones, date);
          })
          .hasMessage("Phones are empty");
    }

    @Test
    public void testWithSamePhoneNumbers() {
      assertThatThrownBy(
          () -> {
            var id = Id.next();
            var date = LocalDateTime.now();
            var name = new Name("A", "B", "C");
            var phones =
                new Phones(
                    new Phone("7", "920", "00000001"), new Phone("7", "920", "00000001"));
            var address = new Address("New", "Test", "Address", "Street", "25a");

            new Employee(id, name, address, phones, date);
          })
          .hasMessage("Duplicate phone found");
    }
  }

  @Nested
  class PhoneTest {

    @Test
    public void testAdd() {
      var employee = (new EmployeeBuilder()).build();

      var phone = new Phone("7", "888", "00000001");
      employee.addPhone(phone);

      var phones = employee.phones().getAll();
      assertThat(phones).isNotEmpty();
      assertThat(phones.get(phones.size() - 1)).isEqualTo(phone);

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeePhoneAdded.class);
    }

    @Test
    public void testAddExists() {
      var phone = new Phone("7", "888", "00000001");
      var employee = (new EmployeeBuilder()).withPhones(phone).build();

      assertThatThrownBy(() -> employee.addPhone(phone)).hasMessage("Duplicate phone found");
    }

    @Test
    public void testRemove() {
      var employee =
          (new EmployeeBuilder())
              .withPhones(new Phone("7", "888", "00000001"), new Phone("7", "888", "00000002"))
              .build();

      assertThat(employee.phones().getAll()).hasSize(2);

      employee.removePhone(1);

      assertThat(employee.phones().getAll()).hasSize(1);

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeePhoneRemoved.class);
    }

    @Test
    public void testRemoveNotExists() {
      var employee = (new EmployeeBuilder()).build();

      assertThatThrownBy(() -> employee.removePhone(42)).hasMessage("Phone was not found");
    }

    @Test
    public void testRemoveLast() {
      var employee = (new EmployeeBuilder()).withPhones(new Phone("7", "888", "00000001")).build();

      assertThatThrownBy(() -> employee.removePhone(0)).hasMessage("Cannot remove the last phone");
    }
  }

  @Nested
  class ReinstateTest {

    @Test
    public void testSuccess() {
      var employee = (new EmployeeBuilder()).archived().build();

      assertThat(employee.isActive()).isFalse();
      assertThat(employee.isArchived()).isTrue();

      var date = LocalDateTime.of(2011, 6, 15, 0, 0);
      employee.reinstate(date);

      assertThat(employee.isActive()).isTrue();
      assertThat(employee.isArchived()).isFalse();

      var statuses = employee.statuses();
      assertThat(statuses).isNotEmpty();
      assertThat(statuses.getLast().isActive()).isTrue();

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeeReinstated.class);
    }

    @Test
    public void testNotArchived() {
      var employee = (new EmployeeBuilder()).build();

      assertThatThrownBy(() -> employee.reinstate(LocalDateTime.of(2011, 6, 15, 0, 0)))
          .hasMessage("Employee is not archived");
    }
  }

  @Nested
  class RemoveTest {

    @Test
    public void testSuccess() {
      var employee = (new EmployeeBuilder()).archived().build();

      employee.remove();

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeeRemoved.class);
    }

    @Test
    public void testNotArchived() {
      var employee = (new EmployeeBuilder()).build();

      assertThatThrownBy(employee::remove).hasMessage("Cannot remove active employee");
    }
  }

  @Nested
  class RenameTest {

    @Test
    public void testSuccess() {
      var employee = (new EmployeeBuilder()).build();

      var name = new Name("New", "Test", "Name");
      employee.rename(name);
      assertThat(employee.name()).isEqualTo(name);

      var events = employee.releaseEvents();
      assertThat(events).isNotEmpty();
      assertThat(events.getLast()).isInstanceOf(EmployeeRenamed.class);
    }
  }
}
