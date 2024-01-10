package lk.ijse.GameCafe.view.tdm.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartTm {
    private String id;
    private double hours;
    private double rate;
    private double total;
    private Button btn;
}
