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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MarzoNegro
 */
@Entity
@Table(name = "horario_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorarioCargo.findAll", query = "SELECT h FROM HorarioCargo h")
    , @NamedQuery(name = "HorarioCargo.findByIdHorarioCargo", query = "SELECT h FROM HorarioCargo h WHERE h.idHorarioCargo = :idHorarioCargo")})
public class HorarioCargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_horario_cargo")
    private Integer idHorarioCargo;
    @ManyToMany(mappedBy = "horarioCargoCollection")
    private Collection<Curso> cursoCollection;
    @JoinColumn(name = "fk_aula", referencedColumnName = "id_aula")
    @ManyToOne
    private Aula fkAula;
    @JoinColumn(name = "fk_empleado_cargo", referencedColumnName = "fk_matricula")
    @ManyToOne
    private EmpleadoCargo fkEmpleadoCargo;
    @JoinColumn(name = "fk_horario_dia", referencedColumnName = "id_horario_dia")
    @ManyToOne
    private HorarioDia fkHorarioDia;

    public HorarioCargo() {
    }

    public HorarioCargo(Integer idHorarioCargo) {
        this.idHorarioCargo = idHorarioCargo;
    }

    public Integer getIdHorarioCargo() {
        return idHorarioCargo;
    }

    public void setIdHorarioCargo(Integer idHorarioCargo) {
        this.idHorarioCargo = idHorarioCargo;
    }

    @XmlTransient
    public Collection<Curso> getCursoCollection() {
        return cursoCollection;
    }

    public void setCursoCollection(Collection<Curso> cursoCollection) {
        this.cursoCollection = cursoCollection;
    }

    public Aula getFkAula() {
        return fkAula;
    }

    public void setFkAula(Aula fkAula) {
        this.fkAula = fkAula;
    }

    public EmpleadoCargo getFkEmpleadoCargo() {
        return fkEmpleadoCargo;
    }

    public void setFkEmpleadoCargo(EmpleadoCargo fkEmpleadoCargo) {
        this.fkEmpleadoCargo = fkEmpleadoCargo;
    }

    public HorarioDia getFkHorarioDia() {
        return fkHorarioDia;
    }

    public void setFkHorarioDia(HorarioDia fkHorarioDia) {
        this.fkHorarioDia = fkHorarioDia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHorarioCargo != null ? idHorarioCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorarioCargo)) {
            return false;
        }
        HorarioCargo other = (HorarioCargo) object;
        if ((this.idHorarioCargo == null && other.idHorarioCargo != null) || (this.idHorarioCargo != null && !this.idHorarioCargo.equals(other.idHorarioCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.HorarioCargo[ idHorarioCargo=" + idHorarioCargo + " ]";
    }
    
}
