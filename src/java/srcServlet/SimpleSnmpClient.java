package srcServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModel;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;


/**
 * Simplest client possible
 * 
 * @author johanrask
 *
 */
public class SimpleSnmpClient {

	private String address;

	private Snmp snmp;
	

	public SimpleSnmpClient(String address) {
		super();
		this.address = address;
		try {
			start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Since snmp4j relies on asynch req/resp we need a listener
	// for responses which should be closed
	public void stop() throws IOException {
		snmp.close();
	}

	private void start() throws IOException {
		TransportMapping transport = new DefaultUdpTransportMapping();
		snmp = new Snmp(transport);
		// Do not forget this line!
		transport.listen();
	}
	
	public void setAttribute(OID oid, String value,int attrNo)
	{
		PDU pdu = new PDU();
	    
	    // Setting the Oid and Value for sysContact variable
	    //OID oid = new OID(sysContactOid);
	    Variable var = new OctetString(value);
	    VariableBinding varBind = new VariableBinding(oid,var);
	    pdu.add(varBind);
	    
	    pdu.setType(PDU.SET);
	    pdu.setRequestID(new Integer32(1));

	    // Create Snmp object for sending data to Agent
	    

	    System.out.println("Sending Snmp SET REQUEST to Agent...");
	    ResponseEvent response;
	    try {
			response = snmp.send(pdu, getTarget());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	    
	    if (response != null)
	    {
	      System.out.println("Recieved Snmp SET RESPONSE from Agent");
	      PDU responsePDU = response.getResponse();

	      if (responsePDU != null)
	      {
	        int errorStatus = responsePDU.getErrorStatus();
	        int errorIndex = responsePDU.getErrorIndex();
	        String errorStatusText = responsePDU.getErrorStatusText();

	        if (errorStatus == PDU.noError)
	        {
	          System.out.println("Snmp SET RESPONSE = " + responsePDU.getVariableBindings());
            /*    switch(attrNo)
                  {
                      case 0 :
                            Utility.setPwr(value);
                            break;
                      case 1:
                          Utility.setTemperature(value);
                          break;
                      case 2:
                          Utility.setFanSpeed(value);
                          break;
                      case 3:
                          Utility.setSwingStatus(value);
                          break;
                  };*/
	        }
	        else
	        {
	          System.out.println("Error: Request Failed");
	          System.out.println("Error Status = " + errorStatus);
	          System.out.println("Error Index = " + errorIndex);
	          System.out.println("Error Status Text = " + errorStatusText);

	        }
	      }
	      else
	      {
	        System.out.println("Error: Response PDU is null");
	      }
	    }
	    else
	    {
	      System.out.println("Error: Agent Timeout... ");
	    }
	}
	
	
	public String getAsString(OID oid) throws IOException {
		ResponseEvent event = get(new OID[]{oid});
		return event.getResponse().get(0).getVariable().toString();
	}
	
	
	public void getAsString(OID oids,ResponseListener listener) {
		try {
			snmp.send(getPDU(new OID[]{oids}), getTarget(),null, listener);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private PDU getPDU(OID oids[]) {
		PDU pdu = new PDU();
		for (OID oid : oids) {
			pdu.add(new VariableBinding(oid));
		}
	 	   
		pdu.setType(PDU.GET);
		return pdu;
	}
	
	public ResponseEvent get(OID oids[]) throws IOException {
	   ResponseEvent event = snmp.send(getPDU(oids), getTarget(), null);
	   if(event != null) {
		   return event;
	   }
	   throw new RuntimeException("GET timed out");	  
	}
	
	private Target getTarget() {
		Address targetAddress = GenericAddress.parse(address);
		CommunityTarget target = new CommunityTarget();
	/*	target.setSecurityName(new OctetString("cpublic"));
		target.setSecurityModel(SecurityModel.SECURITY_MODEL_SNMPv2c);
		target.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);*/
		target.setCommunity(new OctetString("public"));
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(50000);
		target.setVersion(SnmpConstants.version2c);
		return target;
	}

	/**
	 * Normally this would return domain objects or something else than this...
	 */
	public List<List<String>> getTableAsStrings(OID[] oids) {
		TableUtils tUtils = new TableUtils(snmp, new DefaultPDUFactory());
		
		@SuppressWarnings("unchecked") 
			List<TableEvent> events = tUtils.getTable(getTarget(), oids, null, null);
		
		List<List<String>> list = new ArrayList<List<String>>();
		for (TableEvent event : events) {
			if(event.isError()) {
				throw new RuntimeException(event.getErrorMessage());
			}
			List<String> strList = new ArrayList<String>();
			list.add(strList);
			for(VariableBinding vb: event.getColumns()) {
				strList.add(vb.getVariable().toString());
			}
		}
		return list;
	}
	
	public static String extractSingleString(ResponseEvent event) {
		return event.getResponse().get(0).getVariable().toString();
	}
}
