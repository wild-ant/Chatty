package ru.wildant.chatty;

//NOTHING TO SEE HERE!
public class Extension {
    public static String JoinStringArray(String[] strings, Integer first, Integer last, String divider)
    {
        String result = "";
        for(int i = first; i < last; i++) {
            result += strings[i] + divider;
        }
        return result;
    }
}
