package employee.exception;

public class Employee_already_exists_exception extends Exception{

    public Employee_already_exists_exception(){

    }

    public Employee_already_exists_exception(String message){
        super(message);
    }
	
}
