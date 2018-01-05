package uk.ac.kent.models.records.recommendations;

import java.text.MessageFormat;
import java.time.LocalDate;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Base recommendation is the superclass for all types
 * of recommendations kept in the system.
 * <p>
 * It equips all subclasses with basic fields and
 * getters / setters. Because it is abstract, it cannot
 * be directly instantiated.
 *
 * @author norbert
 */

@Table(name = "recommendations")
@SuppressWarnings({"AlibabaAbstractClassShouldStartWithAbstractNaming", "DesignForExtension", "PublicMethodNotExposedInInterface"})
@Entity

@Inheritance(strategy = InheritanceType.JOINED)
@Access(AccessType.FIELD)
public abstract class BaseRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Transient
    private static int nextId;

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    protected BaseRecommendation() {
        id = nextId;
        nextId++;
    }

    @Column(name = "modified_date")
    @UpdateTimestamp
    private LocalDate modifiedDate;

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @SuppressWarnings("DesignForExtension")
    @Override
    public String toString() {
        return MessageFormat.format("{0}<id={1}>", getClass().getName(), id);
    }
}
