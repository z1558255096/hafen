package com.moil.hafen.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Random;

/**
 * @author 900045
 * @description:
 * @name UUIDFactory
 * @date By 2021-04-09 14:37:08
 */
public abstract class UUIDFactory {

	public static final Logger LOGGER = LoggerFactory.getLogger(UUIDFactory.class);


	/**
	 * Global Unified Identifier
	 */
	public static final String UID_GUID = "GUID";

	/**
	 * United Unified Identifier
	 */
	public static final String UID_UUID = "UUID";

	/**
	 * Current Epoch millis SEED
	 */
	protected static final long EPOCH = System.currentTimeMillis();

	/**
	 * JVM Hashcode
	 */
	protected static final long JVMHASH = Integer.MIN_VALUE;

	/**
	 * Epoch has millisecond
	 */
	protected static final long MACHINEID = getMachineID();

	/**
	 * Random by seed
	 */
	protected static final Random M_RANDOM = new Random(EPOCH);

	/**
	 * MD5 Instance
	 */
	private static MessageDigest md5;
	
	static {
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (java.security.NoSuchAlgorithmException ex) {
			LOGGER.info("Init MessageDigest failed.", ex);
		}
	}

	/**
	 * MD5 flag
	 */
	private boolean isMd5 = false;

	/**
	 * Get Default UIDFactory.
	 *
	 * @return UIDFactory UID manager object
	 */
	public static UUIDFactory getDefault() {
		return UUID.getInstance();
	}

	/**
	 * Get Specified UIDFactory.
	 *
	 * @param uidFactory Description of the Parameter
	 * @return UIDFactory
	 * @throws Exception Description of the Exception
	 */
	public static UUIDFactory getInstance(String uidFactory) throws Exception {
		if (uidFactory.equalsIgnoreCase(UID_UUID)) {
			return UUID.getInstance();
		}

		throw new Exception(uidFactory + " Not Found!");
	}

	/**
	 * Get next UID.
	 *
	 * @return String StorageAble UID
	 */
	public abstract String getNextUID();

	/**
	 * Get current UID.
	 *
	 * @return String StorageAble UID
	 */
	public abstract String getUID();

	/**
	 * Is MD5 switch ON.
	 *
	 * @return ON is true.
	 */
	public boolean isMD5() {
		return isMd5;
	}

	/**
	 * Set MD5 output.
	 *
	 * @param flag MD5 switch
	 */
	public void setMD5(boolean flag) {
		isMd5 = flag;
	}

	/**
	 * Set current UID.
	 *
	 * @param uid Object uid
	 * @throws Exception Description of the Exception
	 */
	public abstract void setUID(String uid) throws Exception;

	/**
	 * Return Printable ID String.
	 *
	 * @return String
	 */
	public abstract String toPrintableString();

	/**
	 * Convert bytes to MD5 bytes.
	 *
	 * @param bytes Description of the Parameter
	 * @return
	 */
	protected static byte[] toMD5(byte[] bytes) {
		return md5.digest(bytes);
	}

	/**
	 * Gets the machineID attribute of the GUID class
	 *
	 * @return The machineID value
	 */
	private static long getMachineID() {
		long i = 0;

		try {
			InetAddress inetaddress = InetAddress.getLocalHost();
			byte[] byte0 = inetaddress.getAddress();

			i = toInt(byte0);
		} catch (Exception ex) {
			LOGGER.info("Get MachineId Failed", ex);
		}

		return i;
	}

	/**
	 * Convert bytes to int utils.
	 *
	 * @param byte0 Object bytes array
	 * @return Result int
	 */
	private static int toInt(byte[] byte0) {
		return ((byte0[0] << 24) & 0xff000000) | ((byte0[1] << 16) & 0xff0000) | ((byte0[2] << 8) & 0xff00)
				| (byte0[3] & 0xff);
	}
}
