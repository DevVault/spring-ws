/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.ws.soap.saaj;

import java.util.Locale;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;

import org.springframework.util.Assert;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.soap11.Soap11Fault;

/**
 * Internal class that uses SAAJ 1.3 to implement the <code>Soap11Fault</code> interface.
 *
 * @author Arjen Poutsma
 */
class Saaj13Soap11Fault implements Soap11Fault {

    private final SOAPFault saajFault;

    Saaj13Soap11Fault(SOAPFault saajFault) {
        Assert.notNull(saajFault, "No saajFault given");
        this.saajFault = saajFault;
    }

    public Locale getFaultStringLocale() {
        return saajFault.getFaultStringLocale();
    }

    public String getFaultString() {
        return saajFault.getFaultString();
    }

    public Source getSource() {
        return new DOMSource(saajFault);
    }

    public QName getName() {
        return saajFault.getElementQName();
    }

    public SoapFaultDetail addFaultDetail() {
        try {
            Detail saajDetail = saajFault.addDetail();
            return saajDetail != null ? new Saaj13SoapFaultDetail(saajDetail) : null;
        }
        catch (SOAPException ex) {
            throw new SaajSoapFaultException(ex);
        }
    }

    public SoapFaultDetail getFaultDetail() {
        Detail saajDetail = saajFault.getDetail();
        return saajDetail != null ? new Saaj13SoapFaultDetail(saajDetail) : null;
    }

    public String getFaultActorOrRole() {
        return saajFault.getFaultActor();
    }

    public void setFaultActorOrRole(String faultActor) {
        try {
            saajFault.setFaultActor(faultActor);
        }
        catch (SOAPException ex) {
            throw new SaajSoapFaultException(ex);
        }
    }

    public QName getFaultCode() {
        return saajFault.getFaultCodeAsQName();
    }
}
