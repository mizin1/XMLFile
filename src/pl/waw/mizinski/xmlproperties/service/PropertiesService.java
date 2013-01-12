package pl.waw.mizinski.xmlproperties.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pl.waw.mizinski.xmlproperties.exceptions.InvalidXMLContentExcepcion;
import pl.waw.mizinski.xmlproperties.exceptions.XMLParseException;
import pl.waw.mizinski.xmlproperties.lexer.Lexer;
import pl.waw.mizinski.xmlproperties.lexer.token.Token;
import pl.waw.mizinski.xmlproperties.parser.Parser;
import pl.waw.mizinski.xmlproperties.properties.PropertiesFile;
import pl.waw.mizinski.xmlproperties.properties.PropertiesFileImpl;
import pl.waw.mizinski.xmlproperties.properties.Property;
import pl.waw.mizinski.xmlproperties.properties.PropertyImpl;
import pl.waw.mizinski.xmlproperties.properties.Section;
import pl.waw.mizinski.xmlproperties.properties.SectionImpl;
import pl.waw.mizinski.xmlproperties.xml.XMLAttribute;
import pl.waw.mizinski.xmlproperties.xml.XMLAttributeImpl;
import pl.waw.mizinski.xmlproperties.xml.XMLElement;
import pl.waw.mizinski.xmlproperties.xml.XMLElementImpl;
import pl.waw.mizinski.xmlproperties.xml.XMLFile;
import pl.waw.mizinski.xmlproperties.xml.XMLFileImpl;

public class PropertiesService
{
	private PropertiesService()
	{
	}

	private static PropertiesService service = new PropertiesService();

	public static PropertiesService getInstance()
	{
		return service;
	}

	PropertiesFile getPropertiesFile(String content) throws XMLParseException, InvalidXMLContentExcepcion
	{
		XMLFile xmlFile = generateXMLFileFromString(content);
		PropertiesFile propertiesFile = generatePropertiesFileFromXMLFile(xmlFile);
		return propertiesFile;
	}

	PropertiesFile getPropertiesFile(File file) throws XMLParseException, InvalidXMLContentExcepcion, FileNotFoundException
	{
		String content = getFileContent(file);
		return getPropertiesFile(content);
	}
	
	public PropertiesFile getEmptyPropertiesFile()
	{
		return new PropertiesFileImpl();
	}

	String serailizePropertiesFile(PropertiesFile propertiesFile)
	{
		XMLFile xmlFile = generateXMLFileFromPropertiefile(propertiesFile);
		return xmlFile.getContent();
	}

	void savePropertiesFile(PropertiesFile propertiesFile, File file) throws FileNotFoundException
	{
		String fileContent = serailizePropertiesFile(propertiesFile);
		saveContentToFile(fileContent,file);
	}

	private XMLFile generateXMLFileFromString(String xml) throws XMLParseException
	{
		Lexer lexer = new Lexer(xml);
		List<Token> tokens = lexer.getTokens();
		Parser parser = new Parser(tokens);
		XMLFile xmlFile = parser.getXMLFile();
		return xmlFile;
	}

	private PropertiesFile generatePropertiesFileFromXMLFile(XMLFile xmlFile) throws InvalidXMLContentExcepcion
	{
		checkXMLFileCorrect(xmlFile);
		PropertiesFile propertiesFile = new PropertiesFileImpl();
		XMLElement rooElement = xmlFile.getRootElement();
		for (XMLElement element : rooElement.getChildElements())
		{
			Section section = generateSectionFromXMLElement(element);
			propertiesFile.addSection(section);
		}
		return propertiesFile;
	}

	private Section generateSectionFromXMLElement(XMLElement element)
	{
		Section section = new SectionImpl();
		String name = element.getName();
		section.setName(name);
		for (XMLElement xmlElement : element.getChildElements())
		{
			Property property = generatePropertyFromXMLElement(xmlElement);
			section.setPropertry(property);
		}
		return section;
	}

	private Property generatePropertyFromXMLElement(XMLElement element)
	{
		Property property = new PropertyImpl();
		String name = element.getAttributes().get(0).getValue();
		String value = element.getValue();
		property.setName(name);
		property.setValue(value);
		return property;
	}

	private void checkXMLFileCorrect(XMLFile xmlFile) throws InvalidXMLContentExcepcion
	{
		XMLElement rootElement = xmlFile.getRootElement();
		checkName("properties", rootElement);
		checkHasNoAttributes(rootElement);
		checkHasNullValue(rootElement);
		for (XMLElement element : rootElement.getChildElements())
		{
			checkSectionElement(element);
		}
		checkChildElementsNamesUnique(rootElement);

	}

	private void checkSectionElement(XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		checkName("section", xmlElement);
		checkHasNameAttribute(xmlElement);
		checkHasNullValue(xmlElement);
		for (XMLElement element : xmlElement.getChildElements())
		{
			checkPropertyElement(element);
		}
		checkChildElementsNamesUnique(xmlElement);
	}

