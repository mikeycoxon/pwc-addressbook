package au.com.pwc.addressbook.model;

public class Messages {
    public static final String ERR_UNSUPPORTED_COMMAND = "A command must start with either see, list, add or exit";
    public static final String ERR_SEE_REQUIRES_SWITCH = "The 'see' command must be followed by a switch";
    public static final String ERR_SEE_WITH_F_MUST_HAVE_B = "The 'see' command with an -f switch must have a -b switch";
    public static final String ERR_ADD_REQUIRES_SWITCH = "The 'add' command must be followed by a switch";
    public static final String ERR_ADD_WITH_F_MUST_HAVE_B = "The 'add' command with an -f switch must have a -b switch";
    public static final String ERR_BAD_F_SWITCH_FORMAT = "The friend switch (-f) must be surrounded by spaces";
    public static final String ERR_BAD_B_SWITCH_FORMAT = "The book switch (-b) must be surrounded by spaces";
}
