package com.basicPerformances;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalculatePerformaces
 */
@WebServlet("/CalculatePerformances")
public class CalculatePerformances extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalculatePerformances() {
		super();
	
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		long firstStart = System.nanoTime();
		long taskStart = firstStart;
		String path = getServletContext().getRealPath("/WEB-INF");
		String fileName = "/myOriginalFile.txt";
		Path myFile = Paths.get(path + fileName);

		myFile = create10000LinesFile(myFile, request);
		
		

		request.setAttribute("createFileTime", (double) (System.nanoTime() - taskStart) / 1000000000.0);
		
		taskStart = System.nanoTime();
		for (int i = 0; i < 5; i++) {
			Path copyOfFile = Paths.get(path + "/copyOfFile" + (i + 1) + ".txt");
			try {
				Files.copy(myFile,copyOfFile,StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				request.setAttribute("error", "Erreur en copiant le fichier");

			}
		}
		request.setAttribute("copyFilesTime", (double) (System.nanoTime() - taskStart) / 1000000000.0);
		
		taskStart = System.nanoTime();
		String zipFileName = "/generatedFiles.zip";
		compressGeneratedFiles(path, zipFileName, request);
		request.setAttribute("zipFilesTime", (double) (System.nanoTime() - taskStart) / 1000000000.0);
		
		request.setAttribute("allTime", (double) (System.nanoTime() - firstStart) / 1000000000.0);
		request.setAttribute("filesPath", path);

	    try {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} catch (IOException e) {
			request.setAttribute("error", "Erreur en montrant les résultats");
		}

	  
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public Path create10000LinesFile(Path myFile,HttpServletRequest request) {

		try {
			Files.deleteIfExists(myFile);
			myFile = Files.createFile(myFile);
			int numberOfLines = 10000;
			Random random = new Random();

			BufferedWriter writer;
		
				writer = Files.newBufferedWriter(myFile,
						Charset.defaultCharset());
			

			for (int i = 0; i < numberOfLines; i++) {
				char[] word = new char[random.nextInt(8) + 3]; // words of length 3 through 10.

				for (int j = 0; j < word.length; j++)
					word[j] = (char) ('a' + random.nextInt(26));

				writer.append(new String(word));
				writer.newLine();
			}
			writer.flush();
			
			
		} catch (IOException e) {
			request.setAttribute("error", "Erreur en créant le fichier");
		}

		return myFile;

	}
	
	public void compressGeneratedFiles(String path, String zipFileName, HttpServletRequest request) {
		File generatedFiels = new File(path + zipFileName);
	    ZipOutputStream out;
		try {
			out = new ZipOutputStream(new FileOutputStream(generatedFiels));
			 for (int i = 0; i < 5; i++){
			    	
				ZipEntry ent = new ZipEntry("copyOfFile" + (i + 1) + ".txt");
				out.putNextEntry(ent);
				FileInputStream in = new FileInputStream(path + "/copyOfFile"
						+ (i + 1) + ".txt");
				byte[] buffer = new byte[1024];
				int len;
				while ((len = in.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}

				in.close();
				out.closeEntry();
			    }
			    
			    out.close();
		} catch (FileNotFoundException e) {
			request.setAttribute("error", "Erreur en compressant le fichier");

		}catch (IOException e) {
			request.setAttribute("error", "Erreur en compressant le fichier");

		}
	    
	   
	}

}
