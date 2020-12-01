package com.mentor.utility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

public class Main {
	public static void main(String txt,String app) {
		String mypath=Constants.JBOSS_SERVER_PATH+Constants.JBOSS_LINX_PATH;
		String relativePath=mypath+File.separator+"ExciseUp"+File.separator+"reports";
		ByteArrayOutputStream out = QRCode.from(txt)
										.to(ImageType.PNG).stream();

		try {
			FileOutputStream fout = new FileOutputStream(new File(
					relativePath+File.separator+app+"QR_Code.jpg"));
                 
			fout.write(out.toByteArray());

			fout.flush();
			fout.close();

		} catch (FileNotFoundException e) {
			// Do Logging
		} catch (IOException e) {
			// Do Logging
		}
	}
}