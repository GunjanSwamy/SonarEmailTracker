package com.sonar.sonarAdmin.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

/**
 * Class contains various commonly used general methods
 * 
 * @author LVK
 *
 */
/**
 * @author LVK
 *
 */
@Component
public class CommonUtils {

	/**
	 * Converts time in string format to Date
	 * 
	 * @param strTime
	 * @return
	 * @throws ParseException
	 */
	public Date getTimeFromString(String strTime) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date d = dateFormat.parse(strTime);
		return d;
	}

	/**
	 * Converts date in string format to Date
	 * 
	 * @param strDate
	 * @return
	 * @throws ParseException
	 */
	public Date getDateFromString(String strDate) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date d = dateFormat.parse(strDate);
		return d;
	}

	/**
	 * Converted used to get time from string
	 */
	public Converter<String, Date> toStringTime = new AbstractConverter<String, Date>() {
		@Override
		public Date convert(String source) {
			try {
				return getTimeFromString(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}
	};

	/**
	 * Converter used to get Date from string
	 */
	public Converter<String, Date> toStringDate = new AbstractConverter<String, Date>() {
		@Override
		public Date convert(String source) {
			try {
				return getDateFromString(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}
	};

	/**
	 * Generates random 6 digit alphanumeric String
	 * 
	 * @return
	 */
	protected String generateRandomAlphaNumericValue() {
		String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String code = new String();
		Random rnd = new Random();
		while (code.length() < 6) { // length of the random string.
			int index = (int) (rnd.nextFloat() * allChars.length());
			code += (allChars.charAt(index));
		}
		return code;

	}

}
