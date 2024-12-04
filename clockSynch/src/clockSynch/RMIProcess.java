package clockSynch;

import java.rmi.Naming;
import java.util.concurrent.TimeUnit;

public class RMIProcess {
	public static void main(String[] args) {
        try {
            TimeServerInterface timeServer = (TimeServerInterface) Naming.lookup("rmi://localhost/TimeServer");
            long initialTime = System.currentTimeMillis(); //Start with the current system time
            long driftAmount = 5; //Clock drifts by 5ms each interval (once every second)
            
            Process process = new Process(timeServer, initialTime, driftAmount);
            System.out.println("Initial local clock: " + process.getLocalClock());
            
            while (true) {
            	TimeUnit.SECONDS.sleep(5);	//Wait to synchronize after every 5 seconds to allow drift to accumulate
            	System.out.println("Before synchronization, local clock: " + process.getLocalClock());
            	process.synchronize();
                System.out.println("Synchronized local clock: " + ((Process) process).getLocalClock());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
