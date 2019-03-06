package com.azimi.phonebook;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.ContactWithDetail;
import com.azimi.phonebook.database.DataRepository;
import com.azimi.phonebook.database.Email;
import com.azimi.phonebook.database.Phone;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {

    private DataRepository dataRepository;
    private LiveData<List<Contact>> allContacts;


    public ContactViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        allContacts = dataRepository.getAllContacts();
    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

//    public Contact getLastInsertedContact() {
//        return dataRepository.getLastInsertedContact();
//    }

    public void insert(Contact contact, List<Phone> phones, List<Email> emails) {
        dataRepository.insert(contact);
        for (Phone phone : phones) {
            if (!phone.getNumber().equals("")) {
                dataRepository.insert(phone);
            }
        }
        for (Email email : emails) {
            if (!email.getEmail().equals("")) {
                dataRepository.insert(email);
            }
        }
    }

    public void insert(Contact contact, Phone phone, Email email) {
        dataRepository.insert(contact);
        if (!phone.getNumber().equals("")) {
            dataRepository.insert(phone);
        }
        if (!email.getEmail().equals("")) {
            dataRepository.insert(email);
        }
    }

    public void insert(Contact contact) {
        dataRepository.insert(contact);
    }

    public void insert(Phone phone, Email email) {
        if (!phone.getNumber().equals("")) {
            dataRepository.insert(phone);
        }
        if (!email.getEmail().equals("")) {
            dataRepository.insert(email);
        }
    }

    public void delete(Contact contact){
        dataRepository.delete(contact);
    }

}
