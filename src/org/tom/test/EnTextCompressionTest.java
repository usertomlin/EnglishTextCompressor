package org.tom.test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import org.tom.compression.EnTextCompressor;

/**
 * @author Tom3_Lin
 *
 */
public class EnTextCompressionTest {

	private static ArrayList<String> getFilePathsRecursively(File dir) {
		if (dir.isDirectory() == false)
			return null;
		ArrayList<String> list = new ArrayList<String>();
		File[] listFiles = dir.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			File file = listFiles[i];
			String thePath = file.getAbsolutePath();
			if (file.isDirectory()) {
				ArrayList<String> subFileList = getFilePathsRecursively(file);
				list.addAll(subFileList);
			} else {
				list.add(thePath);
			}
		}
		return list;
	}
	
	private static String read(String path) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			reader.close();
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/// /////////////////////////////////////////////////////////////
	private static byte[] compressWithGz(String text){
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(new GZIPOutputStream(baos));
			oos.writeObject(text);
			oos.close();
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected static void test0() {
		String rawTextsFileOrDir = "D:/Corpora/wiki/enwiki/AA";
		
		File file = new File(rawTextsFileOrDir);
		ArrayList<String> rawTextsFilePaths = new ArrayList<String>();
		if (file.isDirectory()){	// is directory
			rawTextsFilePaths = getFilePathsRecursively(file);	
		} else {	//is file
			rawTextsFilePaths.add(rawTextsFileOrDir);
		}
		
		final int windowLength = 10000;
		
		long originalLength = 0;
		long compressedLength1 = 0;
		long compressedLength2 = 0;
		for (String rawTextsFilePath : rawTextsFilePaths) {
			String enText = read(rawTextsFilePath);
			
			for (int i = 0; i < 800000 ; ){
				String text = enText.substring(i, i + windowLength);
				originalLength += text.getBytes().length;
				byte[] compressedBytes = EnTextCompressor.compress(text);
				compressedLength1 += compressedBytes.length;
				String uncompressedText = EnTextCompressor.uncompress(compressedBytes);
				compressedLength2 += compressWithGz(text).length;
				
				if (text.equals(uncompressedText) == false){
					System.out.println(text);
					System.out.println("----------------------------------------------------------");
					System.out.println(uncompressedText);
					throw new Error("Some bugs occurred to the compressor");
				} else {
					
				}
				i += windowLength;
			}
			
			System.out.println("EnTextCompressionTest.test0() finished " + rawTextsFilePath);
		}
		
		System.out.println("originalLength = " + originalLength);
		System.out.println("compressedLength = " + compressedLength1);
		System.out.println("compression rate = " + ((double) compressedLength1) /originalLength);
		System.out.println("Gz compression rate = " + ((double) compressedLength2) /originalLength);
		System.out.println("System.exit(0) at EnTextCompressionTest.enclosing_method()");
		System.exit(0);
	}
	
	
	protected static void test1() {
		String rawTextsFileOrDir = "D:/Corpora/wiki/enwiki/AA";
		
		File file = new File(rawTextsFileOrDir);
		ArrayList<String> rawTextsFilePaths = new ArrayList<String>();
		if (file.isDirectory()){	// is directory
			rawTextsFilePaths = getFilePathsRecursively(file);	
		} else {	//is file
			rawTextsFilePaths.add(rawTextsFileOrDir);
		}
		
		long originalLength = 0;
		long compressedLength = 0;
		for (String rawTextsFilePath : rawTextsFilePaths) {
			String enText = read(rawTextsFilePath);
			originalLength += enText.getBytes().length;
			byte[] compressedBytes = EnTextCompressor.compress(enText);
			compressedLength += compressedBytes.length;
			String uncompressedText = EnTextCompressor.uncompress(compressedBytes);
			if (enText.equals(uncompressedText) == false){
				throw new Error("Some bugs occurred to the compressor");
			} else {
				System.out.println("Successfully compressed the text in : " + rawTextsFilePath);
			}
		}
		
		System.out.println("originalLength = " + originalLength);
		System.out.println("compressedLength = " + compressedLength);
		System.out.println("compression rate = " + ((double) compressedLength) /originalLength);
	}
	
	protected static void test2() {
		String enText = "Elephants are large mammals of the family Elephantidae and "
				+ "the order Proboscidea. Two species are traditionally recognised,"
				+ " the African elephant (Loxodonta africana) and the Asian "
				+ "elephant (Elephas maximus), although some evidence suggests "
				+ "that African bush elephants and African forest elephants are "
				+ "separate species (L. africana and L. cyclotis respectively). ";
		int originalLength = enText.getBytes().length;
		byte[] compressedBytes = EnTextCompressor.compress(enText);
		int compressedLength = compressedBytes.length;
		String uncompressedText = EnTextCompressor.uncompress(compressedBytes);
		System.out.println(enText.equals(uncompressedText));
		System.out.println("originalLength = " + originalLength);
		System.out.println("compressedLength = " + compressedLength);
		System.out.println("compression rate = " + ((double) compressedLength) / originalLength);
	}
	
	public static void main(String[] args) {
		test0();
	}
}
