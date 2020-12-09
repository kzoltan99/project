package employee.exception;

public class UnknownDepartmentException extends Exception{

    public UnknownDepartmentException(){

    }

    public UnknownDepartmentException(String message){
        super(message);
    }
	
}
