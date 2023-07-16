package com.extension.findyourmeme.generic.enums;

import com.extension.findyourmeme.generic.exceptions.BaseErrorMessage;

public enum ErrorMessage implements BaseErrorMessage{
    ITEM_NOT_FOUND("Item not found!"),
    ITEM_IS_EXIST("Image is exists in db."),
    INVALID_REQUEST_PARAMETERS("Invalid request parameters"),
    TAG_MIN_LENGTH("Tag must be at least 3 characters long."),
    FILE_IS_NOT_IMAGE("File is not image."),
    IMAGE_NOT_FOUND_BY_TAG("Image not found by given tag."),
    PARAMETER_CANNOT_BE_NULL("Parameter(s) cannot be null."),
    USERNAME_ALREADY_EXISTS("Username already exists."),
    PASSWORD_NOT_STRONG_ENOUGH("Password must be at least 8 characters long, contain at least one digit, one lower case letter, one upper case letter and one special character."),
    USER_NOT_ADMIN("User is not admin. Only admin can login."),
    USERNAME_OR_PASSWORD_INCORRECT("Username or password incorrect."),
    IMAGE_NOT_FOUND("Image not found with given id."),
    USER_NOT_FOUND("User not found with given id."),
    ;
    private String message;
    ErrorMessage(String message){
        this.message = message;
    }
    @Override
    public String getMessage() {
        return null;
    }
    @Override
    public String toString(){
        return message;
    }
}
