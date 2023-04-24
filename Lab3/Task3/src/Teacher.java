import java.util.ArrayList;

public class Teacher {
    private final Journal journal;
    private final ArrayList<Group> groups;


    public Teacher(Journal journal, ArrayList<Group> groups) {
        this.journal = journal;
        this.groups = groups;
    }

    public void publishingGrades()  {
        PublishingGradesThread thread = new PublishingGradesThread(journal, groups);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException ignore){}
    }
}
