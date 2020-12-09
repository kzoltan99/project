package employee.exception;

public class UnknownDeptManagerException extends Exception{

    public UnknownDeptManagerException(){

    }

    public UnknownDeptManagerException(String message){
        super(message);
    }
	
}
