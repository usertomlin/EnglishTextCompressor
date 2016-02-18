---
title: "Java English Short Text Compressor"
author: Tom Lin
output: html_document
---

A simple Java library for lossless compression of short English natural language texts. To ensure performance, the dominant language of the text to compress should be English, but the text can also be mixed with other non-alphanumeric or rare characters. 

#### Dependncies

The trove library. Add 'trove-3.1a1.jar' or an another version trove library to build path.

#### Example

After including the jar file 'EnTextCompressor-1.1.jar' (in the lib folder) to build path, it can be used to compress English texts.


```{r compress}

#
#String string = "The ticks that transmit Lyme disease, a debilitating flulike illness caused by Borrelia bacteria, are spreading rapidly across the United States. A new study shows just how rapidly. Over the past 20 years, the two species known to spread the disease to humans have together advanced into half of all the counties in the United States."

## compress to byte array
#byte[] bytes = EnTextCompressor.compress(string);

## uncompress from byte array
#String restoredString = EnTextCompressor.uncompress(bytes);


```


#### Feature

Performs better than GZ in terms of compression rate by a good margin for short (10000 characters or less, especially 1000 characters or less) English natural language texts. 


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

The average compression rates (compressed size / uncompressed size) tested on many Wikipedia texts are approximately:
	  
	 * 50 characters (50-character windows): 
	 * rate_this = 0.395
	 * rate_gz = 1.503
	 * 
	 * 100 characters:
	 * rate_this = 0.3536
	 * rate_gz = 1.12
	 * 
	 * 200 characters:
	 * rate_this = 0.333
	 * rate_gz = 0.865
	 * 
	 * 500 characters:
	 * rate_this = 0.321
	 * rate_gz = 0.663
	 * 
	 * 1000 characters:
	 * rate_this = 0.316
	 * rate_gz = 0.574
	 * 
	 * 2000 characters:
	 * rate_this = 0.314
	 * rate_gz = 0.517
	 * 
	 * 5000 characters:
	 * rate_this = 0.313
	 * rate_gz = 0.465
	 * 
	 * 10000 characters:
	 * rate_this = 0.313 
	 * rate_gz = 0.434
	 
	 * around en texts with size around 5M
	 * rate_this = 0.313 
	 * rate_gz = 0.367
	 *



#### Usage scenarios

For example, can be used to compress a few columns of short English natural language texts not for searching in a large database table. Not recommended for compressing large (>10M) text files.



