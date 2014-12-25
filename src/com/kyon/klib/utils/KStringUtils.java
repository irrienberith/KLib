package com.kyon.klib.utils;

import java.util.StringTokenizer;

/**
 * <p>Operations on {@link String} that are
 * <code>null</code> safe.</p>
 * <p/>
 * <ul>
 * <li><b>IsEmpty/IsBlank</b>
 * - checks if a String contains text</li>
 * <li><b>Trim/Strip</b>
 * - removes leading and trailing whitespace</li>
 * <li><b>Equals</b>
 * - compares two strings null-safe</li>
 * <li><b>startsWith</b>
 * - check if a String starts with a prefix null-safe</li>
 * <li><b>endsWith</b>
 * - check if a String ends with a suffix null-safe</li>
 * <li><b>IndexOf/LastIndexOf/Contains</b>
 * - null-safe index-of checks
 * <li><b>IndexOfAny/LastIndexOfAny/IndexOfAnyBut/LastIndexOfAnyBut</b>
 * - index-of any of a set of Strings</li>
 * <li><b>ContainsOnly/ContainsNone/ContainsAny</b>
 * - does String contains only/none/any of these characters</li>
 * <li><b>Substring/Left/Right/Mid</b>
 * - null-safe substring extractions</li>
 * <li><b>SubstringBefore/SubstringAfter/SubstringBetween</b>
 * - substring extraction relative to other strings</li>
 * <li><b>Split/Join</b>
 * - splits a String into an array of substrings and vice versa</li>
 * <li><b>Remove/Delete</b>
 * - removes part of a String</li>
 * <li><b>Replace/Overlay</b>
 * - Searches a String and replaces one String with another</li>
 * <li><b>Chomp/Chop</b>
 * - removes the last part of a String</li>
 * <li><b>LeftPad/RightPad/Center/Repeat</b>
 * - pads a String</li>
 * <li><b>UpperCase/LowerCase/SwapCase/Capitalize/Uncapitalize</b>
 * - changes the case of a String</li>
 * <li><b>CountMatches</b>
 * - counts the number of occurrences of one String in another</li>
 * <li><b>IsAlpha/IsNumeric/IsWhitespace/IsAsciiPrintable</b>
 * - checks the characters in a String</li>
 * <li><b>DefaultString</b>
 * - protects against a null input String</li>
 * <li><b>Reverse/ReverseDelimited</b>
 * - reverses a String</li>
 * <li><b>Abbreviate</b>
 * - abbreviates a string using ellipsis</li>
 * <li><b>Difference</b>
 * - compares Strings and reports on their differences</li>
 * <li><b>LevensteinDistance</b>
 * - the number of changes needed to change one String into another</li>
 * </ul>
 * <p/>
 * <p>The <code>IFStringUtils</code> class defines certain words related to
 * String handling.</p>
 * <p/>
 * <ul>
 * <li>null - <code>null</code></li>
 * <li>empty - a zero-length string (<code>""</code>)</li>
 * <li>space - the space character (<code>' '</code>, char 32)</li>
 * <li>whitespace - the characters defined by {@link Character#isWhitespace(char)}</li>
 * <li>trim - the characters &lt;= 32 as in {@link String#trim()}</li>
 * </ul>
 * <p/>
 * <p><code>IFStringUtils</code> handles <code>null</code> input Strings quietly.
 * That is to say that a <code>null</code> input will return <code>null</code>.
 * Where a <code>boolean</code> or <code>int</code> is being returned
 * details vary by method.</p>
 * <p/>
 * <p>A side effect of the <code>null</code> handling is that a
 * <code>NullPointerException</code> should be considered a bug in
 * <code>IFStringUtils</code> (except for deprecated methods).</p>
 * <p/>
 * <p>Methods in this class give sample code to explain their operation.
 * The symbol <code>*</code> is used to indicate any input including <code>null</code>.</p>
 *
 * @author <a href="http://jakarta.apache.org/turbine/">Apache Jakarta Turbine</a>
 * @author <a href="mailto:jon@latchkey.com">Jon S. Stevens</a>
 * @author Daniel L. Rall
 * @author <a href="mailto:gcoladonato@yahoo.com">Greg Coladonato</a>
 * @author <a href="mailto:ed@apache.org">Ed Korthof</a>
 * @author <a href="mailto:rand_mcneely@yahoo.com">Rand McNeely</a>
 * @author Stephen Colebourne
 * @author <a href="mailto:fredrik@westermarck.com">Fredrik Westermarck</a>
 * @author Holger Krauth
 * @author <a href="mailto:alex@purpletech.com">Alexander Day Chaffee</a>
 * @author <a href="mailto:hps@intermeta.de">Henning P. Schmiedehausen</a>
 * @author Arun Mammen Thomas
 * @author Gary Gregory
 * @author Phil Steitz
 * @author Al Chou
 * @author Michael Davey
 * @author Reuben Sivan
 * @author Chris Hyzer
 * @author Scott Johnson
 * @version $Id: IFStringUtils.java 635447 2008-03-10 06:27:09Z bayard $
 * @see String
 * @since 1.0
 */
