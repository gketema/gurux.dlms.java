//
// --------------------------------------------------------------------------
//  Gurux Ltd
// 
//
//
// Filename:        $HeadURL:  $
//
// Version:         $Revision: $,
//                  $Date:  $
//                  $Author: $
//
// Copyright (c) Gurux Ltd
//
//---------------------------------------------------------------------------
//
//  DESCRIPTION
//
// This file is a part of Gurux Device Framework.
//
// Gurux Device Framework is Open Source software; you can redistribute it
// and/or modify it under the terms of the GNU General Public License 
// as published by the Free Software Foundation; version 2 of the License.
// Gurux Device Framework is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of 
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
// See the GNU General Public License for more details.
//
// More information of Gurux DLMS/COSEM Director: https://www.gurux.org/GXDLMSDirector
//
// This code is licensed under the GNU General Public License v2. 
// Full text may be retrieved at http://www.gnu.org/licenses/gpl-2.0.txt
//---------------------------------------------------------------------------

package gurux.dlms.server.example;

import java.io.IOException;

import javax.xml.stream.XMLStreamException;

import gurux.dlms.enums.InterfaceType;
import gurux.dlms.objects.GXDLMSAssociationLogicalName;
import gurux.dlms.objects.GXDLMSHdlcSetup;
import gurux.dlms.objects.GXDLMSIECLocalPortSetup;

/**
 * DLMS Server that uses Logical Name referencing with IEC 62056-46 Data link
 * layer using HDLC protocol. Example Iskraemeco and Actaris uses this.
 */
public class GXDLMSServerHdlcWithModeELN extends GXDLMSBase {
    public GXDLMSServerHdlcWithModeELN(final String port) throws XMLStreamException, IOException {
        super(new GXDLMSAssociationLogicalName("0.0.40.0.1.255"), new GXDLMSHdlcSetup(), port,
                InterfaceType.HDLC_WITH_MODE_E);
        setFlaID("GRX");
        // Add local port setup.
        GXDLMSIECLocalPortSetup lps = new GXDLMSIECLocalPortSetup();
        getItems().add(lps);
        setLocalPortSetup(lps);
    }
}