package com.example.crud;

import com.example.crud.Utils.CustomFileChooser;
import com.example.crud.Utils.GUI;
import com.example.crud.Utils.ObjectInfo;
import com.example.crud.factories.hierarchy.*;
import com.example.crud.factories.serializers.BinarySerializerFactory;
import com.example.crud.factories.serializers.JSONSerializerFactory;
import com.example.crud.factories.serializers.SerializerFactory;
import com.example.crud.factories.serializers.TextSerializerFactory;
import com.example.crud.hierarchy.*;
import com.example.crud.plugins.Plugin;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.*;

public class MainController implements Initializable {
    public static final String TABLET = "Tablet";
    public static final String LAPTOP = "Laptop";
    public static final String SMARTPHONE = "Smartphone";
    public static final String PUSH_BUTTON_PHONE = "PushButtonPhone";
    public static final String NONE = "None";
    private final HashMap<String, Factory> mapOfFactories = new HashMap<>();
    private final HashMap<String, SerializerFactory> mapOfSerializers = new HashMap<>();
    private final HashMap<String, Plugin> mapOfPlugins = new HashMap<>();
    private final ObservableList<ObjectInfo> objects = FXCollections.observableArrayList();
    private ArrayList<Gadget> gadgets = new ArrayList<>();
    private ArrayList<Control> inputs;
    private ObjectInfo selectedRow;
    private boolean isUpdated = false;
    private File selectedFile;

    @FXML
    private ChoiceBox<String> ClassChoice;

    @FXML
    private ChoiceBox<String> PluginChoice;

    @FXML
    private Button ConfirmPluginBtn;

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
    private HBox LabelsAndInputsHBox;

    @FXML
    private VBox ContainerVBox;

    @FXML
    private VBox InputsVBox;

    @FXML
    private VBox LabelsVBox;

    @FXML
    private Label PluginLabel;

    @FXML
    private MenuBar FileMenuBar;

    public static void createAlert(final Alert.AlertType type, final String title, final String header, final String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

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
        Factory factory = mapOfFactories.get(ClassChoice.getValue());
        if (factory.checkInputs()) {
            gadgets.add(factory.getGadget());
            String instanceName = ((TextField) factory.getInputs().get(0)).getText();
            objects.add(new ObjectInfo(gadgets.size(), instanceName.equals("") ? factory.getGadget().getName() : instanceName, ClassChoice.getValue()));
            GUI.clearInputs(factory.getInputs(), getLabels());
            InputsVBox.setSpacing(5);
        } else {
            InputsVBox.setSpacing(3);
        }
    }

