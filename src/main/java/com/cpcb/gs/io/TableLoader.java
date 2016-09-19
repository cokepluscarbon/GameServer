package com.cpcb.gs.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cpcb.gs.io.ProtocolDeploy.TestEnum;

public class TableLoader {
	private static HashMap<String, TableT> cacheMap;

	public static TableT<? extends BaseDeploy> Load(String path, Class<? extends BaseDeploy> clazz) {
		Iterable<CSVRecord> records = getCSVRecords(path);

		try {
			for (CSVRecord record : records) {
				BaseDeploy deploy = clazz.newInstance();
				deploy.setId(Integer.parseInt(record.get("id")));

				for (Field field : clazz.getDeclaredFields()) {
					if (!record.isSet(field.getName())) {
						continue;
					}

					Class<?> fieldClass = field.getType();
					String fieldValue = record.get(field.getName());
					if (int.class.equals(fieldClass)) {
						field.setInt(deploy, Integer.parseInt(fieldValue));
					} else if (long.class.equals(fieldClass)) {
						field.setLong(deploy, Long.parseLong(fieldValue));
					} else if (float.class.equals(fieldClass)) {
						field.setFloat(deploy, Float.parseFloat(fieldValue));
					} else if (double.class.equals(fieldClass)) {
						field.setDouble(deploy, Double.parseDouble(fieldValue));
					} else if (byte.class.equals(fieldClass)) {
						field.setByte(deploy, Byte.parseByte(fieldValue));
					} else if (String.class.equals(fieldClass)) {
						field.set(deploy, fieldValue);
					} else if (boolean.class.equals(fieldClass)) {
						field.setBoolean(deploy, Integer.parseInt(fieldValue) != new Integer(0));
					} else if (fieldClass.isEnum()) {
						field.set(deploy, TestEnum.valueOf(fieldValue));
					} else if (List.class.equals(fieldClass)) {
						field.set(deploy, JSON.parseArray(fieldValue));
					} else if (JSONObject.class.equals(fieldClass)) {
						field.set(deploy, JSON.parseObject(fieldValue));
					} else {
						field.set(deploy, JSON.parseObject(fieldValue, fieldClass));
					}
				}

				System.out.println(deploy.toString());
			}

			return null;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return null;
	}

	private static Iterable<CSVRecord> getCSVRecords(String relativePath) {
		try {
			String absolutePath = System.getProperty("user.dir") + "/config/table/" + relativePath + ".xls";
			Reader in = new FileReader(absolutePath);
			return CSVFormat.TDF.withHeader().withCommentMarker('#').parse(in);
		} catch (FileNotFoundException e) {
			System.err.println(String.format("Table[path=%s] not exists!", relativePath));
			return null;
		} catch (IOException e) {
			System.err.println(String.format("Parse Table[path=%s] Error[Error=%s]!", relativePath, e.getMessage()));
			return null;
		}
	}

}
