package com.cpcb.gs.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import com.cpcb.gs.io.ProtocolDeploy;
import com.cpcb.gs.io.TableLoader;

public class TestCommonsCSV {

	@Test
	public void read_01() throws IOException {
		File file = new File("test");
		file.createNewFile();
		System.out.println(file.getAbsolutePath());
		System.out.println(getClass().getResource("/").toString());
		System.out.println(System.getProperty("user.dir"));
		Reader in = new FileReader("path/to/file.csv");
		Iterable<CSVRecord> records = CSVFormat.TDF.parse(in);
		for (CSVRecord record : records) {
			String lastName = record.get("LastName");
			String firstName = record.get("FirstName");

			System.out.println(lastName + "->" + firstName);
		}

	}

	@Test
	public void test_tableLoader() throws IOException {
		long start = System.currentTimeMillis();
		TableLoader.Load("protocol", ProtocolDeploy.class);
		long end = System.currentTimeMillis();
		System.err.println("Spend Time : " + (end - start));
		System.err.println("Per Spend Time : " + (end - start) * 1f / 500f);
	}
}