	private void checkPropertyElement(XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		checkName("property", xmlElement);
		checkHasNameAttribute(xmlElement);
		checkPropertyValue(xmlElement);
	}

	private void checkPropertyValue(XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		if (xmlElement.getValue() == null)
		{
			throw new InvalidXMLContentExcepcion(String.format("Property with name: '%s' has no value", xmlElement
					.getAttributes().get(0).getValue()));
		}

	}

	private void checkHasNameAttribute(XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		List<XMLAttribute> attributes = xmlElement.getAttributes();
		if (attributes.size() > 1)
		{
			throw new InvalidXMLContentExcepcion(String.format("Element with name: '%s' has to many attributes",
					xmlElement.getName()));
		}
		if (attributes.isEmpty())
		{
			throw new InvalidXMLContentExcepcion(String.format("Element with name: '%s' has no 'name' attribute",
					xmlElement.getName()));
		}
		XMLAttribute attribute = attributes.get(0);
		if (!attribute.getName().equals("name"))
		{
			throw new InvalidXMLContentExcepcion(String.format(
					"Element with name: '%s' has no '%s' attribute instead 'name' attribute", xmlElement.getName(),
					attribute.getName()));
		}
		if (attribute.getValue() == "")
		{
			throw new InvalidXMLContentExcepcion(String.format("Element with name: '%s' has empty value of 'name' attribute",
					xmlElement.getName()));
		}
	}

	private void checkHasNoAttributes(XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		List<XMLAttribute> attributes = xmlElement.getAttributes();
		if(!attributes.isEmpty())
		{
			throw new InvalidXMLContentExcepcion(String.format("Element with name: '%s' should have no attributes",
					xmlElement.getName()));
		}
	}

	private void checkHasNullValue(XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		if(xmlElement.getValue() == null)
		{
			throw new InvalidXMLContentExcepcion(String.format(
					"Element with name: '%s' should not have value(shoud have list of elements instead)",xmlElement.getName()));
		}
	}

	private void checkName(String expectedName, XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		if(xmlElement.getName().equals(expectedName))
		{
			throw new InvalidXMLContentExcepcion(String.format(
					"Element with name: '%s' expected('%s' found)",expectedName,xmlElement.getName()));
		}

	}

	private void checkChildElementsNamesUnique(XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		List<String> childNames = new ArrayList<String>();
		for(XMLElement element : xmlElement.getChildElements()){
			String name = element.getAttributes().get(0).getValue();
			checkNotIn(name, childNames, xmlElement);
			childNames.add(name);
		}
	}

	private void checkNotIn(String name, List<String> names, XMLElement xmlElement) throws InvalidXMLContentExcepcion
	{
		for (String listName : names)
		{
			if(listName.equals(name))
			{
				throw new InvalidXMLContentExcepcion(String.format(
						"Element with name: '%s' can not have to child elements with name: '%s'",xmlElement.getName(),name));
			}
		}
		
	}
	
	private String getFileContent(File file) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(file);
		StringBuilder builder = new StringBuilder();
		while(scanner.hasNextLine())
		{
			builder.append(scanner.nextLine());
		}
		return builder.toString();
	}
	
	private void saveContentToFile(String content, File file) throws FileNotFoundException
	{
		PrintStream out = new PrintStream(file);
		out.print(content);
	}
	
	private XMLFile generateXMLFileFromPropertiefile(PropertiesFile propertiesFile)
	{
		XMLElement rootElement= new XMLElementImpl();
		rootElement.setName("properties");
		for (Section section : propertiesFile.getSections())
		{
			XMLElement element = generateXMLElementForSection(section);
			rootElement.addChildElement(element);
		}
		XMLFileImpl xmlFile= new XMLFileImpl();
		xmlFile.setRootElement(rootElement);
		return xmlFile;
	}

	private XMLElement generateXMLElementForSection(Section section)
	{
		XMLElement xmlElement = new XMLElementImpl();
		xmlElement.setName("section");
		XMLAttribute attribute = new XMLAttributeImpl();
		attribute.setName("name");
		attribute.setValue(section.getName());
		xmlElement.setAttribute(attribute);
		for (Property property : section.getProperties())
		{	
			XMLElement element = generateXMLElementForProperty(property);
			xmlElement.addChildElement(element);
		}
		return xmlElement;
	}

	private XMLElement generateXMLElementForProperty(Property property)
	{
		XMLElement xmlElement = new XMLElementImpl();
		xmlElement.setName("property");
		XMLAttribute attribute = new XMLAttributeImpl();
		attribute.setName("name");
		attribute.setValue(property.getName());
		xmlElement.setAttribute(attribute);
		xmlElement.setValue(property.getValue());
		return xmlElement;
	}
}
