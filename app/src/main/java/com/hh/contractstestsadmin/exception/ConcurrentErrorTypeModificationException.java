package com.hh.contractstestsadmin.exception;

import java.util.ConcurrentModificationException;

public class ConcurrentErrorTypeModificationException extends ConcurrentModificationException {
    public ConcurrentErrorTypeModificationException(String s) {
        super(s);
    }
}
