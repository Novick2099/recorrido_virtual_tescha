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
@Table(name = "cat_aula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CatAula.findAll", query = "SELECT c FROM CatAula c")
    , @NamedQuery(name = "CatAula.findByIdCatAula", query = "SELECT c FROM CatAula c WHERE c.idCatAula = :idCatAula")
    , @NamedQuery(name = "CatAula.findByDescripcionCat", query = "SELECT c FROM CatAula c WHERE c.descripcionCat = :descripcionCat")})
public class CatAula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cat_aula")
    private Integer idCatAula;
    @Size(max = 30)
    @Column(name = "descripcion_cat")
    private String descripcionCat;
    @OneToMany(mappedBy = "fkCatAula")
    private Collection<Aula> aulaCollection;

    public CatAula() {
    }

    public CatAula(Integer idCatAula) {
        this.idCatAula = idCatAula;
    }

    public Integer getIdCatAula() {
        return idCatAula;
    }

    public void setIdCatAula(Integer idCatAula) {
        this.idCatAula = idCatAula;
    }

    public String getDescripcionCat() {
        return descripcionCat;
    }

    public void setDescripcionCat(String descripcionCat) {
        this.descripcionCat = descripcionCat;
    }

    @XmlTransient
    public Collection<Aula> getAulaCollection() {
        return aulaCollection;
    }

    public void setAulaCollection(Collection<Aula> aulaCollection) {
        this.aulaCollection = aulaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatAula != null ? idCatAula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatAula)) {
            return false;
        }
        CatAula other = (CatAula) object;
        if ((this.idCatAula == null && other.idCatAula != null) || (this.idCatAula != null && !this.idCatAula.equals(other.idCatAula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CatAula[ idCatAula=" + idCatAula + " ]";
    }
    
}
