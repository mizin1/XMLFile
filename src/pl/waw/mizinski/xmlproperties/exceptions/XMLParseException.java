package pl.waw.mizinski.xmlproperties.exceptions;

public class XMLParseException extends Exception {

	private static final long serialVersionUID = -5300989715539067363L;
	
	public XMLParseException()
	{
		super();
	}
	
	public XMLParseException(String message)
	{
		super(message);
	}
	
	public XMLParseException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
	public XMLParseException(Throwable cause)
	{
		super(cause);
	}
}
