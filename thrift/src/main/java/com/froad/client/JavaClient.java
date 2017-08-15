package com.froad.client;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;

import com.froad.shared.*;
import com.froad.tutorial.*;

import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class JavaClient {
  public static void main(String [] args) {

//    if (args.length != 1) {
//      System.out.println("Please enter 'simple' or 'secure'");
//      System.exit(0);
//    }

    try {
    	TSocket transport;
//      if (args[0].contains("simple")) {
        transport = new TSocket("localhost", 9090);
        transport.open();
//      }
//      else {
//        /*
//         * Similar to the server, you can use the parameters to setup client parameters or
//         * use the default settings. On the client side, you will need a TrustStore which
//         * contains the trusted certificate along with the public key. 
//         * For this example it's a self-signed cert. 
//         */
//        TSSLTransportParameters params = new TSSLTransportParameters();
//        params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
//        /*
//         * Get a client transport instead of a server transport. The connection is opened on
//         * invocation of the factory method, no need to specifically call open()
//         */
//        transport = TSSLTransportFactory.getClientSocket("localhost", 9091, 0, params);
//      }

      TProtocol protocol = new  TBinaryProtocol(transport);
      Calculator.Client client = new Calculator.Client(protocol);

      perform(client);
      
      try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
      try {
		if(transport.getSocket().getKeepAlive()) {
			  perform(client);
		  } else {
			  System.out.println("Socket is Closed");
		  }
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
      transport.close();
    } catch (TException x) {
      x.printStackTrace();
    } 
  }
  
  private static void run() throws UnknownHostException, IOException {
	  Socket s = new Socket("localhost", 9090);
	  if(s.isConnected()) {
		  System.out.println("Socket is Connect!");
	  }
	  try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  if(s.isConnected()) {
		  System.out.println("Socket is Connect!");
		  s.close();
	  } else {
		  System.out.println("Socket is disConnect!");
	  }
  }

  private static void perform(Calculator.Client client) throws TException
  {
    client.ping();
    System.out.println("ping()");

    int sum = client.add(1,1);
    System.out.println("1+1=" + sum);

    Work work = new Work();

    work.op = Operation.DIVIDE;
    work.num1 = 1;
    work.num2 = 0;
    try {
      int quotient = client.calculate(1, work);
      System.out.println("Whoa we can divide by 0");
    } catch (InvalidOperation io) {
      System.out.println("Invalid operation: " + io.why);
    }

    work.op = Operation.SUBTRACT;
    work.num1 = 15;
    work.num2 = 10;
    try {
      int diff = client.calculate(1, work);
      System.out.println("15-10=" + diff);
    } catch (InvalidOperation io) {
      System.out.println("Invalid operation: " + io.why);
    }
  }
}
