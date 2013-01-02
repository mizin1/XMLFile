package pl.waw.mizinski.xmlproperties.lexer.token;

public class OpenStartTag extends OpenTag
{
	private String value;
	
	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String getValue()
	{
		return value;
	}
}
