package com.example.cobweb.treat.user;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.example.cobweb.treat.Tools;

public final class Position {
	private InetAddress address;
	private int port;
	private int hashCode;
	private byte type=3;
	public Position(DatagramPacket packet) {
		Start(packet.getAddress(),packet.getPort());
	}
	public Position(byte[] data) throws UnknownHostException {
		Start(InetAddress.getByAddress(Tools.byteArrayCut(0, 4, data)),Tools.shortToInt(Tools.byteArrayToShort(Tools.byteArrayCut(4,2, data))));
	}
	public Position(InetAddress address,int port) {
		Start(address,port);
	}
	public Position(String position) throws UnknownHostException {
		String[] str= position.split(":");
		Start(InetAddress.getByName(str[0]),Integer.parseInt(str[1]));
	}
	
	private void Start(InetAddress address,int port) {
		this.address=address;
		this.port=port;
		int temporary=port<<16|port;
		int temAddress=Tools.byteArrayToInt(address.getAddress());
		hashCode=temporary&temAddress;
	}
	
	public InetAddress getAddress() {
		return address;
	}
	public int getPort() {
		return port;
	}
	public boolean isPortSame(byte[] port) {
		return Tools.intToByteArray(this.port)[0]==port[0]&Tools.intToByteArray(this.port)[1]==port[1];
	}
	public byte[] getByteArray() {
		return Tools.byteArrayMerge(address.getAddress(),Tools.shortToByteArray(Tools.intToShort(port)));
	}
	public DatagramPacket getDatagramPacket(byte[] data) {
		return new DatagramPacket(data,data.length,address,port);
	}
	
	public String toString() {
		StringBuffer position=new StringBuffer(address.getHostAddress());
		position.append(":");
		position.append(port);
		return position.toString();
	}
	
	public boolean equals(Object position) {
		return position.hashCode()==this.hashCode;
	}
	public int hashCode(){
		return hashCode;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		if(type<4&type>=0) {
			this.type = type;
		}
	}
}
