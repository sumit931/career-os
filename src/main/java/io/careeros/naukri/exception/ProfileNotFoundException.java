package io.careeros.naukri.exception;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(Long id) {
        super("Profile not found with id: " + id);
    }
}
