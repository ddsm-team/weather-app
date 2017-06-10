package pl.lodz.p.ics.weatherapp.exceptions;

public class UserIsNotLoggedInException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = -7976118279619831628L;

    public UserIsNotLoggedInException(String message){
        super(message);
    }
}
