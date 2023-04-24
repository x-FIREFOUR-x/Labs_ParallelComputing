import java.util.ArrayList;

public class Group {
    private final ArrayList<Student> students;
    private final String name;

    public Group(String name){
        this.name = name;
        students  = new ArrayList<>();
    }

    public Group(String name, int countStudent)
    {
        this.name = name;
        students  = new ArrayList<>();
        for (int i = 0; i < countStudent; i++) {
            students.add(new Student());
        }
    }

    public String getName(){
        return name;
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
