package pl.waw.mizinski.xmlproperties.xml;

import java.util.LinkedList;
import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.CanNotUpdateElementException;
import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public class XMLElementImpl implements XMLElement
{
	String name;

	List<XMLAttribute> attributes = new LinkedList<XMLAttribute>();

	List<XMLElement> elements = new LinkedList<XMLElement>();

	String value;

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
	public boolean isComplexElement()
	{
		return !elements.isEmpty();
	}

	@Override
	public boolean isEmty()
	{
		return (value == null && elements.isEmpty());
	}

	@Override
	public List<XMLAttribute> getAttributes()
	{
		return attributes;
	}

	@Override
	public void setAttribute(XMLAttribute attribute)
	{
		for (XMLAttribute att : attributes)
		{
			if (att.getName().equals(attribute.getName()))
			{
				att.setValue(attribute.getValue());
				return;
			}
		}
		attributes.add(attribute);
	}

	public void setAttributes(List<XMLAttribute> attributes)
	{
		this.attributes = attributes;
	}

	@Override
	public void removeAttribute(XMLAttribute attribute) throws MissingObjectException
	{
		if (attributes.contains(attribute))
		{
			attributes.remove(attribute);
		}
		else
		{
			throw new MissingObjectException(String.format("Can not find attribute with name: '%s'",
					attribute.getName()));
		}
	}

	@Override
	public List<XMLElement> getChildElements()
	{
		return elements;
	}
	
	public void setChildElements(List<XMLElement> elements)
	{
		if (value == null || elements.isEmpty())
		{
			this.elements = elements;
		}
		else
		{
			throw new CanNotUpdateElementException(String.format(
					"Can not set child elements in element with name: '%s'", name));
		}
	}

	@Override
	public void addChildElement(XMLElement element) throws CanNotUpdateElementException
	{
		if (value == null)
		{
			elements.add(element);
		}
		else
		{
			throw new CanNotUpdateElementException(String.format(
					"Can not add child element to element with name: '%s'", name));
		}
	}

	@Override
	public void removeChildElement(XMLElement element) throws MissingObjectException
	{
		if (attributes.contains(element))
		{
			attributes.remove(element);
		}
		else
		{
			throw new MissingObjectException(String.format("Can not find element with name: '%s'", element.getName()));
		}
	}

	@Override
	public String getValue()
	{
		return value;
	}

	@Override
	public void setValue(String value) throws CanNotUpdateElementException
	{
		if (elements.isEmpty())
		{
			this.value = value;
		}
		else
		{
			throw new CanNotUpdateElementException(String.format("Can not set value of element with name: '%s'", name));
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XMLElementImpl other = (XMLElementImpl) obj;
		if (attributes == null)
		{
			if (other.attributes != null)
				return false;
		}
		else if (!attributes.equals(other.attributes))
			return false;
		if (elements == null)
		{
			if (other.elements != null)
				return false;
		}
		else if (!elements.equals(other.elements))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (value == null)
		{
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<");
		builder.append(name);
		builder.append(" ");
		for(XMLAttribute attribute : attributes)
		{
			builder.append(attribute);
			builder.append(" ");
		}
		if (isEmty())
		{
			builder.append("/>");
		}
		else
		{
			builder.append(">");
			if (isComplexElement())
			{
					for (XMLElement element : elements){
						builder.append(element);
					}
			}
			else
			{
				builder.append(value);
			}
			builder.append("</");
			builder.append(name);
			builder.append(">");
		}
		return builder.toString();
	}
}
