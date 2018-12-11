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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MarzoNegro
 */
@Entity
@Table(name = "cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c")
    , @NamedQuery(name = "Cargo.findByIdCargo", query = "SELECT c FROM Cargo c WHERE c.idCargo = :idCargo")
    , @NamedQuery(name = "Cargo.findByDescripcionCargo", query = "SELECT c FROM Cargo c WHERE c.descripcionCargo = :descripcionCargo")})
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cargo")
    private Integer idCargo;
    @Size(max = 30)
    @Column(name = "descripcion_cargo")
    private String descripcionCargo;
    @OneToMany(mappedBy = "fkCargo")
    private Collection<EmpleadoCargo> empleadoCargoCollection;
    @OneToMany(mappedBy = "fkCargo")
    private Collection<FuncionCargo> funcionCargoCollection;
    @JoinColumn(name = "fk_departamento", referencedColumnName = "id_departamento")
    @ManyToOne
    private Departamento fkDepartamento;

    public Cargo() {
    }

    public Cargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    @XmlTransient
    public Collection<EmpleadoCargo> getEmpleadoCargoCollection() {
        return empleadoCargoCollection;
    }

    public void setEmpleadoCargoCollection(Collection<EmpleadoCargo> empleadoCargoCollection) {
        this.empleadoCargoCollection = empleadoCargoCollection;
    }

    @XmlTransient
    public Collection<FuncionCargo> getFuncionCargoCollection() {
        return funcionCargoCollection;
    }

    public void setFuncionCargoCollection(Collection<FuncionCargo> funcionCargoCollection) {
        this.funcionCargoCollection = funcionCargoCollection;
    }

    public Departamento getFkDepartamento() {
        return fkDepartamento;
    }

    public void setFkDepartamento(Departamento fkDepartamento) {
        this.fkDepartamento = fkDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Cargo[ idCargo=" + idCargo + " ]";
    }
    
}
