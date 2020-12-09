package employee.exception;

public class UnknownTitleException extends Exception{

    public UnknownTitleException(){

    }

    public UnknownTitleException(String message){
        super(message);
    }
	
}
