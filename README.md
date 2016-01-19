---
title: "Java English Short Text Compressor"
author: Tom Lin
output: html_document
---

A simple Java library used for losslessly compressing short English natural language texts. To ensure performance, the dominant language of the text to compress should be English, but the text can also be mixed with other non-alphanumeric or rare characters. 



#### Example

After including the Jar file 'EnTextCompressor-1.0.jar' to build path, it can be used to compress English texts.


```{r compress}

#
#String string = "The ticks that transmit Lyme disease, a debilitating flulike illness caused by Borrelia bacteria, are spreading rapidly across the United States. A new study shows just how rapidly. Over the past 20 years, the two species known to spread the disease to humans have together advanced into half of all the counties in the United States."

## compress to byte array
#byte[] bytes = EnTextCompressor.compress(string);

## uncompress from byte array
#String restoredString = EnTextCompressor.uncompress(bytes);


```


#### Feature

It works much better than gz in terms of compression ratio for short (1000 characters or less) English natural language texts. 

Suppose:

```{r}
#double originalLength = string.getBytes().length;
#byte[] bytes = EnTextCompressor.compress(string);

#ByteArrayOutputStream baos = new ByteArrayOutputStream();
#ObjectOutputStream oos = new ObjectOutputStream(useGZip ? new GZIPOutputStream(baos) : baos);
#oos.writeObject(string);
#oos.close();
#byte[] bytes2 = baos.toByteArray();

#double rate_this = originalLength / bytes.length;
#double rate_gz = originalLength / bytes2.length;

```

The average compression ratios (uncompressed size / compressed size) tested on many Wikipedia texts are approximately:
	  
	 * 50 characters (50-character windows): 
	 * rate_this  = 2.05
	 * rate_gz  = 0.67
	 * 
	 * 100 characters:
	 * rate_this = 2.32
	 * rate_gz  = 0.89
	 * 
	 * 200 characters:
	 * rate_this  = 2.48
	 * rate_gz  = 1.16
	 * 
	 * 500 characters:
	 * rate_this = 2.59
	 * rate_gz  = 1.51
	 * 
	 * 1000 characters:
	 * rate_this  = 2.62
	 * rate_gz  = 1.74
	 * 
	 * 2000 characters:
	 * rate_this  = 2.63
	 * rate_gz  = 1.93
	 * 
	 * 5000 characters:
	 * rate_this  = 2.64 
	 * rate_gz = 2.15
	 * 
	 * 10000 characters:
	 * rate_this = 2.64 
	 * rate_gz = 2.30

The current maximum text size it can compress is around 524M bytes.

#### Use cases

For example, it can be used to compress and uncompress a few columns of short natural language texts in a large databased table, and the text columns are not used for searching. Not recommended for compressing larger text files.

