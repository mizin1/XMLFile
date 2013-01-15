package pl.waw.mizinski.xmlproperties.properties;

import java.util.ArrayList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public class SectionImpl implements Section
{
	String name;

	List<Property> properties = new ArrayList<Property>();

	public SectionImpl()
	{	
	}
	
	public SectionImpl(String name)
	{
		this.name = name;
	}
	
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
	public boolean isEmpty()
	{
		return properties.isEmpty();
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
	public String getPropertyValue(String name)
	{
		return getPropertyByName(name).getValue();
	}

	@Override
	public void setProperty(String name, String value)
	{
		Property property = new PropertyImpl();
		property.setName(name);
		property.setValue(value);
		setProperty(property);
	}
	
	@Override
	public void setProperty(Property property)
	{
		for (Property localProperty : properties)
		{
			if (localProperty.getName().equals(property.getName()))
			{
				localProperty.setValue(property.getValue());
				return;
			}
		}
		properties.add(property);
	}

	@Override
	public void removeProperty(Property property) throws MissingObjectException
	{
		for (Property localProperty : properties)
		{
			if (localProperty.getName().equals(property.getName()))
			{
				properties.remove(localProperty);
				return;
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
				return;
			}
		}
		throw new MissingObjectException(String.format("Can not find propertry with name '%s'", propertyName));
	}
}
