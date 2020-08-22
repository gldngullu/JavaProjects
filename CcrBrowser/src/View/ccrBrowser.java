package View;

import entities.Course;
import entities.Enrollment;
import entities.EnrollmentPK;
import entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ccrBrowser {

    private static Set<Course> allCourses = new HashSet<>();
    private static Set<Student> allStudents = new HashSet<>();
    private static ArrayList<Enrollment> allEnrollments = new ArrayList<>();
    private static ArrayList<Enrollment> courseEnrollmentList;
    private static ArrayList<Enrollment> studentEnrollmentList;


    public static void main(String[] args) {
        findFiles();
        writeToDatabase();
    }

    private static void findFiles() {
        File folder = new File("resources\\ccrData");
        for (final File fileEntry : folder.listFiles()) {
            readFiles(fileEntry.getPath());
        }
    }

    private static void readFiles(String fileName) {
        try {
            File file = new File(fileName);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String tempString;

            //Student object information
            Student tempStudent = new Student();
            studentEnrollmentList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                tempString = bufferedReader.readLine();
                String[] studentContent = tempString.split(" ");
                switch (studentContent[0]) {
                    case "majorleavingdate":
                        StringBuilder dateBuilder = new StringBuilder();
                        dateBuilder.append(studentContent[3] + "-");
                        dateBuilder.append(studentContent[2] + "-");
                        dateBuilder.append(studentContent[1]);
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = formatter.parse(dateBuilder.toString());
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        tempStudent.setGraduation_date(sqlDate);
                        break;
                    case "studentnumber":
                        tempStudent.setStudentNo(studentContent[1]);
                        break;
                    case "minor":
                        tempStudent.setMinor(Boolean.valueOf(studentContent[1]));
                }
            }

            while ((tempString = bufferedReader.readLine()) != null) {
                String[] lineContent = tempString.split(" ");
                int currentSemester;
                if (lineContent[0].equals("semester")) {
                    currentSemester = Integer.valueOf(lineContent[1]);
                    while (!(tempString = bufferedReader.readLine()).contains("semester")) {
                        Course tempCourse = new Course();
                        courseEnrollmentList = new ArrayList<>();
                        String[] courseContent = tempString.split(" ");
                        tempCourse.setSlotCode(courseContent[0]);
                        tempCourse.setSlotName(courseContent[1]);
                        tempCourse.setCredit(Integer.valueOf(courseContent[2]));
                        tempString = bufferedReader.readLine();
                        int numberOfRepeats = Integer.valueOf(tempString.split(" ")[1]);
                        for (int j = 0; j < numberOfRepeats; j++) {
                            Enrollment tempEnrollment = new Enrollment();
                            EnrollmentPK tempEnrollmentPK = new EnrollmentPK();
                            tempString = bufferedReader.readLine();
                            String[] enrollmentContent = tempString.split(" ");

                            tempEnrollmentPK.setCourseCode(enrollmentContent[3]);
                            tempCourse.setCode(enrollmentContent[3]);
                            tempEnrollmentPK.setSemester(enrollmentContent[1]);
                            tempEnrollmentPK.setStudentstudentNo(tempStudent.getStudentNo());
                            tempEnrollmentPK.setYear(Integer.valueOf(enrollmentContent[0]));

                            tempEnrollment.setSemesterOfStudent(currentSemester);
                            tempEnrollment.setCourse(tempCourse);
                            tempEnrollment.setGrade(enrollmentContent[2]);
                            tempEnrollment.setStudent(tempStudent);
                            tempEnrollment.setEnrollmentPK(tempEnrollmentPK);

                            allCourses.add(tempCourse);
                            allEnrollments.add(tempEnrollment);
                            courseEnrollmentList.add(tempEnrollment);
                            studentEnrollmentList.add(tempEnrollment);
                        }
                        tempCourse.setEnrollmentList(courseEnrollmentList);
                    }
                }
            }
            tempStudent.setEnrollmentList(studentEnrollmentList);
            allStudents.add(tempStudent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToDatabase() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NewPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();

        for (Course course : allCourses) {
            em.persist(course);
        }
        for (Student student : allStudents) {
            em.persist(student);
        }
        for (Enrollment enrollment : allEnrollments) {
            em.persist(enrollment);
        }
        etx.commit();
        em.close();
    }
}
