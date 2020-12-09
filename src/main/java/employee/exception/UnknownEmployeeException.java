package employee.exception;

public class UnknownEmployeeException extends Exception{

    public UnknownEmployeeException(){

    }

    public UnknownEmployeeException(String message){
        super(message);
    }
	
}
