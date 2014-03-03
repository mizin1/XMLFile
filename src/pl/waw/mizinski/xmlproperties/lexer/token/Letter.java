package pl.waw.mizinski.xmlproperties.lexer.token;

public class Letter extends CharacterToken
{
	private char value;
	
	public Letter(char value)
	{
		this.value = value;
	}

	@Override
	public char getValue()
	{
		return value;
	}
}