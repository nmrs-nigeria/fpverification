//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.26 at 09:32:03 AM WAT 
//

package org.openmrs.module.fpverification.model.ndr;

import javax.xml.bind.annotation.*;

/**
 * <p>
 * Java class for anonymous complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EmrType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="NMRS"/>
 *               &lt;enumeration value="LAMISPlus"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MessageHeader" type="{}MessageHeaderType"/>
 *         &lt;element name="PatientDemographics" type="{}PatientDemographicsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "emrType", "messageHeader", "patientDemographics" })
@XmlRootElement(name = "Container")
public class Container {
	
	@XmlElement(name = "EmrType", required = true)
	protected String emrType;
	
	@XmlElement(name = "MessageHeader", required = true)
	protected MessageHeaderType messageHeader;
	
	@XmlElement(name = "PatientDemographics", required = true)
	protected PatientDemographicsType patientDemographics;
	
	/**
	 * Gets the value of the emrType property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getEmrType() {
		return emrType;
	}
	
	/**
	 * Sets the value of the emrType property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setEmrType(String value) {
		this.emrType = value;
	}
	
	/**
	 * Gets the value of the messageHeader property.
	 * 
	 * @return possible object is {@link MessageHeaderType }
	 */
	public MessageHeaderType getMessageHeader() {
		return messageHeader;
	}
	
	/**
	 * Sets the value of the messageHeader property.
	 * 
	 * @param value allowed object is {@link MessageHeaderType }
	 */
	public void setMessageHeader(MessageHeaderType value) {
		this.messageHeader = value;
	}
	
	/**
	 * Gets the value of the patientDemographics property.
	 * 
	 * @return possible object is {@link PatientDemographicsType }
	 */
	public PatientDemographicsType getPatientDemographics() {
		return patientDemographics;
	}
	
	/**
	 * Sets the value of the patientDemographics property.
	 * 
	 * @param value allowed object is {@link PatientDemographicsType }
	 */
	public void setPatientDemographics(PatientDemographicsType value) {
		this.patientDemographics = value;
	}
	
}
