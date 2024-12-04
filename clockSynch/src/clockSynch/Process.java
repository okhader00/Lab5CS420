package clockSynch;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class Process extends UnicastRemoteObject implements ProcessInterface {
    private long localClock;
    private final TimeServerInterface timeServer;
    private final long driftAmount; //Amount of drift applied each interval to simulate clock desynch
    private long lastUpdatedTime;	//Keeps track of last time local clock was updated

    public Process(TimeServerInterface timeServer, long initialTime, long driftAmount) throws RemoteException {
        super();
        this.timeServer = timeServer;
        this.localClock = initialTime; 
        this.driftAmount = driftAmount;
        this.lastUpdatedTime = initialTime;
    }

    @Override
    public void synchronize() throws RemoteException {
        updateLocalClock();
    	long serverTime = timeServer.getTime();
        long offset = serverTime - localClock;	//Calculate how far local clock is from server clock
        localClock += offset; //Adjust local clock
        lastUpdatedTime = System.currentTimeMillis();
        System.out.println("Synchronized. Local clock adjusted by " + offset*(-1) + "ms.");
    }
    
    private synchronized void updateLocalClock() {
    	long currentTime = System.currentTimeMillis();
    	long elapsedTime = currentTime - lastUpdatedTime; //Time since clock was last updated
    	localClock += elapsedTime + (elapsedTime * driftAmount / 1000);	//Update local clock and add artificial drift
    	lastUpdatedTime = currentTime;
    }

    public synchronized long getLocalClock() {
        updateLocalClock();
    	return localClock;
    }
   
}
