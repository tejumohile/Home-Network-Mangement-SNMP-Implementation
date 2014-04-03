/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
/**
 *
 * @author TAM
 */
public class WebManagerSNMP {
static final OID sysDescr = new OID(".1.3.6.1.2.1.1.1.0");
	static final OID pwr = new OID(".1.3.6.1.2.1.3.1.1");
	static final OID temp = new OID(".1.3.6.1.2.1.4.2.1");
	static final OID fan = new OID(".1.3.6.1.2.1.5.3.1");
	static final OID swing = new OID(".1.3.6.1.2.1.6.4.1");
	static final OID interfacesTable = new OID(".1.3.6.1.2.1.2.2.1");

	static Agent agent;

	static SimpleSnmpClient client;

        public static String power = "";
        public static String temperature = "";
        public static String swing2 = "";
        public static String fanSpeed = "";

	public static void getAllValues() throws Exception {

				// Setup the client to use our newly started agent

//                                InetAddress address = InetAddress.getByName("cs2.utdallas.edu");
//                                System.out.println(address.getHostAddress());
//	  			client = new SimpleSnmpClient("udp:"+address.getHostAddress()+"/2001");

				client = new SimpleSnmpClient("udp:127.0.0.1/2001");
//                                client = new SimpleSnmpClient("udp:10.21.16.38/2001");
				System.out.println("Initiating GET messages.");

				//while(true) {
					//System.out.println("Manager running...");
                                        /*
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
                                            */
					power = client.getAsString(pwr);
                                        System.out.println("GET RESPONSE Power : " + power);
					temperature = client.getAsString(temp);
                                        System.out.println("GET RESPONSE Temperature : " + temperature);
					fanSpeed = client.getAsString(fan);
                                        System.out.println("GET RESPONSE Fan Speed : " + fanSpeed);
					swing2 =client.getAsString(swing);
                                        System.out.println("GET RESPONSE Swing : " + swing2);
					//client.setAttribute(pwr, "0");
					//System.out.println("PWR:"+client.getAsString(pwr));

					//System.out.println(client.getAsString(sysDescr));
					//Thread.sleep(5000);

					client.stop();

				//}
		}
        
        
        
        
        public static void setAllValues() throws Exception {

				// Setup the client to use our newly started agent
				client = new SimpleSnmpClient("udp:127.0.0.1/2001");
//                                InetAddress address = InetAddress.getByName("cs2.utdallas.edu");
//                                System.out.println(address.getHostAddress());
//	  			client = new SimpleSnmpClient("udp:"+address.getHostAddress()+"/2001");
				System.out.println("Initiating SET messages.");

				//while(true) {
				/*System.out.println("Manager running...");
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
*/
					
					client.setAttribute(pwr, power,0);
                                        client.setAttribute(temp, temperature,1);
                                        client.setAttribute(fan, fanSpeed,2);
                                        client.setAttribute(swing, swing2,3);
					//System.out.println("PWR:"+client.getAsString(pwr));

					//System.out.println(client.getAsString(sysDescr));
					//Thread.sleep(5000);

					client.stop();

				//}
		}

    public static String getFanSpeed() {
        return fanSpeed;
    }

    public static void setFanSpeed(String fanSpeed) {
        WebManagerSNMP.fanSpeed = fanSpeed;
    }

    public static String getPower() {
        return power;
    }

    public static void setPower(String power) {
        WebManagerSNMP.power = power;
    }

    public static String getSwing2() {
        return swing2;
    }

    public static void setSwing2(String swing2) {
        WebManagerSNMP.swing2 = swing2;
    }

    public static String getTemperature() {
        return temperature;
    }

    public static void setTemperature(String temperature) {
        WebManagerSNMP.temperature = temperature;
    }

}
