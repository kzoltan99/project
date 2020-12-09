package employee.exception;

public class UnknownDeptEmpException extends Exception{

    public UnknownDeptEmpException(){

    }

    public UnknownDeptEmpException(String message){
        super(message);
    }
	
}
