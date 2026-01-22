package exceptions_personnalisees;
import java.lang.Exception;

public class FournisseurInexsistantException extends Exception
{
	public FournisseurInexsistantException()
	{
		super("Fournisseur inexsistant !");
	}

}
