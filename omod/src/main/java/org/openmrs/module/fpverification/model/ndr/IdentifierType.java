//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.05.28 at 11:46:26 AM WAT 
//

package org.openmrs.module.fpverification.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for IdentifierType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentifierType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDNumber" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="IDTypeCode" type="{}CodeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentifierType", propOrder = { "idNumber", "idTypeCode" })
public class IdentifierType {
	
	@XmlElement(name = "IDNumber")
	protected String idNumber;
	
	@XmlElement(name = "IDTypeCode")
	protected String idTypeCode;
	
	/**
	 * Gets the value of the idNumber property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getIDNumber() {
		return idNumber;
	}
	
	/**
	 * Sets the value of the idNumber property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setIDNumber(String value) {
		this.idNumber = value;
	}
	
	/**
	 * Gets the value of the idTypeCode property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getIDTypeCode() {
		return idTypeCode;
	}
	
	/**
	 * Sets the value of the idTypeCode property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setIDTypeCode(String value) {
		this.idTypeCode = value;
	}
	
}