public class KStringUtils {
    // Performance testing notes (JDK 1.4, Jul03, scolebourne)
    // Whitespace:
    // Character.isWhitespace() is faster than WHITESPACE.indexOf()
    // where WHITESPACE is a string of all whitespace characters
    //
    // Character access:
    // String.charAt(n) versus toCharArray(), then array[n]
    // String.charAt(n) is about 15% worse for a 10K string
    // They are about equal for a length 50 string
    // String.charAt(n) is about 4 times better for a length 3 string
    // String.charAt(n) is best bet overall
    //
    // Append:
    // String.concat about twice as fast as StringBuffer.append
    // (not sure who tested this)

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 空白字符串
     */
    public static final String BLANK = " ";


    private KStringUtils() {

    }

    // Empty checks
    //-----------------------------------------------------------------------

    /**
     * <p>检查一个字符串是否是空字符串</p>
     * <p/>
     * <pre>
     * IFStringUtils.isEmpty(null)      = true
     * IFStringUtils.isEmpty("")        = true
     * IFStringUtils.isEmpty(" ")       = false
     * IFStringUtils.isEmpty("bob")     = false
     * IFStringUtils.isEmpty("  bob  ") = false
     * </pre>
     * <p/>
     *
     * @param str 被检查的字符串，可能为null
     * @return 如果字符串为空或者是null则返回true，否则返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>检查一个字符串是否不为空字符串</p>
     * <p/>
     * <pre>
     * IFStringUtils.isNotEmpty(null)      = false
     * IFStringUtils.isNotEmpty("")        = false
     * IFStringUtils.isNotEmpty(" ")       = true
     * IFStringUtils.isNotEmpty("bob")     = true
     * IFStringUtils.isNotEmpty("  bob  ") = true
     * </pre>
     *
     * @param str 被检查的字符串，可能是null
     * @return 如果字符串不为空且不是null则返回true，否则返回false
     */
    public static boolean isNotEmpty(String str) {
        return !KStringUtils.isEmpty(str);
    }

    /**
     * <p>检查一个字符串是否为空白字符串</p>
     * IFStringUtils.isBlank(null)      = true
     * IFStringUtils.isBlank("")        = true
     * IFStringUtils.isBlank(" ")       = true
     * IFStringUtils.isBlank("bob")     = false
     * IFStringUtils.isBlank("  bob  ") = false
     *
     * @param str 被检查的字符串
     * @return 如果字符串为空、空格符或者null那么返回true，否则返回false
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>检查一个字符串是否不是空白字符串</p>
     * <p/>
     * <pre>
     * IFStringUtils.isNotBlank(null)      = false
     * IFStringUtils.isNotBlank("")        = false
     * IFStringUtils.isNotBlank(" ")       = false
     * IFStringUtils.isNotBlank("bob")     = true
     * IFStringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str 被检查的字符串
     * @return 如果字符串不是空字符串、空格符以及null那么就返回true，否则返回false
     */
    public static boolean isNotBlank(String str) {
        return !KStringUtils.isBlank(str);
    }

    // Trim
    //-----------------------------------------------------------------------

