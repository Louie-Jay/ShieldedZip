package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

public class ExtractModel {
	
	private ArrayList<File> files;
	private String destination;
	private String errorMsg;
	private ZipFile zipFile;
	
	public ExtractModel() {
		files = new ArrayList<File>();
		destination = "C:\\";
		errorMsg = "Unknown";
	}
	
	public boolean extract(File prmFile) {
		try {
			zipFile = new ZipFile(prmFile.getAbsolutePath());
			
			zipFile.extractAll(destination);
			zipFile = null;
			return true;
		} catch (ZipException e) {
			String parseError = e.getMessage().substring(e.getMessage().indexOf("("), e.getMessage().indexOf(")"));
			errorMsg = parseError+")...";
			return false;
		}
	}
	
	public boolean extract(File prmFile, char[] prmPassword) {
		try {
			zipFile = new ZipFile(prmFile.getAbsolutePath());
			zipFile.setPassword(prmPassword);
			zipFile.extractAll(destination);
			zipFile = null;
			return true;
		} catch (ZipException e) {
			try {
				String parseError = e.getMessage().substring(e.getMessage().indexOf("("), e.getMessage().indexOf(")"));
				errorMsg = parseError+")...";
				return false;
			} catch (Exception e2) {
				errorMsg = "Wrong password!";
				return false;
			}
		}
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(File prmDest) throws IOException, Exception {
		String canonicalDestinationDirPath = prmDest.getCanonicalPath();
		File destinationfile = new File(prmDest, prmDest.getName());
		String canonicalDestinationFile = destinationfile.getCanonicalPath();
		if (!canonicalDestinationFile.startsWith(canonicalDestinationDirPath + File.separator)) {
		  errorMsg = "Entry is outside of the target dir: " + prmDest.getName();
		  throw new Exception(errorMsg);
		}else {
			destination = prmDest.getAbsolutePath();
		}
	}
	
	public void clearFiles() {
		files = null;
		files = new ArrayList<File>();
	}
	
	public void setFile(ArrayList<File> prmFile) {
		files = prmFile;
	}
	
	public ArrayList<File> getFiles(){
		return files;
	}

}
