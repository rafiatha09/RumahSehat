package apap.tugaskelompok.rumahsehat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"listJumlahResepObat"}, allowSetters = true)
@Table(name = "obat")
public class ObatModel implements Serializable {

        @Id
        private String id;

        @NotNull
        @Size(max = 50)
        @Column(name = "nama_obat", nullable = false)
        private String nama_obat;

        @Column(name = "stok", nullable = true, columnDefinition = "integer default 100")
        private Integer stok;

        @NotNull
        @Column(name = "harga", nullable = false)
        private Integer harga;

        // Relasi dengan resep
        @OneToMany(mappedBy = "obat", fetch=FetchType.LAZY, cascade= CascadeType.ALL)
        private List<JumlahResepObatModel> listJumlahResepObat;
}
