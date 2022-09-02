package com.fullcle.admin.catalogo.domain.exceptions;

import com.fullcle.admin.catalogo.domain.validation.Error;
import com.fullcle.admin.catalogo.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(String aMessage, Notification notification) {

        super(aMessage, notification.getErrors());
    }

    public Error firstError() {
        if (getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }

}
