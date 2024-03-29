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


import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * NetAddress is an Object that contains an inetaddress
 * of an remote internet address, consisting of an
 * ip address and a port number.
 * @author andreas schlegel
 * 
 */
public class NetworkAddress {
  protected InetAddress inetaddress = null;

  protected String hostAddress;

  public String name = "";

  protected int port = 0;

  protected boolean isValid = false;

  /**
   *
   * @param theAddress String
   * @param thePort int
   */
  public NetworkAddress(final String theAddress,
                    final int thePort) {
    hostAddress = theAddress;
    port = thePort;
    if (thePort > 0) {
      try {
        inetaddress = InetAddress.getByName(theAddress);
        isValid = true;
      }
      catch (UnknownHostException e) {
        System.out.println("no such host " + inetaddress);
      }
    }
  }

  public NetworkAddress(NetworkAddress theNetAddress) {
   this(theNetAddress.address(),theNetAddress.port());
  }


  /**
   *
   * @param theInetAddress InetAddress
   * @param thePort int
   */
  public NetworkAddress(InetAddress theInetAddress, int thePort) {
    inetaddress = theInetAddress;
    hostAddress = inetaddress.getHostAddress();
    port = thePort;
  }



  /**
   *
   * @return InetAddress
   */
  public InetAddress inetaddress() {
    return inetaddress;
  }



  /**
   *returns the remote ip address as string
   * @return String
   */
  public String address() {
    return hostAddress;
  }



  /**
   *returns the remote port number
   * @return int
   */
  public int port() {
    return port;
  }



  /**
   *check if the netAddress is valid. this is true if
   * the remote ip address was found.
   * @return boolean
   */
  public boolean isvalid() {
    return isValid;
  }

  public String toString() {
    return hostAddress+":"+port;
  }
}
