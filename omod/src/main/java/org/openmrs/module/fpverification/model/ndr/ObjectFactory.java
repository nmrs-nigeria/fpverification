//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.26 at 09:32:03 AM WAT 
//

package org.openmrs.module.fpverification.model.ndr;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the org.openmrs.module.extractionemr.model.ndr package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 */
@XmlRegistry
public class ObjectFactory {
	
	/**
	 * Create a new ObjectFactory that can be used to create new instances of schema derived classes
	 * for package: org.openmrs.module.extractionemr.model.ndr
	 */
	public ObjectFactory() {
	}
	
	/**
	 * Create an instance of {@link Container }
	 */
	public Container createContainer() {
		return new Container();
	}
	
	/**
	 * Create an instance of {@link MessageHeaderType }
	 */
	public MessageHeaderType createMessageHeaderType() {
		return new MessageHeaderType();
	}
	
	/**
	 * Create an instance of {@link PatientDemographicsType }
	 */
	public PatientDemographicsType createPatientDemographicsType() {
		return new PatientDemographicsType();
	}
	
	/**
	 * Create an instance of {@link LeftHandType }
	 */
	public LeftHandType createLeftHandType() {
		return new LeftHandType();
	}
	
	/**
	 * Create an instance of {@link RightHandType }
	 */
	public RightHandType createRightHandType() {
		return new RightHandType();
	}
	
	/**
	 * Create an instance of {@link MessageSendingOrganisationType }
	 */
	public MessageSendingOrganisationType createMessageSendingOrganisationType() {
		return new MessageSendingOrganisationType();
	}
	
	/**
	 * Create an instance of {@link FingerPrintType }
	 */
	public FingerPrintType createFingerPrintType() {
		return new FingerPrintType();
	}
	
}
