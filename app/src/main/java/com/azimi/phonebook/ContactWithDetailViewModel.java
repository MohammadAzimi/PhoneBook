package com.azimi.phonebook;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.azimi.phonebook.database.Contact;
import com.azimi.phonebook.database.ContactWithDetail;
import com.azimi.phonebook.database.DataRepository;

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

}
