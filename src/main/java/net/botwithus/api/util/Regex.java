package net.botwithus.api.util;

//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class Regex {
    //    private static final Cache<String, Pattern> EQUALS_CACHE;
//    private static final Cache<String, Pattern> EQUALS_MULTIPLE_CACHE;
//    private static final Cache<String, Pattern> CONTAINS_CACHE;
//    private static final Cache<String, Pattern> OTHER_CACHE;
    private static final Pattern NULL_PATTERN;
    private static final Pattern ITEM_NAME_PATTERN = Pattern.compile("(.*?)(?:|\\s\\()(?:.(?!\\s\\())+$");

    static {
//        EQUALS_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(10L, TimeUnit.MINUTES).build();
//        EQUALS_MULTIPLE_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(10L, TimeUnit.MINUTES).build();
//        CONTAINS_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(10L, TimeUnit.MINUTES).build();
//        OTHER_CACHE = CacheBuilder.newBuilder().softValues().expireAfterAccess(10L, TimeUnit.MINUTES).build();
        NULL_PATTERN = Pattern.compile("");
    }

    public static Pattern getPatternForExactString(String string) {
        if (string == null) {
            return NULL_PATTERN;
        } else {
            return Pattern.compile("^(" + escapeSpecialCharacters(string) + ")$");
//            Pattern pattern = (Pattern)EQUALS_CACHE.getIfPresent(string);
//            if (pattern != null) {
//                return pattern;
//            } else {
//                EQUALS_CACHE.put(string, pattern = Pattern.compile("^" + escapeSpecialCharacters(string) + "$"));
//                return pattern;
//            }
        }
    }

    private static String escapeSpecialCharacters(String string) {
        StringBuilder escaped_string = new StringBuilder();
        char[] chars = string.toCharArray();
        int charLength = chars.length;

        for (int i = 0; i < charLength; ++i) {
            char c = chars[i];
            if (c == '(' || c == ')' || c == '^' || c == '$' || c == '.' || c == '*' || c == '?' || c == '|' || c == '[' || c == '{' || c == '+') {
                escaped_string.append('\\');
            }

            escaped_string.append(c);
        }

        return escaped_string.toString();
    }

    public static Pattern getPatternForContainsString(String string) {
        if (string == null) {
            return NULL_PATTERN;
        } else {
            return Pattern.compile(".*" + escapeSpecialCharacters(string) + ".*");
//            Pattern pattern = (Pattern)CONTAINS_CACHE.getIfPresent(string);
//            if (pattern != null) {
//                return pattern;
//            } else {
//                CONTAINS_CACHE.put(string, pattern = Pattern.compile(".*" + escapeSpecialCharacters(string) + ".*"));
//                return pattern;
//            }
        }
    }

    public static Pattern getPatternForExactStrings(String... strings) {
        StringBuilder pattern_builder = (new StringBuilder()).append("^(");
        boolean first = true;
        String[] stringArray = strings;
        int stringsLength = strings.length;

        for (int i = 0; i < stringsLength; ++i) {
            String s = stringArray[i];
            if (first) {
                first = false;
            } else {
                pattern_builder.append('|');
            }

            pattern_builder.append(escapeSpecialCharacters(s));
        }

        String regex = pattern_builder.append(")$").toString();
        return Pattern.compile(regex);
//        Pattern pattern = (Pattern)EQUALS_MULTIPLE_CACHE.getIfPresent(regex);
//        if (pattern != null) {
//            return pattern;
//        } else {
//            pattern = Pattern.compile(regex);
//            EQUALS_MULTIPLE_CACHE.put(regex, pattern);
//            return pattern;
//        }
    }

    public static Pattern getPatternForContainingOneOf(String... strings) {
        StringBuilder pattern_builder = (new StringBuilder()).append('^');
        boolean first = true;
        String[] stringArray = strings;
        int stringsLength = strings.length;

        for (int i = 0; i < stringsLength; ++i) {
            String s = stringArray[i];
            if (first) {
                first = false;
            } else {
                pattern_builder.append('|');
            }

            pattern_builder.append(".*").append(escapeSpecialCharacters(s)).append(".*");
        }

        return Pattern.compile(pattern_builder.append('$').toString());
    }

    public static List<Pattern> getPatternsForExactStrings(String... strings) {
        List<Pattern> list = new ArrayList(strings.length);
        String[] stringArray = strings;
        int stringLength = strings.length;

        for (int i = 0; i < stringLength; ++i) {
            String string = stringArray[i];
            list.add(getPatternForExactString(string));
        }

        return list;
    }

    public static List<Pattern> getPatternsForContainsStrings(String... strings) {
        List<Pattern> list = new ArrayList(strings.length);
        String[] stringArray = strings;
        int stringsLength = strings.length;

        for (int i = 0; i < stringsLength; ++i) {
            String string = stringArray[i];
            list.add(getPatternForContainsString(string));
        }

        return list;
    }

    public static Pattern getPatternForNotContainingAnyString(String... strings) {
        StringBuilder pattern_builder = (new StringBuilder()).append('^');
        boolean first = true;
        String[] stringArray = strings;
        int stringsLength = strings.length;

        pattern_builder.append("(?!.*(?:");
        for (int i = 0; i < stringsLength; ++i) {
            String s = stringArray[i];
            if (first) {
                first = false;
            } else {
                pattern_builder.append('|');
            }

            pattern_builder.append(escapeSpecialCharacters(s));
        }
        pattern_builder.append(")).*");
        return Pattern.compile(pattern_builder.append('$').toString());
    }

    public static Pattern getPattern(String regex) {
        return Pattern.compile(regex);
//        Pattern pattern = (Pattern)OTHER_CACHE.getIfPresent(regex);
//        if (pattern != null) {
//            return pattern;
//        } else {
//            pattern = Pattern.compile(regex);
//            OTHER_CACHE.put(regex, pattern);
//            return pattern;
//        }
    }
}
