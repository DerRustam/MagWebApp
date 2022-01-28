package com.rustam.magbackend.exception;

import com.rustam.magbackend.enums.DataConflictType;

public class DataServiceException extends RuntimeException{
    DataConflictType conflict;

    public DataServiceException(DataConflictType conflict, String message) {
        super(message);
        this.conflict = conflict;
    }

    public DataConflictType getConflict() {
        return conflict;
    }
}
