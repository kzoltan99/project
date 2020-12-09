package employee.exception;

public class Unknown_employee_exception extends Exception{

    public Unknown_employee_exception(){

    }

    public Unknown_employee_exception(String message){
        super(message);
    }
	
}
