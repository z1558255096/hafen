package com.moil.hafen.common.utils;

import com.moil.hafen.common.constant.NumberConstant;

/**
 * @author 900045
 * @description:
 * @name UUID
 * @date By 2021-04-09 14:40:21
 */
public class UUID extends UUIDFactory {

	/**
	 * Length bits
	 */
	protected static final int BITS8 = 8;

	/**
	 * Length byte
	 */
	protected static final int BYTELEN = 16;

	/**
	 * High order mask
	 */
	protected static final int HIMASK = 240;

	/**
	 * Low order 8bits mask
	 */
	protected static final int LO8BITMASK = 255;

	/**
	 * Low order mask
	 */
	protected static final int LOMASK = 15;

	/**
	 * Upper limit Short
	 */
	protected static final long MAX_INT = 32767;

	/**
	 * Upper limit Integer
	 */
	protected static final long MAX_LONG = 2147483647;

	/** Epoch has millisecond */
	/**
	 * High order tag
	 */
	protected long mhiTag;

	/**
	 * Low order tag
	 */
	protected long mloTag;

	/**
	 * UUID Cache
	 */
	protected String uuid = null;

	/**
	 * Construct overpass user data.
	 *
	 * @param highTag High order tag
	 * @param loTag   Low order tag
	 */
	protected UUID(long highTag, long loTag) {
		mhiTag = highTag;
		mloTag = loTag;
		uuid = toString(this.toByteArray());
	}

	/**
	 * Construct default.
	 */
	protected UUID() {
		next();
		uuid = toString(this.toByteArray());
	}

	/**
	 * Return high order byte.
	 *
	 * @param b Object byte
	 * @return Result byte
	 */
	private static byte hiNibble(byte b) {
		return (byte) ((b >> 4) & 0xf);
	}

	/**
	 * Return low order byte.
	 *
	 * @param b Object byte
	 * @return Result byte
	 */
	private static byte loNibble(byte b) {
		return (byte) (b & 0xf);
	}

	/**
	 * Set high order byte.
	 *
	 * @param dest
	 *            Object byte
	 * @param b
	 *            Source byte
	 * @return Result byte
	 */
/*    private static final byte setHiNibble(byte dest, int b)
    {
    dest &= 0xf;
    dest |= ((byte)b << 4);

    return dest;
    }*/

	/**
	 * Set low order byte.
	 *
	 * @param dest
	 *            Object byte
	 * @param b
	 *            Source byte
	 * @return Result byte
	 */
/*    private static final byte setLoNibble(byte dest, int b)
    {
    dest &= 0xf0;
    dest |= ((byte)b & 0xf);

    return dest;
    }*/

	/**
	 * Equals UUID.
	 *
	 * @param obj Object UUID
	 * @return Ture if equal
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			if (obj == null) {
				return false;
			} else {
				UUID uuid = (UUID) obj;
				boolean flag = (uuid.mhiTag == mhiTag)
						&& (uuid.mloTag == mloTag);

				return flag;
			}
		} catch (ClassCastException cce) {
			return false;
		}
	}


	@Override
	public int hashCode() {
		int result = (int) (mhiTag ^ (mhiTag >>> 32));
		result = 31 * result + (int) (mloTag ^ (mloTag >>> 32));
		return result;
	}

	/**
	 * Get back next new uid.
	 *
	 * @return java.lang.String
	 */
	@Override
	public String getNextUID() {
		next();

		return uuid;
	}

	/**
	 * Get back current uid.
	 *
	 * @return java.lang.String
	 */
	@Override
	public String getUID() {
		return uuid;
	}

	/**
	 * Set current UID.
	 *
	 * @param uidStr The new uID value
	 * @throws Exception Bad string format
	 */
	@Override
	public void setUID(String uidStr) throws Exception {
		long loTag = 0L;
		long hiTag = 0L;
		int len = uidStr.length();

		if (NumberConstant.THIRTY_TWO != len) {
			throw new Exception("bad string format");
		}

		int i = 0;
		int idx = 0;

		for (; i < NumberConstant.TWO; i++) {
			loTag = 0L;

			for (int j = 0; j < (len / NumberConstant.TWO); j++) {
				String s = uidStr.substring(idx++, idx);
				int val = Integer.parseInt(s, 16);

				loTag <<= 4;
				loTag |= val;
			}

			if (i == 0) {
				hiTag = loTag;
			}
		}

		mhiTag = hiTag;
		mloTag = loTag;
		uuid = toString(this.toByteArray());
	}

	/**
	 * Get printable String.
	 *
	 * @return java.lang.String
	 */
	@Override
	public String toPrintableString() {
		byte[] bytes = toByteArray();

		if (NumberConstant.SIXTEEN != bytes.length) {
			return "** Bad UUID Format/Value **";
		}

		StringBuffer buf = new StringBuffer();
		int i;

		for (i = 0; i < NumberConstant.FOUR; i++) {
			buf.append(Integer.toHexString(hiNibble(bytes[i])));
			buf.append(Integer.toHexString(loNibble(bytes[i])));
		}

		while (i < NumberConstant.TEN) {
			buf.append('-');

			int j = 0;

			while (j < NumberConstant.TWO) {
				buf.append(Integer.toHexString(hiNibble(bytes[i])));
				buf.append(Integer.toHexString(loNibble(bytes[i++])));
				j++;
			}
		}

		buf.append('-');

		for (; i < NumberConstant.SIXTEEN; i++) {
			buf.append(Integer.toHexString(hiNibble(bytes[i])));
			buf.append(Integer.toHexString(loNibble(bytes[i])));
		}

		return buf.toString();
	}

	/**
	 * Return UID String.
	 *
	 * @return UID String
	 */
	@Override
	public String toString() {
		return uuid;
	}

	/**
	 * Get new UUID instance.
	 *
	 * @return UUID
	 */
	protected static UUIDFactory getInstance() {
		return new UUID();
	}

	/**
	 * Overpass a bytes array generator UID String.
	 *
	 * @param bytes Object bytes array
	 * @return UID String
	 */
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

	/**
	 * Generator & get back a UUID & cache String.
	 */
	protected void next() {
		mhiTag = (System.currentTimeMillis() + (JVMHASH * 4294967296L))
				^ MACHINEID;
		mloTag = EPOCH + Math.abs(M_RANDOM.nextLong());
		uuid = toString(this.toByteArray());
	}

	/**
	 * Overpass high order tag & low order tag convert to array bytes.
	 *
	 * @return Array bytes
	 */
	protected byte[] toByteArray() {
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

		if (!this.isMD5()) {
			return bytes;
		} else {
			return toMD5(bytes);
		}
	}
}
