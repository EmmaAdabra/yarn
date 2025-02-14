package com.adb.chat_app.utils;

import com.adb.chat_app.exceptions.InputValidationException;

import javax.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInputs {
    public static String verifyEmail(String email) throws InputValidationException {

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

    public static String verifyName(String name) throws InputValidationException {
        String label = "Firstname or Lastname";

        if(name == null || name.isEmpty()){
            throw new InputValidationException(label + " can't be empty");
        }


        verifyNameLength(name, label);

        final String nameRegex = "^[a-zA-Z]{2,50}([ '-][a-zA-Z]{2,50})*$";
        var pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);

        if(!matcher.matches()){
            throw new InputValidationException(
                    "Name contains illegal character - only [space, letters, ',-] are allowed"
            );
        }
        return name;
    }

    public static String  verifyUsername(String username) throws InputValidationException{

        String label = "UserName";

        verifyNameLength(username, label);

        String usernameRegex = "^[a-zA-Z][a-zA-Z0-9.]{4,49}(?<!\\.)$";
        var pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);

        if(!matcher.matches()){
            throw new InputValidationException(
                    "Username contains invalid character - valid character - [letters, numbers, or .]"
            );
        }
        return username;
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

    public static boolean verifyConfirmPassword(
            String password, String confirmPassword
    ) throws InputValidationException{
        if(!password.equals(confirmPassword)){
            throw new InputValidationException("Confirm password not match password");
        }

        return true;
    }

    private static boolean verifyNameLength(String name, String label) throws InputValidationException {
//        throws InputValidationException error if name > 50
//        throws InputValidationException if name < 2

        final int MAX_NAME_LENGTH = 50;
        final int MIN_NAME_LENGTH = 2;

        if(name.length() < MIN_NAME_LENGTH){
            throw new InputValidationException(label + " too short - min length should be 2");
        }

        if(name.length() > MAX_NAME_LENGTH) {
            throw new InputValidationException(label + " too long - max length should be 50");
        }

        return true;
    }

    public static boolean validatePfpUpload(Part imagePart) throws InputValidationException{
        final int MAX_IMAGE_SIZE = 500 * 1024;
        if (imagePart == null || imagePart.getSize() == 0 || imagePart.getSubmittedFileName() == null) {
            throw new InputValidationException("No file uploaded");
        }

        if(imagePart.getSize() > MAX_IMAGE_SIZE) {
            throw new InputValidationException("File should not be greater than 500KB");
        }

        return true;
    }

    public static boolean validateUserBio(String bio) throws InputValidationException {
        if(bio.trim().length() > 166){
            throw new InputValidationException("User bio should not be greater than 166 characters");
        }

        return true;
    }

    public static boolean validatePostImage(Part postImage) throws InputValidationException {
//        ToDo: change size literal
        if(postImage.getSize() > 1024 * 500){
            throw new InputValidationException("Attached image should not be greater than 500kb");
        }

        if(!postImage.getContentType().startsWith("image/")){
            throw new InputValidationException("Invalid file type --- post attachment should only be image format");
        }
        return true;
    }
}
