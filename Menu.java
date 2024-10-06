import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Menu {

    private static Canvas canvas = new Canvas();

    // Save the panel state to a file
    public static void saveState(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeInt(canvas.getBallX());
            oos.writeInt(canvas.getBallY());
            oos.writeInt(canvas.getRectX()); // Corrected method name
            oos.writeInt(canvas.getRectY()); // Corrected method name
            oos.writeInt(canvas.getScore());
            System.out.println("Panel state saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the panel state from a file
    public static void loadState(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            canvas.setBallX(ois.readInt());
            canvas.setBallY(ois.readInt());
            canvas.setRectX(ois.readInt());
            canvas.setRectY(ois.readInt());
            canvas.setScore(ois.readInt());
            System.out.println("Panel state loaded successfully.");
            canvas.repaint(); // Ensure the canvas updates after loading state
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Load");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem exitItem = new JMenuItem("Exit");

        newItem.addActionListener(e -> {
            // Consider resetting game state as well
            canvas.startGame(); // You may need to implement a startGame method in Canvas
            canvas.repaint(); 
        });

        openItem.addActionListener(e -> loadState("Game.bin"));

        saveItem.addActionListener(e -> saveState("Game.bin"));

        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        return menuBar;
    }
}

