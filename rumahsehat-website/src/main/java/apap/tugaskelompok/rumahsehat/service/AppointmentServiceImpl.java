package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.repository.AppointmentDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentDb appointmentDb;

    @Autowired
    AppointmentRestService appointmentRestService;

    @Override
    public AppointmentModel getAppointment(String codeAppointment) {
        Optional<AppointmentModel> appointment = appointmentDb.findById(codeAppointment);
        if (appointment.isPresent()) {
            return appointment.get();
        }
        throw new NoSuchElementException();
    }

    @Override
    public AppointmentModel updateAppointment(String kode, AppointmentModel appointmentModel) {
        AppointmentModel appointment = getAppointmentByCode(kode);
        appointment.setTanggalDimulai(appointmentModel.getTanggalDimulai());
        appointment.setDone(appointmentModel.isDone());
        appointment.setDokter(appointmentModel.getDokter());
        appointment.setPasien(appointmentModel.getPasien());
        appointment.setTagihan(appointmentModel.getTagihan());
        appointment.setResep(appointmentModel.getResep());
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
    public List<LocalTime> getAdminEndTime() {
        List<AppointmentModel> listAppointmentAll = appointmentRestService.getAllAppointment();
        List<LocalTime> listLocalEndTime = new ArrayList<>();
        for (AppointmentModel appointmentModel : listAppointmentAll) {
            var addLocalTime = appointmentModel.getWaktuDimulai().plusHours(1);
            listLocalEndTime.add(addLocalTime);
        }
        return listLocalEndTime;
    }

    @Override
    public List<AppointmentModel> getListDokterAppointment(String uuid) {
        List<AppointmentModel> listAppointmentAll = appointmentRestService.getAllAppointment();
        List<AppointmentModel> listAppointmentDokter = new ArrayList<>();
        for (AppointmentModel appointmentModel : listAppointmentAll) {
            if (appointmentModel.getDokter().getUuid().equals(uuid)) {
                listAppointmentDokter.add(appointmentModel);
            }
        }
        return listAppointmentDokter;
    }

    @Override
    public List<LocalTime> getDokterEndTime(String uuid) {
        List<AppointmentModel> listAppointmentAll = appointmentRestService.getAllAppointment();
        List<LocalTime> listEndDokterTime = new ArrayList<>();
        for (AppointmentModel appointmentModel : listAppointmentAll) {
            if (appointmentModel.getDokter().getUuid().equals(uuid)) {
                var addLocalTime = appointmentModel.getWaktuDimulai().plusHours(1);
                listEndDokterTime.add(addLocalTime);
            }
        }
        return listEndDokterTime;
    }
}
