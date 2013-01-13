package pl.waw.mizinski.xmlproperties.parser;

import java.util.ArrayList;
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
import pl.waw.mizinski.xmlproperties.lexer.token.Prolog;
import pl.waw.mizinski.xmlproperties.lexer.token.Text;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;
import pl.waw.mizinski.xmlproperties.xml.XMLAttribute;
import pl.waw.mizinski.xmlproperties.xml.XMLAttributeImpl;
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
		try
		{
			this.tokens = tokens;
			parse();
		}
		catch (Exception e)
		{
			if(e.getClass().equals(XMLParseException.class))
			{
				throw e;
			}
			else
			{
				throw new XMLParseException(e);
			}
		}
	}

	public XMLFile getXMLFile()
	{
		return xmlFile;
	}

	private void parse() throws XMLParseException
	{
		parseDocument();
		checkEndOfDocument();
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
				popNextToken();
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
		if (!isNextTokenClassEquals(OpenEndTag.class))
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

	private Content parseContent() throws XMLParseException
	{
		Content content = new Content();
		List<XMLElement> elements = new ArrayList<XMLElement>();
		content.setElements(elements);
		if (isNextTokenClassEquals(Text.class))
		{
			content.setValue(popNextToken().getValue());
		}
		while(isNextTokenClassEquals(OpenStartTag.class))
		{
			elements.add(parseElement());
		}
		return content;
	}

	private List<XMLAttribute> parseAttributes() throws XMLParseException
	{
		List<XMLAttribute> attributes = new LinkedList<XMLAttribute>();
		while(isNextTokenClassEquals(AttributeName.class))
		{
			XMLAttribute xmlAttribute = new XMLAttributeImpl();
			xmlAttribute.setName(popNextToken().getValue());
			if(isNextTokenClassEquals(Equals.class))
			{
				popNextToken();
			}
			else
			{
				throw new XMLParseException(String.format("'=' is expected after attribute withe name: '%s'", xmlAttribute.getName()));
			}
			if(isNextTokenClassEquals(AttributeValue.class))
			{
				xmlAttribute.setValue(popNextToken().getValue());
			}
			else
			{
				throw new XMLParseException(String.format("Attribute value is expected for attribute with name '%s'",xmlAttribute.getName()));
			}
			attributes.add(xmlAttribute);
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
	
	private void checkEndOfDocument() throws XMLParseException
	{
		if((position<tokens.size()))
		{
			throw new XMLParseException("Invalid content found at and of document");
		}
	}
}
