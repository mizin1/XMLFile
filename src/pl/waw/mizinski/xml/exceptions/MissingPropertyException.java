package pl.waw.mizinski.xml.exceptions;

public class MissingPropertyException extends RuntimeException
{
	private static final long serialVersionUID = 5244831903773945250L;

	public MissingPropertyException()
	{
		super();
	}

	public MissingPropertyException(String message)
	{
		super(message);
	}

	public MissingPropertyException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public MissingPropertyException(Throwable cause)
	{
		super(cause);
	}
}
