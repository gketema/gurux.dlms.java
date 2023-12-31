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

import gurux.common.GXCmdParameter;
import gurux.common.GXCommon;
import gurux.common.enums.TraceLevel;
import gurux.dlms.GXDLMSTranslator;
import gurux.serial.GXSerial;

class Settings {
    public TraceLevel trace = TraceLevel.INFO;
    public int port = 4060;
    public String serial;
    public boolean useLogicalNameReferencing = true;
}

/**
 * @author Gurux Ltd
 */
public class GuruxDlmsServerExample {

    private static void updateKeys(final Settings settings, final GXDLMSBase server) {

        System.out.println(
                "System Title: " + GXCommon.bytesToHex(server.getCiphering().getSystemTitle()));
        System.out.println("Authentication key: "
                + GXCommon.bytesToHex(server.getCiphering().getAuthenticationKey()));
        System.out.println("Block cipher key: "
                + GXCommon.bytesToHex(server.getCiphering().getBlockCipherKey()));

        System.out.println(
                "Client System title: " + GXDLMSTranslator.toHex(server.getClientSystemTitle()));
        System.out.println("Master key (KEK) title: " + GXDLMSTranslator.toHex(server.getKek()));
    }

    /**
     * Server component that handles received DLMS messages.
     */
    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Settings settings = new Settings();
        // Create servers and start listen events.
        try {
            getParameters(args, settings);
            if (settings.serial != null) {
                GXDLMSBase server;
                if (settings.useLogicalNameReferencing) {
                    server = new GXDLMSServerLN(settings.serial);
                    System.out
                            .println("Logical Name DLMS Server in serial port " + settings.serial);
                } else {
                    server = new GXDLMSServerSN(settings.serial);
                    System.out.println("Short Name DLMS Server in serial port " + settings.serial);
                }
                try {
                    server.initialize(settings.serial, settings.trace);
                } catch (Exception ex) {
                    System.out
                            .println("----------------------------------------------------------");
                    System.out.println(ex.getMessage());
                    System.out.println("Available ports:");
                    StringBuilder sb = new StringBuilder();
                    for (String it : GXSerial.getPortNames()) {
                        if (sb.length() != 0) {
                            sb.append(", ");
                        }
                        sb.append(it);
                    }
                    System.out.println(sb.toString());
                    return;
                }
                updateKeys(settings, server);
                System.out.println("----------------------------------------------------------");
                System.out.println("Press Enter to close.");
                while (System.in.read() != 13) {
                    System.out.println("Press Enter to close.");
                }
                // Close server.
                server.close();
            } else {
                ///////////////////////////////////////////////////////////////////////
                // Create Gurux DLMS server component for Short Name
                // and start listen events.
                GXDLMSServerSN SNServer = new GXDLMSServerSN(String.valueOf(settings.port));
                SNServer.initialize(settings.port, settings.trace);
                updateKeys(settings, SNServer);
                System.out
                        .println("Short Name DLMS Server in port " + String.valueOf(settings.port));
                System.out.println("Example connection settings:");
                System.out.println("Gurux.DLMS.Client.Example.Net -r sn -h localhost -p "
                        + String.valueOf(settings.port));
                System.out.println("----------------------------------------------------------");
                ///////////////////////////////////////////////////////////////////////
                // Create Gurux DLMS server component for Logical Name
                // and start listen events.
                GXDLMSServerLN LNServer = new GXDLMSServerLN(String.valueOf(settings.port + 1));
                LNServer.initialize(settings.port + 1, settings.trace);
                updateKeys(settings, LNServer);
                System.out.println(
                        "Logical Name DLMS Server in port " + String.valueOf(settings.port + 1));
                System.out.println("Example connection settings:");
                System.out.println("Gurux.DLMS.Client.Example.Net -h=localhost -p "
                        + String.valueOf(settings.port + 1));
                System.out.println("----------------------------------------------------------");
                ///////////////////////////////////////////////////////////////////////
                // Create Gurux DLMS server component for Short Name
                // and start listen events.
                GXDLMSServerSN_47 SN_47Server =
                        new GXDLMSServerSN_47(String.valueOf(settings.port + 2));
                SN_47Server.initialize(settings.port + 2, settings.trace);
                updateKeys(settings, SN_47Server);
                System.out.println("Short Name DLMS Server with IEC 62056-47 in port "
                        + String.valueOf(settings.port + 2));
                System.out.println("Example connection settings:");
                System.out.println("Gurux.DLMS.Client.Example.Net -r sn -h localhost -i WRAPPER -p "
                        + String.valueOf(settings.port + 2));
                System.out.println("----------------------------------------------------------");
                ///////////////////////////////////////////////////////////////////////
                // Create Gurux DLMS server component for Logical Name
                // and start listen events.
                GXDLMSServerLN_47 LN_47Server =
                        new GXDLMSServerLN_47(String.valueOf(settings.port + 3));
                LN_47Server.initialize(settings.port + 3, settings.trace);
                updateKeys(settings, LN_47Server);
                System.out.println("Logical Name DLMS Server with IEC 62056-47 in port "
                        + String.valueOf(settings.port + 3));
                System.out.println("Example connection settings:");
                System.out.println("Gurux.DLMS.Client.Example.Net -h localhost -i WRAPPER -p "
                        + String.valueOf(settings.port + 3));

                ///////////////////////////////////////////////////////////////////////
                // Create Gurux DLMS server component for Logical Name
                // and start listen events.
                GXDLMSServerHdlcWithModeELN iecServer =
                        new GXDLMSServerHdlcWithModeELN(String.valueOf(settings.port + 4));
                iecServer.initialize(settings.port + 4, settings.trace);
                updateKeys(settings, iecServer);
                System.out.println(
                        "Logical Name DLMS Server in port " + String.valueOf(settings.port + 4));
                System.out.println("Example connection settings:");
                System.out.println("Gurux.DLMS.Client.Example.Net -h=localhost -i HdlcWithModeE -p "
                        + String.valueOf(settings.port + 4));
                System.out.println("----------------------------------------------------------");

                System.out.println("Press Enter to close.");
                while (System.in.read() != 13) {
                    System.out.println("Press Enter to close.");
                }
                /// Close servers.
                System.out.println("Closing servers.");
                SNServer.close();
                LNServer.close();
                SN_47Server.close();
                LN_47Server.close();
                System.out.println("Servers closed.");
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
            throw ex;
        }
    }

