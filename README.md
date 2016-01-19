---
title: "Java English Short Text Compressor"
output: html_document
---

A simple Java library used for losslessly compressing short English natural language texts. To ensure performance, the dominant language of the text to compress should be English, but the text can also be mixed with other non-English or rare characters. 


#### Example

After including the Jar file 'EnTextCompressor-1.0.jar' to build path, it can be used to compress English texts.


```{r compress}

#
#String string = "The ticks that transmit Lyme disease, a debilitating flulike illness caused by Borrelia bacteria, are spreading rapidly across the United States. A new study shows just how rapidly. Over the past 20 years, the two species known to spread the disease to humans have together advanced into half of all the counties in the United States."

## compress to byte array
#byte[] bytes = EnTextCompressor.compress(string);

## uncompress from byte array
#String restoredString = uncompress(bytes);


```


#### A brief performance introduction

It works much better than gz in terms of compression ratio for short (length < 1000) English natural language texts. 

Suppose:

```{r}
#double originalLength = string.getBytes().length;
#byte[] bytes = EnTextCompressor.compress(substring);

#ByteArrayOutputStream baos = new ByteArrayOutputStream();
#			ObjectOutputStream oos = new ObjectOutputStream(useGZip ? new GZIPOutputStream(baos) : baos);
#			oos.writeObject(object);
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

#### Use case

For example, it can be used to compress and uncompress a few columns of short natural language texts of a large databased table, and the text columns not used for searching. Not recommended for compressing larger texts files.

