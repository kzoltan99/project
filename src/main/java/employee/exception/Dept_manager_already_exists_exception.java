package employee.exception;

public class Dept_manager_already_exists_exception extends Exception{

    public Dept_manager_already_exists_exception(){

    }

    public Dept_manager_already_exists_exception(String message){
        super(message);
    }
	
}
