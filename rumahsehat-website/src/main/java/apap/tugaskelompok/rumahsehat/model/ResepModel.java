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

import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"appointment","listJumlahResepObat"}, allowSetters = true)
@Table(name = "resep")
public class ResepModel implements Serializable {
    @Id
    private Long id;

    @NotNull
    @Column(name = "isDone", nullable = false)
    private boolean isDone;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    // Relasi dengan apoteker
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "confirmer_uuid", referencedColumnName = "uuid", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ApotekerModel apoteker;

    // Relasi dengan appointment
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment", referencedColumnName = "kode", nullable = false)
    private AppointmentModel appointment;

    // Relasi dengan obat
    @OneToMany(mappedBy = "resep", fetch=FetchType.LAZY, cascade= CascadeType.ALL)
    private List<JumlahResepObatModel> listJumlahResepObat;

}
