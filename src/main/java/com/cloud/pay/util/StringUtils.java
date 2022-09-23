package com.cloud.pay.util;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    /**
     * 非负整数校验
     */
    public static final Pattern P_NUMBER = Pattern.compile("([0])|([1-9][0-9]*)");
    /**
     * 邮箱校验
     */
    public static final Pattern P_EMAIL = Pattern.compile("\\w[-\\w{1,2}]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
    /**
     * 手机号校验
     */
    public static final Pattern P_PHONE = Pattern.compile("0?(13|14|15|17|18|19)[0-9]{9}");
    /**
     * 身份证号校验
     */
    public static final Pattern P_IDCARD = Pattern.compile("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)");
    /**
     * URL校验
     */
    public static final Pattern P_URL = Pattern.compile("^(?=^.{3,255}$)(http(s)?:\\/\\/)?(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+\\.\\w+)*([\\?&]\\w+=\\w*)*$");
    /**
     * 保留两位正数校验
     */
    public static final Pattern P_POSITIVE_2 = Pattern.compile("^(?!0+(?:\\.0+)?$)(?:[1-9]\\d*|0)(?:\\.\\d{1,2})?$");

    /**
     *	 金额(正10.2)
     */
    public static final Pattern P_AMOUNT = Pattern.compile("([0])|(?!^0*(\\.0{1,2})?$)^\\d{1,10}(\\.\\d{1,2})?$");

    /**
     *	 金额(正10.223)
     */
    public static final Pattern P_AMOUNT_THREE_DECIMAL = Pattern.compile("([0])|(?!^0*(\\.0{1,2})?$)^\\d{1,10}(\\.\\d{1,3})?$");

    /**
     * 两位数字
     */
    public static final Pattern P_TWO_NUMBER = Pattern.compile("(^\\d{2}$)");

    public static String byte2Hex(byte[] b) {
        StringBuilder sb = new StringBuilder();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                sb.append("0").append(stmp);
            } else {
                sb.append(stmp);
            }
        }
        return sb.toString().toUpperCase();
    }

    public static final byte[] hex2Byte(String str) {
        if (str == null) {
            return null;
        }
        str = str.trim();
        int len = str.length();
        if (len <= 0 || len % 2 == 1) {
            return null;
        }
        byte[] b = new byte[len / 2];
        try {
            for (int i = 0; i < str.length(); i += 2) {
                b[i / 2] = (byte) Integer
                        .decode("0x" + str.substring(i, i + 2)).intValue();
            }
            return b;
        } catch (Exception e) {
            return null;
        }
    }

    public static final byte[] int2Byte(int intValue) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
        }
        return b;
    }

    public static final int byte2Int(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }

    // 小数点加法
    public static final String add(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.add(b2).toString();
    }

    // 小数点减法
    public static final String sub(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.subtract(b2).toString();
    }

    // 小数点乘法
    public static final String mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).toString();
    }

    // 小数点除法
    public static final String division(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_EVEN).toString();
    }

    public static final String addBlank(String name, String price, int length,
                                        String code) {

        if (length(name) >= 22) {
            name = "" + name + "\r\n";
            if (!"".equals(code)) {
                name = "" + name + "【" + code + "】\r\n";
            }
            name = "" + name + addBl(price, 22);
        } else {
            name = "" + name + addBl(price, 22 - (length(name)));
            if (!"".equals(code)) {
                name = "" + name + "【" + code + "】\r\n";
            }
        }
        return name;
    }

    public static final int length(String value) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static final String addBl(String price, int length) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append(" ");
        }
        price = buf.toString() + price + "\r\n";
        return price;
    }

    /**
     * 判断传入参数中是否存在为空的
     * @param objs
     * @return
     */
    public static final boolean isAnyEmpty(Object... objs) {
        for (Object obj : objs) {
            if (isNullOrEmpty(obj)) {
                return true;
            }
        }
        return true;
    }

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     */
    public static final boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }

