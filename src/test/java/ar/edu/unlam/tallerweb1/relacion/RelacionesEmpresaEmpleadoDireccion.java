package ar.edu.unlam.tallerweb1.relacion;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Direccion;
import ar.edu.unlam.tallerweb1.modelo.Empleado;
import ar.edu.unlam.tallerweb1.modelo.Empresa;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RelacionesEmpresaEmpleadoDireccion extends SpringTest {

    private Direccion direccionBase;
    private Empresa empresaBase;
    private Empleado empleadoBase;

    @Before
    public void persistData() {
        setDireccionBase(direccionFactory("Calle falsa", 123));
        getSession().save(getDireccionBase());
        setEmpresaBase(empresaFactory("Pepe", getSession().get(Direccion.class, getDireccionBase().getId())));
        getSession().save(getEmpresaBase());
        setEmpleadoBase(empleadoFactory("Juan", getEmpresaBase()));
        getSession().save(getEmpleadoBase());
    }


    @Test
    @Transactional
    @Rollback()
    public void laEmpresaLeeDesdeDirecciones() {
        Empresa empresaDos = getSession().get(Empresa.class, getEmpresaBase().getId());
        Direccion direccionDos = getSession().get(Direccion.class, getDireccionBase().getId());
        assertThat(empresaDos.getDireccion().getId()).isEqualTo(direccionDos.getId());
    }

    @Test
    @Transactional
    @Rollback
    public void detectarEmpresaEmpleado() {
        Empleado empleadoLeido = getSession().get(Empleado.class, getEmpleadoBase().getId());
        Empresa empresaLeida = getSession().get(Empresa.class, getEmpresaBase().getId());
        assertThat(empleadoLeido.getEmpresa().getId()).isEqualTo(empresaLeida.getId());
    }

    private Direccion direccionFactory(String calle, Integer numero) {
        Direccion direccion = new Direccion();
        direccion.setCalle(calle);
        direccion.setNumero(numero);
        return direccion;
    }

    private Empresa empresaFactory(String nombre, Direccion direccion) {
        Empresa empresa = new Empresa();
        empresa.setNombre(nombre);
        empresa.setDireccion(direccion);
        return empresa;
    }

    private Empleado empleadoFactory(String nombre, Empresa empresa) {
        Empleado empleado = new Empleado();
        empleado.setNombre(nombre);
        empleado.setEmpresa(empresa);
        return empleado;
    }

    public Direccion getDireccionBase() {
        return direccionBase;
    }

    public void setDireccionBase(Direccion direccionBase) {
        this.direccionBase = direccionBase;
    }

    public Empresa getEmpresaBase() {
        return empresaBase;
    }

    public void setEmpresaBase(Empresa empresaBase) {
        this.empresaBase = empresaBase;
    }

    public Empleado getEmpleadoBase() {
        return empleadoBase;
    }

    public void setEmpleadoBase(Empleado empleadoBase) {
        this.empleadoBase = empleadoBase;
    }
}
