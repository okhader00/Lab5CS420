package clockSynch;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class TimeServer extends UnicastRemoteObject implements TimeServerInterface {
	private long referenceClock; //The server's own clock in milliseconds
    private final long incrementInterval; //How often the clock is incremented (in milliseconds)
	
    public TimeServer(long initialTime, long incrementInterval) throws RemoteException {
        super();
        this.referenceClock = initialTime; //Initialize the reference clock
        this.incrementInterval = incrementInterval;

        //Start a periodic task to increment the clock
        Timer timer = new Timer(true); //Daemon thread to simulate running clock
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                incrementClock();
            }
        }, incrementInterval, incrementInterval);
    }

    @Override
    public long getTime() throws RemoteException {
        return referenceClock; //Returns current time in milliseconds
    }
    
    private synchronized void incrementClock() {
        referenceClock += incrementInterval;
    }
}
