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

import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.util.Vector;

/**
 * @invisible
 */
public abstract class MulticastAbstract implements Runnable {

	protected NetworkAddress _myNetAddress;

	protected boolean isRunning;

	protected boolean isSocket;

	protected MulticastSocket _myMulticastSocket;

	protected UDPPacketListener _myListener;

	protected int _myDatagramSize = 1536;

	private Thread _myThread;


	/**
	 * @invisible
	 * @param theDatagramListener UdpPacketListener
	 * @param theMulticastAddress String
	 * @param thePort int
	 * @param theBufferSize int
	 */
	public MulticastAbstract(
			final UDPPacketListener theDatagramListener,
			final String theMulticastAddress, 
			final int thePort,
			final int theBufferSize) {
		_myDatagramSize = theBufferSize;
		_myListener = theDatagramListener;
		if (_myListener != null) {
			init(theMulticastAddress, thePort);
		}
	}

	/**
	 * @invisible
	 * @param theDatagramListener UdpPacketListener
	 * @param theMulticastAddress String
	 * @param thePort int
	 */
	public MulticastAbstract(
			final UDPPacketListener theDatagramListener,
			final String theMulticastAddress, 
			final int thePort) {
		_myListener = theDatagramListener;
		if (_myListener != null) {
			init(theMulticastAddress, thePort);
		}
	}

	protected void init(final String theMulticastAddress, final int thePort) {
		_myNetAddress = new NetworkAddress(theMulticastAddress, thePort);
		if (!_myNetAddress.isvalid()) {
			SNetwork.printError("UdpClient", "unknown host "
					+ theMulticastAddress);
		}
		isRunning = openSocket();
		start();
	}
	
	
	/**
	 * get the running multicast socket.
	 * @return MulticastSocket
	 */
	public MulticastSocket socket() {
		return _myMulticastSocket;
	}

	/**
	 * set the buffer size  of the datagrams received by the multicast socket.
	 * @param theDatagramSize int
	 */
	public void setDatagramSize(int theDatagramSize) {
		_myDatagramSize = theDatagramSize;
	}

