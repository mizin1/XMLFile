package pl.waw.mizinski.xmlproperties.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.XMLParseException;
import pl.waw.mizinski.xmlproperties.lexer.Lexer;
import pl.waw.mizinski.xmlproperties.lexer.token.CharacterToken;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseEmptyElementTag;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Equals;
import pl.waw.mizinski.xmlproperties.lexer.token.Letter;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenEndTag;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenStartTag;
import pl.waw.mizinski.xmlproperties.lexer.token.PrologEnd;
import pl.waw.mizinski.xmlproperties.lexer.token.PrologStart;
import pl.waw.mizinski.xmlproperties.lexer.token.QuotationMark;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;
import pl.waw.mizinski.xmlproperties.lexer.token.WhiteSpace;
import pl.waw.mizinski.xmlproperties.xml.XMLAttribute;
import pl.waw.mizinski.xmlproperties.xml.XMLAttributeImpl;
import pl.waw.mizinski.xmlproperties.xml.XMLElement;
import pl.waw.mizinski.xmlproperties.xml.XMLElementImpl;
import pl.waw.mizinski.xmlproperties.xml.XMLFile;
import pl.waw.mizinski.xmlproperties.xml.XMLFileImpl;

public class Parser
{
	Lexer lexer;

	XMLFile xmlFile;

	public Parser(Lexer lexer)
	{
		super();
		this.lexer = lexer;
	}

	public XMLFile parseXMLFile() throws IOException, XMLParseException
	{
		if (xmlFile == null)
		{
			xmlFile = parseDocument();
			checkEndOfDocument();
		}
		return xmlFile;
	}

	private void checkEndOfDocument() throws IOException, XMLParseException
	{
		lexer.skipWhiteSpaces();
		if ((lexer.popNextToken() != null))
		{
			throw new XMLParseException("Invalid content found at the and of document");
		}

	}

	private XMLFile parseDocument() throws IOException, XMLParseException
	{
		parseProlog();
		skipWhiteSpaces();
		XMLElement rootElement = parseElement();
		XMLFileImpl xmlFile = new XMLFileImpl();
		xmlFile.setRootElement(rootElement);
		return xmlFile;
	}

	private XMLElement parseElement() throws IOException, XMLParseException
	{
		XMLElementImpl xmlElement = new XMLElementImpl();
		Token token = lexer.popNextToken();
		if (token.getClass().equals(OpenStartTag.class))
		{
			String name = parseText();
			xmlElement.setName(name);
			skipWhiteSpaces();
			List<XMLAttribute> attributes = parseAttributes();
			xmlElement.setAttributes(attributes);
			token = lexer.popNextToken();
			if (token.getClass().equals(CloseEmptyElementTag.class))
			{
				lexer.skipWhiteSpaces();
			}
			else if (token.getClass().equals(CloseTag.class))
			{
				Content content = parseContent();
				xmlElement.setValue(content.getValue());
				xmlElement.setChildElements(content.getElements());
				checkEndTag(xmlElement.getName());
			}
			else
			{
				throw new XMLParseException(String.format("Tag with name: '%s' must end with '>' character",
						xmlElement.getName()));
			}

		}
		else
		{
			throw new XMLParseException("< is expected");
		}

		return xmlElement;
	}

	private void checkEndTag(String name) throws IOException, XMLParseException
	{
		boolean correct = true;
		Token token = lexer.popNextToken();
		if (!token.getClass().equals((OpenEndTag.class)))
		{
			correct = false;
		}
		String actualName = parseText();
		if (!name.equals(actualName))
		{
			correct = false;
		}
		skipWhiteSpaces();
		token = lexer.popNextToken();
		if (!token.getClass().equals((CloseTag.class)))
		{
			correct = false;
		}
		if (!correct)
		{
			throw new XMLParseException(String.format("Element with name: '%s' must end with </%s> tag.", name, name));
		}
	}

	private Content parseContent() throws IOException, XMLParseException
	{
		skipWhiteSpaces();
		Content content = new Content();
		List<XMLElement> elements = new ArrayList<XMLElement>();
		content.setElements(elements);
		Token token = lexer.getNextToken();
		if (token.getClass().equals(Letter.class))
		{
			String textWithWhiteSpaces = parseTextWithWhiteSpaces();
			content.setValue(textWithWhiteSpaces);
		}
		else if (token.getClass().equals(OpenStartTag.class))
		{
			while (token.getClass().equals(OpenStartTag.class))
			{
				elements.add(parseElement());
				skipWhiteSpaces();
				token = lexer.getNextToken();
			}
		}

		return content;
	}

	private String parseTextWithWhiteSpaces() throws IOException, XMLParseException
	{
		skipWhiteSpaces();
		StringBuilder builder = new StringBuilder();
		Token token = lexer.getNextToken();
		while (token.getClass().equals(Letter.class) || token.getClass().equals(WhiteSpace.class))
		{
			char c = ((CharacterToken) token).getValue();
			if (c == '&')
			{
				c = parseSpecialCharacter();
				builder.append(c);
			}
			else
			{
				builder.append(c);
				lexer.popNextToken();
			}
			token = lexer.getNextToken();
		}
		while (Lexer.isWhiteCharacter(builder.charAt(builder.length() - 1)) && builder.length() > 0)
		{
			builder.deleteCharAt(builder.length() - 1);
		}
		if (builder.length() == 0)
		{
			throw new XMLParseException("Text length can not be 0");
		}
		return builder.toString();
	}

