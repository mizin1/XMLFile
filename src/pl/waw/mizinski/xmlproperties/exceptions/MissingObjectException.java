package pl.waw.mizinski.xmlproperties.exceptions;

public class MissingObjectException extends RuntimeException
{
	private static final long serialVersionUID = 5244831903773945250L;

	public MissingObjectException()
	{
		super();
	}

	public MissingObjectException(String message)
	{
		super(message);
	}

	public MissingObjectException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MissingObjectException(Throwable cause)
	{
		super(cause);
	}
}
