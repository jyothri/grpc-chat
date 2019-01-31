package edu.jyo.app;

import edu.jyo.addressbook.protos.AddressBookProtos.AddressBook;
import edu.jyo.addressbook.protos.AddressBookProtos.Person;


public class Application {
    public static void main(String[] args) {
        System.out.println("Started Program");
        System.out.println("Completed Execution");
    }

    private void serializeAddressBook() {
        AddressBook.Builder addressBookBuilder = AddressBook.newBuilder();
        Person.Builder personBuilder = Person.newBuilder();
        personBuilder.setName("John");
        personBuilder.setEmail("john.doe@company.net");

        Person.PhoneNumber.Builder phoneBuilder = Person.PhoneNumber.newBuilder();
        phoneBuilder.setNumber("111.234.2324");
        phoneBuilder.setType(Person.PhoneType.HOME);

        personBuilder.setPhones(0, phoneBuilder.build());
        addressBookBuilder.addPeople(personBuilder.build());
        AddressBook addressBook = addressBookBuilder.build();
    }
}
