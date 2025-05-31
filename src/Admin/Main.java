package Admin;

import Classes.User;
import com.formdev.flatlaf.FlatIntelliJLaf;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static int indicator = 0;
    public static Color transparent = new Color(0, 0, 0, 0);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BufferedImage icon;
            Font merriweather, boldonse;
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                icon = ImageIO.read(new File("images/icon.png"));
                merriweather = Font.createFont(Font.TRUETYPE_FONT,
                                new File("fonts_and_resources/static/Merriweather_24pt-Regular.ttf"))
                        .deriveFont(Font.PLAIN, 14);
                boldonse = Font.createFont(Font.TRUETYPE_FONT,
                                new File("fonts_and_resources/Boldonse-Regular.ttf"))
                        .deriveFont(Font.PLAIN, 14);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(merriweather);
                ge.registerFont(boldonse);
            } catch (IOException e) {
                e.getStackTrace();
                return;
            } catch (FontFormatException | UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

            JFrame main_frame = new JFrame();
            main_frame.setMinimumSize(new Dimension(1330, 780));
            main_frame.setTitle("OWSB Automated Purchase Order Management System");
            main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            main_frame.setLocationRelativeTo(null);
            main_frame.setResizable(true);
            main_frame.setIconImage(icon);

            SignIn.Loader(main_frame, merriweather, boldonse);
            Home.Loader(main_frame, merriweather, boldonse);
            PageChanger(main_frame);

            main_frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (indicator == 0) {
                        SignIn.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight());
                        SwingUtilities.invokeLater(() -> SignIn.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight()));
                    } else if (indicator == 1) {
                        Home.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight());
                        SwingUtilities.invokeLater(() -> Home.UpdateComponentSize(
                                main_frame.getContentPane().getWidth(),
                                main_frame.getContentPane().getHeight()));
                    }
                }
            });

            main_frame.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Component clickedComponent = e.getComponent();
                    SwingUtilities.invokeLater(clickedComponent::requestFocusInWindow);
                }
            });
        });
    }

    public static void PageChanger(JFrame parent) {
        parent.getContentPane().removeAll();
        parent.revalidate();
        parent.repaint();
        switch (indicator) {
            case 0:
                SignIn.ShowPage();
                if (new User().GetRememberedUser() != null) {
                    SignIn.LoginRemembered();
                    break;
                } else {
                    SignIn.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                    SwingUtilities.invokeLater(() -> {
                        SignIn.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                        parent.getContentPane().revalidate();
                        parent.getContentPane().repaint();
                    });
                }
                parent.setVisible(true);
                break;
            case 1:
                Home.ShowPage();
                Home.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                SwingUtilities.invokeLater(() -> {
                    Home.UpdateComponentSize(parent.getWidth(), parent.getHeight());
                    parent.getContentPane().revalidate();
                    parent.getContentPane().repaint();
                });
                parent.setVisible(true);
                break;
        }
    }
}