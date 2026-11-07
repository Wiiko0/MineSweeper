# 💣 Java Minesweeper

A classic Minesweeper desktop game built from scratch in Java using
object-oriented programming and the MVC pattern.

## ✨ Features

- Classic grid with randomized mine placement
- Safe first click and recursive empty-cell reveal
- Flagging mechanics for suspected mines
- Beginner, Intermediate, Expert, and Custom difficulties
- Win/loss screens with an elapsed-time counter
- Scoreboard with the last 10 completed games
- Scoreboard saved between application launches
- Themed Java Swing interface with image assets

## 🛠 Tech Stack

- **Language:** Java (JDK 17+)
- **GUI:** Java Swing and AWT
- **Testing:** JUnit 5

## 🚀 How to Run Locally

### 1. Clone the repository

```bash
git clone https://github.com/Wiiko0/MineSweeper.git
cd MineSweeper
```

### 2. Compile and start the game

From the project directory, run:

```powershell
New-Item -ItemType Directory -Force out
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp "out;resources" app.MinesweeperApplication
```

The `resources` folder is included so the game can load its images. Recompile
after changing the source code.

You can also launch `app.MinesweeperApplication` from a Java-compatible IDE.

## 🧪 Run the Tests

Install Maven, then run:

```powershell
mvn test
```

## 🎮 Controls

- **Left-click:** Reveal a cell
- **Right-click:** Place or remove a flag
- **New game:** Start a new game with the selected settings

The first revealed cell and its surrounding cells are always safe.

## 📁 Project Structure

```text
src/
├── app/          Application entry point
├── model/        Game rules and board state
├── controller/   User input and game coordination
├── persistence/  Scoreboard file storage
└── view/         Swing interface and scenes
```

## 🖼 Image Assets

The game loads these files from `resources\images`:

The scoreboard is saved locally in `.minesweeper\scoreboard.txt` in the user's
home directory and is excluded from version control.
