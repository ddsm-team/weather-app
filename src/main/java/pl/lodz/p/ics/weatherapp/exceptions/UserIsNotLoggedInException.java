package pl.lodz.p.ics.weatherapp.exceptions;

public class UserIsNotLoggedInException extends Exception {

    public UserIsNotLoggedInException(String message) {
        super(message);
    }
}
