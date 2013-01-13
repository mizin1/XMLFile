package pl.waw.mizinski.xmlproperties.properties;

public class PropertyImpl implements Property
{

	private String name;
	
	private String value;
	
	public PropertyImpl(){}
	
	public PropertyImpl(String name)
	{
		super();
		this.name = name;
	}

	public PropertyImpl(String name, String value)
	{
		super();
		this.name = name;
		this.value = value;
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
	public String getValue()
	{
		return value;
	}

	@Override
	public void setValue(String value)
	{
		this.value = value;
	}

}