    @FXML
    void onUpdateBtnClick() {
        if (!isUpdated) {
            ClassChoice.setValue(selectedRow.getObjectType());
        }
        Factory factory = mapOfFactories.get(ClassChoice.getValue());
        inputs = factory.getInputs();
        ArrayList<Label> labels = getLabels();
        disableElements(isUpdated);
        if (!isUpdated) {
            TextField instanceInput = (TextField) inputs.get(0);
            instanceInput.setText(selectedRow.getObjectName());
            factory.putInfoToInputs(gadgets.get(selectedRow.getId() - 1), getLabels());
        } else {
            if (factory.checkInputs()) {
                gadgets.set(selectedRow.getId() - 1, factory.getGadget());
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
                gadgets.remove(selectedRow.getId() - 1);
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
        mapOfFactories.put(TABLET, new TabletFactory());
        mapOfFactories.put(LAPTOP, new LaptopFactory());
        mapOfFactories.put(SMARTPHONE, new SmartphoneFactory());
        mapOfFactories.put(PUSH_BUTTON_PHONE, new PushButtonPhoneFactory());
    }

    private void initSerializers() {
        mapOfSerializers.put("bin", new BinarySerializerFactory());
        mapOfSerializers.put("json", new JSONSerializerFactory());
        mapOfSerializers.put("txt", new TextSerializerFactory());
    }

    private void initGUI() {
        PluginLabel.setVisible(false);
        PluginChoice.setVisible(false);
        ConfirmPluginBtn.setVisible(false);
        ClassChoice.getItems().addAll(TABLET, LAPTOP, SMARTPHONE, PUSH_BUTTON_PHONE);
        ClassChoice.setOnAction(this::onClassChoice);
        ClassChoice.setValue(TABLET);
        PluginChoice.getItems().add(NONE);
        for (String key : mapOfPlugins.keySet()) {
            PluginChoice.getItems().add(key);
        }
        PluginChoice.setValue(NONE);

        mapOfFactories.get(ClassChoice.getValue()).configureLabelsAndInputs(LabelsAndInputsHBox);

        IdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("ObjectType"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("ObjectName"));
        ObjectsTable.setItems(objects);

        UpdateBtn.setDisable(true);
        DeleteBtn.setDisable(true);
    }

    private void initTableWithObjects() {
        gadgets.add(new Tablet("samsung a23", 4, 2014, true, true, "Android", true));
        objects.add(new ObjectInfo(gadgets.size(), "Samsung A23", "Tablet"));
        gadgets.add(new PushButtonPhone("TeXet TM-B226", 4, 1999, true, true, new Camera(4.5, 2.7), "swap", 1, 36));
        objects.add(new ObjectInfo(gadgets.size(), "BabushkaPhone", "PushButtonPhone"));
        gadgets.add(new Smartphone("iPhone 11", 6.8, 2019, true, true, new Camera(4.5, 2.7), "11", 1, "IOS", "4G"));
        objects.add(new ObjectInfo(gadgets.size(), "iPhone11", "Smartphone"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initFactories();
        initSerializers();
        loadPlugins();
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
        mapOfFactories.get(ClassChoice.getValue()).configureLabelsAndInputs(LabelsAndInputsHBox);
    }

    //3 lab
    @FXML
    void onMenuClick() {
        selectedRow = null;
        UpdateBtn.setDisable(true);
        DeleteBtn.setDisable(true);
    }

    private String getExtension(String path) {
        String[] parts = path.split("\\.");
        return parts[parts.length - 1];
    }

    private String getObjectType(Gadget gadget) {
        String fullClassName = gadget.getClass().getName();
        String[] classNameParts = fullClassName.split("\\.");
        return classNameParts[classNameParts.length - 1];
    }

    private void deserialize() {
        selectedFile = CustomFileChooser.getOpenFile(mapOfPlugins);
        if (selectedFile != null) {
            String ext = getExtension(selectedFile.getPath());
            boolean isEncoded = checkIfIsEncoded(ext);
            if (isEncoded) {
                decodeAndDeserialize();
            } else {
                gadgets = mapOfSerializers.get(ext).getSerializer().deserialize(selectedFile.getPath());
            }

            objects.clear();
            for (int i = 0; i < gadgets.size(); i++) {
                objects.add(new ObjectInfo(i + 1, gadgets.get(i).getName(), getObjectType(gadgets.get(i))));
            }
        }
    }

    private void serialize() {
        selectedFile = CustomFileChooser.getSaveFile();
        if (selectedFile != null) {
            String ext = getExtension(selectedFile.getPath());
            mapOfSerializers.get(ext).getSerializer().serialize(gadgets, selectedFile.getPath());
        }
    }

    //4 lab
    @FXML
    void onFileOpenClick() {
        loadPlugins();
        deserialize();
    }

    @FXML
    void onFileSaveClick() {
        loadPlugins();
        changeGUIForSaving(true);
    }

    boolean checkIfIsEncoded(String ext) {
        for (String key : mapOfPlugins.keySet()) {
            if (mapOfPlugins.get(key).getExtension().equals(ext)) {
                return true;
            }
        }
        return false;
    }

    void decodeAndDeserialize() {
        byte[] byteArray = new byte[(int) selectedFile.length()];
        try {
            FileInputStream fr = new FileInputStream(selectedFile.getPath());
            fr.read(byteArray, 0, byteArray.length);
            fr.close();
            String ext = getExtension(selectedFile.getPath());
            String pluginName = "";
            for (String key : mapOfPlugins.keySet()) {
                if (mapOfPlugins.get(key).getExtension().equals(ext)) {
                    pluginName = mapOfPlugins.get(key).getName();
                    break;
                }
            }
            ext = getExtension(selectedFile.getPath().substring(0, selectedFile.getPath().lastIndexOf(".")));
            byteArray = mapOfPlugins.get(pluginName).decode(byteArray);
            File tmpFile = new File(selectedFile.getName() + "." + ext);
            FileOutputStream fw = new FileOutputStream(tmpFile);
            fw.write(byteArray);
            fw.close();
            gadgets = mapOfSerializers.get(ext).getSerializer().deserialize(tmpFile.getPath());
            tmpFile.delete();
        } catch (Exception e) {
            createAlert(Alert.AlertType.ERROR, "File Error", "Error while deserialization", "");
        }
    }

    public void loadPlugins() {
        final String PATH = "src\\main\\java\\com\\example\\crud\\plugins\\impl";
        final String PREFIX = "com.example.crud.plugins.impl.";
        mapOfPlugins.clear();
        try {
            File folder = new File(PATH);
            File[] files = folder.listFiles();
            for (File file : Objects.requireNonNull(files)) {
                String className = file.getName().substring(0, file.getName().lastIndexOf("."));
                Class<?> plugin = Class.forName(PREFIX + className);
                Plugin thisPlugin = (Plugin) plugin.getConstructor().newInstance();
                mapOfPlugins.put(thisPlugin.getName(), thisPlugin);
            }

        } catch (Exception ignored) {

        }
    }

    private void changeGUIForSaving(boolean isBefore) {
        PluginLabel.setVisible(isBefore);
        PluginChoice.setVisible(isBefore);
        ConfirmPluginBtn.setVisible(isBefore);
        FileMenuBar.setVisible(!isBefore);
        ClassChoice.setVisible(!isBefore);
        ContainerVBox.setVisible(!isBefore);
        AddBtn.setVisible(!isBefore);
        UpdateBtn.setVisible(!isBefore);
        DeleteBtn.setVisible(!isBefore);
    }

    private void encode() {
        if (selectedFile != null && !PluginChoice.getValue().equals(NONE)) {
            String newPath = selectedFile.getPath() + "." + mapOfPlugins.get(PluginChoice.getValue()).getExtension();
            long fileLength = selectedFile.length();
            selectedFile.renameTo(new File(newPath));
            byte[] byteArray = new byte[(int) fileLength];
            try {
                FileInputStream fr = new FileInputStream(newPath);
                fr.read(byteArray, 0, byteArray.length);
                fr.close();
                byteArray = mapOfPlugins.get(PluginChoice.getValue()).encode(byteArray);
                FileOutputStream fw = new FileOutputStream(newPath);
                fw.write(byteArray);
                fw.close();
            } catch (Exception e) {
                createAlert(Alert.AlertType.ERROR, "File Error", "Error while serialization", "");
            }
        }
    }

    @FXML
    void onConfirmPluginClick(ActionEvent event) {
        serialize();
        encode();
        changeGUIForSaving(false);
    }
}