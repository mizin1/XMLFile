package pl.waw.mizinski.xmlproperties.exceptions;

public class InvalidXMLContentExcepcion extends Exception
{
	private static final long serialVersionUID = 3793001535436539425L;
	
	public InvalidXMLContentExcepcion()
	{
		super();
	}

	public InvalidXMLContentExcepcion(String message)
	{
		super(message);
	}

	public InvalidXMLContentExcepcion(String message, Throwable cause)
	{
		super(message, cause);
	}

	public InvalidXMLContentExcepcion(Throwable cause)
	{
		super(cause);
	}

}
