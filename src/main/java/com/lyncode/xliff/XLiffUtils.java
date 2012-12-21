package com.lyncode.xliff;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.lyncode.xliff.xml.TransUnit;
import com.lyncode.xliff.xml.Xliff;

public class XLiffUtils {
	public static XLIFF read (InputStream st) throws XliffException {
		try {
			JAXBContext ctx = JAXBContext.newInstance(Xliff.class.getPackage().getName());
			Unmarshaller un = ctx.createUnmarshaller();
			Object obj = un.unmarshal(st);
			if (obj instanceof Xliff)
				return new XLIFF((Xliff) obj);
			else
				throw new XliffException("Unknown format");
		} catch (JAXBException e) {
			throw new XliffException(e);
		}
	}
	
	public static void write (XLIFF x, OutputStream out, String sourceLanguage) throws XliffException {
		JAXBContext ctx;
		Xliff obj = new Xliff();
		obj.setVersion("1.2");
		Xliff.File f = new Xliff.File();
		obj.getFile().add(f);
		
		f.setDatatype("plaintext");
		f.setSourceLanguage(sourceLanguage);
		// Default Value
		f.setOriginal("file.ext");
		
		Xliff.File.Body body = new Xliff.File.Body();
		f.setBody(body);
		
		int i = 1;
		
		for (String s : x.getSource()) {
			TransUnit u = new TransUnit();
			u.setId(i++);
			u.setSource(s);
			u.setTarget(x.getMessage(s));
			body.getTransUnit().add(u);
		}
			
		
		try {
			ctx = JAXBContext.newInstance(Xliff.class.getPackage().getName());
			Marshaller m = ctx.createMarshaller();
			m.marshal(obj, out);
		} catch (JAXBException e) {
			throw new XliffException(e);
		}
	}
	
	public static void main (String... args) throws XliffException, IOException {
		FileInputStream input = new FileInputStream("sample/example1.xliff");
		XLIFF x = XLiffUtils.read(input);
		for (String s : x.getSource())
			System.out.println(x.getMessage(s));
		input.close();
	}
}
