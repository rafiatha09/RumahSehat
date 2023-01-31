package apap.tugaskelompok.rumahsehat.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "admin")
public class AdminModel extends UserModel {

}
