package com.JobBoardproject.JobBoard.Exception;

public class ResourceNotFoundException extends Throwable {
    public ResourceNotFoundException(String noImageFound) {
        super(noImageFound);

    }
}
