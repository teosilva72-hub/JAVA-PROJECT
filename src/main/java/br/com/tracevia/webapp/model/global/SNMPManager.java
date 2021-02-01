package br.com.tracevia.webapp.model.global;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SNMPManager {
	
	Snmp snmp = null;	
	String address = null; 
	
	/**	
	* Constructor	
	* @param add - Udp target
	*/
	
	public SNMPManager(String add)	
	{	
		
	address = add;

	}
	
	public SNMPManager() {}

	/**	
    * Inicie a sess�o Snmp. Se voc� esquecer o m�todo listen (), n�o ir�
    * obtenha respostas porque a comunica��o � ass�ncrona
    * e o m�todo listen () ouve as respostas.
	* @throws IOException	
	*/
	
	public void start() throws IOException {	
		
	TransportMapping<UdpAddress> transport = new DefaultUdpTransportMapping();	 
	snmp = new Snmp(transport);		
	transport.listen();	
	} 
	
	/**	
	* M�todo que utiliza um �nico OID e retorna a resposta do agente como uma String.
	* @param oid	
	* @return	
	* @throws IOException	
	*/
	
	public String getAsString(OID oid) throws IOException {	
	ResponseEvent<Address> event = get(new OID[] { oid });	
	return event.getResponse().get(0).getVariable().toString();	
	}

	/**	
	* Esse m�todo � capaz de lidar com v�rios OIDs	
	* @param oids
	* @return	
	* @throws IOException	
	*/
	
	public ResponseEvent<Address> get(OID oids[]) throws IOException {
	
	PDU pdu = new PDU();
	
	for (OID oid : oids) {
	
	pdu.add(new VariableBinding(oid));
	
	}
	
	pdu.setType(PDU.GET);
	
	ResponseEvent<Address> event = snmp.send(pdu, getTarget(), null);
	
	if(event != null) {	
	return event;	
	}	
	throw new RuntimeException("GET timed out");

	}
	 

	/**	
	* Este m�todo retorna um Target, que cont�m informa��es sobre
    * onde os dados devem ser buscados e como.
	* @return	
	*/
	
	private Target<Address> getTarget() {
	
	Address targetAddress = GenericAddress.parse(address);

	CommunityTarget<Address> target = new CommunityTarget<Address>();
	
	target.setCommunity(new OctetString("public"));
	
	target.setAddress(targetAddress);
	
	target.setRetries(3);
	
	target.setTimeout(10000);
	
	target.setVersion(SnmpConstants.version2c);
	
	return target;

	}	

}
