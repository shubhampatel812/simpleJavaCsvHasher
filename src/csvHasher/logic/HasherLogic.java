package csvHasher.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HasherLogic {

	
	private String fFileToHash = "";
	
	public void setInputFile(String fileToHash){
		fFileToHash = fileToHash;
	}
	
	public void startHashing(){
		if(fFileToHash == null || "".equals(fFileToHash)){
			throw new IllegalArgumentException("No input file provided");
		}
		
		String outputFile = "csvHasherOut.csv";
		MessageDigest md5Digest = null;
		try {
			md5Digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedReader reader = null;

		// try to open input file
		try {
			reader = new BufferedReader(new FileReader(fFileToHash));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try to create output file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(outputFile, "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// parse file contents
		try {
			// discard first line as it contains the column headers
			String line = reader.readLine();

			while (line != null) {
				String[] fields = line.split(",", -1);
				String fieldToHash = fields[0];
				
				// create hash
				byte[] fieldHashAsBytes = md5Digest.digest(fieldToHash.getBytes());
				StringBuffer hashedField = new StringBuffer();
				
				for (int i = 0; i < fieldHashAsBytes.length; i++) {
		            if ((0xff & fieldHashAsBytes[i]) < 0x10) {
		            	hashedField.append("0"
		                        + Integer.toHexString((0xFF & fieldHashAsBytes[i])));
		            } else {
		            	hashedField.append(Integer.toHexString(0xFF & fieldHashAsBytes[i]));
		            }
		        }

				// write to output file
				String lineToWrite = fieldToHash + "," + hashedField.toString() + System.getProperty("line.separator");
				writer.write(lineToWrite);
				
				line = reader.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
