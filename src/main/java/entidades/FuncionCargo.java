/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author MarzoNegro
 */
@Entity
@Table(name = "funcion_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FuncionCargo.findAll", query = "SELECT f FROM FuncionCargo f")
    , @NamedQuery(name = "FuncionCargo.findByIdFuncion", query = "SELECT f FROM FuncionCargo f WHERE f.idFuncion = :idFuncion")
    , @NamedQuery(name = "FuncionCargo.findByDescripcion", query = "SELECT f FROM FuncionCargo f WHERE f.descripcion = :descripcion")
    , @NamedQuery(name = "FuncionCargo.findByNombreFuncion", query = "SELECT f FROM FuncionCargo f WHERE f.nombreFuncion = :nombreFuncion")})
public class FuncionCargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_funcion")
    private Integer idFuncion;
    @Size(max = 500)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "nombre_funcion")
    private String nombreFuncion;
    @JoinColumn(name = "fk_cargo", referencedColumnName = "id_cargo")
    @ManyToOne
    private Cargo fkCargo;

    public FuncionCargo() {
    }

    public FuncionCargo(Integer idFuncion) {
        this.idFuncion = idFuncion;
    }

    public Integer getIdFuncion() {
        return idFuncion;
    }

    public void setIdFuncion(Integer idFuncion) {
        this.idFuncion = idFuncion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreFuncion() {
        return nombreFuncion;
    }

    public void setNombreFuncion(String nombreFuncion) {
        this.nombreFuncion = nombreFuncion;
    }

    public Cargo getFkCargo() {
        return fkCargo;
    }

    public void setFkCargo(Cargo fkCargo) {
        this.fkCargo = fkCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFuncion != null ? idFuncion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FuncionCargo)) {
            return false;
        }
        FuncionCargo other = (FuncionCargo) object;
        if ((this.idFuncion == null && other.idFuncion != null) || (this.idFuncion != null && !this.idFuncion.equals(other.idFuncion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FuncionCargo[ idFuncion=" + idFuncion + " ]";
    }
    
}
