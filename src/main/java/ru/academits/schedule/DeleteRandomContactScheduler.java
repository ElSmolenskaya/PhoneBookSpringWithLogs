package ru.academits.schedule;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.academits.service.ContactService;

@EnableAutoConfiguration
@EnableScheduling
@Component
public class DeleteRandomContactScheduler {
    private final ContactService contactService;

    private final Logger logger = LogManager.getLogger(DeleteRandomContactScheduler.class);

    public DeleteRandomContactScheduler(ContactService contactService) {
        this.contactService = contactService;
    }

    @Scheduled(fixedRate = 10000)
    public void deleteRandomContact() {
        try {
            int deletedContactId = contactService.deleteRandomContact();

            if (deletedContactId >= 0) {
                logger.info("Random contact has been deleted. Deleted contact's id = " + deletedContactId);
            }
        } catch (Exception e) {
            logger.error("an error occurred in scheduler method deleteRandomContact: " + e.getMessage());
        }
    }
}