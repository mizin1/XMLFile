package pl.waw.mizinski.xmlproperties.properties;

import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public interface Section
{
	String getName();
	
	void setName(String name);
	
	boolean isEmpty();
	
	List<Property> getProperties();
	
	Property getPropertyByName(String name);
	
	String getPropertyValue(String name);

	void setProperty(Property property);
	
	void setProperty(String name, String value);

	void removeProperty(Property property) throws MissingObjectException;

	void removeProperty(String propertyName) throws MissingObjectException;
}
