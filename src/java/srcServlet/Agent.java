package srcServlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.snmp4j.TransportMapping;
import org.snmp4j.agent.BaseAgent;
import org.snmp4j.agent.CommandProcessor;
import org.snmp4j.agent.DuplicateRegistrationException;
import org.snmp4j.agent.MOGroup;
import org.snmp4j.agent.ManagedObject;
import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.agent.mo.MOTableRow;
import org.snmp4j.agent.mo.snmp.RowStatus;
import org.snmp4j.agent.mo.snmp.SnmpCommunityMIB;
import org.snmp4j.agent.mo.snmp.SnmpNotificationMIB;
import org.snmp4j.agent.mo.snmp.SnmpTargetMIB;
import org.snmp4j.agent.mo.snmp.StorageType;
import org.snmp4j.agent.mo.snmp.VacmMIB;
import org.snmp4j.agent.security.MutableVACM;
import org.snmp4j.log.Log4jLogFactory;
import org.snmp4j.log.LogFactory;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.Variable;
import org.snmp4j.transport.TransportMappings;

/**
 * This Agent contains mimimal functionality for running a version 2c snmp
 * agent.
 * 
 * 
 * @author johanrask
 * 
 */
public class Agent extends BaseAgent {

	// not needed but very useful of course
	static {
		LogFactory.setLogFactory(new Log4jLogFactory());
	}

	private String address;
	static Agent agent;
	
	static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");
	static final OID pwr = new OID(".1.3.6.1.2.1.3.1.1");
	static final OID temp = new OID(".1.3.6.1.2.1.4.2.1");
	static final OID fan = new OID(".1.3.6.1.2.1.5.3.1");
	static final OID swing = new OID(".1.3.6.1.2.1.6.4.1");
	static final OID interfacesTable = new OID(".1.3.6.1.2.1.2.2.1");

	public Agent(String address) throws IOException {

		// These files does not exist and are not used but has to be specified
		// Read snmp4j docs for more info
		super(new File("conf.agent"), new File("bootCounter.agent"),
				new CommandProcessor(
						new OctetString(MPv3.createLocalEngineID())));
		this.address = address;
	}

	/**
	 * We let clients of this agent register the MO they
	 * need so this method does nothing
	 */
	@Override
	protected void registerManagedObjects() {
	}

