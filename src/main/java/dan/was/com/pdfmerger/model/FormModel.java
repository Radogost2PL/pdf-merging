package dan.was.com.pdfmerger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component

public class FormModel {


    @Size (min = 5, max=35, message="Podana nazwa generowanego PDFa musi być pomiędzy 5 a 35 znaków")
    private String name;


}
