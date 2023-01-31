package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;

import java.util.List;


public interface AppointmentRestService {

    AppointmentModel createAppointment(AppointmentModel appointmentModel);
    List<AppointmentModel> getAllAppointment();
    AppointmentModel getAppointmentById(String appointmentId);
    AppointmentModel updateAppointment(String kode, AppointmentModel appointmentModel);
    AppointmentModel getAppointmentByCode(String kode);

    void deleteAppointmentByCode(String kode);
}

