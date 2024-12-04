package clockSynch;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TimeServerInterface extends Remote {
	long getTime() throws RemoteException;
}
