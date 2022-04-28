package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;


public class CompressModel {
	
	private ArrayList<File> files;
	private String destination;
	private ZipFile zipFile;
	private String errorMsg;
	
	public CompressModel() {
		files = new ArrayList<File>();
		destination ="C:\\";
		errorMsg = "Unknown";
		
	}
	public boolean compress(String prmZipName, ZipParameters prmZipPrm, char[] prmPassword) {
		zipFile = new ZipFile(destination+"\\"+prmZipName+".zip");
		zipFile.setPassword(prmPassword);
		if (compress(prmZipName, prmZipPrm)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean compress(String prmZipName, ZipParameters prmZipPrm) {
		try {
			if (zipFile != null) {
				zipFile.addFiles(files, prmZipPrm);
			}else {
				zipFile = new ZipFile(destination+"\\"+prmZipName+".zip");
				zipFile.addFiles(files, prmZipPrm);
			}
			return true;
		}  catch (Exception e) {
			String parseError = e.getMessage().substring(e.getMessage().indexOf("("), e.getMessage().indexOf(")"));
			errorMsg = parseError+")...";
			return false;
		}
	}
	
	public String getDestination() {
		return destination;
	}
	
	public void setDestination(File prmDest) throws IOException {
		destination = prmDest.getAbsolutePath();
	}
	
	public void clearFiles() {
		files = null;
		files = new ArrayList<File>();
		zipFile = null;
	}
	
	public void setFile(ArrayList<File> prmFile) {
		files = prmFile;
	}
	
	public ArrayList<File> getFiles(){
		return files;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

}