	private char parseSpecialCharacter() throws IOException, XMLParseException
	{
		lexer.popNextToken();
		Token token1 = lexer.popNextToken();
		Token token2 = lexer.popNextToken();
		Token token3 = lexer.popNextToken();
		Token token4 = null;
		Token token5 = null;
		if (token1.getClass().equals(Letter.class) && token2.getClass().equals(Letter.class)
				&& token3.getClass().equals(Letter.class))
		{
			String str = "&" + ((Letter) token1).getValue() + ((Letter) token2).getValue()
					+ ((Letter) token3).getValue();
			if (str.equals("&lt;"))
			{
				return '<';
			}
			if (str.equals("&gt;"))
			{
				return '>';
			}
			else if (str.equals("&amp"))
			{
				token4 = lexer.popNextToken();
				if (token4.getClass().equals(Letter.class))
				{
					str = str + ((Letter) token4).getValue();
				}
				if (str.equals("&amp;"))
				{
					return '&';
				}
			}
			else if(str.equals("&quo"))
			{
				token4 = lexer.popNextToken();
				token5 = lexer.popNextToken();
				if (token4.getClass().equals(Letter.class) && token5.getClass().equals(Letter.class))
				{
					str = str + ((Letter) token4).getValue() + ((Letter) token5).getValue();
				}
				if (str.equals("&quot;"))
				{
					return '"';
				}
			}
			else if(str.equals("&apo"))
			{
				token4 = lexer.popNextToken();
				token5 = lexer.popNextToken();
				if (token4.getClass().equals(Letter.class) && token5.getClass().equals(Letter.class))
				{
					str = str + ((Letter) token4).getValue() + ((Letter) token5).getValue();
				}
				if (str.equals("&apos;"))
				{
					return '\'';
				}
			}
		}

		throw new XMLParseException("Ivalid content found after '&' character");

	}

	private List<XMLAttribute> parseAttributes() throws IOException, XMLParseException
	{
		List<XMLAttribute> attributes = new ArrayList<XMLAttribute>();
		while (lexer.getNextToken().getClass().equals(Letter.class))
		{
			attributes.add(parseAttribute());
		}
		return attributes;
	}

	private XMLAttribute parseAttribute() throws IOException, XMLParseException
	{
		XMLAttribute attribute = new XMLAttributeImpl();
		String name = parseText();
		attribute.setName(name);
		skipWhiteSpaces();
		if (!lexer.popNextToken().getClass().equals(Equals.class))
		{
			throw new XMLParseException(String.format("'=' is expected after attribute with name: '%s'", name));
		}
		skipWhiteSpaces();
		if (!lexer.popNextToken().getClass().equals(QuotationMark.class))
		{
			throw new XMLParseException("'\"' is expected after '='");
		}
		skipWhiteSpaces();
		String value = parseTextWithWhiteSpaces();
		attribute.setValue(value);
		if (!lexer.popNextToken().getClass().equals(QuotationMark.class))
		{
			throw new XMLParseException("'\"' is expected after '='");
		}
		skipWhiteSpaces();
		return attribute;
	}

	private String parseText() throws IOException, XMLParseException
	{
		skipWhiteSpaces();
		StringBuilder builder = new StringBuilder();
		Token token = lexer.getNextToken();
		while (token.getClass().equals(Letter.class))
		{
			char c = ((CharacterToken) token).getValue();
			builder.append(c);
			lexer.popNextToken();
			token = lexer.getNextToken();
		}
		while (builder.length() > 0 && Lexer.isWhiteCharacter(builder.charAt(builder.length() - 1)))
		{
			builder.deleteCharAt(builder.length() - 1);
		}
		if (builder.length() == 0)
		{
			throw new XMLParseException("Text length can not be 0");
		}
		return builder.toString();
	}

	private void skipWhiteSpaces() throws IOException
	{
		lexer.skipWhiteSpaces();

	}

	private void parseProlog() throws XMLParseException, IOException
	{
		if (lexer.popNextToken().getClass().equals(PrologStart.class))
		{
			skipWhiteSpaces();
			String name = parseText();
			if (!name.equals("xml"))
			{
				throw new XMLParseException("Prolog is expected at the begining of the document");
			}
			skipWhiteSpaces();
			parseAttributes();
			if (!lexer.popNextToken().getClass().equals(PrologEnd.class))
			{
				throw new XMLParseException("Prolog is expected at the begining of the document");
			}
		}
		else
		{
			throw new XMLParseException("Prolog is expected at the begining of the document");
		}
	}

	private class Content
	{
		private String value;

		private List<XMLElement> elements;

		public String getValue()
		{
			return value;
		}

		public void setValue(String value)
		{
			this.value = value;
		}

		public List<XMLElement> getElements()
		{
			return elements;
		}

		public void setElements(List<XMLElement> elements)
		{
			this.elements = elements;
		}
	}
}
