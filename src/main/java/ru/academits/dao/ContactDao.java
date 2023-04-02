package ru.academits.dao;

import org.springframework.stereotype.Repository;
import ru.academits.model.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ContactDao {
    private List<Contact> contactList = new ArrayList<>();
    private AtomicInteger idSequence = new AtomicInteger(0);

    public ContactDao() {
        Contact contact = new Contact();
        contact.setId(getNewId());
        contact.setFirstName("Иван");
        contact.setLastName("Иванов");
        contact.setPhone("9123456789");
        contactList.add(contact);
    }

    private int getNewId() {
        return idSequence.addAndGet(1);
    }

    public List<Contact> getAllContacts() {
        return contactList;
    }

    public List<Contact> getContacts(String term) {
        if (term == null || term.equals("")) {
            return contactList;
        }

        String upperCaseTerm = term.toUpperCase();

        return contactList
                .stream()
                .filter(c -> c.getFirstName().toUpperCase().contains(upperCaseTerm)
                        || c.getLastName().toUpperCase().contains(upperCaseTerm)
                        || c.getPhone().toUpperCase().contains(upperCaseTerm))
                .collect(Collectors.toList());
    }

    public void add(Contact contact) {
        contact.setId(getNewId());
        contactList.add(contact);
    }

    public int delete(int[] ids) {
        int deletedContactsCount = 0;

        if (ids != null) {
            for (int id : ids) {
                for (int j = 0; j < contactList.size(); j++) {
                    if (Objects.equals(contactList.get(j).getId(), id)) {
                        contactList.remove(j);
                        deletedContactsCount++;

                        break;
                    }
                }
            }
        }

        return deletedContactsCount;
    }
}
