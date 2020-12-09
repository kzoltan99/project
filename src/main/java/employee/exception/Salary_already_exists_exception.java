package employee.exception;

public class Salary_already_exists_exception extends Exception{

    public Salary_already_exists_exception(){

    }

    public Salary_already_exists_exception(String message){
        super(message);
    }
	
}
