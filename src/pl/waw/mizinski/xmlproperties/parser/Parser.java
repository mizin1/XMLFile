package pl.waw.mizinski.xmlproperties.parser;

import java.util.LinkedList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.XMLParseException;
import pl.waw.mizinski.xmlproperties.lexer.token.AttributeName;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseEmptyElementTag;
import pl.waw.mizinski.xmlproperties.lexer.token.CloseTag;
import pl.waw.mizinski.xmlproperties.lexer.token.OpenStartTag;
import pl.waw.mizinski.xmlproperties.lexer.token.Prolog;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;
import pl.waw.mizinski.xmlproperties.xml.XMLAttribute;
import pl.waw.mizinski.xmlproperties.xml.XMLElement;
import pl.waw.mizinski.xmlproperties.xml.XMLElementImpl;
import pl.waw.mizinski.xmlproperties.xml.XMLFile;
import pl.waw.mizinski.xmlproperties.xml.XMLFileImpl;

public class Parser
{
	private List<Token> tokens;

	int position = 0;

	private XMLFile xmlFile;

	public Parser(List<Token> tokens) throws XMLParseException
	{
		this.tokens = tokens;
		parse();
	}

	public XMLFile getXMLFile()
	{
		return xmlFile;
	}

	private void parse() throws XMLParseException
	{
		parseDocument();
	}

	private void parseDocument() throws XMLParseException
	{
		parseProlog();
		XMLElement rootElement = parseElement();
		XMLFileImpl file = new XMLFileImpl();
		file.setRootElement(rootElement);
		xmlFile = file;
	}

	private XMLElement parseElement() throws XMLParseException
	{
		XMLElementImpl xmlElement = new XMLElementImpl();
		if (isNextTokenClassEquals(OpenStartTag.class))
		{
			String name = popNextToken().getValue();
			xmlElement.setName(name);
			List<XMLAttribute> attributes = parseAttributes();
			xmlElement.setAttributes(attributes);
			if (isNextTokenClassEquals(CloseEmptyElementTag.class))
			{
				popNextToken();
			}
			else if (isNextTokenClassEquals(CloseTag.class))
			{
				Content content = parseContent();
				xmlElement.setValue(content.getValue());
				xmlElement.setChildElements(content.getElements());
				checkEndTag(xmlElement.getName());
			}
			else
			{
				throw new XMLParseException(String.format("Tag with name: '%s' must end with '>' character"));
			}
		}
		else
		{
			throw new XMLParseException("The XML element is expected");
		}

		return xmlElement;
	}

	private void checkEndTag(String name) throws XMLParseException
	{
		boolean correct = true;
		if (!isNextTokenClassEquals(OpenStartTag.class))
		{
			correct = false;
		}
		if (!popNextToken().getValue().equals(name))
		{
			correct = false;
		}
		if (isNextTokenClassEquals(CloseTag.class))
		{
			popNextToken();
		}
		else
		{
			correct = false;
		}
		if(!correct)
		{
			throw new XMLParseException(String.format("Element with name: '%s' must end with </%s> tag.", name, name));
		}
	}

	private Content parseContent()
	{
		// TODO Auto-generated method stub
		return null;
	}

	private List<XMLAttribute> parseAttributes()
	{
		List<XMLAttribute> attributes = new LinkedList<XMLAttribute>();
		while(isNextTokenClassEquals(AttributeName.class))
		{
			
		}
		
		return attributes;
	}

	private void parseProlog() throws XMLParseException
	{
		
		if (isNextTokenClassEquals(Prolog.class)){
			popNextToken();
		}
		else
		{
			throw new XMLParseException("Prolog is expected at the begining of the document");
		}
	}

	private Token getNextToken()
	{
		return tokens.get(position);
	}

	private Token popNextToken()
	{
		return tokens.get(position++);
	}

	private boolean hasMoreTokens()
	{
		return position < tokens.size();
	}

	private boolean isNextTokenClassEquals(Class<?> clazz)
	{
		return getNextToken().getClass().equals(clazz);
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
