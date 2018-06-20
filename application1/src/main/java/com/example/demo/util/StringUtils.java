package com.example.demo.util;


import org.apache.commons.lang3.math.NumberUtils;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class StringUtils extends org.apache.commons.lang3.StringUtils{

	final static char[] CHAR_1ST = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138,
			0x3139, 0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147, 0x3148,
			0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

	final static char[] CHAR_2ND = { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153,
			0x3154, 0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a, 0x315b,
			0x315c, 0x315d, 0x315e, 0x315f, 0x3160, 0x3161, 0x3162, 0x3163 };

	final static char[] CHAR_3RD = { 0, 0x3131, 0x3132, 0x3133, 0x3134, 0x3135,
			0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c, 0x313d, 0x313e,
			0x313f, 0x3140, 0x3141, 0x3142, 0x3144, 0x3145, 0x3146, 0x3147,
			0x3148, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

	public static String convertCamel(String name){
		String lower = name.toLowerCase();
		String split[] = lower.split("_",-1);
		StringBuffer sb = new StringBuffer(split[0]);
		for(int i=1;i<split.length;i++){
			sb.append(StringUtils.capitalize(split[i]));
		}
		return sb.toString();
	}

	public static String nvl(Object o) {
		return ((o == null) ? "" : o.toString());
	}

	public static String nvl(Object o,String rtn) {
		return ((o == null) ? rtn : o.toString());
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static String decimalFormat(Object n,String format)
	{
		try {
			if(isEmpty( n )){
				return "0";
			}
			if(format.equals(".0")){
				format = "0.0";
			}
			String val = String.valueOf(n);
			val = change(val, ",", "");
			double tmp = Double.parseDouble(val);
			DecimalFormat df = new DecimalFormat(format);
			df.setRoundingMode(RoundingMode.DOWN);
			return df.format(tmp);
		} catch(Exception e) {
			return "0";
		}
	}
	public static String decimalFormatEmpty(Object n,String format)
	{
		try {
			if(isEmpty( n )){
				return "";
			}
			if(format.equals(".0")){
				format = "";
			}
			String val = String.valueOf(n);
			val = change(val, ",", "");
			double tmp = Double.parseDouble(val);
			DecimalFormat df = new DecimalFormat(format);
			df.setRoundingMode(RoundingMode.DOWN);
			return df.format(tmp);
		} catch(Exception e) {
			return "";
		}
	}

	public static String getDecimal(Object n)
	{
		try {
			if(isEmpty( n )){
				return "0";
			}
			String val = String.valueOf(n);
			return val.replaceAll(",", "");
		} catch(Exception e) {
			return "0";
		}
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static String concat(String str, String concatStr) {
		if (isEmpty(str)) {
			return concatStr;
		}
		if (isEmpty(concatStr)) {
			return str;
		}
		return str.concat(concatStr);
	}

	public static String concat(String... strs) {
		StringBuilder builder = new StringBuilder();
		if (strs != null) {
			for (String s : strs) {
				builder.append(s);
			}
		}
		return null;
	}

	public static String ltrim(String str) {
		String result = "";
		if (str != null) {
			char ch[] = str.toCharArray();
			int size = ch.length;
			boolean isEmptyFirst = true;
			for (int i = 0; i < size; i++) {
				if (Character.isWhitespace(ch[i]) && isEmptyFirst) {
					;
				}
				else {
					result += ch[i];
					isEmptyFirst = false;
				}
			}
		}
		return result;
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}

	public static String trimToEmpty(String str) {
		return str == null ? EMPTY : str.trim();
	}

	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen)
					&& Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		}
		else if (stripChars.length() == 0) {
			return str;
		}
		else {
			while ((start != strLen)
					&& (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		}
		else if (stripChars.length() == 0) {
			return str;
		}
		else {
			while ((end != 0)
					&& (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	public static boolean equals(String str, String comp) {
		return str == null ? comp == null : str.equals(comp);
	}

	public static boolean equalsIgnoreCase(String str, String comp) {
		return str == null ? comp == null : str.equalsIgnoreCase(comp);
	}

	public static int indexOf(String str, String search) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(search);
	}

	public static int indexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	public static int lastIndexOf(String str, String search) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(search);
	}

	public static int lastIndexOf(String str, char searchChar) {
		if (isEmpty(str)) {
			return -1;
		}
		return str.lastIndexOf(searchChar);
	}

	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return str.indexOf(searchStr) >= 0;
	}

	public static boolean containsIgnoreCase(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return contains(str.toUpperCase(), searchStr.toUpperCase());
	}

	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	private static String[] splitWorker(String str, String separatorChars,
										int max, boolean preserveAllTokens) {

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		if (separatorChars == null) {
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		else if (separatorChars.length() == 1) {
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		else {
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match || preserveAllTokens) {
						lastMatch = true;
						if (sizePlus1++ == max) {
							i = len;
							lastMatch = false;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				else {
					lastMatch = false;
				}
				match = true;
				i++;
			}
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}

	private static String[] splitWorker(String str, char separatorChar,
										boolean preserveAllTokens) {

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match || preserveAllTokens) {
					list.add(str.substring(start, i));
					match = false;
					lastMatch = true;
				}
				start = ++i;
				continue;
			}
			else {
				lastMatch = false;
			}
			match = true;
			i++;
		}
		if (match || (preserveAllTokens && lastMatch)) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}

	public static String[] splitPreserveAllTokens(String str, char separatorChar) {
		return splitWorker(str, separatorChar, true);
	}

	public static String[] split(String str, String separatorChars) {
		return splitWorker(str, separatorChars, -1, false);
	}

	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}

	public static String removeEnd(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}

	public static String remove(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		return replace(str, remove, "", -1);
	}

	public static String replace(String text, String repl, String with, int max) {
		if (isEmpty(text) || isEmpty(repl) || with == null || max == 0) {
			return text;
		}
		int start = 0;
		int end = text.indexOf(repl, start);
		if (end == -1) {
			return text;
		}
		int replLength = repl.length();
		int increase = with.length() - replLength;
		increase = (increase < 0 ? 0 : increase);
		increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
		StringBuffer buf = new StringBuffer(text.length() + increase);
		while (end != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + replLength;
			if (--max == 0) {
				break;
			}
			end = text.indexOf(repl, start);
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	public static String replaceOnce(String text, String repl, String with) {
		return replace(text, repl, with, 1);
	}

	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (isAsciiAlpha(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((isAsciiAlpha(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphaNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (isAsciiAlphanumeric(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAlphaNnumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((isAsciiAlphanumeric(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumeric(String str) {
		if (isEmpty(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (isAsciiNumeric(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((isAsciiNumeric(str.charAt(i)) == false)
					&& (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static String defaultString(String str, String defaultStr) {
		return str == null ? defaultStr : str;
	}

	public static String emptyString(String str)
	{
		String rtn = "";
		if( str == null || str.equals("null") )
		{
			rtn = "";
		}else if( str.trim().length() == 0 )
		{
			rtn = "";
		}else
		{
			rtn = str;
		}
		return rtn;
	}

	public static String defaultIfEmpty(String str, String defaultStr) {
		return StringUtils.isEmpty(str) ? defaultStr : str;
	}

	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	public static String unicodeEscaped(char ch) {
		if (ch < 0x10) {
			return "\\u000" + Integer.toHexString(ch);
		}
		else if (ch < 0x100) {
			return "\\u00" + Integer.toHexString(ch);
		}
		else if (ch < 0x1000) {
			return "\\u0" + Integer.toHexString(ch);
		}
		return "\\u" + Integer.toHexString(ch);
	}

	public static String convertUnicode(String text){
		char[] chBuffer = text.toCharArray();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < chBuffer.length; i++) {
			if((chBuffer[i] == 32)) {
				buffer.append(" ");
				continue;
			}
			buffer.append("\\u");
			buffer.append(Integer.toHexString(chBuffer[i]));
		}
		return buffer.toString();
	}

	public static boolean isAscii(char ch) {
		return ch < 128;
	}

	public static boolean isAsciiPrintable(char ch) {
		return ch >= 32 && ch < 127;
	}

	public static boolean isAsciiAlpha(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}

	public static boolean isAsciiAlphaUpper(char ch) {
		return ch >= 'A' && ch <= 'Z';
	}

	public static boolean isAsciiAlphaLower(char ch) {
		return ch >= 'a' && ch <= 'z';
	}

	public static boolean isAsciiNumeric(char ch) {
		return ch >= '0' && ch <= '9';
	}

	public static boolean isAsciiAlphanumeric(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')
				|| (ch >= '0' && ch <= '9');
	}

	public static String hex(char ch) {
		return Integer.toHexString(ch).toUpperCase();
	}

	public static StringTokenizer getToken(String str, String delimeter) {
		if (str != null) {
			if (delimeter != null) {
				delimeter = "";
			}
			StringTokenizer stringToken = new StringTokenizer(str, delimeter);
			return stringToken;
		}
		return null;
	}

	public static StringTokenizer getToken(String str) {
		if (str != null) {
			return new StringTokenizer(str);
		}
		return null;
	}

	public static String makeMD5Digest(String id) {

		if (id == null || "".equals(id)) {
			id = Long.toString(System.currentTimeMillis());
		}

		byte[] defaultBytes = id.getBytes();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte[] messageDigest = algorithm.digest();
			StringBuilder hexString = new StringBuilder();
			for (int i = 0; i < messageDigest.length; i++) {
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			}
			return hexString.toString();
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static long stringToLong(String str) {

		if (!isNumeric(str)) {
			throw new NumberFormatException();
		}
		return Long.parseLong(str);

	}

	public static int stringToInt(String str) {
		if(str==null || str.equals("null")) {
			return 0;
		} else {
			if (!isNumeric(str)) {
				throw new NumberFormatException();
			}
			return Integer.parseInt(str);
		}

	}

	public static String convertCharSet(String str, String preEnc, String enc)
			throws UnsupportedEncodingException {
		String result = null;
		result = new String(str.getBytes(preEnc), enc);
		return result;
	}

	public static String koToChar(String str) {
		int a = 0;
		int b = 0;
		int c = 0; //
		String result = "";

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			UnicodeBlock unicode = UnicodeBlock.of(ch);
			//if (ch >= 0xAC00 && ch <= 0xD7A3) { //
			if (unicode == UnicodeBlock.HANGUL_JAMO
					|| unicode == UnicodeBlock.HANGUL_SYLLABLES) {

				c = ch - 0xAC00;
				a = c / (21 * 28);
				c = c % (21 * 28);
				b = c / 28;
				c = c % 28;

				result = result + CHAR_1ST[a] + CHAR_2ND[b];
				if (c != 0) {
					result = result + CHAR_3RD[c];
				} //
			}
			else {
				result = result + ch;
			}
		}
		return result;
	}

	public static boolean isHangleCheck(String str){
		boolean flag = false;
		int hanLeng = 0;
		if(str == null || str.length() < 1) {
			return flag;
		}

		for(int i=0; i< str.length(); i++){
			if(isHangleChar(str.charAt(i))){
				hanLeng++;
				flag =  true;
				if(hanLeng > 2) {
					break;
				}
			}else{
				hanLeng = 0;
			}
		}
		return flag;
	}

	public static boolean isHangleChar(char ch) {
		String block = UnicodeBlock.of(ch).toString();

		if(block.equals("HANGUL_JAMO") || block.equals("HANGUL_SYLLABLES") || block.equals("HANGUL_COMPATIBILITY_JAMO")) {
			return true;
		}

		return false;
	}

	public static String defaultString(String str) {
		return str == null ? EMPTY : str;
	}

	//euckr to uniconde로 변경
	public static String euckrToUnicode(String str) {
		str = str.trim();
		char[] chBuffer = str.toCharArray();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < chBuffer.length; i++) {
			char ch = str.charAt(i);
			UnicodeBlock unicodeBlock = UnicodeBlock.of( ch );

			if( UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)|| (chBuffer[i] == 32)) {
				if((chBuffer[i] == 32)) {
					buffer.append(" ");
					continue;
				}
				buffer.append("\\u");
				buffer.append(Integer.toHexString(chBuffer[i]));
			}
			else {
				buffer.append(chBuffer[i]);
			}

		}

		return buffer.toString();
	}

	public static String utf8ToEuckr(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("\\", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	public static String encodingType(String str)
	{
		str = str.trim();
		char[] chBuffer = str.toCharArray();
		String encodingType = "EUC-KR";

		for (int i = 0; i < chBuffer.length; i++) {
			char ch = str.charAt(i);
			UnicodeBlock unicodeBlock = UnicodeBlock.of( ch );

			if( UnicodeBlock.HANGUL_SYLLABLES.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_COMPATIBILITY_JAMO.equals(unicodeBlock) ||
					UnicodeBlock.HANGUL_JAMO.equals(unicodeBlock)) {
				encodingType="UTF-8";
				break;
			}
		}

		return encodingType;
	}

	public static int parseInt(Object obj) {
		try {
			if(obj==null){
				return 0;
			}
			return Integer.parseInt(String.valueOf(obj));
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	public static float parseFloat(Object obj) {
		try {
			if(obj==null){
				return 0;
			}
			return  Float.parseFloat(String.valueOf(obj));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public static String toString(Object src){
		String rst="";
		try{
			rst=String.valueOf(src);
		}catch(Exception e){
			return "";
		}
		return rst;
	}

	public static boolean isEmpty(Object str) {
		return str == null || toString(str).trim().length() == 0;
	}

	public static long parseLong(Object obj) {
		try {
			if(obj==null){
				return 0L;
			}
			return Long.parseLong(String.valueOf(obj));
		} catch (NumberFormatException e) {
			return 0L;
		}
	}

	public static String format(long n)
	{

		NumberFormat nf = NumberFormat.getNumberInstance();
		try {
			return nf.format(n);
		} catch(Exception e) {
			return "0";
		}
	}

	public static String caculateFormat(String src,String intrt,String rpaypd){
		int srcInt = NumberUtils.toInt(src);
		double intrtInt = NumberUtils.toDouble(intrt);
		int rst = (int)(srcInt*(intrtInt/100));
		rst = rst/12;
		return format(rst*NumberUtils.toInt(rpaypd));
	}

	public static String format(Object n)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		try {
			long tmp = parseLong(n);
			return nf.format(tmp);
		} catch(Exception e) {
			return "0";
		}
	}

	public static String formatting(Object source,String format){
		String sourceStr = (String)source;
		if(isEmpty(sourceStr)) {
			return "";
		}
		String args[] = removeEmptyArray(format.split("[^#0]"));
		int argsLen[] = new int[args.length];
		int idx=0;
		for(String tmp : args){
			argsLen[idx] = tmp.length();
			idx++;
		}


		String loopRst[] = new String[args.length];
		int curIdx =0;
		for(int i=0;i<args.length;i++){
			String formatArg =  args[i];
			loopRst[i] = formatting(sourceStr,curIdx,argsLen[i],formatArg);
			curIdx += argsLen[i];
		}

		String head = format.substring(0,1);
		String delims[] =  removeEmptyArray(format.split("[#0]"));
		int loopSize = delims.length > loopRst.length?delims.length:loopRst.length;
		List<String> rstList = new ArrayList<String>();
		if(!head.equals("0") && !head.equals("#")){
			for(int i=0;i<loopSize;i++){
				if(i<delims.length) {
					rstList.add(delims[i]);
				}
				if(i<loopRst.length) {
					rstList.add(loopRst[i]);
				}
			}
		}else{
			for(int i=0;i<loopSize;i++){
				if(i<loopRst.length) {
					rstList.add(loopRst[i]);
				}
				if(i<delims.length) {
					rstList.add(delims[i]);
				}
			}
		}
		String rst = "";
		for(String tmp : rstList){
			rst += tmp;
		}

		return rst;
	}

	public static String[] removeEmptyArray(String  args[]){

		int i=0;
		for(String tmp : args){
			if(isNotEmpty(tmp)){
				i++;
			}
		}

		String rst[] = new String[i];
		i=0;
		for(String tmp : args){
			if(isNotEmpty(tmp)){
				rst[i] = tmp;
				i++;
			}
		}

		return rst;
	}

	public static String formatting(String param, int start,int range,String format){
		String substr = substring(param, start, start+range);
		substr = substring(substr,0,format.length());
		if(substr.length()<format.length()){
			substr = format.substring(0,format.length() - substr.length())+substr;
		}
		return substr.replaceAll("#", "");
	}

	public static boolean isEquals(String a,String b){
		if(a==null) {
			return false;
		}
		if(b==null) {
			return false;
		}
		return a.equals(b);
	}

	public static String isEquals(String a,String b,String success,String fail){
		if(StringUtils.isEquals(a, b)){
			return success;
		}else{
			return fail;
		}
	}

	public static String[] splitVal(String str, String separator)
	{
		StringTokenizer st = new StringTokenizer(str, separator);
		String[] values = new String[st.countTokens()];
		int pos = 0;
		while (st.hasMoreTokens())
		{
			values[pos++] = st.nextToken();
		}

		return values;
	}

	public static String deleteChar(String strString, char strChar)
	{
		if ( isBlank(strString) ) {
			return "";
		}

		strString = strString.trim();
		byte[] source = strString.getBytes();
		byte[] result = new byte[source.length];
		int j = 0;
		for (int i = 0; i < source.length; i++)
		{
			if (source[i] == (byte)strChar ) {
				i++;
			}

			result[j++] = source[i];
		}

		return new String(result).trim();
	}

	public static String getString(String src,int len, String tail){
		if(src==null){
			return "";
		}
		float rstLen=0;
		String rst="";
		char c[]=src.toCharArray();
		int i=0;
		for (i = 0; i < c.length; i++) {
			if (c[i] == 60) { /* < 시작하는거 체크*/
				rstLen += 1;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] == 62) { /* >끝나는거 체크*/
				rstLen += 1;
				rst+=src.substring(i,i+1);
			} else if (src.charAt(i) >255) { /* 한글로 시작하는 부분 체크 */
				rstLen += 1.21;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] >= 97 && (byte)c[i] <= 122) { /* 영문(소문자)으로 시작하는 부분 체크 */
				rstLen += 0.71;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] >= 65 && (byte)c[i] <= 90) { /* 영문(대문자)으로 시작하는 부분 체크 */
				rstLen += 0.82;
				rst+=src.substring(i,i+1);
			} else if ((byte)c[i] >= 48 && (byte)c[i] <= 57) { /* 숫자 인지 체크 */
				rstLen += 0.61;
				rst+=src.substring(i,i+1);
			} else { /* 특수 문자 기호값 */
				rstLen += 0.71;
				rst+=src.substring(i,i+1);
			}
			// System.out.println((int) src.charAt(i));
			if (rstLen >= len) {
				rst+=tail;
				break;
			}
		}
		return rst;
	}

	public static String removeTagScript(String str) {
		return str.replaceAll("\\<.*?\\>", "");
	}

	public static String html(Object string) {
		if(string == null){
			return null;
		}else{
			//'를 치환한다.
			String rst=string.toString();
			rst = StringUtils.replace(rst, "<", "&#60;");
			rst = StringUtils.replace(rst, ">", "&#62;");
			rst = StringUtils.replace(rst, "'", "&#39;");
			rst = StringUtils.replace(rst, "\"", "&#34;");
			return rst;
		}
	}

	public static String script(Object string) {
		if(string == null){
			return null;
		}else{
			//'를 치환한다.
			String rst=string.toString();
			rst = StringUtils.replace(rst, "'", "\\'");
			rst = StringUtils.replace(rst, "&", "\\&");
			return rst;
		}
	}

	public  static  String[]  removeDup(String  []  strArray)
	{
		if(strArray  ==  null  ||  strArray.length  ==  0) {
			return  null;
		}
		LinkedHashSet<String>  hSet  =  new  LinkedHashSet<String>();
		for(String  str  :  strArray){
			hSet.add(str);
		}
		return  hSet.toArray(new  String  [hSet.size()]);
	}

	public  static  String  htmlspecialchars(String content) {
		if(isEmpty(content)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<content.length(); i++) {
			char c = content.charAt(i);
			switch (c) {
				case '<' :
					sb.append("&lt;");
					break;
				case '>' :
					sb.append("&gt;");
					break;
				case '&' :
					sb.append("&amp;");
					break;
				case '"' :
					sb.append("&quot;");
					break;
				case '\'' :
					sb.append("&apos;");
					break;
				default:
					sb.append(c);
			}
		}

		content = sb.toString();
		return content;
	}

	public static int getByteLength(String str){
		int subjectLen = str.length();
		int strLength =0;
		char tempChar[]=new char[subjectLen];
		for(int i=0; i<subjectLen;i++){
			tempChar[i] = str.charAt(i);
			if(tempChar[i]<128) {
				strLength++;
			} else {
				strLength +=2;
			}
		}
		return strLength;
	}

	public static Map<String,String> getMapForQueryString(String queryString){
		String vals[] = StringUtils.splitVal(queryString, "&");
		Map<String,String> tmp = new HashMap<String,String>();
		for(String val : vals){
			String params[] = StringUtils.splitVal(val, "=");
			String key = StringUtils.isNotEmpty(params[0])?params[0]:"";
			String value = "";
			if(params.length>1){
				value = StringUtils.isNotEmpty(params[1])?params[1]:"";
			}
			if(StringUtils.isNotEmpty(key)) {
				tmp.put(key, value);
			}
		}
		return tmp;
	}

	public static Map<String,String> getMapForQueryStringDecode(String queryString) {
		String vals[] = StringUtils.splitVal(queryString, "&");
		Map<String,String> tmp = new HashMap<String,String>();
		for(String val : vals){
			String params[] = StringUtils.splitVal(val, "=");
			String key = StringUtils.isNotEmpty(params[0])?params[0]:"";
			String value = StringUtils.isNotEmpty(params[1])?params[1]:"";
			if(StringUtils.isNotEmpty(key)) {
				try {
					tmp.put(key, URLDecoder.decode(value,"euc-kr"));
				} catch (UnsupportedEncodingException e) { tmp.put(key, value); }
			}
		}
		return tmp;
	}

	public static String convertSet(String src, String from, String to) throws UnsupportedEncodingException {
		return new String(src.getBytes(from),to);
	}

	public static String setBr(String contents){
		return contents.replace("\n", "<BR/>");
	}

	public static String change(String source, String before, String after) {
		int i = 0;
		int j = 0;
		if(source==null){
			return "";
		}
		StringBuffer sb = new StringBuffer();

		while ((j = source.indexOf(before, i)) >= 0) {
			sb.append(source.substring(i, j));
			sb.append(after);
			i = j + before.length();
		}

		sb.append(source.substring(i));
		return sb.toString();
	}

	public static String getFromArray(int idx,String src[]){
		if(src==null || src.length==0){
			return "";
		}
		if(src.length-1<idx){
			return "";
		}
		return src[idx];
	}

	public static boolean matchArray(String src[],String dest){
		for(int i=0;i<src.length;i++){
			if(src[i]!=null){
				if (src[i].equalsIgnoreCase(dest)) {
					return true;
				}
			}
		}
		return false;
	}

	public static int matchArrayIdx(String src[],String dest){
		for(int i=0;i<src.length;i++){
			if(src[i]!=null){
				if (src[i].equalsIgnoreCase(dest)) {
					return i;
				}
			}
		}
		return -1;
	}

	public static String[] explode(String srcParam,String delimParam){
		String src = srcParam;
		String delim = delimParam;
		if(src==null || src.length()==0){
			return new String[0];
		}
		if(delim.length()>=2){
			src=change(src, delim, "\27");
			delim="\27";
		}
		StringTokenizer stk=new StringTokenizer(src,delim);
		int size=stk.countTokens();
		String rst[]=new String[size];
		int i=0;
		while(stk.hasMoreTokens()){
			rst[i]=stk.nextToken();
			i++;
		}
		return rst;
	}

	public static String implode(String src[],String delim){
		if(src==null || src.length==0){
			return "";
		}
		int size=src.length;
		StringBuffer rst= new StringBuffer("");
		for(int i=0;i<size;i++){
			if(i!=size-1){
				String tmp = src[i] + delim;
				rst.append(tmp);
			}else{
				rst.append(src[i]);
			}
		}
		return rst.toString();
	}

	public static int generateNumber(int length) {

		String numStr = "1";
		String plusNumStr = "1";

		for (int i = 0; i < length; i++) {
			numStr += "0";

			if (i != length - 1) {
				plusNumStr += "0";
			}
		}

		Random random = new Random();
		int result = random.nextInt(Integer.parseInt(numStr)) + Integer.parseInt(plusNumStr);

		if (result > Integer.parseInt(numStr)) {
			result = result - Integer.parseInt(plusNumStr);
		}

		return result;
	}

	public static String convertBr(String source){
		return change(source, "\n", "<BR/>");
	}

	public static String percent(Object value1,Object value2,String format){
		try{
			float f1 = Float.parseFloat(String.valueOf(value1));
			float f2 = Float.parseFloat(String.valueOf(value2));
			return StringUtils.decimalFormat(100*f1/f2,format);
		}catch(Exception e){
			return "";
		}
	}

	public static int strLen(Object obj){
		if(obj==null){
			return 0;
		}
		return String.valueOf(obj).length();
	}

	public static String convertToHtml(String contents) {
		String value = "";

		if (isNotEmpty(contents)) {
			value = contents.replaceAll("&lt;","<");
			value = contents.replaceAll("&gt;",">");
		}

		return value;
	}

}


