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


import java.net.Socket;


/**
 * @author andreas schlegel
 */
public class TCPClient
    extends TCPClientAbstract {

  protected final static int NULL = -1;

  protected final static int LISTENER = 0;

  protected final static int EVENT = 1;

  private int _myMode = NULL;

  private Object _myParent;

  private NetworkPlug _myNetPlug;

  private String _myName = "";

  /**
   *
   * @param theAddress String
   * @param thePort int
   */

  public TCPClient(final Object theObject,
                   final String theAddress,
                   final int thePort) {
    super(theAddress, thePort);
    _myParent = theObject;
    initEvent();
  }


  /**
   *
   * @param theObject Object
   * @param theAddress String
   * @param thePort int
   * @param theMode int
   */
  public TCPClient(final Object theObject,
                   final String theAddress,
                   final int thePort,
                   final int theMode
      ) {
    super(theAddress, thePort, theMode);
    _myParent = theObject;
    initEvent();
  }


  /**
   *
   * @param theListener TcpPacketListener
   * @param theServerAddress String
   * @param theServerPort int
   * @param theMode int
   */
  public TCPClient(TCPPacketListener theListener,
                   String theServerAddress,
                   int theServerPort,
                   int theMode) {
    super(theListener, theServerAddress, theServerPort, theMode);
    _myMode = LISTENER;
  }



  /**
   *
   * @param theNetAddress NetAddress
   */
  public TCPClient(final Object theObject,
                   final NetworkAddress theNetAddress) {
    super(theNetAddress.address(), theNetAddress.port());
    _myParent = theObject;
    initEvent();
  }


  /**
   *
   * @param theNetAddress NetAddress
   */
  public TCPClient(final NetworkAddress theNetAddress) {
    super(theNetAddress.address(), theNetAddress.port());
  }



  /**
   *
   * @param theAddress String
   * @param thePort int
   */
  public TCPClient(final String theAddress,
                   final int thePort) {
    super(theAddress, thePort);
  }



  /**
   * @invisible
   */
  public TCPClient(TCPServerAbstract theTcpServer,
                   Socket theSocket,
                   TCPPacketListener theTcpPacketListener,
                   int theServerPort,
                   int theMode) {
    super(theTcpServer,
          theSocket,
          theTcpPacketListener,
          theServerPort,
          theMode);
    _myMode = LISTENER;
  }
  
  

  private void initEvent() {
    _myMode = EVENT;
    _myNetPlug = new NetworkPlug();
  }


  /**
   * @invisible
   * @param theIndex int
   */
  public void handleStatus(int theIndex) {
    switch (_myMode) {
      case (EVENT):
        _myNetPlug.status(theIndex);
        break;
      case (LISTENER):
        _myTcpPacketListener.status(theIndex);
        break;
      case (NULL):
        SNetwork.printDebug("TcpClient.handleStatus()","net status id " + theIndex);
        break;
    }
  }


  /**
   * @invisible
   */
  public void handleInput() {
    switch (_myMode) {
      case (EVENT):
        _myNetPlug.process(new TCPPacket(this, _myStringBuffer, _myBytes),_myServerPort);
        break;
      case (LISTENER):
        _myTcpPacketListener.process(new TCPPacket(this, _myStringBuffer, _myBytes),_myServerPort);
        break;
      case (NULL):
    	  SNetwork.printDebug("TcpClient.handleInput()","received a message : " + _myStringBuffer.toString());
        break;
    }
  }


  /**
   *
   * @return String
   */
  public String name() {
    return _myName;
  }


  /**
   *
   * @param theName String
   */
  public void setName(String theName) {
    _myName = theName;
  }

}
