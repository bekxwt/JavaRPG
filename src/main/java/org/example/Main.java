package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.example.abilities.Ability;
import org.example.abilities.assassin.*;
import org.example.abilities.bard.*;
import org.example.abilities.mage.*;
import org.example.abilities.sniper.*;
import org.example.abilities.tank.*;
import org.example.models.Character;
import org.example.models.CharacterFactory;
import org.example.models.Stats;
import org.example.battle.BattleManager;
import org.example.utils.SystemOutRedirector;

import java.util.*;

public class Main extends Application {
    private static Stage primaryStage;
    // Team 1 Components
    private TextField nameField1, nameField2;
    private Spinner<Integer> strengthField1, agilityField1, tacticsField1, intelligenceField1,
            enduranceField1, defenseField1, initiationField1, luckField1;
    private Spinner<Integer> strengthField2, agilityField2, tacticsField2, intelligenceField2,
            enduranceField2, defenseField2, initiationField2, luckField2;
    private ComboBox<String> classComboBox1, classComboBox2, abilityComboBox1, abilityComboBox2;

    // Team 2 Components
    private TextField nameField3, nameField4;
    private Spinner<Integer> strengthField3, agilityField3, tacticsField3, intelligenceField3,
            enduranceField3, defenseField3, initiationField3, luckField3;
    private Spinner<Integer> strengthField4, agilityField4, tacticsField4, intelligenceField4,
            enduranceField4, defenseField4, initiationField4, luckField4;
    private ComboBox<String> classComboBox3, classComboBox4, abilityComboBox3, abilityComboBox4;

    private BattleArena battleArena;
    private TextArea battleLogArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: #ffffff;");

        battleArena = new BattleArena();
        battleLogArea = new TextArea();

        ScrollPane configScroll = createConfigPanel();
        configScroll.setMinWidth(350);
        configScroll.setPrefWidth(350);

        Region battleLog = createBattleLogPanel();

        mainLayout.setLeft(configScroll);
        mainLayout.setCenter(battleArena);
        mainLayout.setRight(battleLog);

        BorderPane.setAlignment(battleLog, Pos.TOP_LEFT);
        BorderPane.setMargin(battleLog, Insets.EMPTY);

        Scene scene = new Scene(mainLayout, screenBounds.getWidth(), screenBounds.getHeight());
        scene.getStylesheets().add(Objects.requireNonNull(
                getClass().getResource("/styles.css")).toExternalForm());

        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(javafx.scene.input.KeyCombination.NO_MATCH);
        primaryStage.setTitle("RPG Battle Manager");
        primaryStage.setScene(scene);
        primaryStage.show();

