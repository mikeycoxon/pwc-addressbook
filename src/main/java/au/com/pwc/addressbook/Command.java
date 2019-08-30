package au.com.pwc.addressbook;

public enum Command {
    SEE("see"),

    ADD("add"),

    LIST("list");

    Command(String name) {
        this.name = name;
    }

    public static Command read(String cmd) {
        return Command.valueOf(cmd.substring(0, cmd.indexOf('-')));
    }

    private String name;

    public String getName() {
        return name;
    }

    public static String[] subjects(String cmd) {
        String[] subjects = null;

        if (cmd.contains("-f") && cmd.contains("-b")) {
            subjects = new String[]{cmd.substring(cmd.indexOf("-f") + 3, cmd.indexOf("-b") - 1), bSwitchSubject(cmd)};
        } else if (cmd.contains("-f")) {
            subjects = new String[]{fSwitchSubject(cmd)};
        } else if (cmd.contains("-b")) {
            subjects = new String[]{bSwitchSubject(cmd)};
        }
        return subjects;
    }

    public static String validate(String cmd) {

        if (!cmd.startsWith("see") && !cmd.startsWith("list") && !cmd.startsWith("add")) {
            return "A command must start with either see, list or add";
        }

        if (cmd.contains("see") && !cmd.contains("see -f ") && !cmd.contains("see -b ")) {
            return "The 'see' command must be followed by a switch";
        }

        if (cmd.contains("see") && cmd.contains("see -f ") && !cmd.contains(" -b ")) {
            return "The 'see' command with an -f switch must have a -b switch";
        }

        if (cmd.contains("add") && !cmd.contains("add -f ") && !cmd.contains("add -b ")) {
            return "The 'add' command must be followed by a switch";
        }

        if (cmd.contains("add") && cmd.contains("add -f ") && !cmd.contains(" -b ")) {
            return "The 'add' command with an -f switch must have a -b switch";
        }

        if (cmd.contains("-f") && !cmd.contains(" -f ")) {
            return "The friend switch (-f) must be surrounded by spaces";
        }

        if (cmd.contains("-b") && !cmd.contains(" -b ")) {
            return "The book switch (-b) must be surrounded by spaces";
        }

        return null;
    }

    private static String bSwitchSubject(String cmd) {
        return cmd.substring(cmd.indexOf("-b") + 3);
    }

    private static String fSwitchSubject(String cmd) {
        return cmd.substring(cmd.indexOf("-f") + 3);
    }

}
