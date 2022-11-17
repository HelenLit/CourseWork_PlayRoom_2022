import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Child.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label ageGroupLabel;
    @FXML
    private Label parentsCredentialsLabel;
    @FXML
    private ChoiceBox<String> ageGroup;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField parentsCredentialsField;

    private String[] types = {"TODDLER", "MIDDLECHILD", "TEENAGER"};

    private String type = "";


    public void getAgeGroup(javafx.event.ActionEvent e){
        type = ageGroup.getValue();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ageGroup.getItems().addAll(types);
        ageGroup.setOnAction(this::getAgeGroup);
    }

    public boolean checkNumber(String number){

        Pattern p = Pattern.compile(
                "^(\\+?\\d{1,4})?((\\(\\d{1,4}\\))|\\d{1,4})[-.]?\\d{1,4}[-.]?\\d{1,4}[-.]?\\d{1,4}$");

        Matcher m = p.matcher(number);

        boolean res = m.matches();

        return (res);
    }

    public void returnToMain(javafx.event.ActionEvent e) throws IOException {
        new Controller().changeScene("/resources/Main.fxml",e);
    }

    public void registerChild(javafx.event.ActionEvent e) throws IOException {
        String firstName = firstNameField.getText();
        if(firstName.equals(""))
            firstNameLabel.setText("Неправильно введене ім'я");

        String lastName = lastNameField.getText();
        if(lastName.equals(""))
            lastNameLabel.setText("Неправильно введене прізвище");

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


        String credentials = parentsCredentialsField.getText();
        if(credentials.equals("") || !checkNumber(credentials))
            parentsCredentialsLabel.setText("Неправильно введені контакти батьків");

        if(firstName.equals("") || lastName.equals("") || type.equals("") || credentials.equals("") || !checkNumber(credentials)){
            return;
        }
        firstNameLabel.setText("Введіть ім'я:");
        lastNameLabel.setText("Введіть прізвище:");
        ageGroupLabel.setText("Виберіть вікову групу:");
        parentsCredentialsLabel.setText("Введіть контакт до батьків:");
        firstNameField.clear();
        lastNameField.clear();
        parentsCredentialsField.clear();

        Main.adm.getPlayRoom().registerChild(new Child(firstName,lastName, AgeGroup.getAgeGroupByOrd(id),credentials));
        new Controller().changeScene("/resources/Main.fxml",e);
    }
}