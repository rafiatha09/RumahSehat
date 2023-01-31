package apap.tugaskelompok.rumahsehat.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointment")
public class AppointmentModel implements Serializable {

    @Id
    @Column(name = "kode")
    private String kode;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalDimulai;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuDimulai;

    @NotNull
    @Column(name = "isDone")
    private boolean isDone;

    // Relasi dengan dokter
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "dokter", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DokterModel dokter;

    // Relasi dengan pasie
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "pasien", referencedColumnName = "uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PasienModel pasien;

    // Relasi dengan tagihan
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tagihan", referencedColumnName = "kode")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TagihanModel tagihan;

    //  Relasi dengan resep
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resep", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ResepModel resep;
}