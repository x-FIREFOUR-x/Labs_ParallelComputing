import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class Journal {
    private ConcurrentHashMap<Student, ArrayList<Integer>> studentGrades;
    private ArrayList<Group> groups;

    private final ReentrantLock locker = new ReentrantLock();

    public Journal(){
        studentGrades = new ConcurrentHashMap<>();
        groups = new ArrayList<>();
    }

    public void addGroup(Group group){
        locker.lock();
        try {
            groups.add(group);
            for(int i = 0; i < group.getCountStudent(); i++){
                Student student = group.getStudent(i);
                if(!studentGrades.containsKey(student)){
                    studentGrades.put(student, new ArrayList<>());
                }
            }
        }finally {
            locker.unlock();
        }
    }

    public void addGrade(Student student, int grade)
    {
        studentGrades.get(student).add(grade);
    }

    public List<Integer> getGrades(Student student){
        return studentGrades.get(student);
    }
}
