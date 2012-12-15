package pl.waw.mizinski.xml;

import java.util.List;

import pl.waw.mizinski.xml.exceptions.MissingPropertyException;

public interface Section
{
	List<Property> getProperties();
	
	Property getPropertyByName(String name);

	void setPropertry(Property property);

	void removeProperty(Property property) throws MissingPropertyException;
}
