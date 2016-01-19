import abc.corpus.compression.EnTextCompressor;
import abc.utils.ObjectUtils;

public class CompressionExample {

	protected static void testCompress() {
		
		String string = " In the \tlast fwf3t4f two tables, \nyou can see the three-party syllables (trigrams) of the English language. From the original text 185.984 three-party syllables were extracted. In the table, only syllables with a probabilty of at least 0.30% were published. The left list is sorted according to the syllables, the right to the frequencies. Thus the syllable THE is the most common syllable of the English language. " ;
//		String string = FileUtils.read("D:/Corpora/wiki/enwiki/AA/wiki_01");
		string = "The ticks that transmit Lyme disease, a debilitating flulike illness caused by Borrelia bacteria, are spreading rapidly across the United States. A new study shows just how rapidly. Over the past 20 years, the two species known to spread the disease to humans have together advanced into half of all the counties in the United States.";
		System.out.println("string.getBytes().length = " + string.getBytes().length);
		
		byte[] bytes = EnTextCompressor.compress(string);
		String restoredString = EnTextCompressor.uncompress(bytes);
		
		System.out.println("restoredString.equals(string) ? = " + string.equals(restoredString));
		System.out.println("this library: bytes.length = " + (bytes.length));
		System.out.println("gz: bytes.length = " + (ObjectUtils.toBytes(bytes, true).length));
		System.out.println("System.exit(0) at EnWordStatsProcessor.enclosing_method()");
	}
	
	public static void main(String[] args) {
		testCompress();
	}
}
