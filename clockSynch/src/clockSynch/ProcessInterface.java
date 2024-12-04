package clockSynch;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ProcessInterface extends Remote {
	void synchronize() throws RemoteException;
}
