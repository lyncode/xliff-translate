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
	public static Xliff read (InputStream st) throws XliffException {
		try {
			JAXBContext ctx = JAXBContext.newInstance(Xliff.class.getPackage().getName());
			Unmarshaller un = ctx.createUnmarshaller();
			Object obj = un.unmarshal(st);
			if (obj instanceof Xliff)
				return (Xliff) obj;
			else
				throw new XliffException("Unknown format");
		} catch (JAXBException e) {
			throw new XliffException(e);
		}
	}
	
	public static void write (Xliff x, OutputStream out) throws XliffException {
		JAXBContext ctx;
		try {
			ctx = JAXBContext.newInstance(Xliff.class.getPackage().getName());
			Marshaller m = ctx.createMarshaller();
			m.marshal(x, out);
		} catch (JAXBException e) {
			throw new XliffException(e);
		}
	}
	
	public static void main (String... args) throws XliffException, IOException {
		FileInputStream input = new FileInputStream("sample/example1.xliff");
		Xliff x = XLiffUtils.read(input);
		for (Xliff.File f : x.getFile())
			for (TransUnit u : f.getBody().getTransUnit())
				System.out.println(u.getSource()+" => "+u.getTarget());
		input.close();
	}
}