//	    if(obj instanceof JsonNullFormatVisitor)
//	      return true;

        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }

        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }

        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    /**
     * 判断对象或对象数组中每一个对象是否不为空: 对象为null，字符序列长度为0，集合类、Map为empty
     *
     */
    public static final boolean isNotNullOrEmpty(Object obj) {
        return !isNullOrEmpty(obj);
    }

    /**
     * 在JSON中获取数据时,防止key不存在,value为JSONNull等情况
     *
     */
    public static final String valueOf(Object obj) {
        if(isNullOrEmpty(obj)){
            return "";
        }
        return obj.toString();
    }

    /**
     * 两个相同的对象,将源对象中非空的属性拷贝到目标对象中
     *
     * @param orig  原对象
     * @param dest  目标对象
     *
     */
    /*public static final void copyProperties(Object orig,Object dest) {
        try {
            JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(orig));
            Set<Map.Entry<String,Object>> iterator = json.entrySet();
            for(Map.Entry<String,Object> kv:iterator){
                Object value = kv.getValue();
                String key = kv.getKey();
                if (!StringUtils.isNullOrEmpty(value)) {
                    org.apache.commons.beanutils.BeanUtils.setProperty(dest, key, value);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static final boolean patternEmail(String email){
        Matcher m = P_EMAIL.matcher(email);
        return m.find();
    }

    public static final boolean patternPhone(String phone){
        Matcher m = P_PHONE.matcher(phone);
        return m.find();
    }

    public static final boolean patternIdcard(String idcard){
        Matcher m = P_IDCARD.matcher(idcard);
        return m.find();
    }

    public static final boolean patternIsThreeDecimal(String number){
        Matcher m = P_AMOUNT_THREE_DECIMAL.matcher(number);
        return m.find();
    }

    public static final boolean patternIsTwoLength(String str) {
        return isNullOrEmpty(str) ? false : str.length() == 2;
    }

    public static final boolean patternURL(String url){
        Matcher m = P_URL.matcher(url);
        return m.find();
    }

    public static final boolean patternIsNumeric(String number){
        return P_NUMBER.matcher(number).matches();
    }

    public static final boolean patternRate(String rate){
        return P_NUMBER.matcher(rate).matches();
    }

    public static final boolean patternPositive(String number){
        return P_POSITIVE_2.matcher(number).matches();
    }

    public static final boolean patternAmount(String amount){
        return P_AMOUNT.matcher(amount).matches();
    }

    public static final String convertRateToPercent(String rate){
        BigDecimal b1 = new BigDecimal(rate);
        BigDecimal b2 = new BigDecimal("100");
        BigDecimal r = b1.divide(b2);
        return delRightZero(r.toString());
    }

    public static final String delRightZero(String number) {
        if (number.indexOf(".") > 0 && (number.endsWith("0") || number.endsWith("."))) {
            return delRightZero(number.substring(0, number.length() - 1));
        }
        return number;
    }

    public static final BigDecimal ratio(BigDecimal lastData,BigDecimal thisData){
        if (lastData.doubleValue() == 0.0) {
            return new BigDecimal("999999");
        }
        return thisData.subtract(lastData)
                .divide(lastData,4,BigDecimal.ROUND_HALF_EVEN)
                .multiply(new BigDecimal("100"))
                .setScale(2);
    }

    public static final String encryBankCardNo(String cardNo) {
        if(StringUtils.isNotNullOrEmpty(cardNo) && cardNo.length()>10){

            String str = "";
            for (int i = 0; i < cardNo.length()-10; i++) {
                str += "*";
            }
            //卡号显示前6位后四位,中间用*替换
            return cardNo.substring(0, 6) + str + cardNo.substring(cardNo.length() - 4);
        }
        return "";
    }

    /**
     * 负数表示t1<t2,0表示t1=t2,正数表示t1>t2
     * @param time1 时间t1
     * @param time2 时间t2
     * @return 比较结果
     */
    public static final int compareTimeStr(String time1,String time2) {
        String shortTime1 = time1.replace("-", "").replace(" ", "").replace(":", "");
        String shortTime2 = time2.replace("-", "").replace(" ", "").replace(":", "");

        return shortTime1.compareTo(shortTime2);
    }


    public static void main(String[] args) {
//		boolean a = patternIdcard("2311821919208197717");
//		System.out.println(a);
//		System.out.println(compareTimeStr("2019-05-28 18:23:52","2019-05-28 18:59:07"));
        System.out.println(P_AMOUNT.matcher("1").matches());
    }

}
