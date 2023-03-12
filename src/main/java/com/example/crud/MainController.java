package com.example.crud;

import com.example.crud.factories.*;
import com.example.crud.hierarchy.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final ArrayList<Gadget> list = new ArrayList<>();
    private final ArrayList<Label> labels = new ArrayList<>();
    private final ArrayList<TextField> inputs = new ArrayList<>();
    private final String[] classes = {"Tablet", "Laptop", "Smartphone", "PushButtonPhone"};
    private final HashMap<String, Factory> map = new HashMap<>();
    private ObjectInfo selectedRow;
    private String defaultInputStyles;
    public static String errorMessage = "Check data types";

    ObservableList<ObjectInfo> objects = FXCollections.observableArrayList();
    private boolean isUpdated = false;

    @FXML
    private ChoiceBox<String> ClassChoice;

    @FXML
    private Button AddBtn;

    @FXML
    private Button UpdateBtn;

    @FXML
    private Button DeleteBtn;

    @FXML
    private TableColumn<ObjectInfo, Integer> IdColumn;

    @FXML
    private TableColumn<ObjectInfo, String> NameColumn;

    @FXML
    private TableView<ObjectInfo> ObjectsTable;

    @FXML
    private TableColumn<ObjectInfo, String> TypeColumn;

    @FXML
    private VBox InputsVBox;

    @FXML
    private VBox LabelsVBox;

    public static void createAlert(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    void disableElements(boolean isUpdated) {
        AddBtn.setDisable(!isUpdated);
        DeleteBtn.setDisable(!isUpdated);
        ClassChoice.setDisable(!isUpdated);
        ObjectsTable.setDisable(!isUpdated);
        inputs.get(0).setDisable(!isUpdated);
    }

    @FXML
    void onAddBtnClick() {
        Factory factory = map.get(ClassChoice.getValue());
        if (factory.checkInputs(inputs)) {
            list.add(factory.getGadget(inputs));
            objects.add(new ObjectInfo(list.size(), inputs.get(0).getText(), ClassChoice.getValue()));
            for (TextField textField : inputs)
                textField.setText("");
        } else {
            createAlert("Error", "Incorrect inputs", errorMessage);
            errorMessage = "Check data types";
        }
    }

    @FXML
    void onUpdateBtnClick() {
        ClassChoice.setValue(selectedRow.getObjectType());
        disableElements(isUpdated);
        Factory factory = map.get(ClassChoice.getValue());
        if (!isUpdated) {
            inputs.get(0).setText(selectedRow.getObjectName());
            defaultInputStyles = inputs.get(0).getStyle();
            for (int i = 1; i < inputs.size(); i++) {
                inputs.get(i).setStyle("-fx-background-color: #00ffc2; -fx-border-width: 1; -fx-border-color: black; -fx-border-radius: 3");
            }
            InputsVBox.setSpacing(3);
            factory.putInfoToInputs(list.get(selectedRow.getId() - 1), inputs);
        } else {
            if (factory.checkInputs(inputs)) {
                list.set(selectedRow.getId() - 1, factory.getGadget(inputs));
                for (TextField textField : inputs) {
                    textField.setStyle(defaultInputStyles);
                    textField.setText("");
                }
                InputsVBox.setSpacing(5);
            } else {
                createAlert("Error", "Incorrect inputs", errorMessage);
                errorMessage = "Check data types";
                isUpdated = !isUpdated;
                disableElements(isUpdated);
            }
        }
        isUpdated = !isUpdated;
    }

    @FXML
    void onDeleteBtnClick() {
        if (selectedRow != null) {
            list.remove(selectedRow.getId() - 1);
            objects.remove(selectedRow.getId() - 1);
            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).setId(i + 1);
            }
            UpdateBtn.setDisable(true);
            DeleteBtn.setDisable(true);
        }
    }

    private void initLabelsAndInputs(int amount) {
        LabelsVBox.setSpacing(5);
        InputsVBox.setSpacing(5);
        labels.clear();
        LabelsVBox.getChildren().clear();
        inputs.clear();
        InputsVBox.getChildren().clear();
        for (int i = 0; i < amount + 1; i++) {
            Label label = new Label();
            label.setStyle("-fx-font-size: 16px");
            TextField input = new TextField();
            LabelsVBox.getChildren().add(label);
            InputsVBox.getChildren().add(input);
            labels.add(label);
            inputs.add(input);
        }
    }

    void initFactories() {
        map.put("Tablet", new TabletFactory());
        map.put("Laptop", new LaptopFactory());
        map.put("Smartphone", new SmartphoneFactory());
        map.put("PushButtonPhone", new PushButtonPhoneFactory());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFactories();
        ClassChoice.getItems().addAll(classes);
        ClassChoice.setOnAction(this::changeLabelsAndInputs);
        ClassChoice.setValue("Tablet");
        configureLabelsAndInputs(map.get(ClassChoice.getValue()));
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("ObjectType"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("ObjectName"));
        ObjectsTable.setItems(objects);
        UpdateBtn.setDisable(true);
        DeleteBtn.setDisable(true);

        list.add(new Tablet("samsung a23", 4, 2014, true, true, "Android", true));
        objects.add(new ObjectInfo(list.size(), "example", "Tablet"));
        list.add(new PushButtonPhone("name", 4, 1999, true, true, new Camera(4.5, 2.7), "swap", 1, 36));
        objects.add(new ObjectInfo(list.size(), "BabushkaPhone", "PushButtonPhone"));
        list.add(new Smartphone("iPhone 11", 6.8, 2019, true, true, new Camera(4.5, 2.7), "11", 1, "IOS", "4G"));
        objects.add(new ObjectInfo(list.size(), "iPhone11", "Smartphone"));
    }

    @FXML
    void onInstanceSelected(MouseEvent event) {
        if (event.getClickCount() == 1) {
            selectedRow = ObjectsTable.getSelectionModel().getSelectedItem();
            UpdateBtn.setDisable(false);
            DeleteBtn.setDisable(false);
        }
    }

    private void configureLabelsAndInputs(Factory factory) {
        initLabelsAndInputs(factory.getAmountOfFields());
        labels.get(0).setText("Name of instance");
        factory.renameLabels(labels);
        factory.showLabelsAndInputs(labels, inputs);
    }

    private void changeLabelsAndInputs(ActionEvent e) {
        configureLabelsAndInputs(map.get(ClassChoice.getValue()));
        AddBtn.setLayoutY(LabelsVBox.getLayoutY() + 30 * (inputs.size() + 1));
        UpdateBtn.setLayoutY(LabelsVBox.getLayoutY() + 30 * (inputs.size() + 1));
        DeleteBtn.setLayoutY(LabelsVBox.getLayoutY() + 30 * (inputs.size() + 1));
    }
}