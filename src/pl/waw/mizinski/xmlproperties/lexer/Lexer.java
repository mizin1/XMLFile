package pl.waw.mizinski.xmlproperties.lexer;

import java.util.LinkedList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.XMLParseException;
import pl.waw.mizinski.xmlproperties.lexer.token.AttributeName;
import pl.waw.mizinski.xmlproperties.lexer.token.AttributeValue;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Equals;
import pl.waw.mizinski.xmlproperties.lexer.token.Text;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;

public class Lexer
{
	private final String text;

	private List<Token> tokens;

	private int position = 0;

	public Lexer(String text) throws XMLParseException
	{
		this.text = text;
		tokenize();
	}

	public List<Token> getTokens()
	{
		return tokens;
	}

	private void tokenize()
	{
		tokens = new LinkedList<Token>();
		expectProlog();
		while (hasMoreLetters())
		{
			char nextChar = getNextChar();
			if (isWhiteCharacter(nextChar))
			{
				processWhiteCharacter();
			}
			else if (shouldExpectText())
			{
				expectText();
			}
			else if (nextChar == '<')
			{
				expectOpenTag();
			}
			else if (nextChar == '>')
			{
				expectCloseTag();
			}
			else if (nextChar == '/')
			{
				expectCloseEmptyElementTag();
			}
			else if (nextChar == '=')
			{
				expectEquals();
			}
			else if (shouldExpectAttributeValue(nextChar))
			{
				expectAttributeValue();
			}
			else
			{
				expectAttributeName();
			}
		}
	}

	private boolean hasMoreLetters()
	{
		return position == text.length();
	}

	private boolean shouldExpectText()
	{
		return CloseTag.class.isInstance(getLastToken());
	}

	private boolean shouldExpectAttributeValue(char c)
	{
		return c == '"' | c == '\'';
	}

	private void expectText()
	{
		StringBuilder value = new StringBuilder();
		while (getNextChar() != '<')
		{
			if (getNextChar() == '&')
			{
				value.append(processSpecialCharacter());
			}
			else
			{
				value.append(popNextChar());
			}
		}
		Text text = new Text();
		text.setValue(value.toString());
		tokens.add(text);
	}

	private void expectAttributeName()
	{
		// TODO mozesz dorobic inna obsluge pierwszego znaku w nazwie
		StringBuilder name = new StringBuilder();
		char nextChar = getNextChar();
		while (!isWhiteCharacter(nextChar) && nextChar != '=')
		{
			if (getNextChar() == '&')
			{
				name.append(processSpecialCharacter());
			}
			else
			{
				name.append(popNextChar());
			}
		}
		AttributeName attributeName = new AttributeName();
		attributeName.setValue(name.toString());
		tokens.add(attributeName);
	}

	private void expectAttributeValue()
	{
		StringBuilder value = new StringBuilder();
		char endingChar = popNextChar();
		char nextChar = getNextChar();
		while (nextChar != endingChar)
		{
			if (getNextChar() == '&')
			{
				value.append(processSpecialCharacter());
			}
			else
			{
				value.append(popNextChar());
			}
		}
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setValue(value.toString());
		tokens.add(attributeValue);
	}

	private void expectEquals()
	{
		popNextChar();
		tokens.add(new Equals());
	}

	private void expectCloseEmptyElementTag()
	{
		// TODO Auto-generated method stub

	}

	private void expectCloseTag()
	{
		// TODO Auto-generated method stub

	}

	// powinna wywolac expectOpen Start/End tag
	private void expectOpenTag()
	{
		// TODO Auto-generated method stub

	}

	private void expectProlog()
	{
		// TODO Auto-generated method stub

	}

	private char getNextChar()
	{
		return text.charAt(position);
	}

	private char popNextChar()
	{
		return text.charAt(position++);
	}

	private Token getLastToken()
	{
		if (tokens.isEmpty())
		{
			return null;
		}
		int size = tokens.size();
		return tokens.get(size - 1);
	}

	private static boolean isWhiteCharacter(char c)
	{
		// TODO
		return c == ' ';
	}

	private void processWhiteCharacter()
	{
		// TODO Auto-generated method stub

	}

	private char processSpecialCharacter()
	{
		// TODO
		return '<';
	}
}
