package ru.academits.phonebook;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academits.model.Contact;
import ru.academits.model.ContactValidation;
import ru.academits.service.ContactService;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/phoneBook/rpc/api/v1")
public class PhoneBookController {
    private final Logger logger = LogManager.getLogger(PhoneBookController.class);

    private final ContactService contactService;

    public PhoneBookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "getAllContacts", method = RequestMethod.GET)
    @ResponseBody
    public List<Contact> getAllContacts() {
        try {
            logger.info("called method getAllContacts");
            return contactService.getAllContacts();
        } catch (Exception e) {
            logger.error("an error occurred in method getAllContacts: " + e.getMessage());

            return null;
        }
    }

    @RequestMapping(value = "getContacts", method = RequestMethod.POST)
    @ResponseBody
    public List<Contact> getContacts(@RequestBody(required = false) String term) {
        try {
            logger.info("called method getContacts(term = " + term + ")");
            return contactService.getContacts(term);
        } catch (Exception e) {
            logger.error("an error occurred in method getContacts: " + e.getMessage());

            return null;
        }
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    @ResponseBody
    public ContactValidation addContact(@RequestBody Contact contact) {
        try {
            logger.info("called method addContact(contact's phone = " + contact.getPhone() + ")");
            return contactService.addContact(contact);
        } catch (Exception e) {
            logger.error("an error occurred in method addContact: " + e.getMessage());

            ContactValidation contactValidation = new ContactValidation();
            contactValidation.setError(e.getMessage());

            return contactValidation;
        }
    }

    @RequestMapping(value = "deleteContacts", method = RequestMethod.POST)
    @ResponseBody
    public String deleteContacts(@RequestBody String idsToDeleteInString) {
        try {
            logger.info("called method deleteContacts(idsToDeleteInString = " + idsToDeleteInString + ")");

            int[] ids = null;

            if (idsToDeleteInString != null && !idsToDeleteInString.isEmpty()) {
                ids = Arrays.stream(idsToDeleteInString.split(",")).mapToInt(Integer::parseInt).toArray();
            }

            return contactService.deleteContacts(ids);
        } catch (Exception e) {
            logger.error("an error occurred in method deleteContacts: " + e.getMessage());

            return e.getMessage();
        }
    }
}