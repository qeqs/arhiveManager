package entities;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "entry", schema = "public", catalog = "Arhives")
public class EntryEntity implements entities.Entity{
    private int id;
    private String name;
    private ArhiveEntity arhive;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "arhive_id", nullable = false)
    public ArhiveEntity getArhive() {
        return arhive;
    }

    public void setArhive(ArhiveEntity arhive) {
        this.arhive = arhive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryEntity that = (EntryEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
