package pl.waw.mizinski.xmlproperties.lexer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.XMLParseException;
import pl.waw.mizinski.xmlproperties.lexer.token.AttributeName;
import pl.waw.mizinski.xmlproperties.lexer.token.AttributeValue;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseEmptyElementTag;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Equals;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenEndTag;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenStartTag;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Prolog;
import pl.waw.mizinski.xmlproperties.lexer.token.Text;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;

public class Lexer
{
	private final String text;

	private List<Token> tokens;

	private int position = 0;

	public Lexer(String text) throws XMLParseException
	{
		try
		{
			this.text = text;
			tokenize();
		}
		catch (Exception e)
		{
			throw new XMLParseException(e);
		}
	}

	public List<Token> getTokens()
	{
		return tokens;
	}

	private void tokenize() throws XMLParseException
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
			else if (nextChar == '<')
			{
				expectOpenTag();
			}
			else if (nextChar == '>')
			{
				expectCloseTag();
			}
			else if (shouldExpectText())
			{
				expectText();
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
		return position < text.length();
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
		while (!isWhiteCharacter(getNextChar()) && getNextChar() != '=')
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
		while (getNextChar() != endingChar)
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
		popNextChar();
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setValue(value.toString());
		tokens.add(attributeValue);
	}

	private void expectEquals()
	{
		popNextChar();
		tokens.add(new Equals());
	}

	private void expectCloseEmptyElementTag() throws XMLParseException
	{
		popNextChar();
		if (getNextChar() == '>')
		{
			popNextChar();
			tokens.add(new CloseEmptyElementTag());
		}
		else
		{
			throw new XMLParseException(String.format("'>' character expected at position: '%d'", position));
		}
	}

	private void expectCloseTag()
	{
		popNextChar();
		tokens.add(new CloseTag());
	}

	
	private void expectOpenTag() throws XMLParseException
	{
		popNextChar();
		OpenTag openTag;
		if (getNextChar()=='/')
		{
			popNextChar();
			openTag = new OpenEndTag();
		}
		else
		{
			openTag = new OpenStartTag();
		}
		StringBuilder name = new StringBuilder();
		while (!isWhiteCharacter(getNextChar()) && getNextChar() != '/' && getNextChar() !='>' )
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
		checkName(name.toString());
		openTag.setValue(name.toString());
		tokens.add(openTag);
	}

	private void expectProlog() throws XMLParseException
	{
		char first = popNextChar();
		char second = popNextChar();
		if(!(first == '<' && second == '?'))
		{
			throw new XMLParseException("Prolog is expected at the begining of the document");
		}
		while(!(first == '?' && second == '>'))
		{
			first = second;
			second = popNextChar();
		}
		tokens.add(new Prolog());
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
		List<Character> whiteCharacters = Arrays.asList(' ', '\t', '\n', '\r'); 
		return whiteCharacters.contains(c);
	}

	private void processWhiteCharacter()
	{
		popNextChar();
	}

	private char processSpecialCharacter()
	{
		// TODO
		popNextChar();
		return '&';
	}
	
	private void checkName(String name) throws XMLParseException
	{
		if (name == null || name.equals(""))
		{
			throw new XMLParseException(String.format("Element name is expected at position: '%d'", position));
		}
	}
}
