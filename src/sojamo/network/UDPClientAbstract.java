/**
 *  NetP5 is a processing and java library for tcp and udp ip communication.
 *
 *  2006 by Andreas Schlegel
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 *
 * @author Andreas Schlegel (http://www.sojamo.de)
 *
 */

package sojamo.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @invisible
 */
public abstract class UDPClientAbstract {

    protected NetworkAddress _myNetAddress;

    protected DatagramSocket _mySocket;

    protected boolean isRunning = false;

    /**
     * @invisible
     */
    public UDPClientAbstract() {
	isRunning = openSocket();
    }

    /**
     * @invisible
     * @param theAddr
     *                String
     * @param thePort
     *                int
     */
    public UDPClientAbstract(String theAddr, int thePort) {

	_myNetAddress = new NetworkAddress(theAddr, thePort);

	if (!_myNetAddress.isvalid()) {
	    SNetwork.printError("UdpClient", "unknown host " + theAddr);
	}
	isRunning = openSocket();
    }

    /**
     * get the datagram socket of the UDP client. more info at
     * java.net.DatagramSocket
     * 
     * @return DatagramSocket
     */
    public DatagramSocket socket() {
	return _mySocket;
    }

    private boolean openSocket() {
	try {
	    _mySocket = new DatagramSocket();
	} catch (SocketException e) {
	    SNetwork.printError("UdpClient.openSocket", "cant create socket "
		    + e.getMessage());
	    return false;
	}

	SNetwork.printProcess("UdpClient.openSocket", "udp socket initialized.");
	return true;
    }

    /**
     * send a string using UDP to an already specified RemoteAddress.
     * 
     * @param theString
     */
    public void send(String theString) {
	send(theString.getBytes());
    }

    /**
     * send a byte array using UDP to an already specified RemoteAddress.
     * 
     * @param theBytes
     *                byte[]
     */
    public void send(byte[] theBytes) {
	if (_myNetAddress.isvalid()) {
	    send(theBytes, _myNetAddress);
	} else {
	    SNetwork
		    .printWarning("UdpClient.send",
			    "no InetAddress and port has been set. Packet has not been sent.");
	}
    }

    /**
     * send a byte array to the dedicated remoteAddress.
     * 
     * @param theBytes
     * @param theNetAddress
     */
    public void send(final byte[] theBytes, final NetworkAddress theNetAddress) {
	if (_myNetAddress.isvalid()) {
	    send(theBytes, theNetAddress.inetaddress(), theNetAddress.port());
	}
    }

    /**
     * send a byte array to the dedicated remoteAddress.
     * 
     * @param thePacket
     *                OscPacket
     * @param theAddress
     *                String
     * @param thePort
     *                int
     */
    public void send(final byte[] theBytes, final String theAddress,
	    final int thePort) {
	try {
	    InetAddress myInetAddress = InetAddress.getByName(theAddress);
	    send(theBytes, myInetAddress, thePort);
	} catch (UnknownHostException e) {
	    SNetwork.printError("UdpClient.send", "while sending to "
		    + theAddress + " " + e);
	}
    }

    /**
     * @invisible
     * @param thePacket
     *                DatagramPacket
     */
    public void send(DatagramPacket thePacket) {
	if (isRunning) {
	    try {
		_mySocket.send(thePacket);

	    } catch (IOException e) {
		SNetwork.printError("UdpClient.send",
			"ioexception while sending packet.");
	    }
	}
    }

    /**
     * send a byte array to the dedicated remoteAddress.
     * 
     * @param theBytes
     *                byte[]
     * @param theAddress
     *                InetAddress
     * @param thePort
     *                int
     */
    public void send(final byte[] theBytes, final InetAddress theAddress,
	    final int thePort) {
	if (isRunning) {
	    try {
		DatagramPacket myPacket = new DatagramPacket(theBytes,
			theBytes.length, theAddress, thePort);
		send(myPacket);
	    } catch (NullPointerException npe) {
		SNetwork.printError("UdpClient.send",
			"a nullpointer exception occured." + npe);
	    }
	} else {
	    SNetwork.printWarning("UdpClient.send",
		    "DatagramSocket is not running. Packet has not been sent.");
	}
    }

}
