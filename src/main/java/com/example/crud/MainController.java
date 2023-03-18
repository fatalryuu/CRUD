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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final ArrayList<Gadget> list = new ArrayList<>();
    private final String TABLET = "Tablet";
    private final String LAPTOP = "Laptop";
    private final String SMARTPHONE = "Smartphone";
    private final String PUSH_BUTTON_PHONE = "PushButtonPhone";
    private final HashMap<String, Factory> map = new HashMap<>();
    private final ObservableList<ObjectInfo> objects = FXCollections.observableArrayList();
    private ArrayList<Control> inputs;
    private ObjectInfo selectedRow;
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
    private VBox ContainerVBox;

    @FXML
    private HBox LabelsAndInputsHBox;

    @FXML
    private VBox InputsVBox;

    @FXML
    private VBox LabelsVBox;

    @FXML
    private HBox ButtonsVBox;

    void disableElements(boolean isUpdated) {
        AddBtn.setDisable(!isUpdated);
        DeleteBtn.setDisable(!isUpdated);
        ClassChoice.setDisable(!isUpdated);
        ObjectsTable.setDisable(!isUpdated);
        inputs.get(0).setDisable(!isUpdated);
    }

    ArrayList<Label> getLabels() {
        ArrayList<Label> labels = new ArrayList<>();
        for (int i = 0; i < LabelsVBox.getChildren().size(); i++) {
            labels.add((Label) LabelsVBox.getChildren().get(i));
        }
        return labels;
    }

    @FXML
    void onAddBtnClick() {
        Factory factory = map.get(ClassChoice.getValue());

        if (factory.checkInputs()) {
            list.add(factory.getGadget());
            objects.add(new ObjectInfo(list.size(), ((TextField) factory.getInputs().get(0)).getText(), ClassChoice.getValue()));
            GUI.clearInputs(factory.getInputs(), getLabels());
            InputsVBox.setSpacing(5);
        } else {
            InputsVBox.setSpacing(3);
        }
    }

    @FXML
    void onUpdateBtnClick() {
        ClassChoice.setValue(selectedRow.getObjectType());
        Factory factory = map.get(ClassChoice.getValue());
        inputs = factory.getInputs();
        ArrayList<Label> labels = getLabels();
        disableElements(isUpdated);
        if (!isUpdated) {
            TextField instanceInput = (TextField) inputs.get(0);
            instanceInput.setText(selectedRow.getObjectName());
            factory.putInfoToInputs(list.get(selectedRow.getId() - 1), getLabels());
        } else {
            if (factory.checkInputs()) {
                list.set(selectedRow.getId() - 1, factory.getGadget());
                GUI.clearInputs(inputs, labels);
                InputsVBox.setSpacing(5);
            } else {
                InputsVBox.setSpacing(3);
                isUpdated = !isUpdated;
                disableElements(isUpdated);
            }
        }
        isUpdated = !isUpdated;
    }

    @FXML
    void onDeleteBtnClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete object");
        alert.setHeaderText("Are you sure you want to delete the selected object?");
        alert.setContentText("This action cannot be returned.");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent())
            if (result.get() == yesButton) {
                list.remove(selectedRow.getId() - 1);
                objects.remove(selectedRow.getId() - 1);
                for (int i = 0; i < objects.size(); i++) {
                    objects.get(i).setId(i + 1);
                }
                UpdateBtn.setDisable(true);
                DeleteBtn.setDisable(true);
            } else {
                alert.close();
            }
    }

    private void initFactories() {
        map.put(TABLET, new TabletFactory());
        map.put(LAPTOP, new LaptopFactory());
        map.put(SMARTPHONE, new SmartphoneFactory());
        map.put(PUSH_BUTTON_PHONE, new PushButtonPhoneFactory());
    }

    private void initGUI() {
        ClassChoice.getItems().addAll(TABLET, LAPTOP, SMARTPHONE, PUSH_BUTTON_PHONE);
        ClassChoice.setOnAction(this::onClassChoice);
        ClassChoice.setValue(TABLET);

        map.get(ClassChoice.getValue()).configureLabelsAndInputs(LabelsAndInputsHBox);

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("ObjectType"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("ObjectName"));
        ObjectsTable.setItems(objects);

        UpdateBtn.setDisable(true);
        DeleteBtn.setDisable(true);
    }

    private void initTableWithObjects() {
        list.add(new Tablet("samsung a23", 4, 2014, true, true, "Android", true));
        objects.add(new ObjectInfo(list.size(), "example", "Tablet"));
        list.add(new PushButtonPhone("name", 4, 1999, true, true, new Camera(4.5, 2.7), "swap", 1, 36));
        objects.add(new ObjectInfo(list.size(), "BabushkaPhone", "PushButtonPhone"));
        list.add(new Smartphone("iPhone 11", 6.8, 2019, true, true, new Camera(4.5, 2.7), "11", 1, "IOS", "4G"));
        objects.add(new ObjectInfo(list.size(), "iPhone11", "Smartphone"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFactories();
        initGUI();
        initTableWithObjects();
    }

    @FXML
    void onInstanceSelected(MouseEvent event) {
        if (event.getClickCount() == 1) {
            selectedRow = ObjectsTable.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                UpdateBtn.setDisable(false);
                DeleteBtn.setDisable(false);
            }
        }
    }

    private void onClassChoice(ActionEvent e) {
        map.get(ClassChoice.getValue()).configureLabelsAndInputs(LabelsAndInputsHBox);
    }
}