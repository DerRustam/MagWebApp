package com.rustam.magbackend.utils.compression;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PreparedImage {
    @NotNull
    private byte[] thumb;
    private byte[] view;
    private byte[] full;

    public PreparedImage(@NotNull BufferedImage thumb, BufferedImage view, BufferedImage full, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumb, format, baos);
        this.thumb = baos.toByteArray();
        if (view != null) {
            baos.reset();
            ImageIO.write(view, format, baos);
            this.view = baos.toByteArray();
            if (full != null) {
                baos.reset();
                ImageIO.write(full, format, baos);
                this.full = baos.toByteArray();
            }
        }

    }

    public byte[] getThumb() {
        return thumb;
    }

    public byte[] getView() {
        return view;
    }

    public byte[] getFull() {
        return full;
    }
}
