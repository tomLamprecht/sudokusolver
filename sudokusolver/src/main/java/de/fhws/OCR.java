package de.fhws;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.awt.image.BufferedImage;

public class OCR {

    private static OCR instance;

    public static OCR getInstance() {
        if (instance == null)
            instance = new OCR();
        return instance;
    }

    ITesseract tesseract;

    public OCR() {
        this.tesseract = new Tesseract();
        tesseract.setDatapath("./tessdata");
        tesseract.setLanguage("digits");
    }

    public int getNumber(BufferedImage img) {
        try {
            return Integer.parseInt(tesseract.doOCR(img).replaceAll("\\D", ""));
        } catch (Exception e) {
            System.err.println(e.getMessage() + " : returned -1");
            return -1;
        }
    }
}
