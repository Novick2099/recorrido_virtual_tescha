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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author MarzoNegro
 */
@Entity
@Table(name = "curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c")
    , @NamedQuery(name = "Curso.findByIdCurso", query = "SELECT c FROM Curso c WHERE c.idCurso = :idCurso")
    , @NamedQuery(name = "Curso.findByGrupo", query = "SELECT c FROM Curso c WHERE c.grupo = :grupo")
    , @NamedQuery(name = "Curso.findByCicloEscolar", query = "SELECT c FROM Curso c WHERE c.cicloEscolar = :cicloEscolar")})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_curso")
    private Integer idCurso;
    @Column(name = "grupo")
    private Integer grupo;
    @Size(max = 6)
    @Column(name = "ciclo_escolar")
    private String cicloEscolar;
    @JoinTable(name = "curso_horario", joinColumns = {
        @JoinColumn(name = "fk_curso", referencedColumnName = "id_curso")}, inverseJoinColumns = {
        @JoinColumn(name = "fk_horario_cargo", referencedColumnName = "id_horario_cargo")})
    @ManyToMany
    private Collection<HorarioCargo> horarioCargoCollection;
    @JoinColumn(name = "fk_rubrica", referencedColumnName = "id_rubrica")
    @ManyToOne
    private Rubrica fkRubrica;

    public Curso() {
    }

    public Curso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Integer idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getGrupo() {
        return grupo;
    }

    public void setGrupo(Integer grupo) {
        this.grupo = grupo;
    }

    public String getCicloEscolar() {
        return cicloEscolar;
    }

    public void setCicloEscolar(String cicloEscolar) {
        this.cicloEscolar = cicloEscolar;
    }

    @XmlTransient
    public Collection<HorarioCargo> getHorarioCargoCollection() {
        return horarioCargoCollection;
    }

    public void setHorarioCargoCollection(Collection<HorarioCargo> horarioCargoCollection) {
        this.horarioCargoCollection = horarioCargoCollection;
    }

    public Rubrica getFkRubrica() {
        return fkRubrica;
    }

    public void setFkRubrica(Rubrica fkRubrica) {
        this.fkRubrica = fkRubrica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurso != null ? idCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.idCurso == null && other.idCurso != null) || (this.idCurso != null && !this.idCurso.equals(other.idCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Curso[ idCurso=" + idCurso + " ]";
    }
    
}
