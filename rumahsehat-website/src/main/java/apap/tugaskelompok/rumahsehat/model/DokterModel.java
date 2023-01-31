package apap.tugaskelompok.rumahsehat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={"listAppointment"}, allowSetters = true)
@Table(name = "dokter")
public class DokterModel extends UserModel {
    @NotNull
    @Column(name = "tarif", nullable = false)
    private Integer tarif;

    // Relasi degan appointment
    @OneToMany(mappedBy = "dokter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentModel> listAppointment;
}
