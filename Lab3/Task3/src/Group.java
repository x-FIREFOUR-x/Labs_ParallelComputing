import java.util.ArrayList;

public class Group {
    private final ArrayList<Student> students;

    public Group(){
        students  = new ArrayList<>();
    }

    public Group(int countStudent)
    {
        students  = new ArrayList<>();
        for (int i = 0; i < countStudent; i++) {
            students.add(new Student());
        }
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public  Student getStudent(int index) {
        return students.get(index);
    }

    public int getCountStudent() {
        return students.size();
    }
}
