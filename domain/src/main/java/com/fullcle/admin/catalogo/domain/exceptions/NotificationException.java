package com.fullcle.admin.catalogo.domain.exceptions;

import com.fullcle.admin.catalogo.domain.validation.Error;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;

import java.util.List;

public class NotificationException extends DomainException {

    public NotificationException(String aMessage, Notification notification) {
        super(aMessage, notification.getErrors());
    }

}
