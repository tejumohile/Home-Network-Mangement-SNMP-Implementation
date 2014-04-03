package srcServlet;



import java.util.List;

import org.snmp4j.agent.mo.MOAccessImpl;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import java.net.*;
import java.io.*;

public class ManagerSnmp {

	static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");
	static final OID pwr = new OID(".1.3.6.1.2.1.3.1.1");
	static final OID temp = new OID(".1.3.6.1.2.1.4.2.1");
	static final OID fan = new OID(".1.3.6.1.2.1.5.3.1");
	static final OID swing = new OID(".1.3.6.1.2.1.6.4.1");
	static final OID interfacesTable = new OID(".1.3.6.1.2.1.2.2.1");
		
	static Agent agent;
	static SimpleSnmpClient client;
	
	
	public static void main(String args[]) throws Exception {
						
				// Setup the client to use our newly started agent
                                
                                InetAddress address = InetAddress.getByName("cs2.utdallas.edu");
                                System.out.println(address.getHostAddress());
				client = new SimpleSnmpClient("udp:"+address.getHostAddress()+"/2001");
                                
//                          	client = new SimpleSnmpClient("udp:127.0.0.1/2001");

				System.out.println("hello");
				
				//while(true) {
					System.out.println("Manager running...");
					List<List<String>> tableContents = client.getTableAsStrings(new OID[]{
			    			new OID(interfacesTable.toString() + ".1"),
			    			new OID(interfacesTable.toString() + ".2")});
					
					System.out.println(""+tableContents.size());
					System.out.println(""+tableContents.get(0).size());
					System.out.println(""+tableContents.get(1).size());

					// Row 1
					System.out.println(tableContents.get(0).get(0)+":"+tableContents.get(0).get(1));
					System.out.println(tableContents.get(1).get(0)+":"+tableContents.get(1).get(1));
					System.out.println(tableContents.get(2).get(0)+":"+tableContents.get(2).get(1));
					System.out.println(tableContents.get(3).get(0)+":"+tableContents.get(3).get(1));
					
					System.out.println("PWR:"+client.getAsString(pwr));
					System.out.println("TEMP:"+client.getAsString(temp));
					System.out.println("FAN:"+client.getAsString(fan));
					System.out.println("SWING:"+client.getAsString(swing));
					//client.setAttribute(pwr, "0");
					System.out.println("PWR:"+client.getAsString(pwr));
				
					//System.out.println(client.getAsString(sysDescr));
					//Thread.sleep(5000);
					
					client.stop();
					
				//}
		}
}
