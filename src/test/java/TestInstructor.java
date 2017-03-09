import api.IInstructor;
import api.core.impl.Instructor;
import api.IAdmin;
import api.core.impl.Admin;
import api.IStudent;
import api.core.impl.Student;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.junit.Assert.*;

/**
 * Created by tingtingzhu on 3/2/17.
 */
public class TestInstructor {
    private IInstructor instru;
    private IAdmin adm;
    private IStudent stu;


    @Before
    public void setup() {
        this.adm = new Admin();
        this.instru = new Instructor();
        this.stu = new Student();
    }

    /* test basic add homework method. */
    @Test
    public void testAddHomework(){
        this.adm.createClass("aba", 2018,"ABA", 20);
        this.instru.addHomework("ABA", "aba",2018,"HW1", "practice hw1");
        assertTrue(this.instru.homeworkExists("aba",2018,"HW1"));

    }

    /* test when add homework to an unassigned class */
    @Test
    public void testAddHomework1(){
        this.adm.createClass("abb", 2018, "ABB", 20);
       this.instru.addHomework("ABC", "abb", 2018, "HW2", "Hw2 for abc");
       assertFalse(this.instru.homeworkExists("abb",2018,"HW2"));
    }

    /* add hw to a class that is not exist*/
    @Test
    public void testAddHomework2(){
        this.adm.createClass("ada", 2018, "Iaaa", 34);
        this.instru.addHomework("Iaaa","adb",2018,"HW9","hw9 for class adb");
        assertFalse(this.instru.homeworkExists("adb",2018,"HW9"));
    }

    /* test basic function of assginGrade*/
    @Test
    public void testAssignGrade() {
        this.adm.createClass("abd", 2018, "ABD", 23);
        this.instru.addHomework("ABD", "abd", 2018, "HW3", "hw3 for class abd");

        this.stu.submitHomework("Aaron", "HW3", "submit hw3 for class abd","abd", 2018);

        this.instru.assignGrade("ABC", "abd", 2018, "HW3", "Aaron", 70);
        assertTrue(this.instru.getGrade("abd", 2018, "HW3", "Aaron") == 70);
    }

    /* assign grade to a student that haven't submitted the homework in the class*/
    @Test
    public void testAssignGrade1(){
        this.adm.createClass("abg", 2018,"ABG", 6);
        this.stu.registerForClass("Abbey","abg",2018);
        this.instru.addHomework("ABG","abg",2018,"HW3","hw3 for class abg");
        this.instru.assignGrade("ABG","abg",2018,"HW3", "Abbey", 80);
        assertNull(this.instru.getGrade("abg", 2018, "HW3", "Abbey"));
    }

    /* assign grade to an unassigned homework*/
    @Test
    public void testAssignGrade2(){
        this.adm.createClass("abe",2018, "ABE", 35);
        this.stu.registerForClass("Bella","abe",2018);
        this.instru.assignGrade("ABE","abe",2018,"HW2","Bella", 80);
        assertNull(this.instru.getGrade("abe",2018,"HW2","Bella"));
    }

    /* try to assign grade to an unassigned class */
    @Test
    public void testAssignGrade3(){
        this.adm.createClass("abf",2018,"ABF",20);
        this.stu.registerForClass("Belle","abf",2018);
        this.instru.addHomework("ABF","abf",2018,"hw4","hw4 4 abf");
        this.stu.submitHomework("Belle","hw4","sub hw for class abf","abf",2018);
        this.instru.assignGrade("ABG","abf",2018,"hw4","Belle", 50);
        assertNull(this.instru.getGrade("abf",2018,"hw4","Belle"));
    }
}
