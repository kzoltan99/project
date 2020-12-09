package employee.exception;

public class Department_already_exists_exception extends Exception{

    public Department_already_exists_exception(){

    }

    public Department_already_exists_exception(String message){
        super(message);
    }
	
}
