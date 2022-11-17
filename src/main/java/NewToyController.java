import Child.AgeGroup;
import Toy.Toy;
import Toy.ToySize;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewToyController implements Initializable{
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label ageGroupLabel;
    @FXML
    private Label sizeLabel;
    @FXML
    private TextField toyNameField;
    @FXML
    private TextField priceField;
    @FXML
    private ChoiceBox<String> ageGroup;
    @FXML
    private ChoiceBox<String> sizeBox;

    private String[] types = {"TODDLER", "MIDDLECHILD", "TEENAGER"};

    private String[] sizes = {"TINY", "SMALL","MEDIUM","BIG"};

    private String type = "";

    private String size = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ageGroup.getItems().addAll(types);
        ageGroup.setOnAction(this::getAgeGroup);

        sizeBox.getItems().addAll(sizes);
        sizeBox.setOnAction(this::getSize);
    }

    public void getAgeGroup(javafx.event.ActionEvent e){
        type = ageGroup.getValue();
    }

    public void getSize(javafx.event.ActionEvent e){
        size = sizeBox.getValue();
    }

    public void returnToMain(javafx.event.ActionEvent e) throws IOException {
        new Controller().changeScene("/resources/Main.fxml",e);
    }

    public void addNewToy(javafx.event.ActionEvent e) throws IOException {
        System.out.println("Додавання нової іграшки у базу даних\nВведіть\n\tназву,\n\tціну,\n\tвікову групу(1-TODDLER, 2-MIDDLECHILD, 3-TEENAGER)\n\tта розмір (1-TINY, 2-SMALL, 3 -> MEDIUM, 4 -> BIG)");
        String name = toyNameField.getText();
        if(name.equals(""))
            nameLabel.setText("Неправильно введене ім'я");

        int price = 0;

        try{
            price = Integer.parseInt(priceField.getText());
        }catch (NumberFormatException number){
            priceLabel.setText("Неправильно введено ціну");
        }

        int id = 0;

        if(type.equals("")){
            ageGroupLabel.setText("Не вибрано вікової групи");
        }else{
            for(int i = 0; i < types.length;i++){
                if(types[i].equals(type)){
                    id = i + 1;
                    break;
                }
            }
        }

        int toySize = 0;

        if(size.equals("")){
            ageGroupLabel.setText("Не вибрано розмір");
        }else{
            for(int i = 0; i < sizes.length;i++){
                if(sizes[i].equals(size)){
                    toySize = i + 1;
                    break;
                }
            }
        }
        toyNameField.clear();
        priceField.clear();
        if(name.equals("") || price == 0 || type.equals("") || size.equals("") ){
            return;
        }
        ageGroupLabel.setText("Виберіть вікову групу:");
        sizeLabel.setText("Виберіть розмір:");
        nameLabel.setText("Впишіть назву іграшки:");
        priceLabel.setText("Впишіть ціну іграшки:");
        AgeGroup age = AgeGroup.getAgeGroupByOrd(id);
        ToySize size = ToySize.getSizeByOrd(toySize);
        Main.adm.getToyList().addToy(Toy.createToy(0,name,price,age,size));
        new Controller().changeScene("/resources/Main.fxml",e);
    }
}
