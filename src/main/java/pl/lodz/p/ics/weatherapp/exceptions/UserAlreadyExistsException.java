package pl.lodz.p.ics.weatherapp.exceptions;

public class UserAlreadyExistsException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1122901656556979378L;

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
