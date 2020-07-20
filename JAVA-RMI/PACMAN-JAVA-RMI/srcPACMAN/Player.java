import javax.swing.ImageIcon;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.Math;

import java.rmi.Naming;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.Remote;

public class Player extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	Dimension d;
	Font smallfont = new Font("Monospaced", Font.BOLD, 16);

	FontMetrics fmsmall, fmlarge;
	Image ii;
	Color dotcolor = new Color(0, 190, 190);
	Color mazecolor;
	final int pacanimdelay = 2;
	final int pacmananimcount = 4;
	int pacanimcount = pacanimdelay;
	int pacanimdir = 1;
	int pacmananimpos = 0;

	private I_InfoPlayer player;
	private Graphics2D g2d;
	private static I_InfoGame game = null;
	private static String name = "";

	boolean win = false;
	boolean end = false;
	boolean waiting = false;
	


	Image ghost;
	Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
	Image pacman3up, pacman3down, pacman3left, pacman3right;
	Image pacman4up, pacman4down, pacman4left, pacman4right;


	Timer timer;


	public Player(I_InfoGame g) {

		game = g;
		String playerName = "Jugador" + (int)(Math.random()*1000);

		try {
			game.newPlayer(playerName);

		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			player = (I_InfoPlayer) Naming.lookup("rmi://localhost:1099/"+playerName);
		} catch (NotBoundException e){
			System.out.println("El servicio no esta publicado en el servidor");
			System.exit(128);
		} catch (MalformedURLException e){
			System.out.println("URL invalida");
			System.exit(128);
		} catch (RemoteException e){
			System.out.println("Excepcion remota tratando de conectarse al servidor");
			System.exit(128); 
		}


		GetImages();

		addKeyListener(new TAdapter());
		mazecolor = new Color(100, 100, 100);
		setFocusable(true);

		d = new Dimension(500, 500);

		setBackground(Color.white);
		setDoubleBuffered(true);
		timer = new Timer(40, this);
		timer.start();
	}


	public void addNotify() {
		super.addNotify();
		try {
			player.playerInit();
			if (!game.isPlaying()){
				game.GameInit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void DoAnim() {
		pacanimcount--;
		if (pacanimcount <= 0) {
			pacanimcount = pacanimdelay;
			pacmananimpos = pacmananimpos + pacanimdir;
			if (pacmananimpos == (pacmananimcount - 1) || pacmananimpos == 0)
				pacanimdir = -pacanimdir;
		}
	}



	public void ShowEndScreen() {
		try{
			int scrsize = game.getScrsize();

			g2d.setColor(new Color(0, 32, 48));
			g2d.fillRect(50, scrsize / 2 - 30, scrsize - 100, 50);
			g2d.setColor(Color.white);
			g2d.drawRect(50, scrsize / 2 - 30, scrsize - 100, 50);

			String s = "Presione S para iniciar o Q para salir";
			Font small = new Font("Monospaced", Font.BOLD, 20);
			FontMetrics metr = this.getFontMetrics(small);

			g2d.setColor(Color.white);
			g2d.setFont(small);
			g2d.drawString(s, (scrsize - metr.stringWidth(s)) / 2, scrsize / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowDeadScreen() {
		try{
			int scrsize = game.getScrsize();

			g2d.setColor(new Color(0, 32, 48));
			g2d.fillRect(50, scrsize / 2 - 30, scrsize - 100, 50);
			g2d.setColor(Color.white);
			g2d.drawRect(50, scrsize / 2 - 30, scrsize - 100, 50);

			String s = "Haz Muerto";
			Font small = new Font("Monospaced", Font.BOLD, 20);
			FontMetrics metr = this.getFontMetrics(small);

			g2d.setColor(Color.white);
			g2d.setFont(small);
			g2d.drawString(s, (scrsize - metr.stringWidth(s)) / 2, scrsize / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowIntroScreen() {
		try{
			int scrsize = game.getScrsize();

			g2d.setColor(new Color(0, 32, 48));
			g2d.fillRect(50, scrsize / 2 - 30, scrsize - 100, 50);
			g2d.setColor(Color.white);
			g2d.drawRect(50, scrsize / 2 - 30, scrsize - 100, 50);
			
			String s = "Presione S para iniciar juego";
			Font small = new Font("Monospaced", Font.BOLD, 20);
			FontMetrics metr = this.getFontMetrics(small);

			g2d.setColor(Color.white);
			g2d.setFont(small);
			g2d.drawString(s, (scrsize - metr.stringWidth(s)) / 2, scrsize / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ShowWaitingScreen() {
		try{
			int scrsize = game.getScrsize();
			int n = game.getNbPlayerExpected() - game.getNbPlayerWaiting();
			

			g2d.setColor(new Color(0, 32, 48));
			g2d.fillRect(50, scrsize / 2 - 30, scrsize - 100, 50);
			g2d.setColor(Color.white);
			g2d.drawRect(50, scrsize / 2 - 30, scrsize - 100, 50);
			
			String s = "Esperando a " + n + " otros jugadores";
			Font small = new Font("Monospaced", Font.BOLD, 14);
			FontMetrics metr = this.getFontMetrics(small);

			g2d.setColor(Color.white);
			g2d.setFont(small);
			g2d.drawString(s, (scrsize - metr.stringWidth(s)) / 2, scrsize / 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DrawScore() {
		int i;
		String s;

		try{
			int scrsize = game.getScrsize();
			int score = player.getScore();
			int pacsleft = player.getPacsleft();



			g2d.setFont(smallfont);
			g2d.setColor(new Color(96, 128, 255));
			if(end) {
				 if(win) s = "Has ganado. Tu puntaje es: "+score;
				 else s = "Has perdido. Tu puntaje es: "+score;
			}
			else s = "Puntaje: " + score;
			g2d.drawString(s, scrsize / 2 - 14, scrsize + 16);
			for (i = 0; i < pacsleft; i++) {
				g2d.drawImage(pacman3left, i * 28 + 8, scrsize + 1, this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void drawGhost() {

		try{
			int nbrGhosts = game.getNrofghosts();
			int[] ghostX = game.getGhostx();
			int[] ghostY = game.getGhosty();

			for(int k = 0; k<nbrGhosts; k++){
				g2d.drawImage(ghost, ghostX[k]+1, ghostY[k]+1, this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DrawPacMan() {
		try{
		ArrayList<I_InfoPlayer> players = game.getPlayers();
			for(int i = 0; i<players.size(); i++) {
				if (players.get(i).isPlaying()) {
					if (players.get(i).getViewdx() == -1)
						DrawPacManLeft(players.get(i));
					else if (players.get(i).getViewdx() == 1)
						DrawPacManRight(players.get(i));
					else if (players.get(i).getViewdy() == -1)
						DrawPacManUp(players.get(i));
					else
						DrawPacManDown(players.get(i));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void DrawPacManUp(I_InfoPlayer p) {    	
		try{
			switch (pacmananimpos) {
			case 1:
				g2d.drawImage(pacman2up, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 2:
				g2d.drawImage(pacman3up, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 3:
				g2d.drawImage(pacman4up, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			default:
				g2d.drawImage(pacman1, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void DrawPacManDown(I_InfoPlayer p) {
		try {
			switch (pacmananimpos) {
			case 1:
				g2d.drawImage(pacman2down, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 2:
				g2d.drawImage(pacman3down, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 3:
				g2d.drawImage(pacman4down, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			default:
				g2d.drawImage(pacman1, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void DrawPacManLeft(I_InfoPlayer p) {
		try {
			switch (pacmananimpos) {
			case 1:
				g2d.drawImage(pacman2left, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 2:
				g2d.drawImage(pacman3left, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 3:
				g2d.drawImage(pacman4left, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			default:
				g2d.drawImage(pacman1, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void DrawPacManRight(I_InfoPlayer p) {
		try{
			switch (pacmananimpos) {
			case 1:
				g2d.drawImage(pacman2right, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 2:
				g2d.drawImage(pacman3right, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			case 3:
				g2d.drawImage(pacman4right, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			default:
				g2d.drawImage(pacman1, p.getPacmanx() + 1, p.getPacmany() + 1, this);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public void DrawMaze() {
		short i = 0;
		int x, y;

		try{
			int blocksize = game.getBlocksize();
			int scrsize = game.getScrsize();
			short[] screendata = game.getScreendata();


			for (y = 0; y < scrsize; y += blocksize) {
				for (x = 0; x < scrsize; x += blocksize) {
					g2d.setColor(mazecolor);
					g2d.setStroke(new BasicStroke(2));

					if ((screendata[i] & 1) != 0) // draws left
					{
						g2d.drawLine(x, y, x, y + blocksize - 1);
					}
					if ((screendata[i] & 2) != 0) // draws top
					{
						g2d.drawLine(x, y, x + blocksize - 1, y);
					}
					if ((screendata[i] & 4) != 0) // draws right
					{
						g2d.drawLine(x + blocksize - 1, y, x + blocksize - 1,
								y + blocksize - 1);
					}
					if ((screendata[i] & 8) != 0) // draws bottom
					{
						g2d.drawLine(x, y + blocksize - 1, x + blocksize - 1,
								y + blocksize - 1);
					}
					if ((screendata[i] & 16) != 0) // draws point
					{
						g2d.setColor(dotcolor);
						g2d.fillRect(x + 11, y + 11, 2, 2);
					}
					i++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void GetImages()
	{

		ghost = new ImageIcon(Board.class.getResource("./ghost.gif")).getImage();
		pacman1 = new ImageIcon(Board.class.getResource("./pacman.gif")).getImage();
		pacman2up = new ImageIcon(Board.class.getResource("./up1.gif")).getImage();
		pacman3up = new ImageIcon(Board.class.getResource("./up2.gif")).getImage();
		pacman4up = new ImageIcon(Board.class.getResource("./up3.gif")).getImage();
		pacman2down = new ImageIcon(Board.class.getResource("./down1.gif")).getImage();
		pacman3down = new ImageIcon(Board.class.getResource("./down2.gif")).getImage(); 
		pacman4down = new ImageIcon(Board.class.getResource("./down3.gif")).getImage();
		pacman2left = new ImageIcon(Board.class.getResource("./left1.gif")).getImage();
		pacman3left = new ImageIcon(Board.class.getResource("./left2.gif")).getImage();
		pacman4left = new ImageIcon(Board.class.getResource("./left3.gif")).getImage();
		pacman2right = new ImageIcon(Board.class.getResource("./right1.gif")).getImage();
		pacman3right = new ImageIcon(Board.class.getResource("./right2.gif")).getImage();
		pacman4right = new ImageIcon(Board.class.getResource("./right3.gif")).getImage();

	}

	public void paint(Graphics g)
	{
		super.paint(g);

		g2d = (Graphics2D) g;

		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);

		DrawMaze();
		DrawScore();
		DoAnim();
		try {
			if(game.isEnded() && !game.isWaiting()) {
				ShowEndScreen();
			} else if (game.isPlaying() && player.isPlaying()){
				PlayGame();
			} else if (game.isPlaying() && player.isDead()){
				drawGhost();
				DrawPacMan();
				ShowDeadScreen();
			} else if (game.isWaiting()&& player.isWaiting()){
				ShowWaitingScreen();
			} else {
				ShowIntroScreen();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}




		g.drawImage(ii, 5, 5, this);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void PlayGame(){
		try {

				if (player.getDying()) {
					Death();
				} else {
					MovePacMan();
					DrawPacMan();
					game.moveGhosts();
					CheckPlayerKilled();
					drawGhost();
					CheckMaze();
				}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void CheckMaze() {
		short i = 0;
		boolean finished = true;
		try {
			int nrofblocks = game.getNrofblocks();
			int nrofghosts = game.getNrofghosts();

			while (i < nrofblocks * nrofblocks && finished) {
				if ((game.getScreendata()[i] & 48) != 0)
					finished = false;
				i++;
			}

			if (finished) {
				player.setScore(player.getScore()+50);
				game.finished();		
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}

	public void CheckPlayerKilled() {
		short i;
		try {
			int[] ghosty = game.getGhosty();
			int[] ghostx = game.getGhostx();
			int nrofghosts = game.getNrofghosts();



			for (i = 0; i < nrofghosts; i++) {

				if (player.getPacmanx() > (ghostx[i] - 12) && player.getPacmanx() < (ghostx[i] + 12) &&
						player.getPacmany() > (ghosty[i] - 12) && player.getPacmany() < (ghosty[i] + 12) &&
						player.isPlaying()) {

						player.setDying(true);
						game.setDeathcounter(game.getDeathcounter() +64);

							}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	public void MovePacMan() {
		int pos;
		short ch;

		try {
			int blocksize = game.getBlocksize();
			int nrofblocks = game.getNrofblocks();
			short[] screendata = game.getScreendata();

			int reqdx = player.getReqdx();
			int reqdy = player.getReqdy();


			if (reqdx == -player.getPacmandx() && reqdy == -player.getPacmandy()) {
				player.setPacmandx(reqdx);
				player.setPacmandy(reqdy);
				player.setViewdx(reqdx);
				player.setViewdy(reqdy);
			}
			if (player.getPacmanx() % blocksize == 0 && player.getPacmany() % blocksize == 0) {
				pos = player.getPacmanx() / blocksize + nrofblocks * (int)(player.getPacmany() / blocksize);
				ch = screendata[pos];

				if ((ch & 16) != 0) {
					game.setScreendata((short)(ch & 15),pos);
					player.setScore(player.getScore()+1);
				}

				if (reqdx != 0 || reqdy != 0) {
					if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0) ||
							(reqdx == 1 && reqdy == 0 && (ch & 4) != 0) ||
							(reqdx == 0 && reqdy == -1 && (ch & 2) != 0) ||
							(reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
						player.setPacmandx(reqdx);
						player.setPacmandy(reqdy);
						player.setViewdx(reqdx);
						player.setViewdy(reqdy);
					}
				}

				// Check for standstill
				if ((player.getPacmandx() == -1 && player.getPacmandy() == 0 && (ch & 1) != 0) ||
						(player.getPacmandx() == 1 && player.getPacmandy() == 0 && (ch & 4) != 0) ||
						(player.getPacmandx() == 0 && player.getPacmandy() == -1 && (ch & 2) != 0) ||
						(player.getPacmandx() == 0 && player.getPacmandy() == 1 && (ch & 8) != 0)) {
					player.setPacmandx(0);
					player.setPacmandy(0);
				}
			}
			player.setPacmanx(player.getPacmanx() + player.getPacmanspeed() * player.getPacmandx());
			player.setPacmany(player.getPacmany() + player.getPacmanspeed() * player.getPacmandy());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Death() {
		try {
			
			player.setPacsleft(player.getPacsleft()-1);
			if (player.getPacsleft() == 0){
				player.setPlaying(false);
				player.setWaiting(false);
				player.setDead(true);
				game.removePlayerPlaying();
			}
			player.setDying(false);
			player.playerInitPos();

			//Verificamos si todos los pacaman estan muertos
			if (game.allDead() == true) {
				game.levelContinue();
				game.setEnded(true);
				game.setPlaying(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		try{
		game.exit(player.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void win() {
	//	win = true;
	//	end = true;
	//	try {
	//	player.setIngame(false);
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	}
	}

	public void loose() {
	//	win = false;
	//	end = true;
	//	try {
	//	player.setIngame(false);
	//	} catch (Exception e) {
	//		e.printStackTrace();
	//	}
	}

	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();
			int reqdx = 0;
			int reqdy = 0;
			try {
				if (player.isPlaying())
				{
					if (key == KeyEvent.VK_LEFT)
					{
						reqdx=-1;
						reqdy=0;
						player.setReqdx(reqdx);
						player.setReqdy(reqdy);
					}
					else if (key == KeyEvent.VK_RIGHT)
					{
						reqdx=1;
						reqdy=0;
						player.setReqdx(reqdx);
						player.setReqdy(reqdy);
					}
					else if (key == KeyEvent.VK_UP)
					{
						reqdx=0;
						reqdy=-1;
						player.setReqdx(reqdx);
						player.setReqdy(reqdy);
					}
					else if (key == KeyEvent.VK_DOWN)
					{
						reqdx=0;
						reqdy=1;
						player.setReqdx(reqdx);
						player.setReqdy(reqdy);
					}
					else if (key == KeyEvent.VK_ESCAPE && timer.isRunning())
					{
						exit();
						System.out.println("ESC");
						System.exit(0);
					}
					else if (key == KeyEvent.VK_PAUSE) {
						if (timer.isRunning())
							timer.stop();
						else {
							timer.start();
						}
					}
				}
				else
				{
					if (key == 's' || key == 'S')
					{
						if (game.isWaiting()&&!player.isWaiting()) {
							player.playerInit();
							player.setWaiting(true);
							game.addPlayerWaiting();
						} else if(game.isPlaying()){
							player.playerInit();
							player.setPlaying(true);
							game.addPlayerPlaying();
						} else if(game.isEnded()){
							game.restart();

						}
					}
					if (key == KeyEvent.VK_Q)
					{
						exit();
						System.exit(0);
					}
				}

			} catch (Exception exc) {
				exc.printStackTrace();
			}

		}

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == Event.LEFT || key == Event.RIGHT || 
					key == Event.UP ||  key == Event.DOWN)
			{
				try {
					player.setReqdx(0);
					player.setReqdy(0);

				} catch (Exception exc) {
					exc.printStackTrace();
				}

			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();  
	}
}
