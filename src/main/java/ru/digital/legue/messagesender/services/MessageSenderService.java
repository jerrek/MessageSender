package ru.digital.legue.messagesender.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService implements MessageSender {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderService.class);

    @Override
    public Boolean sendMessage(String message, String to) {
        /*
            Implements you code here
         */
        LOG.debug("sendMessage " + message);
        LOG.debug("sendMessage " + to);
        return true;
    }
}
