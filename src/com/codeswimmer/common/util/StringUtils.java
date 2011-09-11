package com.codeswimmer.common.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringUtils {
    public static final String EMPTY = "";
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static final String NEW_LINE = "\n";
    public static final String CARRIAGE_RETURN = "\r";
    
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }
    
    public static boolean isNotEmpty(String value) {
        return value !=null && value.length() > 0;
    }
    
    public static boolean isBlank(String value) {
        return isEmpty(value) || isWhitespace(value);
    }
    
    public static boolean isNotBlank(String value) {
        return isBlank(value) == false;
    }
    
    public static boolean isWhitespace(String value) {
        if (value == null)
            return false;
        
        if (value.length() == 0)
            return true;
        
        CharacterIterator itor = new StringCharacterIterator(value);
        
        for (char c = itor.first(); c != CharacterIterator.DONE; c = itor.next()) {
            if (Character.isWhitespace(c) == false) {
                return false;
            }
        }
        
        return true;
    }
    
    public static boolean isNotWhitespace(String value) {
        return isWhitespace(value) == false;
    }
    
}
