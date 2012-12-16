package pl.waw.mizinski.xmlproperties.properties;

import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public interface Section
{
	String getName();
	
	String setName(String name);
	
	List<Property> getProperties();
	
	Property getPropertyByName(String name);

	void setPropertry(Property property);

	void removeProperty(Property property) throws MissingObjectException;
}
