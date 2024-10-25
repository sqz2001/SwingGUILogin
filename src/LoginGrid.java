import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

        public class LoginGrid extends JFrame {
            //2D array to represent the 5x5 grid
            private JButton[][] buttons = new JButton[5][5];
            //Hashset to store the unique (x,y) points of the pre-set pattern
            private Set<Point> pattern = new HashSet<>();
            //ArrayList to store the (x,y) points for the pattern the user clicks
            private ArrayList<Point> userSelection = new ArrayList<>();

            public LoginGrid() {
                setTitle("Login Verification");
                setSize(400, 400);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLayout(new GridLayout(5, 5));

                // Initialize each button in the 5x5 grid
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        buttons[i][j] = new JButton();
                        buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        buttons[i][j].setActionCommand(i + "," + j);
                        buttons[i][j].addActionListener(new ButtonClickListener());
                        add(buttons[i][j]);
                    }
                }
                generatePattern();
                highlightPattern();

                setLocationRelativeTo(null);
                // center the box on screen
            }

            private void generatePattern() {
                //generate the random pattern that users will have to click to verify login
                Random rand = new Random();
                //set the number of selected boxes in the 5x5 grid to value (this case, we are using 5)
                while (pattern.size() < 5) {
                    //set parameter for the random x and y val generated to be between 0 and 4
                    int x = rand.nextInt(5);
                    int y = rand.nextInt(5);
                    pattern.add(new Point(x, y));
                }
            }

            private void highlightPattern() {
                // show the pattern that was randomly generated, so users have template to know what to click
                for (Point p : pattern) {
                    buttons[p.x][p.y].setBackground(Color.GREEN);
                    //display the generated pattern in green
                }
            }

            private class ButtonClickListener implements ActionListener {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //helps us find which button was clicked
                    String command = e.getActionCommand();
                    //splits the data into x and y point, with parts[0] as x, parts[1] as y
                    String[] parts = command.split(",");
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    //create point object from the x and y coordinates
                    Point selectedPoint = new Point(x, y);

                    if (userSelection.contains(selectedPoint)) {
                        //if the user-selected points already contains the selected point, then unmark it
                        userSelection.remove(selectedPoint);
                        //reset the button so that it is unselected
                        buttons[x][y].setBackground(Color.LIGHT_GRAY);
                    } else {
                        userSelection.add(selectedPoint);
                        //if button is not selected yet, make it selected
                        buttons[x][y].setBackground(Color.BLUE);
                        //mark selected with blue
                    }

                    if (userSelection.size() == pattern.size()) {
                        //after the user selects the required number of boxes for the pattern
                        // check the pattern for correctness
                        checkSelection();
                    }
                }
            }

            private void checkSelection() {
                if (userSelection.containsAll(pattern) && pattern.containsAll(userSelection)) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Login Failed! Try again.");
                    resetGame();
                }
            }

            private void resetGame() {
                userSelection.clear();
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        buttons[i][j].setBackground(Color.LIGHT_GRAY);
                        //put square back to default color of grey
                    }
                }
                pattern.clear();
                generatePattern();
                highlightPattern();
            }

            public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                    LoginGrid frame = new LoginGrid();
                    frame.setVisible(true);
                });
            }
        }
