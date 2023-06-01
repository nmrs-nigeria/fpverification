//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.05.26 at 09:32:03 AM WAT 
//

package org.openmrs.module.fpverification.model.ndr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for leftHandType complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="leftHandType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LeftThumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HashedLeftThumb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftThumbQuality" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LeftIndex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HashedLeftIndex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftIndexQuality" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LeftMiddle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HashedLeftMiddle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftMiddleQuality" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LeftWedding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HashedLeftWedding" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftWeddingQuality" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="LeftSmall" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HashedLeftSmall" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LeftSmallQuality" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "leftHandType", propOrder = { "leftThumb", "hashedLeftThumb", "leftThumbQuality", "leftIndex",
        "hashedLeftIndex", "leftIndexQuality", "leftMiddle", "hashedLeftMiddle", "leftMiddleQuality", "leftWedding",
        "hashedLeftWedding", "leftWeddingQuality", "leftSmall", "hashedLeftSmall", "leftSmallQuality" })
public class LeftHandType {
	
	@XmlElement(name = "LeftThumb")
	protected String leftThumb;
	
	@XmlElement(name = "HashedLeftThumb")
	protected String hashedLeftThumb;
	
	@XmlElement(name = "LeftThumbQuality")
	protected Integer leftThumbQuality;
	
	@XmlElement(name = "LeftIndex")
	protected String leftIndex;
	
	@XmlElement(name = "HashedLeftIndex")
	protected String hashedLeftIndex;
	
	@XmlElement(name = "LeftIndexQuality")
	protected Integer leftIndexQuality;
	
	@XmlElement(name = "LeftMiddle")
	protected String leftMiddle;
	
	@XmlElement(name = "HashedLeftMiddle")
	protected String hashedLeftMiddle;
	
	@XmlElement(name = "LeftMiddleQuality")
	protected Integer leftMiddleQuality;
	
	@XmlElement(name = "LeftWedding")
	protected String leftWedding;
	
	@XmlElement(name = "HashedLeftWedding")
	protected String hashedLeftWedding;
	
	@XmlElement(name = "LeftWeddingQuality")
	protected Integer leftWeddingQuality;
	
	@XmlElement(name = "LeftSmall")
	protected String leftSmall;
	
	@XmlElement(name = "HashedLeftSmall")
	protected String hashedLeftSmall;
	
	@XmlElement(name = "LeftSmallQuality")
	protected Integer leftSmallQuality;
	
	/**
	 * Gets the value of the leftThumb property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLeftThumb() {
		return leftThumb;
	}
	
	/**
	 * Sets the value of the leftThumb property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setLeftThumb(String value) {
		this.leftThumb = value;
	}
	
	/**
	 * Gets the value of the hashedLeftThumb property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHashedLeftThumb() {
		return hashedLeftThumb;
	}
	
	/**
	 * Sets the value of the hashedLeftThumb property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHashedLeftThumb(String value) {
		this.hashedLeftThumb = value;
	}
	
	/**
	 * Gets the value of the leftThumbQuality property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getLeftThumbQuality() {
		return leftThumbQuality;
	}
	
	/**
	 * Sets the value of the leftThumbQuality property.
	 * 
	 * @param value allowed object is {@link Integer }
	 */
	public void setLeftThumbQuality(Integer value) {
		this.leftThumbQuality = value;
	}
	
	/**
	 * Gets the value of the leftIndex property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLeftIndex() {
		return leftIndex;
	}
	
	/**
	 * Sets the value of the leftIndex property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setLeftIndex(String value) {
		this.leftIndex = value;
	}
	
	/**
	 * Gets the value of the hashedLeftIndex property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHashedLeftIndex() {
		return hashedLeftIndex;
	}
	
	/**
	 * Sets the value of the hashedLeftIndex property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHashedLeftIndex(String value) {
		this.hashedLeftIndex = value;
	}
	
	/**
	 * Gets the value of the leftIndexQuality property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getLeftIndexQuality() {
		return leftIndexQuality;
	}
	
	/**
	 * Sets the value of the leftIndexQuality property.
	 * 
	 * @param value allowed object is {@link Integer }
	 */
	public void setLeftIndexQuality(Integer value) {
		this.leftIndexQuality = value;
	}
	
	/**
	 * Gets the value of the leftMiddle property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLeftMiddle() {
		return leftMiddle;
	}
	
	/**
	 * Sets the value of the leftMiddle property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setLeftMiddle(String value) {
		this.leftMiddle = value;
	}
	
	/**
	 * Gets the value of the hashedLeftMiddle property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHashedLeftMiddle() {
		return hashedLeftMiddle;
	}
	
	/**
	 * Sets the value of the hashedLeftMiddle property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHashedLeftMiddle(String value) {
		this.hashedLeftMiddle = value;
	}
	
	/**
	 * Gets the value of the leftMiddleQuality property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getLeftMiddleQuality() {
		return leftMiddleQuality;
	}
	
	/**
	 * Sets the value of the leftMiddleQuality property.
	 * 
	 * @param value allowed object is {@link Integer }
	 */
	public void setLeftMiddleQuality(Integer value) {
		this.leftMiddleQuality = value;
	}
	
	/**
	 * Gets the value of the leftWedding property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLeftWedding() {
		return leftWedding;
	}
	
	/**
	 * Sets the value of the leftWedding property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setLeftWedding(String value) {
		this.leftWedding = value;
	}
	
	/**
	 * Gets the value of the hashedLeftWedding property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHashedLeftWedding() {
		return hashedLeftWedding;
	}
	
	/**
	 * Sets the value of the hashedLeftWedding property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHashedLeftWedding(String value) {
		this.hashedLeftWedding = value;
	}
	
	/**
	 * Gets the value of the leftWeddingQuality property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getLeftWeddingQuality() {
		return leftWeddingQuality;
	}
	
	/**
	 * Sets the value of the leftWeddingQuality property.
	 * 
	 * @param value allowed object is {@link Integer }
	 */
	public void setLeftWeddingQuality(Integer value) {
		this.leftWeddingQuality = value;
	}
	
	/**
	 * Gets the value of the leftSmall property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getLeftSmall() {
		return leftSmall;
	}
	
	/**
	 * Sets the value of the leftSmall property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setLeftSmall(String value) {
		this.leftSmall = value;
	}
	
	/**
	 * Gets the value of the hashedLeftSmall property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getHashedLeftSmall() {
		return hashedLeftSmall;
	}
	
	/**
	 * Sets the value of the hashedLeftSmall property.
	 * 
	 * @param value allowed object is {@link String }
	 */
	public void setHashedLeftSmall(String value) {
		this.hashedLeftSmall = value;
	}
	
	/**
	 * Gets the value of the leftSmallQuality property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getLeftSmallQuality() {
		return leftSmallQuality;
	}
	
	/**
	 * Sets the value of the leftSmallQuality property.
	 * 
	 * @param value allowed object is {@link Integer }
	 */
	public void setLeftSmallQuality(Integer value) {
		this.leftSmallQuality = value;
	}
	
}