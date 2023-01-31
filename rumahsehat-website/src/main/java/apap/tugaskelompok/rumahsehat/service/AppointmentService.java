package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public interface AppointmentService {
    AppointmentModel getAppointment (String codeAppointment);
    AppointmentModel updateAppointment(String kode, AppointmentModel appointmentModel);
    AppointmentModel getAppointmentByCode(String kode);
    List<LocalTime> getAdminEndTime();
    List<AppointmentModel> getListDokterAppointment(String uuid);
    List<LocalTime> getDokterEndTime(String uuid);

}
