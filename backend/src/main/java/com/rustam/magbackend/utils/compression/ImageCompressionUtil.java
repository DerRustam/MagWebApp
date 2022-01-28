package com.rustam.magbackend.utils.compression;

import org.imgscalr.Scalr;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

public class ImageCompressionUtil {
    private static final Integer MAX_DIM = 3840;
    private static final Integer VIEW_DIM = 800;
    private static final Integer THUMB_DIM = 160;

    public PreparedImage prepareImage(@NonNull MultipartFile file) throws IOException{
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        String format;
        if (file.getContentType() == null){
            throw new IOException("File has null content type");
        }
        switch (file.getContentType()) {
            case "image/jpeg": {
                format = "jpg";
                break;
            }
            case "image/png": {
                format = "png";
                break;
            }
            /*case "image/gif": {
                format = "gif";
                break;
            }*/
            default: {
                throw new IOException("File has incorrect content type");
            }
        }
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (width <= MAX_DIM && height <= MAX_DIM) {
            if (width <= VIEW_DIM && height <= VIEW_DIM) {
                if (width <= THUMB_DIM && height <= THUMB_DIM) {
                    return new PreparedImage(bufferedImage, null, null, format);
                } else {
                    BufferedImage thumb = width > height
                            ? Scalr.resize(bufferedImage, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_WIDTH, THUMB_DIM)
                            : Scalr.resize(bufferedImage, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_HEIGHT, THUMB_DIM, THUMB_DIM);
                    return new PreparedImage(thumb, bufferedImage, null, format);
                }
            } else {
                BufferedImage view = width > height
                        ? Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, VIEW_DIM)
                        : Scalr.resize(bufferedImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_HEIGHT, VIEW_DIM, VIEW_DIM);
                BufferedImage thumb = width > height
                        ? Scalr.resize(view, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_WIDTH, THUMB_DIM)
                        : Scalr.resize(view, Scalr.Method.BALANCED, Scalr.Mode.FIT_TO_HEIGHT, THUMB_DIM, THUMB_DIM);
                return new PreparedImage(thumb, view, bufferedImage, format);
            }
        } else {
            throw new IOException("Image Dimensions too big");
        }
    }

}
