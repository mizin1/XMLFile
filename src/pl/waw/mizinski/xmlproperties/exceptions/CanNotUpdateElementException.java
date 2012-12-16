package pl.waw.mizinski.xmlproperties.exceptions;

public class CanNotUpdateElementException extends RuntimeException
{
	private static final long serialVersionUID = 8092586330885334135L;

	public CanNotUpdateElementException()
	{
		super();
	}

	public CanNotUpdateElementException(String message)
	{
		super(message);
	}

	public CanNotUpdateElementException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CanNotUpdateElementException(Throwable cause)
	{
		super(cause);
	}
}
