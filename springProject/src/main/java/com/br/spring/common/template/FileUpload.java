package com.br.spring.common.template;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.br.spring.board.model.vo.Board;

public class FileUpload {

	public static String saveFile(MultipartFile upfile, HttpSession session, String folderPath) {	
		//파일명 수정 작업
		String originName = upfile.getOriginalFilename();
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int ranNum = (int)(Math.random()*90000 + 10000);
		String ext = originName.substring(originName.lastIndexOf("."));
		String changeName = currentTime + ranNum + ext;
		
		//업로드할 폴더의 물리적인 경로
		String savePath = session.getServletContext().getRealPath(folderPath);
		
		
		try {
			//upfile객체에 데이터 넣기
			upfile.transferTo(new File(savePath+changeName));
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return folderPath + changeName;
	}
}
