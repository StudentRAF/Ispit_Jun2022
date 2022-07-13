package rs.raf.student.jun_2022.view.form;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import rs.raf.student.jun_2022.database.Database;
import rs.raf.student.jun_2022.model.Court;
import rs.raf.student.jun_2022.model.Match;
import rs.raf.student.jun_2022.model.Player;

public class FormMain extends TilePane {

    private final Button buttonFilter      = new Button("Filtriraj");
    private final Button buttonCreateMatch = new Button("Kreiraj mec");
    private final Button buttonAddPlayers  = new Button("Dodaj igrace");
    private final Button buttonSaveMatches = new Button("Sacuvaj meceve");

    private final CheckBox checkIsMan   = new CheckBox("M");
    private final CheckBox checkIsWoman = new CheckBox("Z");

    private final TextField textFilter = new TextField();

    private final ListView<Court>  listCourts  = new ListView<>();
    private final ListView<Player> listPlayers = new ListView<>();

    private final TableView<Match>           tableMatches          = new TableView<>();
    private final TableColumn<Match, String> columnMatchCourt      = new TableColumn<>("Teren");
    private final TableColumn<Match, String> columnMatchStartTime  = new TableColumn<>("Vreme");
    private final TableColumn<Match, String> columnMatchFirstTeam  = new TableColumn<>("Prvi tim");
    private final TableColumn<Match, String> columnMatchSecondTeam = new TableColumn<>("Drugi tim");

    public FormMain() {
        configure();
        initialize();
        associate();
    }

    private void configure() {
        setAlignment(Pos.CENTER);

        textFilter.setPrefWidth(200);
        listCourts.setPrefWidth(140);
        columnMatchCourt.setPrefWidth(80);
        columnMatchStartTime.setPrefWidth(80);
        columnMatchFirstTeam.setPrefWidth(300);
        columnMatchSecondTeam.setPrefWidth(300);
        tableMatches.setPrefHeight(240);
        tableMatches.setPrefWidth(760);

        checkIsMan.setSelected(true);
        checkIsWoman.setSelected(true);

        listPlayers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        columnMatchCourt.setCellValueFactory(new PropertyValueFactory<>("CourtStr"));
        columnMatchStartTime.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        columnMatchFirstTeam.setCellValueFactory(new PropertyValueFactory<>("FirstTeamStr"));
        columnMatchSecondTeam.setCellValueFactory(new PropertyValueFactory<>("SecondTeamStr"));
        tableMatches.getColumns().addAll(columnMatchCourt, columnMatchStartTime, columnMatchFirstTeam, columnMatchSecondTeam);

        listCourts.setItems(FXCollections.observableArrayList(Database.Courts.get()));
        listPlayers.setItems(FXCollections.observableArrayList(Database.Players.get()));
        tableMatches.setItems(FXCollections.observableArrayList(Database.Matches.get()));

    }

    private void initialize() {
        BorderPane main = new BorderPane();
        main.setPadding(new Insets(20));

        VBox left = new VBox();
        left.setAlignment(Pos.CENTER);
        left.setSpacing(10);

        HBox leftFirstRow = new HBox();
        leftFirstRow.setAlignment(Pos.CENTER_LEFT);
        leftFirstRow.setSpacing(10);
        leftFirstRow.getChildren().addAll(textFilter, buttonFilter, checkIsMan, checkIsWoman);

        left.getChildren().addAll(leftFirstRow, listPlayers);

        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        center.setPadding(new Insets(30));
        center.setSpacing(40);
        center.getChildren().addAll(buttonCreateMatch, buttonAddPlayers);

        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setPadding(new Insets(10, 0, 0, 0));
        bottom.setSpacing(10);
        bottom.getChildren().addAll(tableMatches, buttonSaveMatches);

        main.setLeft(left);
        main.setCenter(center);
        main.setRight(listCourts);
        main.setBottom(bottom);

        getChildren().add(main);
    }

    private void associate() {
        buttonFilter.setOnAction(event -> listPlayers.setItems(FXCollections.observableArrayList(Database.Players.filter(textFilter.getText(), checkIsMan.isSelected(),
                                                                                                                         checkIsWoman.isSelected()))));

        buttonCreateMatch.setOnAction(event -> {
            try {
                Database.Matches.createMatch(listCourts.getSelectionModel().getSelectedItem());
                listCourts.setItems(FXCollections.observableArrayList(Database.Courts.get()));
                tableMatches.setItems(FXCollections.observableArrayList(Database.Matches.get()));
                tableMatches.refresh();
            }
            catch (IllegalArgumentException exception) {
                throwMessage(exception.getMessage());
            }
        });

        buttonAddPlayers.setOnAction(event -> {
            try {
                Database.Matches.addPlayers(listPlayers.getSelectionModel().getSelectedItems());
                tableMatches.setItems(FXCollections.observableArrayList(Database.Matches.get()));
                tableMatches.refresh();
            }
            catch (IllegalArgumentException exception) {
                throwMessage(exception.getMessage());
            }
        });

        buttonSaveMatches.setOnAction(event -> {
            try {
                Database.Matches.save();
            }
            catch (IllegalArgumentException exception) {
                throwMessage(exception.getMessage());
            }
        });
    }

    private void throwMessage(String message) {
        new Alert(Alert.AlertType.NONE, message, ButtonType.OK).show();
    }
}
