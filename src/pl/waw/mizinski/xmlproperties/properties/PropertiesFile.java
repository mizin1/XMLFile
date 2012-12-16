package pl.waw.mizinski.xmlproperties.properties;

import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public interface PropertiesFile
{
	List<Section> getSections();
	
	Section getSectionByName(String name);
	
	void addSection (Section section);
	
	void removeSection(Section section) throws MissingObjectException;
}
