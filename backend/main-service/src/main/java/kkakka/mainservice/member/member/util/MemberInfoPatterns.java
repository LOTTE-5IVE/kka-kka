package kkakka.mainservice.member.member.util;

import java.util.regex.Pattern;

public class MemberInfoPatterns {


    private static String NAME_PATTERN = "^[가-힣]+$";
    private static String EMAIL_PATTERN = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$";
    private static String PHONE_NUMBER_PATTERN = "^[0-9]{3}[-]+[0-9]{3,4}[-]+[0-9]{4}$";
    private static String ADDRESS_PATTERN = "^\\([0-9]*\\)+[a-zA-Zㄱ-ㅎ가-힣-_.*\\s\\d]*$";

    public static boolean isValidName(String value) {
        return Pattern.matches(NAME_PATTERN, value);
    }

    public static boolean isValidEmail(String value) {
        return Pattern.matches(EMAIL_PATTERN, value);
    }

    public static boolean isValidPhoneNumber(String value) {
        return Pattern.matches(PHONE_NUMBER_PATTERN, value);
    }

    public static boolean isValidAddress(String value) {
        return Pattern.matches(ADDRESS_PATTERN, value);
    }
}
