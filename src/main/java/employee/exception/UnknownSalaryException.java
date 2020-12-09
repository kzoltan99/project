package employee.exception;

public class UnknownSalaryException extends Exception{

    public UnknownSalaryException(){

    }

    public UnknownSalaryException(String message){
        super(message);
    }
	
}
