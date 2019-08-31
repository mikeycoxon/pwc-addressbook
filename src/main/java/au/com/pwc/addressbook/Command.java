package au.com.pwc.addressbook;

import static au.com.pwc.addressbook.model.Messages.ERR_ADD_REQUIRES_SWITCH;
import static au.com.pwc.addressbook.model.Messages.ERR_ADD_WITH_F_MUST_HAVE_B;
import static au.com.pwc.addressbook.model.Messages.ERR_BAD_B_SWITCH_FORMAT;
import static au.com.pwc.addressbook.model.Messages.ERR_BAD_F_SWITCH_FORMAT;
import static au.com.pwc.addressbook.model.Messages.ERR_SEE_REQUIRES_SWITCH;
import static au.com.pwc.addressbook.model.Messages.ERR_SEE_WITH_F_MUST_HAVE_B;
import static au.com.pwc.addressbook.model.Messages.ERR_UNSUPPORTED_COMMAND;

public enum Command {
    SEE("see"),

    ADD("add"),

    LIST("list"),

    EXIT("exit"),

    GEN("gen");

    Command(String name) {
        this.name = name;
    }

    public static Command read(String cmd) {
        if (cmd.contains("-")) {
            return Command.valueOf(cmd.substring(0, cmd.indexOf('-') - 1).toUpperCase());
        } else {
            return Command.valueOf(cmd.toUpperCase());
        }

    }

    private String name;

    public String getName() {
        return name;
    }

    public static String[] subjects(String cmd) {
        String[] subjects = null;

        if (cmd.contains("-f") && cmd.contains("-b")) {
            subjects = new String[]{cmd.substring(cmd.indexOf("-f") + 3, cmd.indexOf("-b") - 1), bSwitchSubject(cmd)};
        } else if (cmd.contains("-u")) {
            subjects = new String[]{uSwitchSubject()};
        } else if (cmd.contains("-f")) {
            subjects = new String[]{fSwitchSubject(cmd)};
        } else if (cmd.contains("-b")) {
            subjects = new String[]{bSwitchSubject(cmd)};
        } else {
            subjects = new String[]{};
        }
        return subjects;
    }

    public static String validate(String cmd) {

        if (!cmd.startsWith("see") && !cmd.startsWith("list") && !cmd.startsWith("add") && !cmd.startsWith("exit") && !cmd.startsWith("gen")) {
            return ERR_UNSUPPORTED_COMMAND;
        }

        if (cmd.contains("see") && !cmd.contains("see -f") && !cmd.contains("see -b")) {
            return ERR_SEE_REQUIRES_SWITCH;
        }

        if (cmd.contains("see") && cmd.contains("see -f") && !cmd.contains(" -b")) {
            return ERR_SEE_WITH_F_MUST_HAVE_B;
        }

        if (cmd.contains("add") && !cmd.contains("add -f") && !cmd.contains("add -b")) {
            return ERR_ADD_REQUIRES_SWITCH;
        }

        if (cmd.contains("add") && cmd.contains("add -f") && !cmd.contains(" -b")) {
            return ERR_ADD_WITH_F_MUST_HAVE_B;
        }

        if (cmd.contains("-f") && !cmd.contains(" -f ")) {
            return ERR_BAD_F_SWITCH_FORMAT;
        }

        if (cmd.contains("-b") && !cmd.contains(" -b ")) {
            return ERR_BAD_B_SWITCH_FORMAT;
        }

        return null;
    }

    private static String bSwitchSubject(String cmd) {
        return cmd.substring(cmd.indexOf("-b") + 3);
    }

    private static String fSwitchSubject(String cmd) {
        return cmd.substring(cmd.indexOf("-f") + 3);
    }

    private static String uSwitchSubject() {
        return "";
    }

}
