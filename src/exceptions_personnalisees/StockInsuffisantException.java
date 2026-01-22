package exceptions_personnalisees;

public class StockInsuffisantException extends Exception 
{
    public StockInsuffisantException(String message)
    {
        super(message);
    }
}