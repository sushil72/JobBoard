package com.JobBoardproject.JobBoard.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String invalidUsernameOrPassword){
        super(invalidUsernameOrPassword);
    }
}
