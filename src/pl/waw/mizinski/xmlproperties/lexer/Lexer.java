package pl.waw.mizinski.xmlproperties.lexer;

import java.util.LinkedList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.XMLParseException;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Text;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;

public class Lexer
{
	private String text;

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
			else if (shouldAttributeValueExpect(nextChar))
			{
				expectAttributeValue();
			}
			else if (shouldAttributeNameExpect())
			{
				expectAttributeName();
			}
			else
			{
				expectText();
			}
		}
	}

	private boolean hasMoreLetters()
	{
		return position == text.length();
	}

	private boolean shouldAttributeNameExpect()
	{
		if (CloseTag.class.isInstance(getLastToken()))
		{
			return false;
		}
		return true;
	}

	private boolean shouldAttributeValueExpect(char c)
	{
		return c == '"' | c == '\'';
	}

	private void expectText()
	{
		// TODO zrob obsuge znakow z ampersantem
		StringBuilder value = new StringBuilder();
		while(getNextChar() != '<')
		{
			value.append(popNextChar());
		}
		Text text = new Text();
		text.setValue(value.toString());
		tokens.add(text);
	}

	private void expectAttributeName()
	{
		// TODO Auto-generated method stub

	}

	private void expectAttributeValue()
	{
		// TODO Auto-generated method stub

	}

	private void expectEquals()
	{
		// TODO Auto-generated method stub

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
}
