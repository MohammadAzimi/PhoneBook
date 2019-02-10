package com.azimi.phonebook.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class DataRepository {

    private ContactDao contactDao;
    private PhoneDao phoneDao;
    private EmailDao emailDao;

    private LiveData<List<Contact>> allContacts;
    //private LiveData<List<ContactWithDetail>> contactWithDetail;

    public DataRepository(Application application){
        AppDatabase database = AppDatabase.getDatabase(application);
        contactDao = database.contactDao();
        phoneDao = database.phoneDao();
        emailDao = database.emailDao();

        allContacts = contactDao.getAllContacts();

    }

    public LiveData<List<Contact>> getAllContacts(){
        return allContacts;
    }

    public LiveData<List<ContactWithDetail>> getContactDetails(String contactId){
        //this.contactWithDetail = contactDao.getContactsWithDetail(contact.getId());
        //return contactWithDetail;
        return contactDao.getContactsWithDetail(contactId);
    }

//    public Contact getLastInsertedContact(){
//        return contactDao.getLastContact();
//    }
    public void insert(Contact contact){
        new insertContactAsyncTask(contactDao).execute(contact);
    }

    public void insert(Phone phone){
        new insertPhoneAsyncTask(phoneDao).execute(phone);
    }

    public void insert(Email email){
        new insertEmailAsyncTask(emailDao).execute(email);
    }

    private static class insertContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        insertContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertPhoneAsyncTask extends AsyncTask<Phone, Void, Void> {

        private PhoneDao mAsyncTaskDao;

        insertPhoneAsyncTask(PhoneDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Phone... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertEmailAsyncTask extends AsyncTask<Email, Void, Void> {

        private EmailDao mAsyncTaskDao;

        insertEmailAsyncTask(EmailDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Email... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
