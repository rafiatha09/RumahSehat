package apap.tugaskelompok.rumahsehat.dto;

import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.AppointmentModel;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

@Getter
public class DTODokterIncome {
    private Map<Integer, Integer> pemasukan;
    private DokterModel dokter;

    public DTODokterIncome(DokterModel dokterInput) {
        this.dokter = dokterInput;
    }

    public void getResultYear() {
        Map<Integer, Integer> result = new LinkedHashMap<Integer, Integer>();
        for (var i = 0; i < 12; i++) {
            int pendapatanBulan = getIncomePerMonth(dokter,i);
            result.put(i,pendapatanBulan);
        }
        this.pemasukan = result;
    }

    public Integer getIncomePerMonth(DokterModel dokter, int month) {
        var result = 0;
        String[] months = {"January", "February", 
        "March", "April", "May", "June", "July", "August", "September", 
        "October", "November", "December"};
        List<AppointmentModel> listAppointment = dokter.getListAppointment();
        for (var i = 0; i < listAppointment.size(); i++) {
            if ((listAppointment.get(i).isDone()) && listAppointment.get(i).getTanggalDimulai().getMonth().toString().equals(months[month])) {
                result += dokter.getTarif();
            }
        }
        return result;
    }
}
