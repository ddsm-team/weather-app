package pl.lodz.p.ics.weatherapp.exceptions;

public class BadCredentialsException extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 2145424532824102702L;
    
    public BadCredentialsException(String message){
        super(message);
    }

}
