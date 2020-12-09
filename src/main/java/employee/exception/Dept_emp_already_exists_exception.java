package employee.exception;

public class Dept_emp_already_exists_exception extends Exception{

    public Dept_emp_already_exists_exception(){

    }

    public Dept_emp_already_exists_exception(String message){
        super(message);
    }
	
}
