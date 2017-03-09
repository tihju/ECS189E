import api.IAdmin;
import api.core.impl.Admin;
import api.IStudent;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tingtingzhu on 3/4/17.
 */
public class TestAdmin {
    private IAdmin admin;
    private IStudent stu;

    @Before
    public void setup() {

        this.admin = new Admin();
        this.stu = new Student();
    }

    @Test
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    /* test can not make past year class*/
    @Test
    public void testMakeClass1() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }

    /* test can unique classname/year */
    @Test
    public  void testMakeClass2() {
        this.admin.createClass("aaa", 2017, "AAA", 29);
        this.admin.createClass("aaa", 2017, "AAB", 39);
        assertFalse(this.admin.getClassInstructor("aaa", 2017).equals("AAB"));
    }

    /* test no instructor can have more than two class per year */
    @Test
    public  void testMakeClass3(){
        this.admin.createClass("aab", 2018, "AAC", 30);
        this.admin.createClass("aac", 2018, "AAC", 38);
        this.admin.createClass("aad", 2018, "AAC", 37);
        assertFalse(this.admin.classExists("aad", 2018));
    }

    /* create class can not have a capacity that is less than or equal to 0 */
    @Test
    public void testMakeClass4(){
        this.admin.createClass("aae", 2019, "AAD", 0);
        assertFalse(this.admin.classExists("aae", 2019));
    }

    /* test change capacity */
    @Test
    public void testChangeCapacity(){
        this.admin.createClass("aaf", 2019, "AAE", 30);
        this.admin.changeCapacity("aaf", 2019, 20);
        assertTrue(this.admin.getClassCapacity("aaf", 2019) == 20);
    }

    /* try to change capacity to 0 */
    @Test
    public void testChangeCapacity2(){
        this.admin.createClass("aam", 2019, "AAM", 30);
        this.admin.changeCapacity("aaf", 2019, 0);
        assertTrue(this.admin.getClassCapacity("aaf", 2019) == 30);

    }

    /* test when change capacity that less than number of student enrolled */
    @Test
    public void testChangeCapacity1(){
        this.admin.createClass("aag", 2018, "AAF", 5);
        this.stu.registerForClass("Alex", "aag", 2018);
        this.stu.registerForClass("Ben", "aag", 2018);
        this.stu.registerForClass("Chri", "aag",2018);
        this.admin.changeCapacity("aag", 2018, 2);
        assertFalse(this.admin.getClassCapacity("aag", 2018) == 2);
    }
}
