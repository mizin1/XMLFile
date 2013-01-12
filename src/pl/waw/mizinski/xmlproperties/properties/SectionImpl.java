package pl.waw.mizinski.xmlproperties.properties;

import java.util.ArrayList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public class SectionImpl implements Section
{
	String name;

	List<Property> properties = new ArrayList<Property>();

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public List<Property> getProperties()
	{
		return properties;
	}

	@Override
	public Property getPropertyByName(String name)
	{
		for (Property property : properties)
		{
			if (name.equals(property.getName()))
			{
				return property;
			}
		}
		return null;
	}

	@Override
	public void setPropertry(Property property)
	{
		for (Property localProperty : properties)
		{
			if (localProperty.getName().equals(property.getName()))
			{
				localProperty.setValue(property.getValue());
			}
		}

	}

	@Override
	public void removeProperty(Property property) throws MissingObjectException
	{
		for (Property localProperty : properties)
		{
			if (localProperty.getName().equals(property.getName()))
			{
				properties.remove(localProperty);
			}
		}
		throw new MissingObjectException(String.format("Can not find propertry with name '%s'", property.getName()));
	}

	@Override
	public void removeProperty(String propertyName) throws MissingObjectException
	{
		for (Property localProperty : properties)
		{
			if (localProperty.getName().equals(propertyName))
			{
				properties.remove(localProperty);
			}
		}
		throw new MissingObjectException(String.format("Can not find propertry with name '%s'", propertyName));
	}
}