    /**
     * <p>Removes control characters (char &lt;= 32) from both
     * ends of this String, handling <code>null</code> by returning
     * <code>null</code>.</p>
     * <p/>
     * <p>The String is trimmed using {@link String#trim()}.
     * Trim removes start and end characters &lt;= 32.
     * To strip whitespace use {@link #(String)}.</p>
     * <p/>
     * <p>To trim your choice of characters, use the
     * {@link #(String, String)} methods.</p>
     * <p/>
     * <pre>
     * IFStringUtils.trim(null)          = null
     * IFStringUtils.trim("")            = ""
     * IFStringUtils.trim("     ")       = ""
     * IFStringUtils.trim("abc")         = "abc"
     * IFStringUtils.trim("    abc    ") = "abc"
     * </pre>
     *
     * @param str the String to be trimmed, may be null
     * @return the trimmed string, <code>null</code> if null String input
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 去掉字符串首尾的空白，如果剩余的结果是空白字符串那么返回null
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * 检查一个字符串是否以某个指定的字符串开始，如果是的话不做改变，如果不是的话将把指定的字符串添加到原
     * 字符串的起始位置
     */
    public static String perfectStart(String str, String attach) {
        if (str == null) {
            return attach;
        }
        return str.startsWith(attach) ? str : (attach + str);
    }

    /**
     * 检查一个字符串是否以某个指定的字符串结束，如果是的话不做改变，如果不是的话将把指定的字符串添加到原
     * 字符串的末尾位置
     */
    public static String perfectEnd(String str, String attach) {
        if (str == null) {
            return attach;
        }
        return str.endsWith(attach) ? str : (str + attach);
    }

    public static String perfectSurround (String str, String attach) {
        if (str == null) {
            return attach;
        }
        str = str.endsWith(attach) ? str : (str + attach);
        str = str.startsWith(attach) ? str : (attach + str);

        return str;
    }
    /**
     * 获取字符串的长度，如果是null则返回0
     */
    public static int getLength(String str) {
        return str == null ? 0 : str.length();
    }

    /**
     * richer:判断两个字符串是否出去前后的附加字符串外是相等的
     * eg：equalsIgnore("/File/", "File", "/") == true
     */
    public static boolean equalsIgnore(String str1, String str2, String attach){
        return perfectStart(perfectEnd(str1, attach), attach).equals(perfectStart(perfectEnd(str2, attach), attach));
    }

    public static boolean contains(String text, String ch) {
        if (text != null) {
            return text.indexOf(ch) > -1;
        }
        return false;
    }

    /**
     * text 做兼容类.
     * @param text 文本兼容
     * @return StringTokenizer
     */
    public static StringTokenizer text2StringTokenizer(String text) {
        StringTokenizer perLine = new StringTokenizer(text, "\n");
        /// Determine "Return Char" used in this file
        /// This simply finds first occurrence of CR, CR+LF or LF...
        int textLength = text.length();
        for (int i = 0; i < textLength; i++) {
            char iTh = text.charAt(i);
            if (iTh == '\r') {
                if (i < textLength - 1 && text.charAt(i + 1) == '\n')
                    perLine = new StringTokenizer(text, "\r\n");
                else
                    perLine = new StringTokenizer(text, "\r");
                break;
            } else if (iTh == '\n') {
                /// Use the one already created
                break;
            }
        }

        return perLine;
    }

    /**
     * carl:拼接数组字符
     */
    public static String join(String seperator, String[] strings) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        StringBuffer buf = new StringBuffer(length * strings[0].length())
                .append(strings[0]);
        for (int i = 1; i < length; i++) {
            buf.append(seperator).append(strings[i]);
        }
        return buf.toString();
    }

    public static String parseVersion(String xmlDesignerVersion) {
        xmlDesignerVersion = xmlDesignerVersion.replace('A', '0');
        xmlDesignerVersion = xmlDesignerVersion.replace('B', '1');
        xmlDesignerVersion = xmlDesignerVersion.replace('C', '2');
        xmlDesignerVersion = xmlDesignerVersion.replace('D', '3');
        xmlDesignerVersion = xmlDesignerVersion.replace('E', '4');
        xmlDesignerVersion = xmlDesignerVersion.replace('F', '5');
        xmlDesignerVersion = xmlDesignerVersion.replace('G', '6');
        xmlDesignerVersion = xmlDesignerVersion.replace('H', '7');
        xmlDesignerVersion = xmlDesignerVersion.replace('I', '8');
        xmlDesignerVersion = xmlDesignerVersion.replace('J', '9');
        return xmlDesignerVersion;
    }

    public static boolean equals(String str1, String str2){
        return !(str1 == null || str2 == null) && str1.equals(str2);
    }
}