    private static int getParameters(String[] args, Settings settings) throws IOException {
        java.util.List<GXCmdParameter> parameters = GXCommon.getParameters(args, "t:p:S:r:");
        for (GXCmdParameter it : parameters) {
            switch (it.getTag()) {
            case 't':
                // Trace.
                try {
                    settings.trace = TraceLevel.valueOf(it.getValue().toUpperCase());
                } catch (RuntimeException e) {
                    throw new IllegalArgumentException(
                            "Invalid Authentication option. (Error, Warning, Info, Verbose, Off)");
                }
                break;
            case 'p':
                // Port.
                settings.port = Integer.parseInt(it.getValue());
                break;
            case 'r':
                if ("sn".compareTo(it.getValue()) == 0) {
                    settings.useLogicalNameReferencing = false;
                } else if ("ln".compareTo(it.getValue()) == 0) {
                    settings.useLogicalNameReferencing = true;
                } else {
                    throw new IllegalArgumentException("Invalid reference option.");
                }
                break;
            case 'S':
                // serial port.
                settings.serial = it.getValue();
                break;
            case '?':
                switch (it.getTag()) {
                case 'p':
                    throw new IllegalArgumentException("Missing mandatory port option.");
                case 't':
                    throw new IllegalArgumentException("Missing mandatory trace option.");
                case 'r':
                    throw new IllegalArgumentException("Missing mandatory reference option.");
                case 'S':
                    throw new IllegalArgumentException("Missing mandatory Serial port option.");
                case 'K':
                    throw new IllegalArgumentException(
                            "Missing mandatory private key file option.");
                case 'k':
                    throw new IllegalArgumentException("Missing mandatory public key file option.");
                default:
                    showHelp();
                    return 1;
                }
            default:
                showHelp();
                return 1;
            }
        }
        return 0;
    }

    private static void showHelp() {
        System.out.println("Gurux DLMS example Server implements four DLMS/COSEM devices.\r\n");
        System.out.println(" -t [Error, Warning, Info, Verbose] Trace messages.\r\n");
        System.out.println(" -p Start port number. Default is 4060.\r\n");
        System.out.println(" -S \t serial port.");
        System.out.println(
                " -r [sn, sn]\t Short name or Logican Name (default) referencing is used.");
        System.out.println(" -T \t System title of the meter. Ex -T 4775727578313233");

    }
}
