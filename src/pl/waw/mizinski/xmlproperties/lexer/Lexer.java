package pl.waw.mizinski.xmlproperties.lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import pl.waw.mizinski.xmlproperties.lexer.token.*;

public class Lexer
{
	private InputStream inputStream;
	private int nextChar;
	private Token nextToken;

	public Lexer(File file) throws IOException
	{
		this.inputStream = new FileInputStream(file);
		this.nextChar = inputStream.read();
	}

	public void skipWhiteSpaces() throws IOException
	{
		if (nextToken != null && nextToken.getClass().equals(WhiteSpace.class))
		{
			nextToken = null;
		}
		if (nextToken == null)
		{
			while (isWhiteCharacter(getNextChar()))
			{
				popNextChar();
			}
		}
	}

	public Token getNextToken() throws IOException
	{
		if (nextToken == null)
		{
			nextToken = createNextToken();
		}
		return nextToken;
	}

	public Token popNextToken() throws IOException
	{
		if (nextToken == null)
		{
			return createNextToken();
		}
		else
		{
			Token ret = nextToken;
			nextToken = null;
			return ret;
		}

	}

	private Token createNextToken() throws IOException
	{
		if (!hasNextChar())
			return null;
		char next = popNextChar();

		if (next == '<')
		{
			if (getNextChar() == '/')
			{
				popNextChar();
				return new OpenEndTag();
			}
			else if (getNextChar() == '?')
			{
				popNextChar();
				return new PrologStart();
			}
			else
			{
				return new OpenStartTag();
			}
		}
		else if (next == '>')
		{
			return new CloseTag();
		}
		else if (next == '/' && getNextChar() == '>')
		{
			popNextChar();
			return new CloseEmptyElementTag();
		}
		else if (next == '?' && getNextChar() == '>')
		{
			popNextChar();
			return new PrologEnd();
		}
		else if (next == '=')
		{
			return new Equals();
		}
		else if (next == '"')
		{
			return new QuotationMark();
		}
		else if (isWhiteCharacter(next))
		{
			return new WhiteSpace();
		}
		else
		{
			return new Letter(next);
		}
	}

	private char getNextChar()
	{
		return (char) nextChar;
	}

	private char popNextChar() throws IOException
	{
		char tmp = (char) nextChar;
		nextChar = inputStream.read();
		return tmp;
	}

	private boolean hasNextChar()
	{
		return nextChar != -1;
	}

	public static boolean isWhiteCharacter(char c)
	{
		List<Character> whiteCharacters = Arrays.asList(' ', '\t', '\n', '\r');
		return whiteCharacters.contains(c);
	}
}
