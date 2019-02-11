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

    public DataRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        contactDao = database.contactDao();
        phoneDao = database.phoneDao();
        emailDao = database.emailDao();

        allContacts = contactDao.getAllContacts();

    }

    public LiveData<List<Contact>> getAllContacts() {
        return allContacts;
    }

    public LiveData<List<ContactWithDetail>> getContactDetails(String contactId) {
        //this.contactWithDetail = contactDao.getContactsWithDetail(contact.getId());
        //return contactWithDetail;
        return contactDao.getContactsWithDetail(contactId);
    }

    //    public Contact getLastInsertedContact(){
//        return contactDao.getLastContact();
//    }

    public boolean isPhoneAvailable(String phoneID) {
        return phoneDao.getPhoneById(phoneID) != null;
    }

    public boolean isEmailAvailable(String emailID) {
        return emailDao.getEmailById(emailID) != null;
    }

    public void insert(Contact contact) {
        new insertContactAsyncTask(contactDao).execute(contact);
    }

    public void insert(Phone phone) {
        new insertPhoneAsyncTask(phoneDao).execute(phone);
    }

    public void insert(Email email) {
        new insertEmailAsyncTask(emailDao).execute(email);
    }

    public void update(Contact contact) {
        new updateContactAsyncTask(contactDao).execute(contact);
    }

    public void update(Phone phone) {
        new updatePhoneAsyncTask(phoneDao).execute(phone);
    }

    public void update(Email email) {
        new updateEmailAsyncTask(emailDao).execute(email);
    }

    public void delete(Contact contact) {
        new deleteContactAsyncTask(contactDao).execute(contact);
    }

    public void delete(Phone phone) {
        new deletePhoneAsyncTask(phoneDao).execute(phone);
    }

    public void delete(Email email) {
        new deleteEmailAsyncTask(emailDao).execute(email);
    }


    //insert Async
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


    //update Async
    private static class updateContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        updateContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class updatePhoneAsyncTask extends AsyncTask<Phone, Void, Void> {

        private PhoneDao mAsyncTaskDao;

        updatePhoneAsyncTask(PhoneDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Phone... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    private static class updateEmailAsyncTask extends AsyncTask<Email, Void, Void> {

        private EmailDao mAsyncTaskDao;

        updateEmailAsyncTask(EmailDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Email... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }


    //delete Async
    private static class deleteContactAsyncTask extends AsyncTask<Contact, Void, Void> {

        private ContactDao mAsyncTaskDao;

        deleteContactAsyncTask(ContactDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Contact... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deletePhoneAsyncTask extends AsyncTask<Phone, Void, Void> {

        private PhoneDao mAsyncTaskDao;

        deletePhoneAsyncTask(PhoneDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Phone... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteEmailAsyncTask extends AsyncTask<Email, Void, Void> {

        private EmailDao mAsyncTaskDao;

        deleteEmailAsyncTask(EmailDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Email... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
