# Mini Football Game

A 2D top-down soccer (football) game built with Java Swing and AWT.  
Supports **two players** on the same keyboard, dribbling, tackling, shooting, and scoring goals.  
Includes simple AI-controlled goalkeepers and collectible items.

---

## 🎮 Features
- **Two-player local gameplay**
  - Player 1: WASD movement, F to shoot, SPACE to tackle
  - Player 2: IJKL movement, H to shoot, SHIFT to tackle
- **Goal detection and scoring system**
  - Each goal updates the scoreboard with sound effects
- **Dribbling and stamina system**
  - Picking up the ball drains stamina
  - Stealing the ball restores stamina
- **Collectible items**
  - Water: Restores stamina
  - Shoes: Increases speed
- **AI Goalkeepers** that patrol their goal areas and block shots
- **Save/Load game state** to a MySQL database (5 save slots)
- **Title screen**, **pause screen**, and **in-game UI** (scoreboard, timers, messages)

---

## 🎯 Controls

### **Player 1 (Red Team)**
| Action     | Key     |
|------------|---------|
| Move Up    | W       |
| Move Down  | S       |
| Move Left  | A       |
| Move Right | D       |
| Shoot      | F       |
| Tackle     | SPACE   |

### **Player 2 (Blue Team)**
| Action     | Key     |
|------------|---------|
| Move Up    | I       |
| Move Down  | K       |
| Move Left  | J       |
| Move Right | L       |
| Shoot      | H       |
| Tackle     | SHIFT   |

### **Global Controls**
| Action     | Key     |
|------------|---------|
| Pause Game | P       |
| Save Game  | F1      |
| Exit Game  | ESC     |

---

## 📦 Requirements
- **Java 8+**
- **MySQL database** with a table named `TB_SAVE`:
```sql
CREATE TABLE TB_SAVE (
    IDX INT PRIMARY KEY,
    P1_WORLD_X INT,
    P1_WORLD_Y INT,
    P2_WORLD_X INT,
    P2_WORLD_Y INT,
    P1_LIFE INT,
    P2_LIFE INT,
    TIME FLOAT,
    BLUE_SCORE INT,
    RED_SCORE INT
);
```

---

## 🚀 How to Run
1. **Clone or download** the project:
   ```bash
   git clone https://github.com/yourusername/minisoccergame.git
   cd minisoccergame
   ```
2. Ensure resources are in the correct /resources paths.
3. Set up MySQL and update connection details in SaveLoad.java:
    ```bash
    String url = "jdbc:mysql://localhost:3306/GAME_DB"; 
    String id = "root";
    String pw = "your_password_here";
    ```
4. Compile and run:
    ```bash
    javac -d bin src/main/*.java src/entity/*.java
    java -cp bin main.Main
    ```

---

## 📌 Notes
- Game runs at 60 FPS.
- Camera follows Player 1.
- Only one ball is in play.
- Dribbling automatically moves the ball with the player until shooting.
- Tackling can steal the ball from the opponent.

## Sources
- Java 2D Tutorial: [RyiSnow Java Game Tutorial](https://www.youtube.com/watch?v=om59cwR7psI&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq)
- Sprites created with: [Piskel](https://www.piskelapp.com/p/create/sprite/)
- Tiles made using: [2D Tile Editor](https://drive.google.com/drive/folders/1cxKDCfIbPVpgRfRN6PLco8eoV0bPNPQI)

## 📜 License
This project is for educational purposes only.
It may not be distributed commercially without replacing assets.
