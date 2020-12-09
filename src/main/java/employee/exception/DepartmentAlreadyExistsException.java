package employee.exception;

public class DepartmentAlreadyExistsException extends Exception{

    public DepartmentAlreadyExistsException(){

    }

    public DepartmentAlreadyExistsException(String message){
        super(message);
    }
	
}
