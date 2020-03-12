package ru.digital.legue.messagesender.services;

public interface MessageSender {
    Boolean sendMessage(String message, String to);
}
