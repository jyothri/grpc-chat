package edu.jyo.app.serviceimpl;

import edu.jyo.addressbook.protos.AddressBookProtos;
import edu.jyo.addressbook.protos.AddressBookProtos.AddressBook;
import edu.jyo.addressbook.protos.AddressBookServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressBookService extends AddressBookServiceGrpc.AddressBookServiceImplBase {

    private static final Logger logger = Logger.getLogger(AddressBookService.class.getName());

    private AddressBook.Builder addressBookBuilder;

    public AddressBookService() {
        addressBookBuilder = AddressBook.newBuilder();
    }

    @Override
    public void addPerson(edu.jyo.addressbook.protos.AddressBookProtos.Person person,
                          io.grpc.stub.StreamObserver<edu.jyo.addressbook.protos.AddressBookProtos.Person> responseObserver) {
        logger.info("received request for addPerson");
        addressBookBuilder.addPeople(person);
        responseObserver.onNext(person);
        responseObserver.onCompleted();
        logger.info("completed service of addPerson");
    }

    @Override
    public void writeToDisk(AddressBookProtos.DiskStoreRequest request,
                            StreamObserver<AddressBookProtos.DiskStoreResponse> responseObserver) {
        logger.info("received request for writeToDisk");
        // Write the new address book back to disk.
        FileOutputStream output = null;
        try {
            output = new FileOutputStream("addressBook.ser");
            addressBookBuilder.build().writeTo(output);
            output.close();
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "Error writing addressBook to file", e);
            System.err.println(e.getMessage());
        }
        responseObserver.onCompleted();
        logger.info("completed service of writeToDisk");
    }

    @Override
    public void readFromDisk(AddressBookProtos.DiskReadRequest request,
                             StreamObserver<AddressBookProtos.DiskReadResponse> responseObserver) {
        logger.info("received request of readFromDisk");
        // Write the new address book back to disk.
        FileInputStream input = null;
        try {
            input = new FileInputStream("addressBook.ser");
            addressBookBuilder.mergeFrom(input);
            input.close();
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "Error reading addressBook from file", e);
            System.err.println(e.getMessage());
        }
        responseObserver.onCompleted();
        logger.info("completed service of readFromDisk");
    }
}
