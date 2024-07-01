
package org.example;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicDialog extends JDialog {

    private byte[] audioBytes;

    public MusicDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        initComponents();
        loadMusic();
    }

    private void initComponents() {
        JLabel messageLabel = new JLabel("<html>Ты смог пройти игру! <br>А теперь ответь на глоавный вопрос - кто ты? <br>Чтоб потомки знали эту легенду</html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 10));
        messageLabel.setForeground(Color.RED);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(messageLabel, BorderLayout.CENTER);

        setContentPane(contentPanel);
        setSize(300, 200);
        setLocationRelativeTo(null);
    }

    private void loadMusic() {
        try (InputStream audioSrc = getClass().getClassLoader().getResourceAsStream("final.mp3")) {
            if (audioSrc == null) {
                throw new IOException("Audio file not found: resources/final.mp3");
            }
            audioBytes = audioSrc.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playMusic() {
        try {
            ByteArrayInputStream audioStream = new ByteArrayInputStream(audioBytes);
            AdvancedPlayer player = new AdvancedPlayer(audioStream);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            playMusic();
        }
        super.setVisible(visible);
    }


    }
