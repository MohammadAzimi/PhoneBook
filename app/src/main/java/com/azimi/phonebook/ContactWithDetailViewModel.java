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

public class ContactWithDetailViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<ContactWithDetail>> contact;

    public ContactWithDetailViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
//        this.contact = dataRepository.getContactDetails(contact);
    }

    public LiveData<List<ContactWithDetail>> getContact(String contactId) {
        this.contact = dataRepository.getContactDetails(contactId);
        return contact;
    }

    public void update(Contact contact, Phone phone, Email email) {
        dataRepository.update(contact);

        //check whether there is a phone to update
        if (!phone.getId().equals("")) {
            if (phone.getNumber().equals("")) {
                dataRepository.delete(phone);
            } else {
                dataRepository.update(phone);
            }
        } else {
            if (!phone.getNumber().equals("")){
                phone.setId(phone.getContactId()+phone.getNumber());
                dataRepository.insert(phone);
            }
        }

        //check whether there is an email to update
        if (!email.getId().equals("")) {
            if (email.getEmail().equals("")) {
                dataRepository.delete(email);
            } else {
                dataRepository.update(email);
            }
        } else {
            if (!email.getEmail().equals("")){
                email.setId(email.getContactId()+email.getEmail());
                dataRepository.insert(email);}
        }
    }

    public void deleteContact(Contact contact){
        dataRepository.delete(contact);
    }
}
