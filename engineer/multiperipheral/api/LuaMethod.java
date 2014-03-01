package engineer.multiperipheral.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.minecraft.world.World;

/**
 * This annotation marks the method to be a method for the peripheral
 * @see LuaArg
 * @see LuaMethod.Check
 * @see LuaMethod.Describe
 * @author Wesley 'Engineer' Ameling
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LuaMethod 
{	
	/**
	 * This annotation allows you to do a check if you want to include this method in the method list which you can access via the computer.
	 * @see LuaMethod
	 * @author EngineerCoding
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Check 
	{
		/**
		 * <p>This string is the name of another method, which doesnt have {@link LuaMethod} or {@link LuaMethod.Describe}.
		 * This method <strong>always</strong> returns a boolean, because this method gets invoked to determine if the {@link LuaMethod}
		 * should be included. An example:</p>
		 * <pre>
		 * {@literal @}{@link ILuaMethod}(name="Test")
		 * {@literal @}{@link ICheck}("TestChecker")
		 * public void TheTestLuaMethod()
		 * {
		 *    // stuff
		 * }
		 * 
		 * public <strong>boolean</strong> TestChecker()
		 * {
		 *    return false;
		 * }
		 * </pre>
		 * <p>In this example the ILuaMethod never will be added to the method list because TestChecker always returns false. If it
		 * returns true, the ILuamethod is reachable via a computer.</p>
		 * <p>This annotation only should be used when you have a class full of lua methods, and this is the only sensitive method.
		 * If you have a class that contains methods which are sensitive on the same subject, use {@link ILuaPeripheral#isValidPosition(World, int, int, int, int)}.</p>
		 * <p>Moral of the story: only use this in the worst case scenario!</p>
		 * <p>Note the following:</p>
		 * <ul><li>When the method (which is 'TestChecker' in the above example) fails to invoke, the {@link LuaMethod} wont be reachable from
		 * the lua side.</li>
		 * <li>When the method (again 'TestChecker') does not exist or the method doesnt return a boolean value, then this interface will be totally ignored.</li></ul>
		 * 
		 * @return bool to include the method or not
		 */
		public String value();
	}
	
	/**
	 * This annotation describes an {@link LuaMethod}
	 * @author EngineerCoding
	 */
	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Describe 
	{
		/**
		 * Describes the input arguments. Those do not include {@link LuaArg#LuaContext} and {@link LuaArg#ComputerAccess}
		 */
		public String input();
		
		/**
		 * Describes the output values
		 */
		public String output();
		
		/**
		 * The description what this method does
		 */
		public String description();
	}
	
	/**
	 * This sets the name of this method. Though, you have not to name the actual method to this name.
	 * The following method is named 'METHOD' when you see it via ComputerCraft. Though, it's actual is different:
	 * <pre>
	 * package lua.methods;
	 * 
	 * {@literal @}{@link LuaMethod}(name="METHOD")
	 * public void _method()
	 * {
	 *    // Code for this method
	 * }
	 * </pre>
	 * 
	 * @return The lua-methodname
	 */
	public String name();
		
	/**
	 * This lets the parser know that the method uses arguments. It uses the {@link LuaArg} enumeration to determine the values.
	 * If you use one of the following Enum constants on the input, they will be simply filtered out:
	 * <ul><li>{@link LuaArg#LuaObject}</li><li>{@link LuaArg#Nil}</li><li>{@link LuaArg#Multiple}</li><li>{@link LuaArg#Object}</li></ul>
	 * 
	 * So if you for instance the following:
	 * <pre>
	 * {@literal @}{@link LuaMethod}(name="METHOD", input={{@link LuaArg#Number}, {@link LuaArg#Nil}, {@link LuaArg#ComputerAccess}})
	 * public void _method(int t, IComputerAccess pc)
	 * {
	 *   // Code for this method
	 * }
	 * </pre>
	 * That annotation will be converted behind the scenes to:
	 * <pre>
	 * {@literal @}{@link LuaMethod}(name="METHOD", input={{@link LuaArg#Number}, {@link LuaArg#ComputerAccess}})
	 * public void _method(int t, IComputerAccess pc)
	 * {
	 *   // Code for this method
	 * }
	 * </pre>
	 * If {@link LuaMethod#input()} does not match the actual method paramaters, then the method will not be initialized as a 'LuaMethod' because 
	 * the algorithm is not able to figure out what it has to pass where.
	 * <p>The examples above are both valid, but this one is invalid:</p>
	 * <pre>
	 * {@literal @}{@link LuaMethod}(name="METHOD", input={{@link LuaArg#Number}, {@link LuaArg#ComputerAccess}})
	 * public void _method(IComputerAccess pc, int t)
	 * {
	 *   // Code for this method
	 * }
	 * </pre>
	 * 
	 * @return Parameters of the method
	 */
	public LuaArg[] input() default {};
		
	/**
	 * This lets the parser know what type gets returned from this method. Again, it uses {@link LuaArg} enumerations.
	 * If {@link LuaMethod#output()} gets a constant which is only intended for input values, it will default to {@link LuaArg.Nil}.
	 * When you want to return two different types of values, you can use {@link LuaArg#Object}. The return value of the method will be checked
	 * if its an actual value that fits in the output types of {@link LuaArg}
	 * <p>By default it uses the {@link LuaArg#Object}. If you dont want to return anything at all, use {@link LuaArg#Nil}. When you are not returning
	 * multipe types of values, but are only returning for example Strings, use the appropriate type.</p>
	 * <p>If you want return 'nil', simply return null.
	 * 
	 * <p>The following Enum constants will immediately default to {@link LuaArg#Nil}:</p>
	 * <ul><li>{@link LuaArg#ComputerAccess}</li><li>{@link LuaArg#LuaContext}</li></ul>
	 * 
	 * @return Returntype of the method
	 */
	public LuaArg output() default LuaArg.Nil;
}
