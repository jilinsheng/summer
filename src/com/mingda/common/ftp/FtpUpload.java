package com.mingda.common.ftp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUpload {

	static Logger logger = Logger.getLogger(FtpUpload.class);
	String hostName = "10.1.1.101";

	String userName = "wangjia";

	String password = "happy";

	String remoteDir = "html";

	String port = "21";

	public FtpUpload() {
		if (remoteDir == null || remoteDir.equalsIgnoreCase("")) {
			remoteDir = null;
		}
	}

	public boolean UploadFile(InputStream localfileins, String remotefilename) {
		FTPClient ftp = new FTPClient();
		int reply;
		try {
			ftp.connect(hostName, Integer.parseInt(port));
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return false;
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					f.printStackTrace();
					return false;
				}
			}
			e.printStackTrace();
		}

		try {
			if (!ftp.login(userName, password)) {
				ftp.logout();
				return false;
			}

			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			ftp.enterLocalPassiveMode();

			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

			InputStream input = localfileins;

			String[] dirs = remotefilename.split("/");

			ftp.changeWorkingDirectory("/");

			for (int i = 1; i < dirs.length - 1; i++) {
				if (!ftp.changeWorkingDirectory(dirs[i])) {
					ftp.makeDirectory(dirs[i]);
				}
				ftp.changeWorkingDirectory(dirs[i]);
			}

			ftp.storeFile(dirs[dirs.length - 1], input);

			input.close();

			ftp.logout();

		} catch (FTPConnectionClosedException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean UploadFile(String localfilename, String remotefilename) {
		FTPClient ftp = new FTPClient();
		int reply;
		try {
			ftp.connect(hostName, Integer.parseInt(port));
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return false;
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					f.printStackTrace();
					return false;
				}
			}
			e.printStackTrace();
		}

		try {
			if (!ftp.login(userName, password)) {
				ftp.logout();
				return false;
			}
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// ftp.setFileType(FTP.ASCII_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			// ftp.changeWorkingDirectory(remoteDir);
			ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
			InputStream input = new FileInputStream(localfilename);
			String[] dirs = remotefilename.split("/");

			for (String a : dirs) {
			}
			ftp.changeWorkingDirectory("/");
			for (int i = 1; i < dirs.length - 1; i++) {
				if (!ftp.changeWorkingDirectory(dirs[i])) {
					ftp.makeDirectory(dirs[i]);
				}
				ftp.changeWorkingDirectory(dirs[i]);
			}
			ftp.storeFile(remotefilename, input);
			input.close();
			ftp.logout();
		} catch (FTPConnectionClosedException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
			e.printStackTrace();
			return false;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String args[]) throws Exception {
		FtpUpload ftpup = new FtpUpload();
		ftpup.UploadFile("c:/debuglog.log", "/html/aa/a/a/debuglog.log");
	}

}
