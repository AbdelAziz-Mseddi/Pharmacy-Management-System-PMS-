package exceptions_personnalisees;

public class CommandeInexistanteException extends Exception 
{
    public CommandeInexistanteException(String message) 
    {
        super(message);
    }
}