	/**
	 * Clients can register the MO they need
	 */
	public void registerManagedObject(ManagedObject mo) {
		try {
			server.register(mo, null);
		} catch (DuplicateRegistrationException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public void unregisterManagedObject(MOGroup moGroup) {
		moGroup.unregisterMOs(server, getContext(moGroup));
	}

	/*
	 * Empty implementation
	 */
	@Override
	protected void addNotificationTargets(SnmpTargetMIB targetMIB,
			SnmpNotificationMIB notificationMIB) {
	}

	/**
	 * Minimal View based Access Control
	 * 
	 * http://www.faqs.org/rfcs/rfc2575.html
	 */
	@Override
	protected void addViews(VacmMIB vacm) {

		vacm.addGroup(SecurityModel.SECURITY_MODEL_SNMPv2c, 
				new OctetString("cpublic"), 
				new OctetString("v1v2group"),
				StorageType.nonVolatile);

		vacm.addAccess(new OctetString("v1v2group"), new OctetString("public"),
                SecurityModel.SECURITY_MODEL_ANY,
                SecurityLevel.NOAUTH_NOPRIV,
                MutableVACM.VACM_MATCH_EXACT,
                new OctetString("fullReadView"),
                new OctetString("fullWriteView"),
                new OctetString("fullNotifyView"),
                StorageType.nonVolatile);

		vacm.addViewTreeFamily(new OctetString("fullReadView"), new OID("1.3"),
				new OctetString(), VacmMIB.vacmViewIncluded,
				StorageType.nonVolatile);
		
		vacm.addViewTreeFamily(new OctetString("fullWriteView"), new OID("1.3"),
                new OctetString(), VacmMIB.vacmViewIncluded,
                StorageType.nonVolatile);
		
		vacm.addViewTreeFamily(new OctetString("fullNotifyView"), new OID("1.3"),
                new OctetString(), VacmMIB.vacmViewIncluded,
                StorageType.nonVolatile);
	}

	/**
	 * User based Security Model, only applicable to
	 * SNMP v.3
	 * 
	 */
	protected void addUsmUser(USM usm) {
	}

	protected void initTransportMappings() throws IOException {
		transportMappings = new TransportMapping[1];
		Address addr = GenericAddress.parse(address);
		TransportMapping tm = TransportMappings.getInstance()
				.createTransportMapping(addr);
		transportMappings[0] = tm;
	}

	/**
	 * Start method invokes some initialization methods needed to
	 * start the agent
	 * @throws IOException
	 */
	public void start() throws IOException {

		init();
		// This method reads some old config from a file and causes
		// unexpected behavior.
		// loadConfig(ImportModes.REPLACE_CREATE); 
		addShutdownHook();
		getServer().addContext(new OctetString("public"));
		finishInit();
		run();
		sendColdStartNotification();
	}
	

	
	protected void unregisterManagedObjects() {
		// here we should unregister those objects previously registered...
	}

	/**
	 * The table of community strings configured in the SNMP
	 * engine's Local Configuration Datastore (LCD).
	 * 
	 * We only configure one, "public".
	 */
	protected void addCommunities(SnmpCommunityMIB communityMIB) {
		Variable[] com2sec = new Variable[] {
		        new OctetString("public"),              // community name
		        new OctetString("cpublic"),              // security name
		        getAgent().getContextEngineID(),        // local engine ID
		        new OctetString("public"),              // default context name
		        new OctetString(),                      // transport tag
		        new Integer32(StorageType.nonVolatile), // storage type
		        new Integer32(RowStatus.active)         // row status
		};
		MOTableRow row = communityMIB.getSnmpCommunityEntry().createRow(
				new OctetString("public2public").toSubIndex(true), com2sec);
		communityMIB.getSnmpCommunityEntry().addRow(row);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		agent = new Agent("0.0.0.0/2001");
		agent.start();
		
		// Since BaseAgent registers some mibs by default we need to unregister
		// one before we register our own sysDescr. Normally you would
		// override that method and register the mibs that you need
		agent.unregisterManagedObject(agent.getSnmpv2MIB());
		
		// Register a system description, use one from you product environment
		// to test with
		agent.registerManagedObject(
				MOScalarFactory.createReadWrite(sysDescr,"Home Maintenance"));

                ManagedObject mo1 = MOScalarFactory.createReadWrite(pwr,Utility.getPwr());
		agent.registerManagedObject(mo1);

                ManagedObject mo2 = MOScalarFactory.createReadWrite(temp,Utility.getTemperature());
		agent.registerManagedObject(mo2);

                ManagedObject mo3 = MOScalarFactory.createReadWrite(fan,Utility.getFanSpeed());
		agent.registerManagedObject(mo3);

                ManagedObject mo4 = MOScalarFactory.createReadWrite(swing,Utility.getSwingStatus());
		agent.registerManagedObject(mo4);

		/*
                 agent.registerManagedObject(
				MOScalarFactory.createReadWrite(temp,Utility.getTemperature()));
		agent.registerManagedObject(
				MOScalarFactory.createReadWrite(fan,Utility.getFanSpeed()));
		agent.registerManagedObject(
				MOScalarFactory.createReadWrite(swing,Utility.getSwingStatus()));
		*/
		// Read Config file
		
		
		// Build a table. This example is taken from TestAgent and sets up
		// two physical interfaces 
	/*	System.out.println(mo2.toString().split(",")[2].split("=")[1]);
                System.out.println(mo3.toString().split(",")[2].split("=")[1]);
                System.out.println(mo4.toString().split(",")[2].split("=")[1]);
         *
         */

		MOTableBuilder builder = new MOTableBuilder(interfacesTable)
			.addColumnType(SMIConstants.SYNTAX_OCTET_STRING,MOAccessImpl.ACCESS_READ_ONLY)
			.addColumnType(SMIConstants.SYNTAX_OCTET_STRING,MOAccessImpl.ACCESS_READ_WRITE)
			
			// Normally you would begin loop over you two domain objects here
			.addRowValue(new OctetString("PWR"))
			.addRowValue(new OctetString(Utility.getPwr()))

			//next row
			.addRowValue(new OctetString("TEMP"))
			.addRowValue(new OctetString(Utility.getTemperature()))
			
			.addRowValue(new OctetString("FAN"))
			.addRowValue(new OctetString(Utility.getFanSpeed()))
			
			.addRowValue(new OctetString("SWING"))
			.addRowValue(new OctetString(Utility.getSwingStatus()));

		agent.registerManagedObject(builder.build());
						
                  
                System.out.println("Agent running...");

                 while(true) {
                     Scanner input= new Scanner(System.in);
                     String exit = input.nextLine();
                     if(exit.equals("stop"))
                     {
                          String fPow = mo1.toString().split(",")[2].split("=")[1];
                          String fTemp = mo2.toString().split(",")[2].split("=")[1];
                          String fFan = mo3.toString().split(",")[2].split("=")[1];
                          String fSwing = mo4.toString().split(",")[2].split("=")[1];
                          agent.stop();
                          Utility.setPwr(fPow);
                          Utility.setTemperature(fTemp);
                          Utility.setFanSpeed(fFan);
                          Utility.setSwingStatus(fSwing);
                          break;
                         /*
                         Utility.setPwr(initPow);
                         Utility.setTemperature(mo1.toString().split(",")[2].split("=")[1]);
                         Utility.setFanSpeed(mo1.toString().split(",")[2].split("=")[1]);
                         Utility.setSwingStatus(mo1.toString().split(",")[2].split("=")[1]);
                          *
                          */
                     }
                        
                        System.out.println("000");
			
		}
		//agent.stop();
		
	}
}
