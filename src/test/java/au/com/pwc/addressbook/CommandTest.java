package au.com.pwc.addressbook;

import org.junit.Test;

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
    public void two_valid_subjects_returned_when_both_switches_correctly_populated() {
        String[] subjects = Command.subjects("see -f bob -b AddressBook 1");

        assertThat(subjects, is(notNullValue()));
        assertThat(subjects.length, is(2));
        assertThat(subjects[0], is("bob"));
        assertThat(subjects[1], is("AddressBook 1"));
    }
}
