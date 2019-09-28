import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class TipCalculatorController {
   private static final NumberFormat currency = 
      NumberFormat.getCurrencyInstance();
   private static final NumberFormat percent = 
      NumberFormat.getPercentInstance();
   
   private BigDecimal tipPercentage = new BigDecimal(0.15);

   @FXML 
   private TextField amountTextField; 

   @FXML
   private Label tipPercentageLabel; 

   @FXML
   private Slider tipPercentageSlider;

   @FXML
   private TextField tipTextField;

   @FXML
   private TextField totalTextField;

   @FXML
   private TextField peopleAmountTextField;

   @FXML
   private TextField totalForEveryoneTextField;

   @FXML
   private void calculateButtonPressed(ActionEvent e) {
      try {
         BigDecimal amount = new BigDecimal(amountTextField.getText());
         BigDecimal tip = amount.multiply(tipPercentage);
         BigDecimal total = amount.add(tip);

         tipTextField.setText(currency.format(tip));
         totalTextField.setText(currency.format(total));

         Integer peopleAmount = new Integer(peopleAmountTextField.getText());

         if (peopleAmount == 0) {
            totalForEveryoneTextField.setText("Incorrect. Try again");
         } else {
            BigDecimal totalForEveryone = total.divide(new BigDecimal(peopleAmount));
            totalForEveryoneTextField.setText(currency.format(totalForEveryone));
         }
      }
      catch (NumberFormatException ex) {
         amountTextField.setText("Enter amount");
         amountTextField.selectAll();
         amountTextField.requestFocus();
      }
   }

   public void initialize() {
      currency.setRoundingMode(RoundingMode.HALF_UP);
      tipPercentageSlider.valueProperty().addListener(
              new ChangeListener<Number>() {
                 @Override
                 public void changed(ObservableValue<? extends Number> ov,
                                     Number oldValue, Number newValue) {
                    tipPercentage =
                            BigDecimal.valueOf(newValue.intValue() / 100.0);
                    tipPercentageLabel.setText(percent.format(tipPercentage));
                 }
              }
      );
   }

}
