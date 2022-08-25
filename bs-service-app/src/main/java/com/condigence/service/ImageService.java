package com.condigence.service;

import com.condigence.dto.ImageDTO;
import com.condigence.model.Image;
import com.condigence.repository.ImageRepository;
import com.condigence.utils.AppProperties;
import com.condigence.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

	@Autowired
	ImageRepository imageRepository;

	private final Path rootLocation;

	@Autowired
	public ImageService(AppProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	public static final Logger logger = LoggerFactory.getLogger(ImageService.class);

	public Image store(MultipartFile file, String moduleName, String imgPath) throws IOException {
		
		// To store at external/internal directory
		ImageUtil imgutil = new ImageUtil();
		imgutil.createDirectory(imgPath);
		try {
			imgutil.storeImageinDirectory(file,rootLocation);
		} catch (Exception e) {
			throw new RuntimeException("FAIL! -> message = " + e.getMessage());
		}
		// To Store into DB
		Image image = new Image();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		// logger.info("*************file name******************* " + fileName);
		image.setName(fileName);
		image.setType(file.getContentType());
		image.setImagePath(rootLocation.resolve(file.getOriginalFilename()).toString());

		//String cwd = System.getProperty("user.dir");
		

		image.setImageSize(file.getSize());
		image.setImageName(fileName.substring(0, 3) + "_" + imgutil.getDateTimeFormatter());
		image.setPic(file.getBytes());// not storing the byte since byte length is too long
		image.setModuleName(moduleName);

		final Image savedImage = imageRepository.save(image);


		logger.info("***************Image saved*******************");
		return savedImage;
	}

	public Image getImage(String id, String imgpath) {
		//logger.info("In ImageService:::::getImage");
		Image image = null;
		ImageUtil imgutil = new ImageUtil();
		try {
			image = imageRepository.findById(id).get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != image) {
			image.setPic(imgutil.getImageWithFileName(image.getName(), imgpath));
		}
		//logger.info("Image is " + image);
		return image;

	}

	public Image getImageId(String name, String imgpath) {
		// TODO Auto-generated method stub
		//logger.info("In ImageService:::::getImageId");
		ImageUtil imgutil = new ImageUtil();
		Image image = null;
		try {
			image = imageRepository.getByImageName(name).get();

			//logger.info("Image detail is " + image);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != image) {
			image.setPic(imgutil.getImageWithFileName(image.getName(), imgpath));
		}
		return image;
	}


	public ImageDTO getImageById(String imageId) {
		Image image = imageRepository.findById(imageId).get();
		ImageDTO dto = new ImageDTO();
		dto.setId(image.getId());
		dto.setImageId(image.getId());
		dto.setPic(image.getPic());
		return dto;
	}
}
