
GUI Application Development with JavaFX
--------------------------------------------

# Scope and Prerequisites
The materials presented in this tutorial are from the book ["Introduction to Programming Using Java (Version 9.0, JavaFX Edition)"](https://math.hws.edu/javanotes/index.html) by David J. Eck. 

- **Chapters in scope**: 6, 13

This tutorial assumes Java JDK 17 or above.

In order to compile and run JavaFX applications, follow the instructions in [Section 2.6](https://math.hws.edu/javanotes/c2/s6.html) of the book to set up JavaFX for use in your IDE. In particular, if you compile-run your application from the command line then read [Section 2.6.7](https://math.hws.edu/javanotes/c2/s6.html#basics.6.7).

# 6.1: A Basic JavaFX Application

### Slide 1: Overview
- Introduction to JavaFX, GUI elements, and basic application setup.

---

### Slide 2: Introduction to JavaFX GUI Applications
  - GUI provides interactive elements like windows, buttons, and text fields.
  - JavaFX applications allow rich, responsive interfaces.
  - Displayed example: HelloWorldFX application with "Say Hello," "Say Goodbye," and "Quit" buttons.

---

### Slide 3: Basic Structure of a JavaFX Application
  - JavaFX applications extend the `Application` class.
  - **`start(Stage stage)`** method sets up the GUI.
  - **Code Example**: `HelloWorldFX.java`
    ```java
    public class HelloWorldFX extends Application {
        public static void main(String[] args) { launch(); }
        public void start(Stage stage) { /* GUI setup */ }
    }
    ```

  ![HelloWorldFX](https://math.hws.edu/javanotes/c6/HelloWorldFX-screenshot.png)

---

### Slide 4: The Main Components - Stage, Scene, and SceneGraph
  - `Stage`: Represents the window.
  - `Scene`: Holds the GUI components in a tree-like structure called the SceneGraph.
  - **Code Example**:
    ```java
    stage.setTitle("JavaFX Test");
    stage.setScene(scene);
    stage.show();
    ```
  - **Component containment**: 
  
  ![alt text](https://math.hws.edu/javanotes/c6/scene-graph.png)

---

### Slide 5: Components (Nodes) and Layout
  - **GUI components** (nodes) are organized in containers.
    - Component --subtype--> { Node, Parent }
    - Parent (containers) --subtype--> Node
  - **Layout**: an arrangement of the children nodes in a parent
  - Types of nodes: `Button`, `Label`, and layout containers like `HBox`, `VBox`.
  - **Example**:
    ```java
    Label message = new Label("Hello!");
    HBox buttonBar = new HBox(helloButton, goodbyeButton, quitButton);
    ```

---

### Slide 6: Event Handling in JavaFX
  - GUI interactions are handled via events.
  - Event handler example using lambda expressions.
  - **Example Code**:
    ```java
    helloButton.setOnAction( evt -> message.setText("Hello World!") );
    quitButton.setOnAction( evt -> Platform.exit() );
    ```

    ![Event handling](https://math.hws.edu/javanotes/c6/event-handling.png)
---

# 6.2: Some Basic Classes

#### Slide 1: Color and Paint
- JavaFX uses the RGB color system for colors, represented by `Color` objects.
- RGB values are in the range 0.0 to 1.0; `alpha` controls transparency.
- Factory methods like `Color.color(r, g, b, a)` are preferred over constructors.
- Supports both RGB and HSB color systems.
- **Code Example**:
  ```java
  Color myColor = Color.color(0.2, 0.3, 1.0, 1.0);
  Color randomColor = Color.hsb(360 * Math.random(), 1.0, 1.0);
  ```

#### Slide 2: Fonts
- JavaFX uses `Font` objects to define text size and style.
- Fonts are created using `Font.font(family, weight, posture, size)`.
- Common families include "Times New Roman," "Arial."
- **Code Example**:
  ```java
  Font font1 = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 24);
  Font font2 = Font.font(16); // Default family
  ```

#### Slide 3: Image
- JavaFX can load images from files or URLs using `Image`.
- **Code Example**:
  ```java
  Image img = new Image("file:example.png");
  ```

#### Slide 4: Canvas and GraphicsContext
- JavaFX `Canvas` allows custom graphics drawing.
- Drawing is done using a `GraphicsContext` obtained from the canvas.
- **Code Example**:
  ```java
  Canvas canvas = new Canvas(200, 200);
  GraphicsContext gc = canvas.getGraphicsContext2D();
  gc.fillRect(50, 50, 100, 100);
  ```

#### Slide 5: A Bit of CSS
- JavaFX supports CSS for styling.
- Use CSS to control component appearance, like colors, fonts, and borders.
- CSS can be applied inline or in external files.
- **Example**: 
  ```java
  message.setStyle(
   "-fx-padding: 5px; -fx-border-color: black; -fx-border-width: 1px" );
  ```
- **CSS style file**: `mystyle.css`
  ```css
  Button {
   -fx-font: bold 16pt "Times New Roman";
   -fx-text-fill: darkblue;
  }

  Label {
    -fx-font: 15pt sans-serif;
    -fx-padding: 7px;
    -fx-border-color: darkred;
    -fx-border-width: 2px;
    -fx-text-fill: darkred;
    -fx-background-color: pink;
  }
  ```
  ```java
  scene.getStylesheets().add("mystyle.css");
  ```  
---

# 6.3: Basic events

### Slide 1: Basic Events in JavaFX
- Events allow programs to respond to user actions like mouse clicks and key presses.
- In JavaFX, events are handled by creating objects for each event (e.g., `MouseEvent`, `KeyEvent`).
- An **event loop** waits for and processes events. In JavaFX, this is managed and provided by the system.
  ```
  while the program is still running:
    Wait for the next event to occur
    Handle the event
  ```

### Slide 2: Event Handling
- Event handling uses **event listeners** to detect events.
- **EventHandler Interface**: Provides `handle(event)` method for custom responses.
- JavaFX commonly uses lambda expressions for handling events.
- **Code Example**:
  ```java
  helloButton.setOnAction(evt -> message.setText("Hello World!"));
  ```

  ![Event handling](https://math.hws.edu/javanotes/c6/event-handling.png)

### Slide 3: Event Source and Target
- Events have a **source** (origin) and a **target** (affected component).
- Events propagate, meaning multiple objects (e.g., `Canvas`, `Scene`) can respond.
- `evt.getSource()` and `evt.getTarget()` retrieve these references.

### Slide 4: Mouse Events
- `MouseEvent` class handles mouse-related events like click, move, and drag.
- Example: Click generates `mouse pressed`, `mouse released`, and `mouse clicked` events.
- Mouse events can include information such as button pressed and coordinates.
- Example: 
  ```java
  canvas.setOnMousePressed( evt -> redraw() );
  ```

  ```java
  canvas.setOnMousePressed( evt -> {
    GraphicsContext g = canvas.getGraphicsContext2D();
    if ( evt.isShiftDown() ) {
        g.setFill( Color.BLUE );
        g.fillOval( evt.getX() - 30, evt.getY() - 15, 60, 30 )
    }
    else {
        g.setFill( Color.RED );
        g.fillRect( evt.getX() - 30, evt.getY() - 15, 60, 30 );
    }
  } );  
  ```

### Slide 5: Dragging
- Dragging occurs when the user moves the mouse while holding down a button.
- JavaFX provides methods to respond to dragging, like `setOnMouseDragged()`.
- Example: `SimpleTrackMouse.java`
  
  ![SimpleTrackMouse.java](https://math.hws.edu/javanotes/c6/simple-paint.png)

### Slide 6: Key Events
- `KeyEvent` class handles keyboard actions such as key press, release, and typing.
- Key events allow capturing user input, often handled by text fields or forms.
- **Code Example**:
  ```java
  scene.setOnKeyPressed(event -> System.out.println("Key Pressed: " + event.getCode()));
  ```

### Slide 7: AnimationTimer
- `AnimationTimer` helps create animations by executing code at each frame.
- Unlike traditional timers, it adjusts for frame rates, providing smooth updates.
- **Code Example**:
  ```java
  new AnimationTimer() {
      public void handle(long currentNanoTime) {
          // Animation logic here
          draw();
      }
  }.start();
  ```

### Slide 8: State Machines
- State machines track the state of objects to control program behavior over time.
- Useful for managing complex event-driven interactions.
- **Example**: `SubKiller.java`:
  
  ![SubKiller.java](https://math.hws.edu/javanotes/c6/sub-killer.png)
### Slide 9: Observable Values
- Observable values enable automatic updates in UI components.
- Example: Observable properties notify listeners of value changes, supporting data binding.

---

# 6.4: Basic controls

### Slide 1: ImageView
- Displays images in the JavaFX scene graph.
- `ImageView` is a node that wraps an `Image` object, making it easier to place an image without drawing it on a canvas.
- **Code Example**:
  ```java
  Image tux = new Image("icons/tux.png");
  ImageView tuxIcon = new ImageView(tux);
  ```

---

### Slide 2: Label and Button
- `Label` displays static text or graphics and is the simplest control for text display.
- `Button` allows users to interact by clicking and can display both text and graphics.
- Common methods from the `Labeled` superclass:
  - `setText()`, `setGraphic()`, `setFont()`, `setTextFill()`
- **Code Example**:
  ```java
  // label
  Label message = new Label("Hello World");
  Label linuxAd = new Label("Choose Linux First!", tuxIcon);
  // button
  Button btn = new Button("Click Me");
  Button linuxButton = new Button("Get Linux", tuxIcon);
  Button stopButton = new Button("Stop");
  stopButton.setOnAction( e -> animator.stop() );
  ```

---

### Slide 3: CheckBox and RadioButton
- `CheckBox`: Lets users select or deselect an option.
- `RadioButton`: Used for mutually exclusive selections within a `ToggleGroup`.
- CheckBox and RadioButton can be styled and are often used together for form inputs.
- **Code Example**:
  ```java
  CheckBox checkBox = new CheckBox("Accept Terms");
  RadioButton option1 = new RadioButton("Option 1");
  RadioButton option2 = new RadioButton("Option 2");
  ToggleGroup group = new ToggleGroup();
  option1.setToggleGroup(group);
  option2.setToggleGroup(group);
  option1.setSelected(true);    // default selection
  ```

  ![Example options](https://math.hws.edu/javanotes/c6/colorRadioButtons.png)
---

### Slide 4: TextField and TextArea
- `TextField`: A single-line input control for user text entry.
- `TextArea`: A multi-line text input control for larger text input.
- Both support setting initial text, clearing, and event handling for text changes.
- **Code Example**:
  ```java
  TextField nameField = new TextField();
  nameField.setPromptText("Enter your name");
  TextArea commentsArea = new TextArea();
  commentsArea.setPromptText("Enter your comments here");
  ```
- **Example**: `TextInputDemo.java`
  ![TextInputDemo.java](https://math.hws.edu/javanotes/c6/TextInputDemo.png)
---

### Slide 5: Slider
- `Slider`: Allows users to select a value within a specified range.
- Useful for adjusting numerical values (e.g., volume control).
- Configurable for min, max, and current values, and can display tick marks.
- **Code Example**:
  ```java
  Slider slider = new Slider(0, 100, 50);
  slider.setMajorTickUnit(25); // space between big tick marks
  slider.setMinorTickCount(5); // 5 small tick marks between big tick marks.
  slider.setShowTickLabels(true);
  slider.setShowTickMarks(true);
  slider.valueProperty().addListener( e -> handleSliderValueChanged(slider) );
  ```
- **Example**: `SliderDemo.java`:

  ![SliderDemo](https://math.hws.edu/javanotes/c6/slider-demo.png)

---

# 6.5 Basic layout

### Slide 1: Basic Layout Concepts
- Components in a GUI need positioning and resizing within a container.
- Containers in JavaFX manage the layout of child nodes (components).
- Each container has policies for setting child sizes and positions.
- **Graphic**: Nested panel illustration of components within containers.
  
  ![alt text](https://math.hws.edu/javanotes/c6/panels-in-layout.png)

---

### Slide 2: Resizable vs Non-Resizable Components
- Components have minimum, maximum, and preferred sizes.
- Containers usually honor these size constraints.
- **Resizable Nodes**: Controls and containers.
- **Non-Resizable Nodes**: `Canvas` and `ImageView`.
- **Code Example**:
  ```java
  node.setPrefSize(200, 100);  // Preferred size setting
  ```

---

### Slide 3: Do Your Own Layout
- For custom layouts, set nodes as unmanaged using `node.setManaged(false)`.
- Use `Pane` for custom placement by setting positions with `relocate(x, y)`.
- **Code Example**:
  ```java
  node.relocate(50, 50);
  node.resize(100, 100);
  ```

---

### Slide 4: Example Layout in Pane
- **Example**: `OwnLayoutDemo.java` with a checkerboard, two buttons, and a message label.
- Nodes are positioned manually using `relocate()` and `resize()` methods.
- **Code Snippet**:
  ```java
  board.relocate(20, 20);
  newGameButton.relocate(370, 120);
  resignButton.relocate(370, 200);
  message.relocate(20, 370);
  ```

  ![alt text](https://math.hws.edu/javanotes/c6/null-layout-demo.png)
---

### Slide 5: Setting Preferred Size for Pane
- Pane's preferred size is set to fit all managed components.
- Unmanaged nodes aren’t considered in preferred size.
- **Code Example**:
  ```java
  Pane root = new Pane();
  root.setPrefWidth(500);
  root.setPrefHeight(420);
  ```

---

### Slide 6: BorderPane Layout
- **BorderPane**: Divides layout into five regions: top, bottom, left, right, and center.
- Useful for creating structured layouts, such as toolbars, side menus, and main content areas.
- Allows different components in each region, which can resize independently.
- **Code Example**:
  ```java
  BorderPane borderPane = new BorderPane();
  borderPane.setTop(menuBar);
  borderPane.setCenter(contentArea);
  borderPane.setBottom(statusBar);
  ```

  ![alt text](https://math.hws.edu/javanotes/c6/border-layout.png)
---

### Slide 7: HBox and VBox Layouts
- **HBox**: Arranges components in a single horizontal row.
- **VBox**: Arranges components in a single vertical column.
- Useful for aligning elements like buttons, labels, or form fields.
- Can set spacing and padding between elements.
- **Code Example**:
  ```java
  HBox hbox = new HBox(10);  // 10px spacing
  hbox.getChildren().addAll(button1, button2, button3);

  VBox vbox = new VBox(5);  // 5px spacing
  vbox.getChildren().addAll(label, textField);
  ```
- Example: `SimpleCalc.java`
  
  ![alt text](https://math.hws.edu/javanotes/c6/simple-calc.png)
---

### Slide 8: GridPane Layout
- **GridPane**: Arranges components in a grid of rows and columns.
- Great for forms or layouts requiring alignment, like spreadsheets or data tables.
- Cells can span multiple rows or columns.
- **Code Example**:
  ```java
  GridPane gridPane = new GridPane();
  gridPane.add(label1, 0, 0);  // Add at row 0, col 0
  gridPane.add(textField1, 1, 0);  // Add at row 0, col 1
  gridPane.setHgap(10);  // Horizontal spacing
  gridPane.setVgap(10);  // Vertical spacing
  ```

  ![alt text](https://math.hws.edu/javanotes/c6/grid-layout.png)

- **Example**: `SimpleColorChooser.java`  
---

# 6.6 Complete programs

---

### Slide 1: Complete Programs
- Building two complete GUI applications using JavaFX.
  - HighLow card game with GUI.
  - Menus and toolbars.
- Packaging applications with `jpackage`.

---

### Slide 2: A Little Card Game (HighLow)
- `HighLowGUI.java`
- A GUI version of the HighLow card game.
- Player guesses if the next card is higher or lower.
- Winning requires three correct guesses; losing on any wrong guess.

![alt text](https://math.hws.edu/javanotes/c6/high-low-gui.png)

- **Code highlights**: 
  ```java
  public void start(Stage stage) {

      cardImages = new Image("cards.png");     // Load card images.
      board = new Canvas(4*99 + 20, 123 + 80); // Space for 4 cards.
      
      Button higher = new Button("Higher");    // Create the buttons, and
      higher.setOnAction( e -> doHigher() );   //    install event handlers.
      Button lower = new Button("Lower");
      lower.setOnAction( e -> doLower() );
      Button newGame = new Button("New Game");
      newGame.setOnAction( e -> doNewGame() );

      HBox buttonBar = new HBox( higher, lower, newGame );
              
      higher.setPrefWidth(board.getWidth()/3.0);  // Make each button fill
      lower.setPrefWidth(board.getWidth()/3.0);   //     1/3 of the width.
      newGame.setPrefWidth(board.getWidth()/3.0);
              
      BorderPane root = new BorderPane();  // Create the scene graph root node.
      root.setCenter(board);
      root.setBottom(buttonBar);

      doNewGame();  // set up for the first game

      Scene scene = new Scene(root);  // Finish setting up the scene and stage.
      stage.setScene(scene);
      stage.setTitle("High/Low Game");
      stage.setResizable(false);
      stage.show();
      
  }  // end start()
  ```
  ---

- Uses `BorderPane` layout with `Canvas` in the center and buttons in `HBox` at the bottom.
- Buttons adjust their size to fill the HBox.
- **Code Example**:
  ```java
  BorderPane root = new BorderPane();
  root.setCenter(board);
  root.setBottom(buttonBar);
  ```

  ---

- Manages game state with boolean `gameInProgress`.
- `doNewGame()`, `doHigher()`, and `doLower()` control game flow.
- Example: `doNewGame()` resets the game and updates the message.
- **Code Example**:
  ```java
  if (gameInProgress) {
      message = "Finish this game first!";
  } else {
      // Initialize new game
  }
  ```

---

### Slide 3: Menus and MenuBars
- Introduction to `MenuBar` and `Menu` for adding file, edit, and help menus.
- Program: `MosaicCanvas.java`
- Menus organize app actions and provide shortcuts.
- **Code Example**:
  ```java
  Menu fileMenu = new Menu("File");
  fileMenu.getItems().add(new MenuItem("Open"));
  ```

  ![alt text](https://math.hws.edu/javanotes/c6/mosaic-draw.png)

---

### Slide 4: Packaging with `jpackage`
- Use `jpackage` to create standalone applications
- Generates executables or installers for Windows, Mac, and Linux.
- Requires specifying module path, main class, and output format.

---

# What's next
- [Chapter 12](https://math.hws.edu/javanotes/c12/index.html): consolidates threaded programming in Java
- [Chapter 13](https://math.hws.edu/javanotes/c13/index.html): provides slightly more advanced topics in UI programming
- Solve the Sudokui challenge (see Canvas for details)!