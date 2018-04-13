package ar.edu.unlam.tallerweb1.persistencia;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
// import org.hibernate.Session;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

public class PersistenciaUsuarioTest extends SpringTest {

    // EJEMPLO DE TEST DE PERSISTENCIA
    @Test
    @Transactional
    @Rollback()
    public void alGuardarUnUsuarioDeberiaEstarEnLaBase() {
        Usuario usuarioPrueba = this.usuarioFactory("guardar@usuario.com", "guardar", "Guardar");
        super.getSession().save(usuarioPrueba); // Guarda el ID aunque no esté seteado
        Usuario usuarioBuscado = super.getSession().get(Usuario.class, usuarioPrueba.getId());
        assertThat(usuarioBuscado).isNotNull();
    }

    @Test
    @Transactional
    @Rollback()
    public void borrarUsuario() {
        Usuario usuarioPrueba = this.usuarioFactory("delete@usuario.com", "deletepass", "borrar");
        getSession().save(usuarioPrueba);
        Usuario usuarioBuscadoUno = super.getSession().get(Usuario.class, usuarioPrueba.getId());
        assertThat(usuarioBuscadoUno).isNotNull();
        getSession().delete("Usuario", usuarioPrueba);
        Usuario usuarioBuscadoDos = super.getSession().get(Usuario.class, usuarioPrueba.getId());
        assertThat(usuarioBuscadoDos).isNull();
    }

    @Test
    @Transactional
    @Rollback()
    public void updateUsuario() {
        String nuevoMail = "mailnuevo@usuario.com";
        String viejoMail = "mailviejo@usuario.com";
        Usuario usuarioPrueba = this.usuarioFactory(viejoMail, "deletepass", "borrar");
        super.getSession().save(usuarioPrueba);
        System.out.println("Mail viejo");
        System.out.println(usuarioPrueba.getEmail());
        usuarioPrueba.setEmail(nuevoMail);
        super.getSession().update(usuarioPrueba);

        System.out.println("Mail en base");
        System.out.println(super.getSession().get(Usuario.class, usuarioPrueba.getId()).getEmail());
        Usuario usuarioActualizado = super.getSession().get(Usuario.class, usuarioPrueba.getId());
        assertThat(usuarioActualizado.getEmail()).isEqualTo(usuarioPrueba.getEmail());
    }

    private Usuario usuarioFactory(String mail, String pass, String rol) {
        Usuario usuarioPrueba = new Usuario();
        usuarioPrueba.setEmail(mail);
        usuarioPrueba.setPassword(pass);
        usuarioPrueba.setRol(rol);
        return usuarioPrueba;
    }

}
