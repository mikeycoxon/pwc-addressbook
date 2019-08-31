package au.com.pwc.addressbook;

import org.junit.Test;

import static au.com.pwc.addressbook.model.Messages.ERR_ADD_REQUIRES_SWITCH;
import static au.com.pwc.addressbook.model.Messages.ERR_ADD_WITH_F_MUST_HAVE_B;
import static au.com.pwc.addressbook.model.Messages.ERR_BAD_B_SWITCH_FORMAT;
import static au.com.pwc.addressbook.model.Messages.ERR_BAD_F_SWITCH_FORMAT;
import static au.com.pwc.addressbook.model.Messages.ERR_SEE_REQUIRES_SWITCH;
import static au.com.pwc.addressbook.model.Messages.ERR_SEE_WITH_F_MUST_HAVE_B;
import static au.com.pwc.addressbook.model.Messages.ERR_UNSUPPORTED_COMMAND;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class CommandTest {

    @Test
    public void validates_ok_when_list_is_specified_alone() {
        String errs = Command.validate("list");

        assertThat(errs, is(nullValue()));
    }

    @Test
    public void validates_ok_when_exit_is_specified_alone() {
        String errs = Command.validate("exit");

        assertThat(errs, is(nullValue()));
    }

    @Test
    public void validates_ok_when_list_is_specified_with_b() {
        String errs = Command.validate("list -b MyBook");

        assertThat(errs, is(nullValue()));
    }

    @Test
    public void validates_ok_when_see_is_specified_with_b() {
        String errs = Command.validate("see -b MyBook");

        assertThat(errs, is(nullValue()));
    }

    @Test
    public void validates_ok_when_see_is_specified_with_f_and_b() {
        String errs = Command.validate("see -f bob -b MyBook");

        assertThat(errs, is(nullValue()));
    }

    @Test
    public void validates_ok_when_add_is_specified_with_b() {
        String errs = Command.validate("add -b MyBook");

        assertThat(errs, is(nullValue()));
    }

    @Test
    public void validates_ok_when_add_is_specified_with_f_and_b() {
        String errs = Command.validate("add -f bob#0400100100 -b MyBook");

        assertThat(errs, is(nullValue()));
    }

    @Test
    public void validate_err_when_unsupported_command_passed_in() {
        String errs = Command.validate("foo");

        assertThat(errs, is(notNullValue()));
        assertThat(errs, is(ERR_UNSUPPORTED_COMMAND));
    }

    @Test
    public void validate_err_when_see_has_no_switch() {
        String errs = Command.validate("see");

        assertThat(errs, is(notNullValue()));
        assertThat(errs, is(ERR_SEE_REQUIRES_SWITCH));
    }

    @Test
    public void validate_err_when_see_has_f_but_no_b() {
        String errs = Command.validate("see -f ");

        assertThat(errs, is(notNullValue()));
        assertThat(errs, is(ERR_SEE_WITH_F_MUST_HAVE_B));
    }

    @Test
    public void validate_err_when_add_has_no_switch() {
        String errs = Command.validate("add");

        assertThat(errs, is(notNullValue()));
        assertThat(errs, is(ERR_ADD_REQUIRES_SWITCH));
    }

    @Test
    public void validate_err_when_add_has_f_but_no_b() {
        String errs = Command.validate("add -f ");

        assertThat(errs, is(notNullValue()));
        assertThat(errs, is(ERR_ADD_WITH_F_MUST_HAVE_B));
    }

    @Test
    public void validate_err_when_add_has_bad_f() {
        String errs = Command.validate("list -f");

        assertThat(errs, is(notNullValue()));
        assertThat(errs, is(ERR_BAD_F_SWITCH_FORMAT));
    }

    @Test
    public void validate_err_when_add_has_bad_b() {
        String errs = Command.validate("list -b");

        assertThat(errs, is(notNullValue()));
        assertThat(errs, is(ERR_BAD_B_SWITCH_FORMAT));
    }

    @Test
    public void two_valid_subjects_returned_when_both_switches_correctly_populated() {
        String[] subjects = Command.subjects("see -f bob -b AddressBook 1");

        assertThat(subjects, is(notNullValue()));
        assertThat(subjects.length, is(2));
        assertThat(subjects[0], is("bob"));
        assertThat(subjects[1], is("AddressBook 1"));
    }

    @Test
    public void one_valid_subject_returned_when_f_switch_correctly_populated() {
        String[] subjects = Command.subjects("see -f bob");

        assertThat(subjects, is(notNullValue()));
        assertThat(subjects.length, is(1));
        assertThat(subjects[0], is("bob"));
    }

    @Test
    public void one_valid_subject_returned_when_b_switch_correctly_populated() {
        String[] subjects = Command.subjects("see -b AddressBook 1");

        assertThat(subjects, is(notNullValue()));
        assertThat(subjects.length, is(1));
        assertThat(subjects[0], is("AddressBook 1"));
    }

}
