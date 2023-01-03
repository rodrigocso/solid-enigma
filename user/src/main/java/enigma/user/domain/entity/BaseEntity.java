package enigma.user.domain.entity;

public abstract class BaseEntity {
    protected final String id;
    protected int version = 1;

    protected BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }

    public void incrementVersion() {
        version++;
    }
}
