package org.tom.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.tom.compression.EnTextCompressor;
import org.tom.utils.FileUtils;


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
	
	
	
	/// /////////////////////////////////////////////////////////////
	protected static byte[] compressWithGz(String text){
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

	protected static String uncompressWithGz(byte[] bytes) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes, 0, bytes.length);
			ObjectInputStream ois = new ObjectInputStream(new GZIPInputStream(bais));
			String object = (String) ois.readObject();
			bais.close();
			return object;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * this: 45256 milliseconds.
	 * gz: 45142 milliseconds
	 */
	protected static void test1() {
		
		long time = System.currentTimeMillis();

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
		
		
		for (int i = 0; i < rawTextsFilePaths.size(); i++) {
			
			
			String rawTextsFilePath = rawTextsFilePaths.get(i);
			
			String enText = FileUtils.read(rawTextsFilePath);
		
			originalLength += enText.getBytes().length;
			
			byte[] compressedBytes = EnTextCompressor.compress(enText);
			compressedLength += compressedBytes.length;
			
			System.out.println("originalLength = " +  originalLength );
			System.out.println("compressedLength " + compressedLength);
			
			String uncompressedText = EnTextCompressor.uncompress(compressedBytes);
//			byte[] compressedBytes = compressWithGz(enText);
//			compressedLength += compressedBytes.length;
//			String uncompressedText = uncompressWithGz(compressedBytes);
			
			if (enText.equals(uncompressedText) == false){
				System.err.println(uncompressedText);
				throw new Error("Some bugs occurred to the compressor");
			} else {
				System.out.println("Successfully compressed the text in : " + rawTextsFilePath);
			}
		}
		
		System.out.println("originalLength = " + originalLength);
		System.out.println("compressedLength = " + compressedLength);
		System.out.println("compression rate = " + ((double) compressedLength) /originalLength);
		
		System.out.println("EnTextCompressionTest.test1(): stopwatch Elapsed : " + (System.currentTimeMillis() - time) + 
				" milliseconds.");
	}
	
	/**
	 * 
	 */
	protected static void test2() {
		String enText = "Elephants are large mammals of the family Elephantidae and "
				+ "the order Proboscidea. Two species are traditionally recognised,"
				+ " the African elephant (Loxodonta africana) and the Asian "
				+ "elephant (Elephas maximus), although some evidence suggests "
				+ "that African bush elephants and African forest elephants are "
				+ "separate species (L. africana and L. cyclotis respectively).";
		
		
		enText = " This is a large book ";
		int originalLength = enText.getBytes().length;
		byte[] compressedBytes = EnTextCompressor.compress(enText);
		int compressedLength = compressedBytes.length;
		String uncompressedText = EnTextCompressor.uncompress(compressedBytes);
		
		System.out.println("enText = " +enText );
		System.out.println("uncompressed text = " + uncompressedText);
		System.out.println("enText.equals(uncompressedText) = " + enText.equals(uncompressedText));
		System.out.println("originalLength = " + originalLength);
		System.out.println("compressedLength = " + compressedLength);
		System.out.println("compression rate = " + ((double) compressedLength) /originalLength);
	}
	
	
	/**time for compression plus uncompression
	 * this:  milliseconds.
	 * gz:  milliseconds
	 * 
	 */
	protected static void test_compression_time() {
		
		long time11 = 0;
		long time12 = 0;
		long time21 = 0;
		long time22 = 0;
		
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
		long gzCompressedLength = 0;
		
		for (String rawTextsFilePath : rawTextsFilePaths) {
			
			String enText = FileUtils.read(rawTextsFilePath);
			originalLength += enText.getBytes().length;
			
			long start11 = System.currentTimeMillis();
			byte[] compressedBytes = EnTextCompressor.compress(enText);
			compressedLength += compressedBytes.length;
			time11 += System.currentTimeMillis() - start11;
			
			long start12 = System.currentTimeMillis();
			String uncompressedText = EnTextCompressor.uncompress(compressedBytes);
			time12 += System.currentTimeMillis() - start12;
			
			long start21 = System.currentTimeMillis();
			byte[] gzCompressedBytes = compressWithGz(enText);
			gzCompressedLength += gzCompressedBytes.length;
			time21 += System.currentTimeMillis() - start21;
			
			long start22 = System.currentTimeMillis();
			String uncompressedText2 = uncompressWithGz(gzCompressedBytes);
			time22 += System.currentTimeMillis() - start22;
			
			if (enText.equals(uncompressedText) == false){
				throw new Error("Some bugs occurred to the compressor");
			} else {
				System.out.println("Successfully compressed the text in : " + rawTextsFilePath);
			}
			
		}
		
		System.out.println("originalLength = " + originalLength);
		System.out.println("compressedLength = " + compressedLength);
		System.out.println("gzCompressedLength = " + gzCompressedLength);
		System.out.println("compression rate = " + ((double) compressedLength) / originalLength);
		System.out.println("gz compression rate = " + ((double) gzCompressedLength) / originalLength);
		System.out.println("time11 : " + (time11) + 
				" milliseconds.");
		System.out.println("time12 : " + (time12) + 
				" milliseconds.");
		System.out.println("time21: " + (time21) + 
				" milliseconds.");
		System.out.println("time22: " + (time22) + 
				" milliseconds.");
	}
	
	protected static void test4() {
		
		long time11 = 0;
		long time12 = 0;
		long time21 = 0;
		long time22 = 0;
		
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
		long gzCompressedLength = 0;
		
		final int windowLength = 100;
		
		for (int j = 0; j < rawTextsFilePaths.size(); j++) {
			
			String rawTextsFilePath = rawTextsFilePaths.get(j);
			
			String enText = FileUtils.read(rawTextsFilePath);
			
			int units = enText.length() / windowLength;
			
			for(int i = 0; i < units; i++){
				
				String text = enText.substring(i * windowLength, (i+1) * windowLength); 
				originalLength += text.getBytes().length;
				
				long start11 = System.currentTimeMillis();
				byte[] compressedBytes = EnTextCompressor.compress(text);
				compressedLength += compressedBytes.length;
				time11 += System.currentTimeMillis() - start11;
				
				long start12 = System.currentTimeMillis();
				String uncompressedText = EnTextCompressor.uncompress(compressedBytes);
				time12 += System.currentTimeMillis() - start12;
				
				long start21 = System.currentTimeMillis();
				byte[] gzCompressedBytes = compressWithGz(text);
				gzCompressedLength += gzCompressedBytes.length;
				time21 += System.currentTimeMillis() - start21;
				
				long start22 = System.currentTimeMillis();
				String uncompressedText2 = uncompressWithGz(gzCompressedBytes);
				time22 += System.currentTimeMillis() - start22;
				
				if (text.equals(uncompressedText) == false){
					throw new Error("Some bugs occurred to the compressor");
				} else {
				
				}
			}
			
			System.out.println("Successfully compressed the text in : " + rawTextsFilePath);
				
			
		}
		
		System.out.println("originalLength = " + originalLength);
		System.out.println("compressedLength = " + compressedLength);
		System.out.println("gzCompressedLength = " + gzCompressedLength);
		System.out.println("compression rate = " + ((double) compressedLength) / originalLength);
		System.out.println("gz compression rate = " + ((double) gzCompressedLength) / originalLength);
		System.out.println("time11 : " + (time11) + 
				" milliseconds.");
		System.out.println("time12 : " + (time12) + 
				" milliseconds.");
		System.out.println("time21: " + (time21) + 
				" milliseconds.");
		System.out.println("time22: " + (time22) + 
				" milliseconds.");
	}
	public static void main(String[] args) {
//		test1();
//		test2();
		test_compression_time();
//		test4();
		
		
		
	}
}
