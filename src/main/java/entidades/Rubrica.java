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
@Table(name = "rubrica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rubrica.findAll", query = "SELECT r FROM Rubrica r")
    , @NamedQuery(name = "Rubrica.findByIdRubrica", query = "SELECT r FROM Rubrica r WHERE r.idRubrica = :idRubrica")
    , @NamedQuery(name = "Rubrica.findBySemestre", query = "SELECT r FROM Rubrica r WHERE r.semestre = :semestre")})
public class Rubrica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rubrica")
    private Integer idRubrica;
    @Column(name = "semestre")
    private Integer semestre;
    @JoinColumn(name = "fk_carrera", referencedColumnName = "id_carrera")
    @ManyToOne
    private Carrera fkCarrera;
    @JoinColumn(name = "fk_materia", referencedColumnName = "clave_materia")
    @ManyToOne
    private Materia fkMateria;
    @OneToMany(mappedBy = "fkRubrica")
    private Collection<Curso> cursoCollection;

    public Rubrica() {
    }

    public Rubrica(Integer idRubrica) {
        this.idRubrica = idRubrica;
    }

    public Integer getIdRubrica() {
        return idRubrica;
    }

    public void setIdRubrica(Integer idRubrica) {
        this.idRubrica = idRubrica;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public Carrera getFkCarrera() {
        return fkCarrera;
    }

    public void setFkCarrera(Carrera fkCarrera) {
        this.fkCarrera = fkCarrera;
    }

    public Materia getFkMateria() {
        return fkMateria;
    }

    public void setFkMateria(Materia fkMateria) {
        this.fkMateria = fkMateria;
    }

    @XmlTransient
    public Collection<Curso> getCursoCollection() {
        return cursoCollection;
    }

    public void setCursoCollection(Collection<Curso> cursoCollection) {
        this.cursoCollection = cursoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRubrica != null ? idRubrica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rubrica)) {
            return false;
        }
        Rubrica other = (Rubrica) object;
        if ((this.idRubrica == null && other.idRubrica != null) || (this.idRubrica != null && !this.idRubrica.equals(other.idRubrica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Rubrica[ idRubrica=" + idRubrica + " ]";
    }
    
}
