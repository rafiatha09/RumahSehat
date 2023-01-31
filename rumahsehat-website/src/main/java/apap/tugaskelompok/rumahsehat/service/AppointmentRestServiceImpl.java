package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class AppointmentRestServiceImpl implements AppointmentRestService {
    @Autowired
    private AppointmentDb appointmentDb;

    @Override
    public AppointmentModel createAppointment(AppointmentModel appointmentModel) {
        return appointmentDb.save(appointmentModel);
    }

    @Override
    public List<AppointmentModel> getAllAppointment() {
        return appointmentDb.findAll();
    }

    @Override
    public AppointmentModel getAppointmentById(String appointmentId) {
        Optional<AppointmentModel> appointment = appointmentDb.findById(appointmentId);
        if (appointment.isPresent()) {
            return appointment.get();
        }
        return null;
    }

    @Override
    public AppointmentModel updateAppointment(String kode, AppointmentModel appointmentModel) {
        AppointmentModel appointment = getAppointmentByCode(kode);
        appointment.setTanggalDimulai(appointmentModel.getTanggalDimulai());
        appointment.setDone(appointmentModel.isDone());
        appointment.setDokter(appointmentModel.getDokter());
        appointment.setPasien(appointmentModel.getPasien());
        return appointmentDb.save(appointment);
    }

    @Override
    public AppointmentModel getAppointmentByCode(String kode) {
        Optional<AppointmentModel> appointment = appointmentDb.findById(kode);
        if (appointment.isPresent()) {
            return appointment.get();
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteAppointmentByCode(String kode) {
        Optional<AppointmentModel> appointmentModel = appointmentDb.findById(kode);
        if (appointmentModel.isPresent()) {
            appointmentDb.delete(appointmentModel.get());
        } else {
            throw new NoSuchElementException();
        }
    }
}
