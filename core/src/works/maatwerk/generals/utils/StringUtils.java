//Project: Client
//Author: J Putters
//Creation date: 26-Mar-18

package works.maatwerk.generals.utils;

public final class StringUtils {

    private StringUtils(){ }

    /**
     * Returns the same string with the first character capitalized
     * @param string
     * @return
     */
    public static String UcFirst(String string){
        return string.substring(0,1) + string.substring(1).toLowerCase();
    }
}
