package apap.tugaskelompok.rumahsehat.dto;

import java.util.List;

import apap.tugaskelompok.rumahsehat.model.ResepModel;
import lombok.Data;

@Data
public class ApotekerDto extends UserDto {
    private List<ResepModel> listResep;
}
