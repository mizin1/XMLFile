package pl.waw.mizinski.xmlproperties.properties;

import java.util.List;

import pl.waw.mizinski.xmlproperties.exceptions.CanNotUpdateElementException;
import pl.waw.mizinski.xmlproperties.exceptions.MissingObjectException;

public class PropertiesFileImpl implements PropertiesFile
{
	List<Section> sections;

	@Override
	public List<Section> getSections()
	{
		return sections;
	}

	@Override
	public Section getSectionByName(String name)
	{
		for (Section section : sections)
		{
			if (section.getName().equals(name))
			{
				return section;
			}
		}
		return null;
	}

	@Override
	public void addSection(Section section) throws CanNotUpdateElementException
	{
		for (Section localSection : sections)
		{
			if (localSection.getName().equals(section.getName()))
			{
				throw new CanNotUpdateElementException(String.format(
						"Section wirh name: '%s' already exists in this properties file", section.getName()));
			}
		}
		sections.add(section);
	}

	@Override
	public void removeSection(Section section) throws MissingObjectException
	{
		for (Section localSection : sections)
		{
			if (localSection.getName().equals(section.getName()))
			{
				sections.remove(localSection);
				return;
			}
		}
		throw new MissingObjectException(String.format(
				"Can not find attribute with name: '%s'", section.getName()));
	}

}
