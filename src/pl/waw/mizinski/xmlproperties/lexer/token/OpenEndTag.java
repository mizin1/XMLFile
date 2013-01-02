package pl.waw.mizinski.xmlproperties.lexer.token;

public class OpenEndTag extends OpenTag
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
