package employee.exception;

public class Unknown_salary_exception extends Exception{

    public Unknown_salary_exception(){

    }

    public Unknown_salary_exception(String message){
        super(message);
    }
	
}
