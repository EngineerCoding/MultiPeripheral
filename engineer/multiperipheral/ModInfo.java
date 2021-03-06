package engineer.multiperipheral;

public class ModInfo
{
	public static final String ID = "MultiPeripheral";
	public static final String NAME = ID;
	public static final String VERSION = "1.0 Beta";
	
	public static final boolean clientReq = false;
	public static final boolean serverReq = true;
	
	public static final String PROXY_client = "engineer.multiperipheral.addon.client.ClientProxy";
	public static final String PROXY_server = "engineer.multiperipheral.addon.CommonProxy";
	
	public static final String DEPENDENCIES = "required-after:ComputerCraft;after:CCTurtle;";
}
