package ar.edu.unlam.tallerweb1.criteria;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.criterion.Restrictions;

public class CriteriaTest extends SpringTest {

    public void testLoco() {
        getSession().createCriteria(Usuario.class).add(
                Restrictions.eq("email", "pepe")
        );
    }
}
