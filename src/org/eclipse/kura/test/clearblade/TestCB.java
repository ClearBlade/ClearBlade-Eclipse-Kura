package org.eclipse.kura.test.clearblade;

import java.util.HashMap;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.clearblade.java.api.ClearBlade;
import com.clearblade.java.api.ClearBladeException;
import com.clearblade.java.api.InitCallback;
import com.clearblade.java.api.Message;

public class TestCB {
	private static final Logger s_logger = LoggerFactory.getLogger(TestCB.class);

    private static final String APP_ID = "org.eclipse.kura.example.hello_osgi";

    protected void activate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has started!");

        s_logger.debug(APP_ID + ": This is a debug message.");
        initCB();

    }

    protected void deactivate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has stopped!");

    }
    
    private void initCB() {
    	InitCallback initCallback = new InitCallback() {
			
			@Override
			public void done(boolean results) {
				
				System.out.println("ClearBlade platform initialized");
				Message message = new Message("clientID-test", 1);
				try {
					Thread.sleep(2000);
				} catch(InterruptedException ex) {
					ex.getMessage();
				}
				
				message.publish("hellothere", "yes kura works");
			}
			
			@Override
			public void error(ClearBladeException error) {
				
				String message = error.getMessage();
				System.out.println(message);
			}
		};
		
		String systemKey = "d2f7d2dc0ab8cfbfa49cf0feb50b";
		String systemSecret = "D2F7D2DC0AD0E6AEB89AB0E6FAB501";
		String userEmail = "test@clearblade.com";
		String userPassword = "clearblade";
		String platformURL = "https://rtp.clearblade.com";
		String messagingURL = "tcp://rtp.clearblade.com:1883";
		//user = new User(userEmail);
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put("email", userEmail);
		options.put("password", userPassword);
		options.put("platformURL", platformURL);
		options.put("messagingURL", messagingURL);
		
		ClearBlade.initialize(systemKey, systemSecret, options, initCallback);
}
}