	/**
	 * @invisible
	 */
	public void start() {
		_myThread = null;
		_myMulticastSocket = null;
		_myThread = new Thread(this);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException iex) {
			SNetwork.printError("Multicast.start()",
					"Multicast sleep interuption " + iex);
		}
		try {
			_myMulticastSocket = new MulticastSocket(_myNetAddress.port());
			_myMulticastSocket.joinGroup(_myNetAddress.inetaddress());
			SNetwork.printProcess("Multicast.start()",
					"new Multicast DatagramSocket created @ port "
							+ _myNetAddress.port());
		} catch (IOException ioex) {
			SNetwork.printError("Multicast.start()",
					" IOException, couldnt create new DatagramSocket @ port "
							+ _myNetAddress.port() + " " + ioex);
		}
		if (_myMulticastSocket != null) {
			_myThread.start();
			isRunning = _myThread.isAlive();
			isSocket = true;
		} else {
			isRunning = false;
		}
	}

	/**
	 * @invisible
	 */
	public void run() {
		if (_myMulticastSocket != null) {
			if (isRunning) {
				SNetwork.printProcess("Multicast.run()",
						"Multicast is running @ "
								+ _myNetAddress.inetaddress().getHostAddress()
								+ ":" + _myNetAddress.port());
			}
		} else {
			SNetwork.printError("UdpServer.run()",
					"Socket is null. closing UdpServer.");
			return;
		}

		while (isRunning) {
			try {
				byte[] myBuffer = new byte[_myDatagramSize];
				DatagramPacket myPacket = new DatagramPacket(myBuffer,
						_myDatagramSize);
				_myMulticastSocket.receive(myPacket);
				SNetwork.printDebug("Multicast.run()","got it.");
				_myListener.process(myPacket, _myNetAddress.port());
			} catch (IOException ioex) {
				SNetwork.printError("UdpServer.run()", "IOException:  " + ioex);
				break;
			} catch (ArrayIndexOutOfBoundsException ex) {
				SNetwork.printError("UdpServer.run()",
						"ArrayIndexOutOfBoundsException:  " + ex);
			}
		}
		dispose();
	}

	/**
	 * dispose the multicastSocket.
	 */
	public void dispose() {
		close();
	}

	/**
	 * @invisible
	 */
	public void close() {
		isRunning = false;
		if (_myMulticastSocket != null) {
			try {
				_myMulticastSocket.leaveGroup(_myNetAddress.inetaddress());
				_myMulticastSocket.disconnect();
				_myMulticastSocket.close();
				_myMulticastSocket = null;
				SNetwork.printProcess("Multicast.close",
						"Closing multicast datagram socket.");
			} catch (IOException e) {

			}
		}
	}

	private boolean openSocket() {
		try {
			_myMulticastSocket = new MulticastSocket();
		} catch (SocketException e) {
			SNetwork.printError("Multicast.openSocket", "cant create socket "
					+ e.getMessage());
			return false;
		} catch (IOException e) {
			SNetwork.printError("Multicast.openSocket",
					"cant create multicastSocket " + e.getMessage());
			return false;
		}
		SNetwork.printProcess("Multicast.openSocket",
				"multicast socket initialized.");
		return true;
	}

	/**
	 * Set the default time-to-live for multicast packets
	 * sent out on this MulticastSocket in order to control the scope
	 * of the multicasts. theTTL must be in the range 0 <= ttl <= 255
	 * @param theTTL int
	 * @return boolean
	 * @shortdesc Set the default time-to-live for multicast packets.
	 */
	public boolean setTimeToLive(int theTTL) {
		try {
			_myMulticastSocket.setTimeToLive(theTTL);
			return true;
		} catch (IOException ioe) {
			SNetwork.printError("UdpServer.setTimeToLive()", "" + ioe);
		} catch (IllegalArgumentException iae) {
			SNetwork.printError("UdpServer.setTimeToLive()", "" + iae);
		}
		return false;
	}

	/**
	 * get the current time to live value.
	 *
	 * @return int
	 */
	public int timeToLive() {
		try {
			return _myMulticastSocket.getTimeToLive();
		} catch (IOException ioe) {
			SNetwork.printError("Multicast.getTimeToLive()", "" + ioe);
		}
		return -1;
	}

	/**
	 * Disable/Enable local loopback of multicast datagrams.
	 * The option is used by the platform's networking code as a
	 * hint for setting whether multicast data will be
	 * looped back to the local socket.
	 * @shortdesc Disable/Enable local loopback of multicast datagrams.
	 * @param theFlag boolean
	 */
	public void setLoopback(boolean theFlag) {
		try {
			_myMulticastSocket.setLoopbackMode(theFlag);
		} catch (SocketException se) {
			SNetwork.printError("Multicast.setLoopback()", "" + se);
		}
	}

	/**
	 * get the current loopback mode. messages loop back to the local address
	 * if the loopback is set to false. set loopback to false to prevent messages
	 * to loop back to your local address.
	 *
	 * @return boolean
	 * @shortdesc get the current loopback mode.
	 */
	public boolean loopback() {
		try {
			return _myMulticastSocket.getLoopbackMode();
		} catch (SocketException se) {
			SNetwork.printError("Multicast.loopback()", "" + se);
		}
		return false;
	}

	protected void send(DatagramPacket thePacket) {
		if (isRunning) {
			try {
				_myMulticastSocket.send(thePacket);

			} catch (IOException e) {
				SNetwork.printError("Multicast.send",
						"ioexception while sending packet.");
			}
		}
	}

	/**
	 * send a string to the multicast address.
	 * @param theString String
	 */
	public void send(String theString) {
		send(theString.getBytes());
	}

	/**
	 * send a byte array to the mulitcast address.
	 * @param theBytes byte[]
	 */
	public void send(byte[] theBytes) {
		if (isRunning) {
			try {
				DatagramPacket myPacket = new DatagramPacket(theBytes,
						theBytes.length, _myNetAddress.inetaddress(),
						_myNetAddress.port());
				send(myPacket);
			} catch (NullPointerException npe) {
				SNetwork.printError("Multicast.send",
						"a nullpointer exception occured." + npe);
			}
		} else {
			SNetwork.printWarning("Multicast.send",
					"DatagramSocket is not running. Packet has not been sent.");
		}
	}

}
