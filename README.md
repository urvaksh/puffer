puffer
======

pUFFEr : A framework to allow conversions between fixed length messages and objects.

Puffer allows conversion of fixed length messages to Objects in a declarative manner, the developer only has to use annotations and the framework takes care of the rest.

Puffer offers the following features
*	Type conversion is seamless for all basic java types and the framework allows creation of Custom Type converters for types that are not supported out of the box.
*	Out of the box padding for fixed length messages, the framework allows the programmer to specify the padding character and padding side for any given field.
*	Automatically handles the mapping of message segments (a list of segment where the number of segments is not fixed) to java Lists.
*	Allows Inheritance mapping and runtime discovery of which concrete class messages should be mapped to by using field discriminators.


Simple Example
----
For the class:

class OrderDetail extends AbstractPacket{

	@PacketMessage(position=1, length=10)
	private String name;
	
	@PacketMessage(position=2, length=8)
	@TemporalFormat("ddMMyyyy")
	private Date date;
	
	@PacketMessage(position=3, length=5)
	private Long amount;
}


And a fixed message "Item1     1508201300100"

The conversion is as simple as follows

String message = "Item1     1508201300100";
//Method 1 : Creating a object and calling parse
OrderDetail detail = new OrderDetail();
detail.parse(message);

//Method 2: Allowing the framework to create the object (class must have zero-arg constructor)
OrderDetail detail = AbstractPacket.parse(OrderDetail.class, message);

Custom Converters
----
Puffers allows developers to specify how the fixed length message should be converted to and from a field by using Converters.
Although all the basic conversions are handeled, users can create their own Converters.

Example: The fixed length field contains an amount, which maps to a Double, however in the message there are no decimal points, the last two digits are considered to be the decimals. In this case a developer would write their own converter:

public class AmountConverter implements Converter<Double> {

	//Provided by framework
	private Converter<Number> delegate = new NumberConverter();
	
	@Override
	public Double hydrate(Field field, String message) {
		//Cast to Double since the field object identifies it as a Double
		Double value = (Double)delegate.hydrate(field,message);
		return value/100; //Divide by hundred to move the decimal
	}
	
	@Override
	public String stringify(Field field, Double entity) {
		return delegate.stringify(field, entity*100);
	}

}

and now in the class:

class OrderDetail extends AbstractPacket{

	@PacketMessage(position=1, length=10)
	private String name;
	
	@PacketMessage(position=2, length=8)
	@TemporalFormat("ddMMyyyy")
	private Date date;
	
	@PacketMessage(position=3, length=5, converter=AmountConverter.class)
	private Double amount;
	
}

Discriminator
-----
The most powerful feature of puffer is the use of discriminators; it allows developers to use annotations to specify which concrete instance of the object should be created at runtime based on the contents of a message.

	@Packet(description = "Packet to test Discriminators")
	@DiscriminatorField(fieldName = "fld1", 
		values = { @DiscriminatorValue(fieldValues = {"ABC", "abc" }, targetClass = Derived1.class),
					@DiscriminatorValue(fieldValues = {"XYZ", "xyz" }, targetClass = Middle.class)})
	public abstract class Base extends AbstractPacket {
		@PacketMessage(length = 4, position = 1)
		String dummy;

		@PacketMessage(length = 3, position = 2)
		String fld1;

		@PacketMessage(length = 1, position = 3)
		@BooleanFormat
		Boolean val;
	}
	
	
	@DiscriminatorField(fieldName = "subPacket", 
	values = { @DiscriminatorValue(fieldValues = {"ABC", "abc" }, targetClass = ReDerived.class),
				@DiscriminatorValue(fieldValues = {"XYZ", "xyz" }, targetClass = ReDerived2.class)})
	public class Middle extends Base{
		@PacketMessage(length=3, position=4)
		String subPacket;
	}
	
	public class ReDerived extends Middle{
		@PacketMessage(length=5, position=5)
		Long amt;
	}
	
	public class ReDerived2 extends Middle{
		@PacketMessage(length=15, position=5)
		String desc;
	}
	
The following call will yeild an instance of ReDerived2
String input = "----xyzNXYZFive Hundred   ";
AbstractPacket packet= AbstractPacket.parse(Base.class, input);
System.out.println(packet.getClass().getCannonicalName());//Will give the full name of ReDerived2

This is because Base.fld1 mapped to xyz, which results in the target object being of the type Middle.
In Middle the field subPacket mapped to XYZ, this lead to the target class being ReDerived2, and since ReDerived2 does not have any discriminators, this was the class whose object was created by the framework.


List Mapping
--------
Puffer also maps One-to-Many relationships in fixed messages. The restriction being that the Many class must also inherit from PacketMessage.

public class Message extends PacketMessage{

	@PacketMessage(length=10, position=1)
	String details;
	
	@PacketMessage(length=1, position=1)
	@PacketList
	List<InnerMessage> messages;
}

public class InnerMessage extends PacketMessage{
	
	@PacketMessage(length=1, position=1)
	private String identifier;
	
	@PacketMessage(length=5, position=2)
	private String name;
}