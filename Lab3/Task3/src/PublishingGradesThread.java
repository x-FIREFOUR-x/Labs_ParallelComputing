import java.util.ArrayList;
import java.util.Random;

public class PublishingGradesThread extends Thread{
    private final Journal journal;
    private final ArrayList<Group> groups;

    private final Random random = new Random();


    public PublishingGradesThread(Journal journal, ArrayList<Group> groups) {
        this.journal = journal;
        this.groups = groups;
    }

    @Override
    public void run(){
        for(Group group: groups){
            for (int i = 0; i < group.getCountStudent(); i++) {
                int grade = Math.abs(random.nextInt()) % 101;
                journal.addGrade(group.getStudent(i), grade);
            }
        }
    }
}
