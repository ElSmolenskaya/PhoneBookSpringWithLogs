package ru.academits;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.academits.dao.ContactDao;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceTest {
    private ContactService contactService;
    private static final int startTestContactsCount = 4;
    private static final int startBaseContactsCount = 1;
    private static final int startContactsCount = startTestContactsCount + startBaseContactsCount;

    @Before
    public void setUpContactsData() {
        contactService = new ContactService(new ContactDao());

        for (int i = 1; i <= startTestContactsCount; i++) {
            Contact contact = new Contact();
            contact.setFirstName("FirstName" + i);
            contact.setLastName("LastName" + i);
            contact.setPhone("Phone" + i);

            contactService.addContact(contact);
        }
    }

    @Test
    public void testAddContact() {
        Contact newContact = new Contact();

        newContact.setFirstName("NewFirstName");
        newContact.setLastName("NewLastName");
        newContact.setPhone("NewPhone");

        ContactValidation contactValidation = contactService.addContact(newContact);

        List<Contact> result = contactService.getAllContacts();

        int actualSize = result.size();
        int expectedSize = startContactsCount + 1;

        assertTrue(contactValidation.isValid());
        assertNotNull(result);
        assertEquals(expectedSize, actualSize);
        assertEquals(newContact.getPhone(), result.get(result.size() - 1).getPhone());

        Contact existsContact = new Contact();

        existsContact.setFirstName("NewFirstName1");
        existsContact.setLastName("NewLastName1");
        existsContact.setPhone(newContact.getPhone());

        contactValidation = contactService.addContact(existsContact);
        actualSize = contactService.getAllContacts().size();

        assertFalse(contactValidation.isValid());
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testGetAllContacts() {
        List<Contact> result = contactService.getAllContacts();

        assertNotNull(result);
        assertEquals(startContactsCount, result.size());
    }

    @Test
    public void testGetContacts() {
        String term = "phone2";

        List<Contact> result = contactService.getContacts(term);

        int expectedSize = 1;
        int actualSize = result.size();
        String expectedPhone = "Phone2";

        assertNotNull(result);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedPhone, result.get(0).getPhone());
    }

    @Test
    public void testDeleteContacts() {
        List<Contact> contacts = new ArrayList<>(contactService.getAllContacts());

        String actualDeletingContactsInformation = contactService.deleteContacts(new int[]{contacts.get(0).getId(), contacts.get(1).getId()});
        String expectedDeletingContactsInformation = "Количество удаленных контактов: 2";

        contacts.remove(0);
        contacts.remove(0);

        int actualSize = contactService.getAllContacts().size();
        int expectedSize = startContactsCount - 2;

        assertEquals(expectedDeletingContactsInformation, actualDeletingContactsInformation);
        assertEquals(expectedSize, actualSize);
        assertArrayEquals(contacts.toArray(), contactService.getAllContacts().toArray());

        actualDeletingContactsInformation = contactService.deleteContacts(new int[]{});
        expectedDeletingContactsInformation = "Ни один контакт не был удален";
        actualSize = contactService.getAllContacts().size();

        assertEquals(expectedDeletingContactsInformation, actualDeletingContactsInformation);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void testDeleteRandomContact() {
        contactService.deleteRandomContact();

        int actualSize = contactService.getAllContacts().size();
        int expectedSize = startContactsCount - 1;

        assertEquals(expectedSize, actualSize);
    }
}