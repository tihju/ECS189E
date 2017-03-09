import api.IStudent;
import api.core.impl.Student;
import api.IAdmin;
import api.core.impl.Admin;
import api.IInstructor;
import api.core.impl.Instructor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by tingtingzhu on 3/4/17.
 */
public class TestStudent {
    private IStudent stu;
    private IAdmin admin;
    private IInstructor instru;

    @Before
    public void setup() {
        this.stu = new Student();
        this.admin = new Admin();
        this.instru = new Instructor();
    }

    /* basic test for register class*/
    @Test
    public void testRegisterForClass(){
        this.admin.createClass("aca", 2018, "ACA", 5);
        this.stu.registerForClass("Babette","aca", 2018);
        assertTrue(this.stu.isRegisteredFor("Babette","aca",2018));

        //if try to register class that hasn't provide
        this.stu.registerForClass("Bailey", "acb", 2018);
        assertFalse(this.stu.isRegisteredFor("Bailey","acb", 2018));

    }

    @Test
    public void testRegisterForClass1(){
        //make the class  met the capacity and another person try to add to the class
        this.admin.createClass("acc",2018,"ACB",3);
        this.stu.registerForClass("Bambi","acc", 2018);
        this.stu.registerForClass("Bao","acc", 2018);
        this.stu.registerForClass("Barb","acc", 2018);
        this.stu.registerForClass("Bea","acc", 2018);
        assertFalse(this.stu.isRegisteredFor("Bea","acc",2018));
    }

    /* test try to register class that is not exit */
    @Test
    public void testRegisterForClass2(){
        this.stu.registerForClass("saaa","acd",2018);
        assertFalse(this.stu.isRegisteredFor("saaa","acd",2018));
    }


    /* testing drop class method */
    @Test
    public void testDropClass(){
        this.admin.createClass("acd",2018,"ACC",4);
        this.stu.registerForClass("Beata","acd",2018);
        this.stu.dropClass("Beata", "acd", 2018);
        assertFalse(this.stu.isRegisteredFor("Beate","acd", 2018));
    }

    /*try to drop class that is not register for,still do not know how to test this*/


    /* test regular submithimework*/
    @Test
    public void testSubmitHomework(){
        this.admin.createClass("ace",2018, "ACD", 23);
        this.stu.registerForClass("Saab", "ace", 2018);
        this.instru.addHomework("ACD","ace",2018,"HW1","hw1 for class ace");
        this.stu.submitHomework("Saab","HW1","hw1 answer","ace",2018);
        assertTrue(this.stu.hasSubmitted("Saab","HW1","ace",2018));

    }

    /* non registered student try to submit homework*/
    @Test
    public void testSubmitHomework1(){
        this.admin.createClass("acf",2018, "ACE", 23);
        this.instru.addHomework("ACE","acf",2018,"HW5","hw5 for class acf");
        this.stu.submitHomework("Saac","HW5","hw5 answer","acf",2018);
        assertFalse(this.stu.hasSubmitted("Saac","HW5","acf",2018));

    }

    /*student try to submit a hw that is not exit */
    @Test
    public void testSubmitHomework2(){
        this.admin.createClass("acg",2018, "ACG", 23);
        this.stu.registerForClass("Saag", "acg", 2018);
        this.stu.submitHomework("Saag","HW6","hw6 answer","acg",2018);
        assertFalse(this.stu.hasSubmitted("Saag","HW6","acg",2018));
    }
}
