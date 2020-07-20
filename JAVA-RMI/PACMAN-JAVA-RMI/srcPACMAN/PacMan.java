import javax.swing.JFrame;

public class PacMan extends JFrame
{

  public PacMan()
  {
    add(new Board()); //Publica en el JFrame nuestro tablero
    setTitle("Pacman");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(380, 420);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public static void main(String[] args) {
      new PacMan();
  }
}
