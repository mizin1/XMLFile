package pl.waw.mizinski.xmlproperties.service;

import pl.waw.mizinski.xmlproperties.properties.PropertiesFile;
import pl.waw.mizinski.xmlproperties.properties.PropertiesFileImpl;
import pl.waw.mizinski.xmlproperties.properties.Property;
import pl.waw.mizinski.xmlproperties.properties.PropertyImpl;
import pl.waw.mizinski.xmlproperties.properties.Section;
import pl.waw.mizinski.xmlproperties.properties.SectionImpl;

public class PropertiesObjectsFactory
{
	private PropertiesObjectsFactory()
	{
	}

	private static PropertiesObjectsFactory factory = new PropertiesObjectsFactory();

	public static PropertiesObjectsFactory getInstance()
	{
		return factory;
	}

	public PropertiesFile createEmptyPropertiesFile()
	{
		return new PropertiesFileImpl();
	}

	public Section createEmptySection(String sectionName)
	{
		return new SectionImpl(sectionName);
	}

	public Property createProperty(String name)
	{
		return new PropertyImpl(name);
	}

	public Property createProperty(String name, String value)
	{
		return new PropertyImpl(name, value);
	}
}
