package pl.waw.mizinski.xmlproperties.service;

import pl.waw.mizinski.xmlproperties.properties.PropertiesFile;
import pl.waw.mizinski.xmlproperties.properties.PropertiesFileImpl;
import pl.waw.mizinski.xmlproperties.properties.Property;
import pl.waw.mizinski.xmlproperties.properties.PropertyImpl;
import pl.waw.mizinski.xmlproperties.properties.Section;
import pl.waw.mizinski.xmlproperties.properties.SectionImpl;

public class PropertiesObjectsFactory
{
	public static PropertiesFile createEmptyPropertiesFile()
	{
		return new PropertiesFileImpl();
	}
	
	public static Section createEmptySection(String sectionName)
	{
		return new SectionImpl(sectionName);
	}
	
	public static Property createProperty(String name)
	{
		return new PropertyImpl(name);
	}
	
	public static Property createProperty(String name, String value)
	{
		return new PropertyImpl(name, value);
	}
}
