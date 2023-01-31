package apap.tugaskelompok.rumahsehat.repository;


import apap.tugaskelompok.rumahsehat.model.PasienModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasienDb extends JpaRepository<PasienModel, String> {
}
