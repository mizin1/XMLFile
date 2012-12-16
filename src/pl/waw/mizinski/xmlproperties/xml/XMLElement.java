package pl.waw.mizinski.xmlproperties.xml;

import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.CanNotUpdateElementException;
import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public interface XMLElement
{
	String getName();
	
	String setName(String name);
	
	boolean isComplexElement();
	
	boolean isEmty();
	
	List<XMLAttribute> getAttributes();
	
	void setAttribute (XMLAttribute attribute);
	
	void removeAttribute (XMLAttribute attribute) throws MissingObjectException;
	
	List<XMLElement> getChildElements();
	
	void addChildElement(XMLElement element) throws CanNotUpdateElementException;
	
	void removeChildElement(XMLElement element) throws MissingObjectException;
	
	String getValue();
	
	String setValue(String value) throws CanNotUpdateElementException;
}
