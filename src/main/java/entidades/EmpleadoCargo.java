/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MarzoNegro
 */
@Entity
@Table(name = "empleado_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmpleadoCargo.findAll", query = "SELECT e FROM EmpleadoCargo e")
    , @NamedQuery(name = "EmpleadoCargo.findByIdEmpleadoCargo", query = "SELECT e FROM EmpleadoCargo e WHERE e.idEmpleadoCargo = :idEmpleadoCargo")})
public class EmpleadoCargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado_cargo")
    private Integer idEmpleadoCargo;
    @JoinColumn(name = "fk_cargo", referencedColumnName = "id_cargo")
    @ManyToOne
    private Cargo fkCargo;
    @JoinColumn(name = "fk_matricula", referencedColumnName = "matricula")
    @ManyToOne
    private Empleado fkMatricula;
    @OneToMany(mappedBy = "fkEmpleadoCargo")
    private Collection<HorarioCargo> horarioCargoCollection;

    public EmpleadoCargo() {
    }

    public EmpleadoCargo(Integer idEmpleadoCargo) {
        this.idEmpleadoCargo = idEmpleadoCargo;
    }

    public Integer getIdEmpleadoCargo() {
        return idEmpleadoCargo;
    }

    public void setIdEmpleadoCargo(Integer idEmpleadoCargo) {
        this.idEmpleadoCargo = idEmpleadoCargo;
    }

    public Cargo getFkCargo() {
        return fkCargo;
    }

    public void setFkCargo(Cargo fkCargo) {
        this.fkCargo = fkCargo;
    }

    public Empleado getFkMatricula() {
        return fkMatricula;
    }

    public void setFkMatricula(Empleado fkMatricula) {
        this.fkMatricula = fkMatricula;
    }

    @XmlTransient
    public Collection<HorarioCargo> getHorarioCargoCollection() {
        return horarioCargoCollection;
    }

    public void setHorarioCargoCollection(Collection<HorarioCargo> horarioCargoCollection) {
        this.horarioCargoCollection = horarioCargoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleadoCargo != null ? idEmpleadoCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoCargo)) {
            return false;
        }
        EmpleadoCargo other = (EmpleadoCargo) object;
        if ((this.idEmpleadoCargo == null && other.idEmpleadoCargo != null) || (this.idEmpleadoCargo != null && !this.idEmpleadoCargo.equals(other.idEmpleadoCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.EmpleadoCargo[ idEmpleadoCargo=" + idEmpleadoCargo + " ]";
    }
    
}
