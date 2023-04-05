package ru.academits;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactDaoTest {
    private ContactDao contactDao;
    private static final int startTestContactsCount = 10;
    private static final int startBaseContactsCount = 1;
    private static final int startContactsCount = startTestContactsCount + startBaseContactsCount;

    @Before
    public void setUpContactsData() {
        contactDao = new ContactDao();

        for (int i = 1; i <= startTestContactsCount; i++) {
            Contact contact = new Contact();
            contact.setFirstName("FirstName" + i);
            contact.setLastName("LastName" + i);
            contact.setPhone("Phone" + i);

            contactDao.add(contact);
        }
    }

    @Test
    public void testAdd() {
        Contact newContact = new Contact();

        newContact.setFirstName("NewFirstName");
        newContact.setLastName("NewLastName");
        newContact.setPhone("NewPhone");

        contactDao.add(newContact);

        List<Contact> result = contactDao.getAllContacts();

        int actualSize = result.size();
        int expectedSize = startContactsCount + 1;

        assertNotNull(contactDao.getAllContacts());
        assertEquals(expectedSize, actualSize);
        assertEquals(newContact.getPhone(), result.get(result.size() - 1).getPhone());
    }

    @Test
    public void testGetAllContacts() {
        List<Contact> result = contactDao.getAllContacts();

        assertNotNull(result);
        assertEquals(startContactsCount, result.size());
    }

    @Test
    public void testGetContacts() {
        String term = "phone2";

        List<Contact> result = contactDao.getContacts(term);

        int expectedSize = 1;
        int actualSize = result.size();
        String expectedPhone = "Phone2";

        assertNotNull(result);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedPhone, result.get(0).getPhone());
    }

    @Test
    public void testDelete() {
        List<Contact> contacts = new ArrayList<>(contactDao.getAllContacts());

        int actualDeletedContactsCount = contactDao.delete(new int[]{contacts.get(0).getId(), contacts.get(1).getId()});
        int expectedDeletedContactsCount = 2;

        contacts.remove(0);
        contacts.remove(0);

        int actualSize = contactDao.getAllContacts().size();
        int expectedSize = startContactsCount - 2;

        assertEquals(expectedDeletedContactsCount, actualDeletedContactsCount);
        assertEquals(expectedSize, actualSize);
        assertArrayEquals(contacts.toArray(), contactDao.getAllContacts().toArray());

        actualDeletedContactsCount = contactDao.delete(new int[]{});
        expectedDeletedContactsCount = 0;
        actualSize = contactDao.getAllContacts().size();

        assertEquals(expectedDeletedContactsCount, actualDeletedContactsCount);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testDeleteRandomContact() {
        contactDao.deleteRandomContact();

        int actualSize = contactDao.getAllContacts().size();
        int expectedSize = startContactsCount - 1;

        assertEquals(expectedSize, actualSize);
    }
}