package com.ib.ibcheckweb.service.check;

import com.ib.ibcheckweb.twsapi.client.*;

public class GetPosition {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		GetPosition gepo= new GetPosition();
		gepo.getPosition();
		
    }
	
	
	public void getPosition() throws InterruptedException

	{
		EWrapperImpl wrapper = new EWrapperImpl();
		final EClientSocket m_client = wrapper.getClient();
		final EReaderSignal m_signal = wrapper.getSignal();
		m_client.eConnect("127.0.0.1", 7496, 0);
	//	m_client.eConnect("127.0.0.1", 4001, 1);

		//! [ereader]
		final EReader reader = new EReader(m_client, m_signal);   
		
		reader.start();
		//An additional thread is created in this program design to empty the messaging queue
		new Thread(() -> {
		    while (m_client.isConnected()) {
		        m_signal.waitForSignal();
		        try {
		            reader.processMsgs();
		        } catch (Exception e) {
		            System.out.println("Exception: "+e.getMessage());
		        }
		    }
		}).start();
		//! [ereader]
		// A pause to give the application time to establish the connection
		// In a production application, it would be best to wait for callbacks to confirm the connection is complete
		Thread.sleep(1000);
		   /*** Requesting all accounts' positions. ***/
        //! [reqpositions]
        wrapper.getClient().reqPositions();
        //! [reqpositions]
		Thread.sleep(2000);
		//! [cancelpositions]
		wrapper.getClient().cancelPositions();
		//! [cancelpositions]
		Thread.sleep(5000);		
		m_client.eDisconnect();
		
	}
	
	
	}
	