        SystemOutRedirector redirector = new SystemOutRedirector(battleLogArea);
        System.setOut(redirector);
    }

    private Region createBattleLogPanel() {
        VBox logContainer = new VBox();
        logContainer.setStyle("-fx-background-color: #f5f5f5;");
        logContainer.setMaxHeight(Double.MAX_VALUE);

        Label logTitle = new Label("Battle Log");
        logTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        logTitle.setPadding(new Insets(10, 15, 10, 15));

        battleLogArea.setEditable(false);
        battleLogArea.setWrapText(true);
        battleLogArea.setStyle("-fx-control-inner-background: #ffffff;");

        ScrollPane scrollPane = new ScrollPane(battleLogArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.setPrefViewportWidth(400);
        scrollPane.setMinWidth(400);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        logContainer.getChildren().addAll(logTitle, scrollPane);
        VBox.setVgrow(logContainer, Priority.ALWAYS);

        VBox outerContainer = new VBox(logContainer);
        outerContainer.setMinWidth(400);
        outerContainer.setPrefWidth(400);
        outerContainer.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(outerContainer, Priority.ALWAYS);

        return outerContainer;
    }

    private ScrollPane createConfigPanel() {
        HBox teamsContainer = new HBox(15);
        teamsContainer.getChildren().addAll(
                createTeamPanel("Team 1", 1, 2),
                createTeamPanel("Team 2", 3, 4)
        );

        VBox configContent = new VBox(15);
        configContent.getChildren().addAll(teamsContainer, createStartButton());
        VBox.setVgrow(teamsContainer, Priority.ALWAYS);

        ScrollPane configScroll = new ScrollPane(configContent);
        configScroll.setStyle("-fx-background: #f5f5f5;");
        configScroll.setFitToWidth(true);
        configScroll.setFitToHeight(true);
        return configScroll;
    }

    private HBox createStartButton() {
        Button startButton = new Button("Start Battle");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        startButton.setOnAction(e -> startBattle());

        HBox buttonBox = new HBox(startButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));
        return buttonBox;
    }

    private VBox createTeamPanel(String teamName, int... characters) {
        VBox teamBox = new VBox(8);
        teamBox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-width: 1;");
        teamBox.setPadding(new Insets(8));
        VBox.setVgrow(teamBox, Priority.ALWAYS);

        Label lblTeam = new Label(teamName);
        lblTeam.setStyle("-fx-text-fill: #333333; -fx-font-size: 16px; -fx-font-weight: bold;");

        VBox charactersBox = new VBox(10);
        for (int charNum : characters) {
            charactersBox.getChildren().add(createCharacterPanel(charNum));
        }
        VBox.setVgrow(charactersBox, Priority.ALWAYS);

        teamBox.getChildren().addAll(lblTeam, charactersBox);
        return teamBox;
    }

    private VBox createCharacterPanel(int charNumber) {
        VBox panel = new VBox(5);
        panel.setStyle("-fx-background-color: #f8f8f8; -fx-border-color: #eeeeee; -fx-border-width: 1;");
        panel.setPadding(new Insets(5));

        Label lblChar = new Label("CHARACTER #" + charNumber);
        lblChar.setStyle("-fx-text-fill: #333333; -fx-font-weight: bold;");

        VBox classSection = new VBox(5);
        ComboBox<String> classCB = createClassComboBox();
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #333333;");
        classSection.getChildren().addAll(
                new Label("Class:"),
                classCB,
                new Label("Name:"),
                nameField
        );

        VBox statsBox = new VBox(5);
        String[] statLabels = {"Strength", "Agility", "Tactics", "Intelligence",
                "Endurance", "Defense", "Initiation", "Luck"};
        Spinner<Integer>[] spinners = new Spinner[8];
        for (int i = 0; i < statLabels.length; i++) {
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER_LEFT);

            Label label = new Label(statLabels[i]);
            label.setStyle("-fx-text-fill: #333333;");
            label.setMinWidth(70);

            HBox.setHgrow(label, Priority.ALWAYS);

            spinners[i] = createStatField(0);

            StackPane spinnerContainer = new StackPane(spinners[i]);
            spinnerContainer.setAlignment(Pos.CENTER_RIGHT);
            spinnerContainer.setPrefWidth(70);
            spinnerContainer.setMaxWidth(70);

            row.getChildren().addAll(label, spinnerContainer);
            statsBox.getChildren().add(row);
        }

        ComboBox<String> abilityCB = createAbilityComboBox("Tank");
        classCB.valueProperty().addListener((obs, oldVal, newVal) ->
                updateAbilityOptions(abilityCB, newVal));

        storeComponentReferences(charNumber, classCB, nameField, spinners, abilityCB);

        panel.getChildren().addAll(
                lblChar,
                classSection,
                new Separator(),
                new Label("Stats:"),
                statsBox,
                new Separator(),
                new Label("Ability:"),
                abilityCB
        );
        return panel;
    }

    private void storeComponentReferences(int charNumber, ComboBox<String> classCB, TextField nameField,
                                          Spinner<Integer>[] spinners, ComboBox<String> abilityCB) {
        switch (charNumber) {
            case 1:
                classComboBox1 = classCB;
                nameField1 = nameField;
                strengthField1 = spinners[0];
                agilityField1 = spinners[1];
                tacticsField1 = spinners[2];
                intelligenceField1 = spinners[3];
                enduranceField1 = spinners[4];
                defenseField1 = spinners[5];
                initiationField1 = spinners[6];
                luckField1 = spinners[7];
                abilityComboBox1 = abilityCB;
                break;
            case 2:
                classComboBox2 = classCB;
                nameField2 = nameField;
                strengthField2 = spinners[0];
                agilityField2 = spinners[1];
                tacticsField2 = spinners[2];
                intelligenceField2 = spinners[3];
                enduranceField2 = spinners[4];
                defenseField2 = spinners[5];
                initiationField2 = spinners[6];
                luckField2 = spinners[7];
                abilityComboBox2 = abilityCB;
                break;
            case 3:
                classComboBox3 = classCB;
                nameField3 = nameField;
                strengthField3 = spinners[0];
                agilityField3 = spinners[1];
                tacticsField3 = spinners[2];
                intelligenceField3 = spinners[3];
                enduranceField3 = spinners[4];
                defenseField3 = spinners[5];
                initiationField3 = spinners[6];
                luckField3 = spinners[7];
                abilityComboBox3 = abilityCB;
                break;
            case 4:
                classComboBox4 = classCB;
                nameField4 = nameField;
                strengthField4 = spinners[0];
                agilityField4 = spinners[1];
                tacticsField4 = spinners[2];
                intelligenceField4 = spinners[3];
                enduranceField4 = spinners[4];
                defenseField4 = spinners[5];
                initiationField4 = spinners[6];
                luckField4 = spinners[7];
                abilityComboBox4 = abilityCB;
                break;
        }
    }

    private ComboBox<String> createClassComboBox() {
        ComboBox<String> cb = new ComboBox<>();
        cb.getItems().addAll("Mage", "Tank", "Sniper", "Bard", "Assassin");
        cb.setValue("Tank");
        cb.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #333333;");
        cb.setPrefWidth(160);
        return cb;
    }

    private Spinner<Integer> createStatField(int initial) {
        Spinner<Integer> spinner = new Spinner<>(0, 100, initial);
        spinner.setEditable(true);
        spinner.getStyleClass().add("stat-spinner");

        // Fixed width and right alignment for the spinner container
        spinner.setPrefWidth(30);
        spinner.setMaxWidth(30);

        // Left-align text inside the input field
        TextField editor = spinner.getEditor();
        editor.setStyle("-fx-alignment: center-left; -fx-padding: 0 5 0 5;");
        editor.setPrefWidth(150);
        editor.setMaxWidth(150);

        return spinner;
    }

    private ComboBox<String> createAbilityComboBox(String className) {
        ComboBox<String> cb = new ComboBox<>();
        updateAbilityOptions(cb, className);
        cb.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #333333;");
        cb.setPrefWidth(160);
        return cb;
    }

    private void updateAbilityOptions(ComboBox<String> cb, String className) {
        cb.getItems().clear();
        switch (className) {
            case "Mage":
                cb.getItems().addAll("Fireball", "IceArrow");
                break;
            case "Tank":
                cb.getItems().addAll("CounterAttack", "IronDefense", "Taunt");
                break;
            case "Sniper":
                cb.getItems().addAll("CriticalShot", "ExecuteShot", "StunBullet");
                break;
            case "Bard":
                cb.getItems().addAll("BattleHymn", "HealingMelody", "WeakeningSymphony");
                break;
            case "Assassin":
                cb.getItems().addAll("DisableStrike", "PoisonBlades", "ShadowDodge");
                break;
        }
        cb.getSelectionModel().selectFirst();
    }

    private void startBattle() {
        battleLogArea.clear();
        Character[] team1 = {
                buildCharacter(classComboBox1.getValue(), nameField1.getText(),
                        strengthField1.getValue(), agilityField1.getValue(),
                        tacticsField1.getValue(), intelligenceField1.getValue(),
                        enduranceField1.getValue(), defenseField1.getValue(),
                        initiationField1.getValue(), luckField1.getValue(),
                        abilityComboBox1.getValue()),

                buildCharacter(classComboBox2.getValue(), nameField2.getText(),
                        strengthField2.getValue(), agilityField2.getValue(),
                        tacticsField2.getValue(), intelligenceField2.getValue(),
                        enduranceField2.getValue(), defenseField2.getValue(),
                        initiationField2.getValue(), luckField2.getValue(),
                        abilityComboBox2.getValue())
        };

        Character[] team2 = {
                buildCharacter(classComboBox3.getValue(), nameField3.getText(),
                        strengthField3.getValue(), agilityField3.getValue(),
                        tacticsField3.getValue(), intelligenceField3.getValue(),
                        enduranceField3.getValue(), defenseField3.getValue(),
                        initiationField3.getValue(), luckField3.getValue(),
                        abilityComboBox3.getValue()),

                buildCharacter(classComboBox4.getValue(), nameField4.getText(),
                        strengthField4.getValue(), agilityField4.getValue(),
                        tacticsField4.getValue(), intelligenceField4.getValue(),
                        enduranceField4.getValue(), defenseField4.getValue(),
                        initiationField4.getValue(), luckField4.getValue(),
                        abilityComboBox4.getValue())
        };

        BattleManager battleManager = new BattleManager(
                Arrays.asList(team1),
                Arrays.asList(team2)
        );

        battleManager.setWinnerCallback(winner ->
                Platform.runLater(() -> showWinnerPopup(winner))
        );

        battleArena.initializeBattle(Arrays.asList(team1), Arrays.asList(team2));

        battleManager.setBattleLogger(text -> {
            Platform.runLater(() -> {
                if (battleLogArea.getLength() > 10000) {
                    battleLogArea.deleteText(0, 1000);
                }
                battleLogArea.appendText(text + "\n");
            });
        });

        Thread battleThread = new Thread(battleManager::startBattle);
        battleThread.setDaemon(true);
        battleThread.start();
    }

    private Character buildCharacter(String className, String name, int str, int agi,
                                     int tac, int intel, int end, int def, int init,
                                     int luck, String abilityName) {
        Stats stats = new Stats(str, agi, tac, intel, end, def, init, luck);
        Ability ability = createAbility(className, abilityName);
        return CharacterFactory.createCharacter(className, name, stats, ability);
    }

    private Ability createAbility(String className, String abilityName) {
        switch (className) {
            case "Mage":
                return switch (abilityName) {
                    case "Fireball" -> new Fireball();
                    case "IceArrow" -> new IceArrow();
                    default -> throw new IllegalArgumentException("Invalid Mage ability");
                };
            case "Tank":
                return switch (abilityName) {
                    case "CounterAttack" -> new CounterAttack();
                    case "IronDefense" -> new IronDefense();
                    case "Taunt" -> new Taunt();
                    default -> throw new IllegalArgumentException("Invalid Tank ability");
                };
            case "Sniper":
                return switch (abilityName) {
                    case "CriticalShot" -> new CriticalShot();
                    case "ExecuteShot" -> new ExecuteShot();
                    case "StunBullet" -> new StunBullet();
                    default -> throw new IllegalArgumentException("Invalid Sniper ability");
                };
            case "Bard":
                return switch (abilityName) {
                    case "BattleHymn" -> new BattleHymn();
                    case "HealingMelody" -> new HealingMelody();
                    case "WeakeningSymphony" -> new WeakeningSymphony();
                    default -> throw new IllegalArgumentException("Invalid Bard ability");
                };
            case "Assassin":
                return switch (abilityName) {
                    case "DisableStrike" -> new DisableStrike();
                    case "PoisonBlades" -> new PoisonBlades();
                    case "ShadowDodge" -> new ShadowDodge();
                    default -> throw new IllegalArgumentException("Invalid Assassin ability");
                };
            default:
                throw new IllegalArgumentException("Unknown class: " + className);
        }
    }

    class BattleArena extends BorderPane {
        private final Map<Character, CharacterWidget> characterWidgets = new HashMap<>();

        public BattleArena() {
            createLayout();
            setStyle("-fx-background-color: #ffffff;");
        }

        private void createLayout() {
            HBox mainContainer = new HBox(20);
            mainContainer.setAlignment(Pos.CENTER);
            HBox.setHgrow(mainContainer, Priority.ALWAYS);

            // Team 1
            Label team1Label = new Label("Team 1");
            team1Label.getStyleClass().add("team-label");
            VBox team1Container = new VBox(20);
            team1Container.setMaxHeight(Double.MAX_VALUE);
            team1Container.setAlignment(Pos.TOP_CENTER);

            VBox team1Wrapper = new VBox(10, team1Label, team1Container);
            team1Wrapper.setAlignment(Pos.CENTER);

            // Team 2
            Label team2Label = new Label("Team 2");
            team2Label.getStyleClass().add("team-label");
            VBox team2Container = new VBox(20);
            team2Container.setMaxHeight(Double.MAX_VALUE);
            team2Container.setAlignment(Pos.TOP_CENTER);

            VBox team2Wrapper = new VBox(10, team2Label, team2Container);
            team2Wrapper.setAlignment(Pos.CENTER);

            // Separator
            Region separator = new Region();
            separator.setMinWidth(2);
            separator.setStyle("-fx-background-color: #cccccc;");
            HBox.setHgrow(separator, Priority.NEVER);

            mainContainer.getChildren().addAll(team1Wrapper, separator, team2Wrapper);
            setCenter(mainContainer);
        }

        public void initializeBattle(List<Character> team1, List<Character> team2) {
            HBox mainContainer = (HBox) getCenter();
            VBox team1Wrapper = (VBox) mainContainer.getChildren().get(0);
            VBox team2Wrapper = (VBox) mainContainer.getChildren().get(2);

            VBox team1Container = (VBox) team1Wrapper.getChildren().get(1);
            VBox team2Container = (VBox) team2Wrapper.getChildren().get(1);

            team1Container.getChildren().clear();
            team2Container.getChildren().clear();
            characterWidgets.clear();

            for (Character character : team1) {
                CharacterWidget widget = new CharacterWidget(character, false);
                characterWidgets.put(character, widget);
                team1Container.getChildren().add(widget);
                VBox.setMargin(widget, new Insets(5));
            }

            for (Character character : team2) {
                CharacterWidget widget = new CharacterWidget(character, true);
                characterWidgets.put(character, widget);
                team2Container.getChildren().add(widget);
                VBox.setMargin(widget, new Insets(5));
            }
        }

        class CharacterWidget extends VBox {
            private final ProgressBar healthBar;
            private final PseudoClass low = PseudoClass.getPseudoClass("low");
            private final PseudoClass medium = PseudoClass.getPseudoClass("medium");
            private final PseudoClass dead = PseudoClass.getPseudoClass("dead");

            public CharacterWidget(Character character, boolean mirror) {
                setSpacing(5);
                setAlignment(Pos.CENTER);
                getStyleClass().add("character-widget");

                // Load stylesheet
                this.getStylesheets().add(Objects.requireNonNull(
                        getClass().getResource("/styles.css")).toExternalForm());

                // Character Image
                ImageView imageView = new ImageView(loadClassImage(character.getClass().getSimpleName().toLowerCase()));
                imageView.setFitWidth(200);
                imageView.setFitHeight(250);
                if (mirror) imageView.setScaleX(-1);

                // Name Label
                Label nameLabel = new Label(character.getName());
                nameLabel.getStyleClass().add("character-name");

                // Health Bar
                healthBar = new ProgressBar();
                healthBar.getStyleClass().add("health-bar");
                healthBar.progressProperty().bind(Bindings.createDoubleBinding(
                        () -> (double) character.getHealth() / character.getMaxHealth(),
                        character.healthProperty()
                ));

                // Health Label
                Label healthLabel = new Label();
                healthLabel.textProperty().bind(Bindings.createStringBinding(
                        () -> String.format("HP: %d/%d", character.getHealth(), character.getMaxHealth()),
                        character.healthProperty()
                ));
                healthLabel.getStyleClass().add("health-label");

                // Health state listener
                character.healthProperty().addListener((obs, oldVal, newVal) -> {
                    // Clear all states first
                    healthBar.pseudoClassStateChanged(low, false);
                    healthBar.pseudoClassStateChanged(medium, false);
                    healthBar.pseudoClassStateChanged(dead, false);

                    if (newVal.intValue() <= 0) {
                        healthBar.pseudoClassStateChanged(dead, true);
                    } else {
                        double percentage = (double) newVal / character.getMaxHealth();
                        if (percentage < 0.2) {
                            healthBar.pseudoClassStateChanged(low, true);
                        } else if (percentage < 0.5) {
                            healthBar.pseudoClassStateChanged(medium, true);
                        }
                    }
                });

                // Initial setup
                if (character.getHealth() <= 0) {
                    healthBar.pseudoClassStateChanged(dead, true);
                }

                getChildren().addAll(imageView, nameLabel, healthBar, healthLabel);
                VBox.setMargin(healthBar, new Insets(10, 20, 5, 20));
            }

            private Image loadClassImage(String className) {
                try {
                    return new Image(Objects.requireNonNull(
                            getClass().getResourceAsStream("/assets/" + className + ".png")));
                } catch (Exception e) {
                    return createFallbackImage();
                }
            }

            private Image createFallbackImage() {
                return new Image(Objects.requireNonNull(
                        getClass().getResourceAsStream("/assets/placeholder.png")));
            }
        }
    }

    private void showWinnerPopup(String winnerText) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(primaryStage);
        popup.setTitle("Battle Result");
        popup.setMinWidth(300);
        popup.setMinHeight(150);

        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(20));

        Label winnerLabel = new Label(winnerText);
        winnerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button closeButton = new Button("OK");
        closeButton.setStyle("-fx-font-size: 14px; -fx-padding: 8 16;");
        closeButton.setOnAction(e -> popup.close());

        content.getChildren().addAll(winnerLabel, closeButton);

        Scene scene = new Scene(content);
        popup.setScene(scene);
        popup.showAndWait();
    }
}