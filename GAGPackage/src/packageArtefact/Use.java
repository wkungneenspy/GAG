//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.05 at 12:33:35 PM WAT 
//


package packageArtefact;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}inputParameter"/>
 *         &lt;element ref="{}outputParameter"/>
 *       &lt;/sequence>
 *       &lt;attribute name="location" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="manually" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="order" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="serviceName" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "inputParameter",
    "outputParameter"
})
@XmlRootElement(name = "use")
public class Use  implements Serializable{

    @XmlElement(required = true)
    protected InputParameter inputParameter;
    @XmlElement(required = true)
    protected OutputParameter outputParameter;
    @XmlAttribute(name = "location", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String location;
    @XmlAttribute(name = "manually")
    protected Boolean manually;
    @XmlAttribute(name = "order", required = true)
    protected boolean order;
    @XmlAttribute(name = "serviceName", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String serviceName;
    @XmlAttribute(name = "type", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NCName")
    protected String type;

    /**
     * Gets the value of the inputParameter property.
     * 
     * @return
     *     possible object is
     *     {@link InputParameter }
     *     
     */
    public InputParameter getInputParameter() {
        return inputParameter;
    }

    /**
     * Sets the value of the inputParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link InputParameter }
     *     
     */
    public void setInputParameter(InputParameter value) {
        this.inputParameter = value;
    }

    /**
     * Gets the value of the outputParameter property.
     * 
     * @return
     *     possible object is
     *     {@link OutputParameter }
     *     
     */
    public OutputParameter getOutputParameter() {
        return outputParameter;
    }

    /**
     * Sets the value of the outputParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link OutputParameter }
     *     
     */
    public void setOutputParameter(OutputParameter value) {
        this.outputParameter = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the manually property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isManually() {
        return manually;
    }

    /**
     * Sets the value of the manually property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setManually(Boolean value) {
        this.manually = value;
    }

    /**
     * Gets the value of the order property.
     * 
     */
    public boolean isOrder() {
        return order;
    }

    /**
     * Sets the value of the order property.
     * 
     */
    public void setOrder(boolean value) {
        this.order = value;
    }

    /**
     * Gets the value of the serviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
