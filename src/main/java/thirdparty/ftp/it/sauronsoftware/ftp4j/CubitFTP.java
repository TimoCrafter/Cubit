package thirdparty.ftp.it.sauronsoftware.ftp4j;

import java.io.File;
import java.io.IOException;

import de.keks.cubit.CubitPlugin;
import de.keks.internal.command.config.ConfigValues;

/**
 * Copyright:
 * <ul>
 * <li>Autor: Kekshaus</li>
 * <li>2016</li>
 * <li>www.minegaming.de</li>
 * </ul>
 * 
 */

public class CubitFTP {

	public static boolean upload(File localfile, String UUID) {
		String host = ConfigValues.ftpHostname;
		int port = ConfigValues.ftpPort;
		String user = ConfigValues.ftpUsername;
		String pass = ConfigValues.ftpPassword;

		try {
			FTPClient client = new FTPClient();
			try {
				client.connect(host, port);
				client.login(user, pass);
			} catch (IllegalStateException | FTPIllegalReplyException | FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			if (!client.getAndGotoDirectory("cubitsave/")) {
				client.createDirectory("cubitsave/");
				client.changeDirectory("cubitsave/");
			}
			if (!client.getAndGotoDirectory(UUID + "/")) {
				client.createDirectory(UUID + "/");
				client.changeDirectory(UUID + "/");
			}
			client.upload(localfile);
			client.disconnect(true);

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPDataTransferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPAbortedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean download(String regionid, File local, String UUID) { // this
																				// is
																				// a
																				// function
		String host = ConfigValues.ftpHostname;
		int port = ConfigValues.ftpPort;
		String user = ConfigValues.ftpUsername;
		String pass = ConfigValues.ftpPassword;

		try {
			FTPClient client = new FTPClient();
			try {
				client.connect(host, port);
				client.login(user, pass);
			} catch (IllegalStateException | FTPIllegalReplyException | FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

			if (!client.getAndGotoDirectory("cubitsave/" + UUID + "/")) {
				return false;
			}
			client.download(regionid + ".cubit", local);
			client.disconnect(true);

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPDataTransferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPAbortedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean delete(String regionid, String UUID) { // this is a
																	// function
		String host = ConfigValues.ftpHostname;
		int port = ConfigValues.ftpPort;
		String user = ConfigValues.ftpUsername;
		String pass = ConfigValues.ftpPassword;

		try {
			FTPClient client = new FTPClient();
			try {
				client.connect(host, port);
				client.login(user, pass);
			} catch (IllegalStateException | FTPIllegalReplyException | FTPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}

			if (!client.getAndGotoDirectory("cubitsave/" + UUID + "/")) {
				return false;
			}
			client.deleteFile(regionid + ".cubit");
			client.disconnect(true);

		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPIllegalReplyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FTPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
