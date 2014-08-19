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
`class OrderDetail extends AbstractPacket{
	@PacketMessage(position=1, length=10)
	private String name;
	
	@PacketMessage(position=2, length=8)
	@TemporalFormat("ddMMyyyy")
	private Date date;
	
	@PacketMessage(position=1, length=5)
	private Long amount;
}`

And a fixed message "Item1     1508201300100"

The conversion is as simple as follows
`String message = "Item1     1508201300100";
//Method 1 : Creating a object and calling parse
OrderDetail detail = new OrderDetail();
detail.parse(message);

//Method 2: Allowing the framework to create the object (class must have zero-arg constructor)
OrderDetail detail = AbstractPacket.parse(OrderDetail.class, message);`