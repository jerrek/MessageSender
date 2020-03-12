package ru.digital.legue.messagesender.services;

import org.springframework.stereotype.Service;

@Service
public class MessageSenderService implements MessageSender {
    @Override
    public Boolean sendMessage(String message, String to) {
        /*
            Implements you code here
         */
        System.out.println("sendMessage " + message);
        System.out.println("sendMessage " + to);
        return true;
    }
}
