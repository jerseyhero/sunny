
package client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SayHello1Response_QNAME = new QName("http://service/", "sayHello1Response");
    private final static QName _SayHello1_QNAME = new QName("http://service/", "sayHello1");
    private final static QName _SayHello2_QNAME = new QName("http://service/", "sayHello2");
    private final static QName _SayHello2Response_QNAME = new QName("http://service/", "sayHello2Response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayHello1Response }
     * 
     */
    public SayHello1Response createSayHello1Response() {
        return new SayHello1Response();
    }

    /**
     * Create an instance of {@link SayHello1 }
     * 
     */
    public SayHello1 createSayHello1() {
        return new SayHello1();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "sayHello1Response")
    public JAXBElement<SayHello1Response> createSayHello1Response(SayHello1Response value) {
        return new JAXBElement<SayHello1Response>(_SayHello1Response_QNAME, SayHello1Response.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "sayHello1")
    public JAXBElement<SayHello1> createSayHello1(SayHello1 value) {
        return new JAXBElement<SayHello1>(_SayHello1_QNAME, SayHello1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello1 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "sayHello2")
    public JAXBElement<SayHello1> createSayHello2(SayHello1 value) {
        return new JAXBElement<SayHello1>(_SayHello2_QNAME, SayHello1 .class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SayHello1Response }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service/", name = "sayHello2Response")
    public JAXBElement<SayHello1Response> createSayHello2Response(SayHello1Response value) {
        return new JAXBElement<SayHello1Response>(_SayHello2Response_QNAME, SayHello1Response.class, null, value);
    }

}
