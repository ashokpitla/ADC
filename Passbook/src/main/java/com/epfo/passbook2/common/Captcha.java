/*
 * 
 * 
 */
package com.epfo.passbook2.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

/**
 *
 * @author SANJAY-KUNJAM
 */
public class Captcha implements Serializable {

    public static final String FILE_TYPE = "jpeg";
    private String captcha;
    private String answer;
    private String image64;

    public String getImage64() {
        return image64;
    }

    private void setImage64(String image64) {
        this.image64 = image64;
    }

    public String getCaptcha() {
        return captcha;
    }

    private void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getAnswer() {
        return answer;
    }

    private void setAnswer(String answer) {
        this.answer = answer;
    }

    public Captcha() {

        try {
            ArrayList colors = new ArrayList<>();
            colors.add(Color.decode("#337ab7"));
            colors.add(Color.decode("#9e0000"));

            ArrayList fonts = new ArrayList<>();
            fonts.add(new Font("sans-serif", Font.ITALIC, 30));

            nl.captcha.Captcha captcha = new nl.captcha.Captcha.Builder(120, 30)
                    .addText(new DefaultWordRenderer(colors, fonts))
                    .addBackground(new GradiatedBackgroundProducer(Color.GRAY, Color.white))
                    .gimp()
                    .addBorder()
                    .addNoise()
                    .build();

            // display the image produced
            //CaptchaServletUtil.writeImage(response, captcha.getImage());
            BufferedImage cpimg = captcha.getImage();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(cpimg, FILE_TYPE, bos);

            byte[] imageBytes = bos.toByteArray();

            String imageString = java.util.Base64.getEncoder().encodeToString(imageBytes);
            this.setAnswer(captcha.getAnswer());

            this.setImage64(imageString);

        } catch (IOException e) {
            System.out.println("Captcha Error : " + e.toString());
        }

    }

}
