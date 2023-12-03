package com.moil.hafen.common.utils;

import com.moil.hafen.common.constant.NumberConstant;

import java.net.InetAddress;
import java.util.Random;

/**
 * This class provides convenient functions to convert hex string to byte array and vice versa.
 * @author 900045
 * @description:
 * @name HexUtil
 * @date By 2021-03-11 16:31:46
 */
public final class HexUtil {


	private static final String HEX_CHARS = "abc0123456789def";

	/**
	 * High order tag
	 */
	public static long mhiTag;

	public static final long EPOCH = System.currentTimeMillis();

	protected static final long JVM_HASH = Integer.MIN_VALUE;

	protected static final long MACHINES = getMachineID();

	protected static final Random M_RANDOM = new Random(EPOCH);

	/**
	 * Low order tag
	 */
	public static long mloTag;
	

	static  {

		mloTag = EPOCH + Math.abs(M_RANDOM.nextLong());

		mhiTag = (System.currentTimeMillis() + (JVM_HASH * 4294967296L))
				^ MACHINES;
	}

	private static long getMachineID() {
		long i = 0;

		try {
			InetAddress inetaddress = InetAddress.getLocalHost();
			byte[] byte0 = inetaddress.getAddress();

			i = toInt(byte0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return i;
	}

	private static int toInt(byte[] byte0) {
		return ((byte0[0] << 24) & 0xff000000) | ((byte0[1] << 16) & 0xff0000) | ((byte0[2] << 8) & 0xff00)
				| (byte0[3] & 0xff);
	}


	/**
	 * Converts a byte array to hex string.
	 *
	 * @param b -
	 *          the input byte array
	 * @return hex string representation of b.
	 */

	public static String toHexString(byte[] b) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append(HexUtil.HEX_CHARS.charAt(b[i] >>> 4 & 0x0F));
			sb.append(HexUtil.HEX_CHARS.charAt(b[i] & 0x0F));
		}
		return sb.toString();
	}
	

	/**
	 * Converts a hex string into a byte array.
	 *
	 * @param s -
	 *          string to be converted
	 * @return byte array converted from s
	 */
	public static byte[] toByteArray(String s) {
		byte[] buf = new byte[s.length() / 2];
		int j = 0;
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) ((Character.digit(s.charAt(j++), 16) << 4) | Character
					.digit(s.charAt(j++), 16));
		}
		return buf;
	}


	public static String toByteArray() {
		byte[] bytes = new byte[16];
		int idx = 15;
		long val = mloTag;

		for (int i = 0; i < NumberConstant.EIGHT; i++) {
			bytes[idx--] = (byte) (int) (val & (long) 255);
			val >>= 8;
		}

		val = mhiTag;
		for (int i = 0; i < NumberConstant.EIGHT; i++) {
			bytes[idx--] = (byte) (int) (val & (long) 255);
			val >>= 8;
		}
		return toString(bytes);
		
	}

	protected static String toString(byte[] bytes) {
		if (NumberConstant.SIXTEEN != bytes.length) {
			return "** Bad UUID Format/Value **";
		}

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < NumberConstant.SIXTEEN; i++) {
			buf.append(Integer.toHexString(hiNibble(bytes[i])));
			buf.append(Integer.toHexString(loNibble(bytes[i])));
		}

		return buf.toString();
	}

	private static byte hiNibble(byte b) {
		return (byte) ((b >> 4) & 0xf);
	}
	private static byte loNibble(byte b) {
		return (byte) (b & 0xf);
	}
}
