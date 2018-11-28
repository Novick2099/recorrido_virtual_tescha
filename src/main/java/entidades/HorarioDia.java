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
@Table(name = "horario_dia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HorarioDia.findAll", query = "SELECT h FROM HorarioDia h")
    , @NamedQuery(name = "HorarioDia.findByIdHorarioDia", query = "SELECT h FROM HorarioDia h WHERE h.idHorarioDia = :idHorarioDia")})
public class HorarioDia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_horario_dia")
    private Integer idHorarioDia;
    @OneToMany(mappedBy = "fkHorarioDia")
    private Collection<HorarioCargo> horarioCargoCollection;
    @JoinColumn(name = "fk_dia", referencedColumnName = "id_dia")
    @ManyToOne
    private Dia fkDia;
    @JoinColumn(name = "fk_horario", referencedColumnName = "id_horario")
    @ManyToOne
    private Horario fkHorario;

    public HorarioDia() {
    }

    public HorarioDia(Integer idHorarioDia) {
        this.idHorarioDia = idHorarioDia;
    }

    public Integer getIdHorarioDia() {
        return idHorarioDia;
    }

    public void setIdHorarioDia(Integer idHorarioDia) {
        this.idHorarioDia = idHorarioDia;
    }

    @XmlTransient
    public Collection<HorarioCargo> getHorarioCargoCollection() {
        return horarioCargoCollection;
    }

    public void setHorarioCargoCollection(Collection<HorarioCargo> horarioCargoCollection) {
        this.horarioCargoCollection = horarioCargoCollection;
    }

    public Dia getFkDia() {
        return fkDia;
    }

    public void setFkDia(Dia fkDia) {
        this.fkDia = fkDia;
    }

    public Horario getFkHorario() {
        return fkHorario;
    }

    public void setFkHorario(Horario fkHorario) {
        this.fkHorario = fkHorario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHorarioDia != null ? idHorarioDia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HorarioDia)) {
            return false;
        }
        HorarioDia other = (HorarioDia) object;
        if ((this.idHorarioDia == null && other.idHorarioDia != null) || (this.idHorarioDia != null && !this.idHorarioDia.equals(other.idHorarioDia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.HorarioDia[ idHorarioDia=" + idHorarioDia + " ]";
    }
    
}
