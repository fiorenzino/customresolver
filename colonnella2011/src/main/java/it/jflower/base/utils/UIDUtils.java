package it.jflower.base.utils;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

public class UIDUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	protected Logger logger = Logger.getLogger(UIDUtils.class.getName());

	public static Date getUidComparable(String uid) {
		// 1180455378.3887.mx-a.topnet.it,S=4999
		Date dataUid = null;
		Long timestamp = null;
		String[] tokens = null;
		String tokenUno = "";
		tokens = uid.split("\\.");
		if (tokens.length != 0) {
			tokenUno = tokens[0];
		}
		try {
			timestamp = new Long(tokenUno) * 1000;
			dataUid = new Date(timestamp);
			// log.error("retUid:"+retUid);
		} catch (Exception e) {
			// log.error("no numero");
		}

		return dataUid;
	}

	public static int compareTo(String uid1, String uid2) {
		return getUidComparable(uid1).compareTo(getUidComparable(uid2));
	}
}
