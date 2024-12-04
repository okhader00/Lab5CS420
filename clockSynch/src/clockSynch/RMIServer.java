package clockSynch;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
	public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099); //Start RMI Registry
            long initialTime = System.currentTimeMillis();	//Initialize clock with current system time
            long incrementInterval = 1000; //Increment clock every second
            TimeServerInterface server = new TimeServer(initialTime, incrementInterval);
            Naming.rebind("TimeServer", server);
            System.out.println("TimeServer ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
