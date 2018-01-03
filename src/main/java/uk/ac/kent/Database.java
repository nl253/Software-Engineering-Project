package uk.ac.kent;

import java.text.MessageFormat;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Transient;
import uk.ac.kent.models.people.Director;
import uk.ac.kent.models.people.Manager;

/**
 * For details about the API see <a href="http://docs.jboss.org/hibernate/orm/5.2/quickstart/html_single/#hibernate-gsg-tutorial-basic-mapping">
 * Hibernate Quick Start </a>. Each {@link uk.ac.kent.controllers.BaseController}
 * stores a reference to this {@link Database} and executes {@link Query} on it.
 * The result of those is transferred to the GUI.
 *
 * @author norbert
 */

@SuppressWarnings({"WeakerAccess", "AlibabaCommentsMustBeJavadocFormat", "PublicMethodNotExposedInInterface", "FieldCanBeLocal", "ClassHasNoToStringMethod", "NonBooleanMethodNameMayNotStartWithQuestion"})
public final class Database {

    /** Logger for the class */
    @Transient
    private static final Logger log = Logger.getAnonymousLogger();

    private final EntityManagerFactory sessionFactory;
    private EntityManager session;

    public Database() {
        // the name corresponds to a persistance-unit name in persistance.xml
        sessionFactory = Persistence
                .createEntityManagerFactory("dragon.kent.ac.uk");
    }

    /**
     * To be run BEFORE interacting with the database. Used internally.
     */

    private void beginTransaction() {
        session = sessionFactory.createEntityManager();
        session.getTransaction().begin();
    }

    /**
     * To be run AFTER interacting with the database. Used internally.
     */

    private void finishTransaction() {
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Pass SQL (optionally pass parameters), return results as {@link List}.
     *
     * @param queryString SQL
     * @return List of results
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter", "OverloadedVarargsMethod"})
    public List query(final String queryString, final Object... params) {

        beginTransaction();

        final Query query = session.createNativeQuery(queryString);

        // bind parameters
        for (int i = 0; i < params.length; i++)
            query.setParameter(i, params[i]);

        final List result = query.getResultList();

        finishTransaction();

        return result;
    }

    /**
     * @param funct function (a query) to execute
     * @return results of the query
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public List query(final Supplier<List> funct) {
        beginTransaction();
        final List results = funct.get();
        finishTransaction();
        return results;
    }

    /**
     * @param funct a {@link Runnable} to run with an open session
     */

    @SuppressWarnings({"rawtypes", "LawOfDemeter"})
    public void query(final Runnable funct) {
        beginTransaction();
        funct.run();
        finishTransaction();
    }

    /**
     * @param entity an entity to remove
     */

    public void remove(final Object entity) {
        beginTransaction();
        session.remove(entity);
        finishTransaction();
    }

    /**
     * Save the entity in this {@link Database}.
     *
     * @param entity Object to be saved
     */

    public void save(final Object entity) {
        beginTransaction();
        session.persist(entity);
        finishTransaction();
    }

    /**
     * Fill the database.
     * Creating a {@link Manager} will create a bunch of employees and assign them to this {@link Manager}.
     * Creating an {@link uk.ac.kent.models.people.Employee} causes all relevant records to be created (although the data is fake).
     * This includes {@link uk.ac.kent.models.records.PersonalDetailsRecord} and {@link uk.ac.kent.models.records.EmploymentDetailsRecord}.
     */

    public void populate() {
        // Make Directors
        IntStream.range(0, 4).forEach(x -> save(Director.fake()));
        // Make Managers
        IntStream.range(0, 25).forEach(x -> save(Manager.fake()));
    }

    /**
     * String representation of this Database. Gives info about all properties and tells if there's a session running.
     *
     * @return
     */

    @Override
    public String toString() {
        return MessageFormat
                .format("Database<open={0}, properties={1}>", (session != null) && session
                        .isOpen(), sessionFactory.getProperties().values()
                                .stream().map(Object::toString)
                                .collect(Collectors.joining(", ")));
    }
}
