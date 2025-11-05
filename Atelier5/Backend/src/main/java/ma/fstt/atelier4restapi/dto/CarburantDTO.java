package ma.fstt.atelier4restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarburantDTO {
	private Long id;
	private String nom;
	private String description;
}


