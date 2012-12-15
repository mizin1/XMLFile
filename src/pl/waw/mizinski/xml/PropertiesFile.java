package pl.waw.mizinski.xml;

import java.util.List;

public interface PropertiesFile
{
	List<Section> getSections();
	
	Section getSectionByName(String name);
	
	void addSection (Section section);
	
	void removeSection(Section section);
}
