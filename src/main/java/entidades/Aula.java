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
@Table(name = "aula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aula.findAll", query = "SELECT a FROM Aula a")
    , @NamedQuery(name = "Aula.findByIdAula", query = "SELECT a FROM Aula a WHERE a.idAula = :idAula")
    , @NamedQuery(name = "Aula.findByNombreAula", query = "SELECT a FROM Aula a WHERE a.nombreAula = :nombreAula")})
public class Aula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_aula")
    private Integer idAula;
    @Size(max = 30)
    @Column(name = "nombre_aula")
    private String nombreAula;
    @JoinColumn(name = "fk_cat_aula", referencedColumnName = "id_cat_aula")
    @ManyToOne
    private CatAula fkCatAula;
    @JoinColumn(name = "fk_edificio", referencedColumnName = "id_edificio")
    @ManyToOne
    private Edificio fkEdificio;
    @OneToMany(mappedBy = "fkAula")
    private Collection<HorarioCargo> horarioCargoCollection;

    public Aula() {
    }

    public Aula(Integer idAula) {
        this.idAula = idAula;
    }

    public Integer getIdAula() {
        return idAula;
    }

    public void setIdAula(Integer idAula) {
        this.idAula = idAula;
    }

    public String getNombreAula() {
        return nombreAula;
    }

    public void setNombreAula(String nombreAula) {
        this.nombreAula = nombreAula;
    }

    public CatAula getFkCatAula() {
        return fkCatAula;
    }

    public void setFkCatAula(CatAula fkCatAula) {
        this.fkCatAula = fkCatAula;
    }

    public Edificio getFkEdificio() {
        return fkEdificio;
    }

    public void setFkEdificio(Edificio fkEdificio) {
        this.fkEdificio = fkEdificio;
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
        hash += (idAula != null ? idAula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aula)) {
            return false;
        }
        Aula other = (Aula) object;
        if ((this.idAula == null && other.idAula != null) || (this.idAula != null && !this.idAula.equals(other.idAula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Aula[ idAula=" + idAula + " ]";
    }
    
}
