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

import java.net.DatagramPacket;
import java.util.Vector;


/**
 * Multicast is a method of forwarding IP datagrams to a group of interested receivers.
 * UDP is used as the transport portocol.
 */
public class Multicast extends MulticastAbstract implements UDPPacketListener {


    protected Object _myParent;

    protected NetworkPlug _myNetPlug;

    /**
     * create a new instance of Multicast. the buffersize of the datagrams
     * is set to 1536 by default.
     *
     * @param theObject Object
     * @param theMulticastAddress String
     * @param thePort int
     * @param theBufferSize int
     */
    public Multicast(final Object theObject,
                     final String theMulticastAddress,
                     final int thePort,
                     final int theBufferSize) {
        super(null, theMulticastAddress, thePort, theBufferSize);
        _myParent = theObject;
        _myListener = this;
        _myNetPlug = new NetworkPlug();
        init(theMulticastAddress,thePort);
    }


    public Multicast(final Object theObject,
                     final String theMulticastAddress,
                     final int thePort) {
        super(null, theMulticastAddress, thePort, 1536);
        _myParent = theObject;
        _myListener = this;
        _myNetPlug = new NetworkPlug();
        init(theMulticastAddress,thePort);
    }


    public Multicast(final UDPPacketListener theDatagramListener,
                     final String theMulticastAddress,
                     final int thePort,
                     final int theBufferSize) {
        super(theDatagramListener, theMulticastAddress, thePort, theBufferSize);
    }


    public Multicast(final UDPPacketListener theDatagramListener,
                     final String theMulticastAddress,
                     final int thePort) {
        super(theDatagramListener, theMulticastAddress, thePort);
    }

    /**
     * @invisible
     * @param thePacket DatagramPacket
     * @param thePort int
     */
    public void process(DatagramPacket thePacket, int thePort) {
        _myNetPlug.process(thePacket,thePort);
    }
    
	
	public void addListener(NetworkListener theListener) {
		_myNetPlug.addListener(theListener);
	}
	
	
	public void removeListener(NetworkListener theListener) {
		_myNetPlug.removeListener(theListener);
	}
	
	public NetworkListener getListener(int theIndex) {
		return _myNetPlug.getListener(theIndex);
	}

	public Vector getListeners() {
		return _myNetPlug.getListeners();
	}

}
