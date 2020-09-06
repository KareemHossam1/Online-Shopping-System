package online.shopping.system;
// import packages
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class OnlineShoppingSystem extends Application {
    Label itemLbl = new Label("Item");  // Create a label and set its text to Item
    Label priceLbl = new Label("Price");  // Create a label and set its text to Price
    Label quantityLbl = new Label("Quantity");  // Create a label and set its text to Quantity
    Label shippingLbl = new Label("Shipping");  // Create a label and set its text to Shipping
    Label nextLbl = new Label("Next Day");  // Create a label and set its text to Next Day
    Label thisLbl = new Label("This Week");  // Create a label and set its text to This Week
    Label someLbl = new Label("Some Day");  // Create a label and set its text to Day
    Label totalLbl = new Label("Total Due");  // Create a label and set its text to Total Due
    Label totalPriceLbl = new Label("$0.00");  // Create a label and set its text to $0.00
    TextField itemTxt = new TextField();  // Create a text field to accept order name
    TextField priceTxt = new TextField();  // Create a text field to accept price
    TextField quantityTxt = new TextField();  // Create a text field to accept quantity
    CheckBox taxableChk = new CheckBox("Taxable?");  // Create a check box to know if it's taxable
    RadioButton nextRad = new RadioButton("$20");  // Create a radio button for next day shipping
    RadioButton thisRad = new RadioButton("$12");  // Create a radio button for this week shipping
    RadioButton someRad = new RadioButton("$5");  // Create a radio button for some day shipping
    ToggleGroup radioButtons = new ToggleGroup();  // Create a toggle group to put radio buttons
    Button processBtn = new Button("Process");  // Create a button to calculate price
    Button resetBtn = new Button("Reset");  // Create a button to reset variables and elements
    // HBoxes and VBoxes to order elements and set spacing between elements 
    HBox itemH = new HBox(40);      
    HBox priceH = new HBox(38);
    HBox quantityH = new HBox(15);
    HBox nextH = new HBox(12);
    HBox thisH = new HBox(5);
    HBox someH = new HBox(5);
    HBox totalH = new HBox(9);
    HBox buttonsH = new HBox(5);
    VBox elementsV = new VBox(5);
    VBox taxableV = new VBox();
    Alert alert = new Alert(Alert.AlertType.ERROR);  // Create an alert for expected errors
    Double tryD = null;  // Variable to check if the user entered a valid price 
    Integer tryI= null;  // Variable to check if the user entered a valid quantity
    // Calculations variables
    int  quantity=0 , shipping=0;  
    double price=0,total = 0;
    @Override
    public void start(Stage primaryStage) {
        // Order elements on the scene
        taxableV.getChildren().add(taxableChk);
        taxableV.setPadding(new Insets(0,0,0,72));  // To put check box on the right position
        itemH.getChildren().addAll(itemLbl,itemTxt);
        priceH.getChildren().addAll(priceLbl,priceTxt);
        quantityH.getChildren().addAll(quantityLbl,quantityTxt);
        nextH.getChildren().addAll(nextLbl,nextRad);
        thisH.getChildren().addAll(thisLbl,thisRad);
        someH.getChildren().addAll(someLbl,someRad);
        totalH.getChildren().addAll(totalLbl,totalPriceLbl);
        buttonsH.getChildren().addAll(processBtn,resetBtn);
        elementsV.getChildren().addAll(itemH,priceH,quantityH,taxableV,shippingLbl,nextH,thisH,someH,totalH,buttonsH);
        elementsV.setPadding(new Insets(10,10,10,10));
        // Setting radio buttons toggle group to make the user choose only one shipping type
        nextRad.setToggleGroup(radioButtons);
        thisRad.setToggleGroup(radioButtons);
        someRad.setToggleGroup(radioButtons);
        // Handling process button
        processBtn.setOnAction(e->{
            // Display an error alert if the user entered invalid price
            try{
                tryD = Double.valueOf(priceTxt.getText());
            }
            catch (final NumberFormatException Event)
            {
                alert.setTitle("Price Error");     
                alert.setContentText("Price must be only numbers.");
                alert.show();
            }
            if(tryD != null){
                // Display an error alert if the user entered invalid quantity
                try{
                    tryI = Integer.valueOf(quantityTxt.getText());
                }
                catch (final NumberFormatException Event)
                {
                    alert.setTitle("Quantity Error");     
                    alert.setContentText("Quantity must be only integer numbers.");
                    alert.show();
                }
                if(tryI != null){
                    // Display an error alert if the user entered negative or 0 quantity
                    if (Integer.valueOf(quantityTxt.getText())<1){
                        alert.setTitle("Quantity Error");     
                        alert.setContentText("Quantity must be bigger than 1");
                        alert.show();
                    }
                    if (Double.valueOf(priceTxt.getText())<=0){
                        alert.setTitle("Price Error");     
                        alert.setContentText("Price must be bigger than 0");
                        alert.show();
                    }
                    // Calculating total price
                    price = Double.valueOf(priceTxt.getText());  // get price
                    quantity = Integer.valueOf(quantityTxt.getText());  // get quantity
                    // Get shipping, calculate total and displaying it.
                    if (nextRad.isSelected()){
                            shipping=20;
                            total = calculate(taxableChk);
                            totalPriceLbl.setText("$"+total);}
                    else if (thisRad.isSelected()){
                            shipping=12;
                            total = calculate(taxableChk);
                            totalPriceLbl.setText("$"+total);}
                    else if (someRad.isSelected()){
                            shipping=5;
                            total = calculate(taxableChk);
                            totalPriceLbl.setText("$"+total);}
                    // Display an error alert if the didn't choose shipping type
                    else{
                            alert.setTitle("Shipping Error");     
                            alert.setContentText("You must choose shipping type");
                            alert.show();}
                }}});
        // Handling reset button
        resetBtn.setOnAction(e->{
            quantity=0; shipping = 0; price=0; total = 0;
            itemTxt.setText(""); priceTxt.setText(""); quantityTxt.setText("");
            nextRad.setSelected(false); thisRad.setSelected(false);
            someRad.setSelected(false); taxableChk.setSelected(false);
            totalPriceLbl.setText("$0.00");
        });
        Scene scene = new Scene(elementsV, 270, 300);  // Create scene
        primaryStage.setResizable(false);  // Turn off resize option
        primaryStage.setTitle("orinoco.com");  // Stage Title
        primaryStage.setScene(scene);  // Add scene
        primaryStage.show();  // Display stage
    }
    public static void main(String[] args) {
        launch(args);
    }
    // Create a method to 
    public double calculate(CheckBox r){  // pass the check box as an argument 
        if (r.isSelected()){  // check if the check box is selected
                        total = quantity*price;  // calculate total without tax
                        total += total*0.07;  // Add tax
                        total+=shipping;}  // Add shipping
                    else{  // if taxes are unselected
                        total = quantity*price+ shipping;}  // calculate total
        return total;
    };}