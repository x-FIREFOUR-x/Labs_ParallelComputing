import java.util.UUID;

public class Student {
    private final UUID id = UUID.randomUUID();

    public UUID getId()
    {
        return id;
    }
}
