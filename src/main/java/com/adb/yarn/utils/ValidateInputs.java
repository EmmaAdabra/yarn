package com.adb.yarn.utils;

import com.adb.yarn.exceptions.InputValidationException;

import javax.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInputs {
    public static String verifyEmail(String email) throws InputValidationException {
        email = email.trim();

        if(email == null || email.isEmpty()){
            throw new InputValidationException("Email can't be empty");
        }

        final String emailFormat = "example@hostname.server";
        final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        var pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        if(!matcher.matches()) {
            throw new InputValidationException("Invalid email format - valid format: " + emailFormat);
        }

        return email;
    }

    public static void verifyName(String name) throws InputValidationException {
        name = name.trim();
        String label = "Firstname or Lastname";

        if(name == null || name.isEmpty()){
            throw new InputValidationException(label + " can't be empty");
        }


        verifyNameLength(name, label);

        final String nameRegex = "^[a-zA-Z]{1,50}([ '-][a-zA-Z]{1,50})*$";
        var pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);

        if(!matcher.matches()){
            throw new InputValidationException(
                    "Name contains illegal character - only [space, letters, ',-] are allowed"
            );
        }
    }

    public static void  verifyUsername(String username) throws InputValidationException{

        String label = "UserName";
        username = username.trim();

        verifyNameLength(username, label);

        String usernameRegex = "^[a-zA-Z][a-zA-Z0-9.]{4,49}(?<!\\.)$";
        var pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);

        if(username!= null && !matcher.matches()){
            throw new InputValidationException(
                    "Username contains invalid character - valid character - [letters, numbers, or .]"
            );
        }
    }



    public static String verifyPassword(String password) throws InputValidationException{
        final int MIN_PASSWORD_LENGTH = 4;
        final int MAX_PASSWORD_LENGTH = 16;

        if(password == null || password.isEmpty()){
            throw new InputValidationException("Password can't be empty");
        }

        if(password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new InputValidationException(
                    "Password too short or too long (password length - Min: 8, Max: 18)"
            );
        }
        return password;
    }

    public static void verifyConfirmPassword(
            String password, String confirmPassword
    ) throws InputValidationException{
        if(!password.equals(confirmPassword)){
            throw new InputValidationException("Confirm password not match password");
        }
    }

    private static void verifyNameLength(String name, String label) throws InputValidationException {

        final int MAX_NAME_LENGTH = 50;
        final int MIN_NAME_LENGTH = 2;

        if(name.length() < MIN_NAME_LENGTH){
            throw new InputValidationException(label + " too short - min length should be 2");
        }

        if(name.length() > MAX_NAME_LENGTH) {
            throw new InputValidationException(label + " too long - max length should be 50");
        }
    }

    public static void validatePfpUpload(Part imagePart) throws InputValidationException{
        final int MAX_IMAGE_SIZE = 500 * 1024;
        if (imagePart == null || imagePart.getSize() == 0 || imagePart.getSubmittedFileName() == null) {
            throw new InputValidationException("No file uploaded");
        }

        if(imagePart.getSize() > MAX_IMAGE_SIZE) {
            throw new InputValidationException("File should not be greater than 500KB");
        }
    }

    public static void validateUserBio(String bio) throws InputValidationException {
        if(bio.trim().length() > 166){
            throw new InputValidationException("User bio should not be greater than 166 characters");
        }
    }

    public static void validatePostImage(Part postImage) throws InputValidationException {
//        ToDo: change size literal
        if(postImage.getSize() > 1024 * 500){
            throw new InputValidationException("Attached image should not be greater than 500kb");
        }

        if(!postImage.getContentType().startsWith("image/")){
            throw new InputValidationException("Invalid file type --- post attachment should only be image format");
        }
    }

    public static void validateComment(String comment) throws InputValidationException{
        if(comment == null || comment.trim().isEmpty()){
            throw new InputValidationException("Comment can't be empty");
        }
    }

    public static void validateQueryParam(Object ... values) throws InputValidationException{
        for (Object value : values) {
            if (value == null) {
                throw new InputValidationException("Query parameters can't be null");
            }

            // Handle String objects
            if (value instanceof String) {
                String strValue = (String) value;
                if (strValue.isEmpty() || strValue.isBlank()) {
                    throw new InputValidationException("Query parameters can't be empty or blank");
                }
            }

        }
    }
}
