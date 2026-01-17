 import java.util.ArrayList;
 class Student {
    private String name;
    private int rollno;    
    private double attendance;

    public Student(String name, int rollno, double attendance)
    {
        this.name = name;
        this.rollno = rollno;
        this.attendance = attendance;   
    }
    public String getname(){
        return name;
    }
    public int getrollno(){
        return rollno;
    }
    public double getattendance(){
        return attendance;
    }
    public void displayinfo(){
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollno);
        System.out.println("Attendance: " + attendance + "%");
    }
}

 class Department{
    private String deptname;
    private ArrayList<Student> students;
    public Department(String deptname){
        this.deptname = deptname;
        students = new ArrayList<>();
    }
    public void getstudent(Student s){
    students.add(s);
    }
    public void displayinfo(){
        System.out.println("Department: " + deptname);
        for(Student s : students){
            s.displayinfo();
            System.out.println("-----");
        }
    }
    public double calculateavgattendance(){
        double total =0;
        for(Student s : students){
            total += s.getattendance();
        }
         return students.size()>0 ? total / students.size() : 0;
    }
}

 class College{
    private String collegename;
    private ArrayList<Department> Departments;
    public College(String collegename){
        this.collegename = collegename;
        Departments = new ArrayList<>();
    }
    public String getcollegename(){
        return collegename;
    }
    public void adddepartment(Department d){
        Departments.add(d);
    }
    public void displayinfo(){
        System.out.println("College: " + collegename);
        for(Department d : Departments){
            d.displayinfo();
            System.out.println("Average Attendance: " + d.calculateavgattendance() + "%");
            System.out.println("=====");
        }
    }
}

public class student_manager{
    public static void main(String[] args){
        College c = new College("GGITS");
        Student s1 = new Student("Alice" , 101 , 75.77);
        Student s2 = new Student("Bob" , 102 , 82.50);
        Student s3 = new Student("Charlie" , 103 , 90.00);
        Student s4 = new Student("Epson" , 103 , 90.00);
        Student s5 = new Student("Lorem" , 103 , 90.00);
        
        Department d1 = new Department("Computer Science");
        Department d2 = new Department("Information Technology");
        Department d3 = new Department("Artificial Intelligence");
        Department d4 = new Department("Internet of Thikings");
        
        d1.getstudent(s1);
        d1.getstudent(s2);
        d2.getstudent(s3);
        d3.getstudent(s4);
        d4.getstudent(s5);

        c.adddepartment(d1);
        c.adddepartment(d2);    
        c.adddepartment(d3);
        c.adddepartment(d4);
        c.displayinfo();
    }
}