package employee.exception;

public class Title_already_exists_exception extends Exception{

    public Title_already_exists_exception(){

    }

    public Title_already_exists_exception(String message){
        super(message);
    }
	
}
