package com.moil.hafen.common.utils.base64;

/**
 * @author 900045
 * @description:
 * @name Encode64
 * @date By 2021-03-11 09:59:28
 */
public class Encode64 {

	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
			'Z', '+', '/', };


	/**
	 * 把10进制的数字转换成64进制 
	 * @author: 900045
	 * @date: 2021-03-11 09:59:45
	 * @throws 
	 * @param number: 
	 * @return: java.lang.String
	 **/
	public static String CompressNumber(long number) {
		char[] buf = new char[64];
		int charPos = 64;
		int radix = 1 << 6;
		long mask = radix - 1;
		do {
			buf[--charPos] = digits[(int) (number & mask)];
			number >>>= 6;
		} while (number != 0);
		return new String(buf, charPos, (64 - charPos));
	}

	/**
	 * 把64进制的字符串转换成10进制 
	 * @author: 900045
	 * @date: 2021-03-11 10:00:27
	 * @throws 
	 * @param str: 
	 * @return: long
	 **/
	public static long UnCompressNumber(String str) {
		long result = 0;
		for (int i = str.length() - 1; i >= 0; i--) {
			for (int j = 0; j < digits.length; j++) {
				if (str.charAt(i) == digits[j]) {
					result += ((long) j) << 6 * (str.length() - 1 - i);
				}
			}
		}
		return result;
	}
}
